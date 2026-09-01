package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.config.IntegrationsConfig
import com.example.ui.components.*
import com.example.ui.screens.club.ClubScreen
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.local.EnElLocalScreen
import com.example.ui.screens.local.LocalScreen
import com.example.ui.screens.menu.MenuScreen
import com.example.ui.screens.novedades.NovedadesScreen
import com.example.ui.screens.pedir.PedirScreen
import com.example.ui.screens.reservar.ReservarScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        CriolloApp()
      }
    }
  }
}

@Composable
fun CriolloApp(viewModel: AppViewModel = viewModel()) {
  val uiState by viewModel.uiState.collectAsState()
  val context = LocalContext.current
  val snackbarHostState = remember { SnackbarHostState() }

  // Handle back press
  BackHandler(enabled = uiState.currentDestination != AppDestination.HOME) {
    viewModel.navigateBack()
  }

  // Handle feedback snackbar messages
  LaunchedEffect(uiState.feedbackSnackbarMessage) {
    uiState.feedbackSnackbarMessage?.let { message ->
      snackbarHostState.showSnackbar(
        message = message,
        duration = SnackbarDuration.Short
      )
      viewModel.dismissSnackbar()
    }
  }

  // Helper to open browser or telephone links
  val openExternal: (String, String) -> Unit = { title, url ->
    try {
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
      context.startActivity(intent)
    } catch (_: Exception) {
      viewModel.triggerExternalRedirect(title, url)
    }
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      CriolloTopBar(
        currentDestination = uiState.currentDestination,
        locationName = uiState.selectedLocation.name,
        tableNumber = uiState.currentTableNumber,
        cartCount = uiState.cart.sumOf { it.quantity },
        points = uiState.clubMember.points,
        onNavigate = { viewModel.navigateTo(it) },
        onOpenCart = { viewModel.navigateTo(AppDestination.PEDIR) }
      )
    },
    bottomBar = {
      CriolloBottomNav(
        currentDestination = uiState.currentDestination,
        onNavigate = { viewModel.navigateTo(it) }
      )
    },
    snackbarHost = {
      SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(bottom = 70.dp)
      )
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      AnimatedContent(
        targetState = uiState.currentDestination,
        transitionSpec = {
          fadeIn() togetherWith fadeOut()
        },
        label = "destination_transition"
      ) { destination ->
        when (destination) {
          AppDestination.HOME -> {
            HomeScreen(
              uiState = uiState,
              onNavigate = { viewModel.navigateTo(it) },
              onQuickAdd = { item -> viewModel.addToCart(item) },
              onItemClick = { item -> viewModel.setMenuItemForDetail(item) },
              onWriteReview = {
                viewModel.completeGoogleReviewMission()
                openExternal("Reseña Google Maps", IntegrationsConfig.GOOGLE_WRITE_REVIEW_URL)
              },
              onOpenExternalUrl = openExternal
            )
          }

          AppDestination.MENU -> {
            MenuScreen(
              uiState = uiState,
              onCategorySelected = { viewModel.setSelectedCategory(it) },
              onSearchQueryChanged = { viewModel.setSearchQuery(it) },
              onQuickAdd = { item -> viewModel.addToCart(item) },
              onItemClick = { item -> viewModel.setMenuItemForDetail(item) },
              onNavigateToPedir = { viewModel.navigateTo(AppDestination.PEDIR) }
            )
          }

          AppDestination.PEDIR, AppDestination.ORDER_CONFIRMATION -> {
            PedirScreen(
              uiState = uiState,
              onAddToCart = { item, sauce, qty -> viewModel.addToCart(item, sauce, qty) },
              onRemoveFromCart = { item, sauce -> viewModel.removeFromCart(item, sauce) },
              onClearCart = { viewModel.clearCart() },
              onNavigateToMenu = { viewModel.navigateTo(AppDestination.MENU) },
              onProceedCheckout = { url -> openExternal("Last.app Pedido", url) }
            )
          }

          AppDestination.CLUB -> {
            ClubScreen(
              uiState = uiState,
              onRedeemReward = { reward -> viewModel.redeemClubReward(reward) },
              onCompleteGoogleReview = { viewModel.completeGoogleReviewMission() },
              onOpenExternalUrl = openExternal
            )
          }

          AppDestination.LOCAL -> {
            LocalScreen(
              onOpenExternalUrl = openExternal,
              onNavigateToReservas = { viewModel.navigateTo(AppDestination.RESERVAR) },
              onNavigateToEnElLocal = { viewModel.navigateTo(AppDestination.EN_EL_LOCAL) }
            )
          }

          AppDestination.RESERVAR -> {
            ReservarScreen(
              uiState = uiState,
              onSubmitReservation = { req ->
                viewModel.submitReservation(
                  date = req.date,
                  time = req.time,
                  guests = req.guests,
                  name = req.customerName,
                  phone = req.customerPhone,
                  email = req.customerEmail,
                  notes = req.specialRequests
                )
              },
              onOpenExternalUrl = openExternal,
              onReturnHome = { viewModel.navigateTo(AppDestination.HOME) }
            )
          }

          AppDestination.EN_EL_LOCAL -> {
            EnElLocalScreen(
              onNavigateToMenu = { viewModel.navigateTo(AppDestination.MENU) },
              onOpenExternalUrl = openExternal
            )
          }

          AppDestination.NOVEDADES, AppDestination.NOVEDAD_DETAIL -> {
            NovedadesScreen(
              onOpenExternalUrl = openExternal,
              onClaimReviewPromo = {
                viewModel.completeGoogleReviewMission()
                openExternal("Reseña Google Maps", IntegrationsConfig.GOOGLE_WRITE_REVIEW_URL)
              }
            )
          }
        }
      }

      // Dish Detail Bottom Sheet Modal
      if (uiState.selectedMenuItemForDetail != null) {
        DishDetailBottomSheet(
          item = uiState.selectedMenuItemForDetail,
          onDismiss = { viewModel.setMenuItemForDetail(null) },
          onAddToCart = { item, sauce, qty ->
            viewModel.addToCart(item, sauce, qty)
            viewModel.setMenuItemForDetail(null)
          }
        )
      }

      // Last.app / External Redirect Dialog
      if (uiState.lastAppRedirectModalUrl != null) {
        LastAppRedirectDialog(
          title = uiState.lastAppRedirectTitle,
          url = uiState.lastAppRedirectModalUrl,
          onDismiss = { viewModel.dismissRedirectModal() },
          onProceed = { url ->
            viewModel.dismissRedirectModal()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
          }
        )
      }
    }
  }
}
