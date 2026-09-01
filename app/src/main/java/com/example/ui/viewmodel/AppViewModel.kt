package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.config.*
import com.example.data.mock.MockDataFixtures
import com.example.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AppDestination(val title: String, val route: String) {
  HOME("Inicio", "home"),
  MENU("Carta", "menu"),
  PEDIR("Pedir", "pedir"),
  CLUB("Club", "club"),
  LOCAL("Local", "local"),
  RESERVAR("Reservar", "reservar"),
  EN_EL_LOCAL("En el Local", "en_el_local"),
  NOVEDADES("Novedades", "novedades"),
  NOVEDAD_DETAIL("Detalle Novedad", "novedad_detail"),
  ORDER_CONFIRMATION("Pedido Enviado", "order_confirmation")
}

data class AppUiState(
  val currentDestination: AppDestination = AppDestination.HOME,
  val previousDestination: AppDestination? = null,
  val selectedCategory: String = "todos",
  val searchQuery: String = "",
  val cart: List<CartItem> = emptyList(),
  val selectedLocation: Location = MockDataFixtures.primaryLocation,
  val currentTableNumber: Int = 7,
  val isAtRestaurant: Boolean = true,
  val clubMember: ClubMember = MockDataFixtures.mockMember,
  val rewards: List<Reward> = MockDataFixtures.clubRewards,
  val missions: List<GamificationMission> = MockDataFixtures.gamificationMissions,
  val selectedNewsItem: NewsItem? = null,
  val confirmedReservations: List<Reservation> = emptyList(),
  val lastAppRedirectModalUrl: String? = null,
  val lastAppRedirectTitle: String? = null,
  val feedbackSnackbarMessage: String? = null,
  val selectedMenuItemForDetail: MenuItem? = null
)

class AppViewModel(
  private val orderingProvider: OrderingProvider = MockOrderingProvider(),
  private val reservationProvider: ReservationProvider = MockReservationProvider(),
  private val loyaltyProvider: LoyaltyProvider = MockLoyaltyProvider()
) : ViewModel() {

  private val _uiState = MutableStateFlow(AppUiState())
  val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

  fun navigateTo(destination: AppDestination) {
    _uiState.update { current ->
      current.copy(
        previousDestination = current.currentDestination,
        currentDestination = destination
      )
    }
  }

  fun navigateBack() {
    _uiState.update { current ->
      val target = current.previousDestination ?: AppDestination.HOME
      current.copy(
        currentDestination = target,
        previousDestination = null
      )
    }
  }

  fun setSelectedCategory(categoryId: String) {
    _uiState.update { it.copy(selectedCategory = categoryId) }
  }

  fun setSearchQuery(query: String) {
    _uiState.update { it.copy(searchQuery = query) }
  }

  fun setTableNumber(tableNumber: Int) {
    _uiState.update { it.copy(currentTableNumber = tableNumber) }
  }

  fun setMenuItemForDetail(item: MenuItem?) {
    _uiState.update { it.copy(selectedMenuItemForDetail = item) }
  }

  fun setSelectedNews(newsItem: NewsItem) {
    _uiState.update {
      it.copy(
        selectedNewsItem = newsItem,
        previousDestination = it.currentDestination,
        currentDestination = AppDestination.NOVEDAD_DETAIL
      )
    }
  }

  // Cart Management
  fun addToCart(menuItem: MenuItem, sauce: String = "Salsa Verde", quantity: Int = 1) {
    _uiState.update { state ->
      val existingIndex = state.cart.indexOfFirst { it.menuItem.id == menuItem.id && it.selectedSauce == sauce }
      val updatedCart = state.cart.toMutableList()
      if (existingIndex >= 0) {
        val current = updatedCart[existingIndex]
        updatedCart[existingIndex] = current.copy(quantity = current.quantity + quantity)
      } else {
        updatedCart.add(CartItem(menuItem = menuItem, quantity = quantity, selectedSauce = sauce))
      }
      state.copy(
        cart = updatedCart,
        feedbackSnackbarMessage = "Añadido: ${menuItem.name}"
      )
    }
  }

  fun removeFromCart(menuItem: MenuItem, sauce: String = "Salsa Verde") {
    _uiState.update { state ->
      val updatedCart = state.cart.toMutableList()
      val index = updatedCart.indexOfFirst { it.menuItem.id == menuItem.id && it.selectedSauce == sauce }
      if (index >= 0) {
        val current = updatedCart[index]
        if (current.quantity > 1) {
          updatedCart[index] = current.copy(quantity = current.quantity - 1)
        } else {
          updatedCart.removeAt(index)
        }
      }
      state.copy(cart = updatedCart)
    }
  }

  fun clearCart() {
    _uiState.update { it.copy(cart = emptyList()) }
  }

  // Last.app bridge / external redirection modal
  fun triggerExternalRedirect(title: String, url: String) {
    _uiState.update {
      it.copy(
        lastAppRedirectTitle = title,
        lastAppRedirectModalUrl = url
      )
    }
  }

  fun dismissRedirectModal() {
    _uiState.update {
      it.copy(
        lastAppRedirectTitle = null,
        lastAppRedirectModalUrl = null
      )
    }
  }

  fun dismissSnackbar() {
    _uiState.update { it.copy(feedbackSnackbarMessage = null) }
  }

  // Reservation Flow
  fun submitReservation(
    date: String,
    time: String,
    guests: Int,
    name: String,
    phone: String,
    email: String,
    notes: String
  ) {
    viewModelScope.launch {
      val result = reservationProvider.createReservation(
        date = date,
        time = time,
        guests = guests,
        name = name,
        phone = phone,
        email = email,
        notes = notes
      )
      if (result.isSuccess) {
        val newReservation = Reservation(
          id = result.getOrNull() ?: "RES-1",
          locationId = _uiState.value.selectedLocation.id,
          date = date,
          time = time,
          guests = guests,
          guestName = name,
          guestPhone = phone,
          guestEmail = email,
          notes = notes
        )
        _uiState.update {
          it.copy(
            confirmedReservations = it.confirmedReservations + newReservation,
            feedbackSnackbarMessage = "¡Mesa reservada con éxito para $guests personas!"
          )
        }
      }
    }
  }

  // Club Taquero Actions
  fun redeemClubReward(reward: Reward) {
    val currentPoints = _uiState.value.clubMember.points
    if (currentPoints >= reward.pointsCost) {
      _uiState.update { state ->
        val updatedMember = state.clubMember.copy(
          points = currentPoints - reward.pointsCost,
          freeTacosRedeemed = state.clubMember.freeTacosRedeemed + 1
        )
        state.copy(
          clubMember = updatedMember,
          feedbackSnackbarMessage = "¡Canjeaste '${reward.title}'! Muestra el cupón ${reward.code} al camarero."
        )
      }
    } else {
      _uiState.update {
        it.copy(feedbackSnackbarMessage = "Te faltan ${reward.pointsCost - currentPoints} puntos para este premio.")
      }
    }
  }

  fun completeGoogleReviewMission() {
    viewModelScope.launch {
      val pointsEarned = loyaltyProvider.claimReviewReward(_uiState.value.clubMember.id).getOrDefault(80)
      _uiState.update { state ->
        val updatedMissions = state.missions.map {
          if (it.actionType == "REVIEW") it.copy(isCompleted = true) else it
        }
        val updatedMember = state.clubMember.copy(
          points = state.clubMember.points + pointsEarned
        )
        state.copy(
          missions = updatedMissions,
          clubMember = updatedMember,
          feedbackSnackbarMessage = "¡+$pointsEarned puntos por tu reseña! Y tu taquito gratis te espera en barra."
        )
      }
    }
  }
}
