package com.example.data.model

data class MenuItem(
  val id: String,
  val name: String,
  val description: String,
  val price: Double,
  val categoryId: String,
  val imageUrl: String = "",
  val tags: List<String> = emptyList(), // e.g. "Favorito", "Nuevo", "Vegetariano", "Sin Gluten"
  val spicyLevel: Int = 0, // 0 = sin picante, 1 = suave, 2 = medio, 3 = fuego
  val isFavorite: Boolean = false,
  val portions: String = "Orden de 2 tacos"
)

data class Category(
  val id: String,
  val name: String,
  val iconName: String,
  val subtitle: String = ""
)

data class Location(
  val id: String,
  val slug: String,
  val name: String,
  val neighborhood: String,
  val address: String,
  val city: String = "Madrid",
  val postalCode: String = "28020",
  val metro: String,
  val phone: String,
  val schedule: String,
  val googleRating: Double,
  val googleReviewCount: Int,
  val mapsUrl: String,
  val orderUrl: String,
  val reservationUrl: String,
  val qrUrl: String,
  val isPrimary: Boolean = true,
  val active: Boolean = true
)

data class Reservation(
  val id: String,
  val locationId: String,
  val date: String,
  val time: String,
  val guests: Int,
  val guestName: String,
  val guestPhone: String,
  val guestEmail: String,
  val notes: String = "",
  val status: String = "CONFIRMED"
)

data class ReservationRequest(
  val guests: Int,
  val date: String,
  val time: String,
  val customerName: String,
  val customerPhone: String,
  val customerEmail: String = "",
  val specialRequests: String = "",
  val zone: String = "Mesa en Mercado"
)

enum class MemberTier(val title: String, val badgeColorHex: Long) {
  TAQUERO_NOVATO("Taquero Novato", 0xFF9E9E9E),
  TAQUERO_AFICIONADO("Taquero Aficionado", 0xFFFFA000),
  TAQUERO_MAESTRO("Taquero Maestro", 0xFFE53935),
  TAQUERO_LEYENDA("Taquero Leyenda", 0xFFFFD700)
}

data class ClubMember(
  val id: String,
  val name: String,
  val email: String,
  val phone: String,
  val points: Int,
  val tier: MemberTier,
  val nextTierPoints: Int,
  val nextRewardThreshold: Int,
  val memberSince: String,
  val visitsCount: Int,
  val freeTacosRedeemed: Int
)

data class Reward(
  val id: String,
  val title: String,
  val description: String,
  val pointsCost: Int,
  val icon: String,
  val category: String,
  val isAvailable: Boolean = true,
  val code: String = ""
)

data class GamificationMission(
  val id: String,
  val title: String,
  val description: String,
  val pointsReward: Int,
  val icon: String,
  val isCompleted: Boolean = false,
  val actionType: String // REVIEW, VISIT, TRY_TACO, INVITE
)

data class Review(
  val id: String,
  val authorName: String,
  val rating: Int,
  val text: String,
  val relativeTime: String,
  val isGoogleReview: Boolean = true,
  val avatarInitial: String = ""
)

data class NewsItem(
  val id: String,
  val slug: String,
  val title: String,
  val excerpt: String,
  val body: String,
  val imageUrl: String = "",
  val date: String,
  val tag: String,
  val isPromo: Boolean = false,
  val promoCode: String = ""
)

data class CartItem(
  val menuItem: MenuItem,
  var quantity: Int = 1,
  var selectedSauce: String = "Salsa Verde Suave",
  var notes: String = ""
) {
  val subtotal: Double
    get() = menuItem.price * quantity
}
