package com.example.data.config

/**
 * Centralized configuration for all external and operational integrations.
 * Never hardcode external URLs across individual UI components.
 */
object IntegrationsConfig {
  const val BRAND_NAME = "El Criollo"
  const val SLOGAN = "Sazón y Redención"
  const val LOCATION_CITY = "Madrid, Tetuán"

  // Primary Restaurant Contact
  const val PHONE_NUMBER = "+34 607 74 03 58"
  const val PHONE_FORMATTED = "+34 607 74 03 58"
  const val WHATSAPP_URL = "https://wa.me/34607740358?text=Hola%20El%20Criollo,%20quisiera%20informaci%C3%B3n%20sobre%20taquizas%20y%20pedidos"
  const val EMAIL_CONTACT = "hola@el-criollo.com"

  // Google Maps & Reviews
  const val GOOGLE_MAPS_URL = "https://maps.google.com/?q=El+Criollo+Taqueria+Mercado+Maravillas+Madrid"
  const val GOOGLE_REVIEWS_URL = "https://search.google.com/local/reviews?placeid=ChIJElCriolloMadrid"
  const val GOOGLE_WRITE_REVIEW_URL = "https://search.google.com/local/writereview?placeid=ChIJElCriolloMadrid"
  const val GOOGLE_RATING = 4.9
  const val GOOGLE_REVIEWS_COUNT = 808

  // Last.app Integration Bridges (Configurable)
  const val LAST_APP_BASE_URL = "https://last.app"
  const val LAST_SHOP_URL = "https://order.last.app/el-criollo"
  const val LAST_RESERVATION_URL = "https://book.last.app/el-criollo"
  const val LAST_QR_URL = "https://qr.last.app/el-criollo"
  const val LAST_LOYALTY_URL = "https://www.el-criollo.com/es/clubtaquero/"

  // Social Channels
  const val INSTAGRAM_URL = "https://www.instagram.com/elcriollotaqueria/"
  const val TIKTOK_URL = "https://www.tiktok.com/@elcriollomadrid"

  // API Backend Base URL (e.g., Bluehost PHP API endpoint or local)
  const val API_BASE_URL = "https://api.el-criollo.com/api/v1"
}

// -------------------------------------------------------------
// Provider Interfaces & Mock Implementations (Adapter Pattern)
// -------------------------------------------------------------

interface OrderingProvider {
  suspend fun getOrderUrl(locationId: String, orderType: OrderType): String
  suspend fun isOrderingOpen(locationId: String): Boolean
}

enum class OrderType {
  DELIVERY,
  PICKUP,
  DINE_IN_TABLE
}

class MockOrderingProvider : OrderingProvider {
  override suspend fun getOrderUrl(locationId: String, orderType: OrderType): String {
    return when (orderType) {
      OrderType.DELIVERY -> "${IntegrationsConfig.LAST_SHOP_URL}?type=delivery&loc=$locationId"
      OrderType.PICKUP -> "${IntegrationsConfig.LAST_SHOP_URL}?type=pickup&loc=$locationId"
      OrderType.DINE_IN_TABLE -> "${IntegrationsConfig.LAST_QR_URL}?loc=$locationId"
    }
  }

  override suspend fun isOrderingOpen(locationId: String): Boolean = true
}

interface ReservationProvider {
  suspend fun checkAvailability(date: String, guests: Int): List<String>
  suspend fun createReservation(
    date: String,
    time: String,
    guests: Int,
    name: String,
    phone: String,
    email: String,
    notes: String
  ): Result<String>
}

class MockReservationProvider : ReservationProvider {
  override suspend fun checkAvailability(date: String, guests: Int): List<String> {
    return listOf("13:30", "14:00", "14:30", "15:00", "20:30", "21:00", "21:30", "22:00")
  }

  override suspend fun createReservation(
    date: String,
    time: String,
    guests: Int,
    name: String,
    phone: String,
    email: String,
    notes: String
  ): Result<String> {
    // Generates simulated reservation ID
    val id = "RES-CRIOLLO-${(1000..9999).random()}"
    return Result.success(id)
  }
}

interface LoyaltyProvider {
  suspend fun getMemberProfile(memberId: String): String
  suspend fun redeemReward(memberId: String, rewardId: String): Result<String>
  suspend fun claimReviewReward(memberId: String): Result<Int>
}

class MockLoyaltyProvider : LoyaltyProvider {
  override suspend fun getMemberProfile(memberId: String): String = "Diego Alonso"
  override suspend fun redeemReward(memberId: String, rewardId: String): Result<String> {
    return Result.success("CRIOLLO-REWARD-${(100..999).random()}")
  }
  override suspend fun claimReviewReward(memberId: String): Result<Int> {
    return Result.success(80) // 80 bonus points for Google review
  }
}
