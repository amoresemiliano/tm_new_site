package com.example.ui.screens.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.config.IntegrationsConfig
import com.example.data.mock.MockDataFixtures
import com.example.data.model.MenuItem
import com.example.ui.components.DishCard
import com.example.ui.components.FreeTacoPromoBanner
import com.example.ui.components.GoogleReviewsSection
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.AppUiState

@Composable
fun HomeScreen(
  uiState: AppUiState,
  onNavigate: (AppDestination) -> Unit,
  onQuickAdd: (MenuItem) -> Unit,
  onItemClick: (MenuItem) -> Unit,
  onWriteReview: () -> Unit,
  onOpenExternalUrl: (String, String) -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("home_screen_container"),
    contentPadding = PaddingValues(bottom = 100.dp)
  ) {
    // 1. HERO BANNER - El Criollo Tetuán
    item {
      HeroHeaderSection(
        onPedirClick = { onNavigate(AppDestination.PEDIR) },
        onReservarClick = { onNavigate(AppDestination.RESERVAR) },
        onPhoneClick = {
          val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${IntegrationsConfig.PHONE_NUMBER}"))
          context.startActivity(intent)
        }
      )
    }

    // 2. PRIMARY 4-GRID QUICK ACTIONS (Pedir, Reservar, En el Local, Club)
    item {
      QuickActionsGrid(
        onNavigate = onNavigate,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
      )
    }

    // 3. CAMPAÑA ESTRELLA: "¡1 TAQUITO GRATIS CON TU RESEÑA!"
    item {
      FreeTacoPromoBanner(
        onClaimClick = {
          onNavigate(AppDestination.NOVEDADES)
        },
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
      )
    }

    // 4. CLUB TAQUERO MINI-DASHBOARD / STATUS
    item {
      ClubMiniPreviewCard(
        member = uiState.clubMember,
        onClubClick = { onNavigate(AppDestination.CLUB) },
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
      )
    }

    // 5. PLATOS ESTRELLA / FAVORITOS
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 10.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Los Favoritos del Comal",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onBackground
              )
            )
            Text(
              text = "Carnitas, birria estofada y costras asadas",
              style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }

          TextButton(
            onClick = { onNavigate(AppDestination.MENU) },
            modifier = Modifier.testTag("btn_see_full_menu")
          ) {
            Text(
              text = "Ver Carta →",
              color = CriolloRed,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Horizontal items
        LazyRow(
          contentPadding = PaddingValues(horizontal = 16.dp),
          horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          val favorites = MockDataFixtures.menuItems.filter { it.isFavorite }
          items(favorites) { item ->
            DishCard(
              item = item,
              onItemClick = onItemClick,
              onQuickAdd = onQuickAdd,
              modifier = Modifier.width(260.dp)
            )
          }
        }
      }
    }

    // 6. GOOGLE REVIEWS SECTION (808+ Opiniones, 4.9 Estrellas)
    item {
      GoogleReviewsSection(
        reviews = MockDataFixtures.sampleReviews,
        onWriteReviewClick = onWriteReview,
        onViewAllClick = {
          onOpenExternalUrl("Reseñas de Google Maps", IntegrationsConfig.GOOGLE_REVIEWS_URL)
        },
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
      )
    }

    // 7. UBICACIÓN & CONTACTO RÁPIDO MERCADO MARAVILLAS
    item {
      LocationQuickCard(
        location = uiState.selectedLocation,
        onOpenMaps = {
          onOpenExternalUrl("Google Maps El Criollo", IntegrationsConfig.GOOGLE_MAPS_URL)
        },
        onNavigateLocation = { onNavigate(AppDestination.LOCAL) },
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
      )
    }

    // 8. SOCIAL COMMUNITY BANNER (Instagram & TikTok)
    item {
      SocialCommunityCard(
        onInstagramClick = {
          onOpenExternalUrl("Instagram @elcriollotaqueria", IntegrationsConfig.INSTAGRAM_URL)
        },
        onTikTokClick = {
          onOpenExternalUrl("TikTok @elcriollomadrid", IntegrationsConfig.TIKTOK_URL)
        },
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
      )
    }
  }
}

// -------------------------------------------------------------
// Subcomponents for HomeScreen
// -------------------------------------------------------------

@Composable
fun HeroHeaderSection(
  onPedirClick: () -> Unit,
  onReservarClick: () -> Unit,
  onPhoneClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(
        Brush.verticalGradient(
          colors = listOf(TaqueriaBlack, Color(0xFF1E1412), TortillaCream)
        )
      )
      .padding(16.dp)
  ) {
    Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Subtitle Tag
      Surface(
        color = CriolloRedDark,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(bottom = 8.dp)
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("🌮", fontSize = 12.sp)
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "EL SABOR DE MÉXICO EN MADRID",
            color = CriolloYellow,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
          )
        }
      }

      // Punchy Question Headline
      Text(
        text = "¿SE TE ANTOJAN\nUNOS TACOS?",
        style = MaterialTheme.typography.displayMedium.copy(
          fontWeight = FontWeight.Black,
          color = Color.White,
          letterSpacing = 1.sp,
          lineHeight = 34.sp
        ),
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "Tortilla artesanal de maíz, birria con consomé, carnitas jugosas y salsas caseras al momento.",
        style = MaterialTheme.typography.bodyMedium.copy(
          color = TextSecondaryDark,
          textAlign = TextAlign.Center,
          lineHeight = 18.sp
        ),
        modifier = Modifier.padding(horizontal = 16.dp)
      )

      Spacer(modifier = Modifier.height(14.dp))

      // Taquizas direct badge
      Surface(
        color = Color(0xFF261D1D),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CriolloRed.copy(alpha = 0.5f)),
        modifier = Modifier
          .clickable(onClick = onPhoneClick)
          .testTag("btn_info_taquizas")
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Phone,
            contentDescription = null,
            tint = CriolloYellow,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Info Taquizas & Eventos: ${IntegrationsConfig.PHONE_NUMBER}",
            color = Color.White,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.SemiBold
            )
          )
        }
      }
    }
  }
}

@Composable
fun QuickActionsGrid(
  onNavigate: (AppDestination) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // 1. PEDIR COMIDA (Dominant Primary Red Card)
      Card(
        modifier = Modifier
          .weight(1f)
          .height(105.dp)
          .clip(RoundedCornerShape(18.dp))
          .clickable { onNavigate(AppDestination.PEDIR) }
          .testTag("quick_action_pedir"),
        colors = CardDefaults.cardColors(containerColor = CriolloRed),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
          verticalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
          ) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.DeliveryDining,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
              )
            }
            Text(
              text = "35-45 min",
              color = CriolloYellow,
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold
            )
          }
          Column {
            Text(
              text = "PEDIR TACOS",
              color = Color.White,
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
            )
            Text(
              text = "A domicilio / Recoger",
              color = Color.White.copy(alpha = 0.85f),
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp)
            )
          }
        }
      }

      // 2. RESERVAR MESA
      Card(
        modifier = Modifier
          .weight(1f)
          .height(105.dp)
          .clip(RoundedCornerShape(18.dp))
          .clickable { onNavigate(AppDestination.RESERVAR) }
          .testTag("quick_action_reservar"),
        colors = CardDefaults.cardColors(containerColor = TaqueriaBlack),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CriolloYellow.copy(alpha = 0.4f))
      ) {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
          verticalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
          ) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(CriolloYellow.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.EventSeat,
                contentDescription = null,
                tint = CriolloYellow,
                modifier = Modifier.size(18.dp)
              )
            }
            Text(
              text = "Hoy",
              color = SalsaGreenLight,
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold
            )
          }
          Column {
            Text(
              text = "RESERVAR",
              color = Color.White,
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
            )
            Text(
              text = "Tu mesa en segundos",
              color = TextSecondaryDark,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp)
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // 3. ESTOY EN EL LOCAL (Mesa & QR)
      Card(
        modifier = Modifier
          .weight(1f)
          .height(80.dp)
          .clip(RoundedCornerShape(16.dp))
          .clickable { onNavigate(AppDestination.EN_EL_LOCAL) }
          .testTag("quick_action_en_el_local"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
      ) {
        Row(
          modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(38.dp)
              .clip(CircleShape)
              .background(SalsaGreen.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.QrCodeScanner,
              contentDescription = null,
              tint = SalsaGreen,
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "Estoy en el Local",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "Pedir en mesa / QR",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
        }
      }

      // 4. CLUB TAQUERO
      Card(
        modifier = Modifier
          .weight(1f)
          .height(80.dp)
          .clip(RoundedCornerShape(16.dp))
          .clickable { onNavigate(AppDestination.CLUB) }
          .testTag("quick_action_club"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, CriolloYellow.copy(alpha = 0.6f))
      ) {
        Row(
          modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(38.dp)
              .clip(CircleShape)
              .background(CriolloYellow.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Loyalty,
              contentDescription = null,
              tint = CriolloOrange,
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "Club Taquero",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "Puntos y Premios",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
        }
      }
    }
  }
}

@Composable
fun ClubMiniPreviewCard(
  member: com.example.data.model.ClubMember,
  onClubClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(18.dp))
      .clickable(onClick = onClubClick)
      .testTag("club_mini_preview_card"),
    colors = CardDefaults.cardColors(
      containerColor = TaqueriaDarkSurface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, CriolloYellow.copy(alpha = 0.3f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(CriolloYellow.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Text(text = "👑", fontSize = 18.sp)
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "Hola, ${member.name.split(" ").first()}",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
            )
            Text(
              text = member.tier.title,
              style = MaterialTheme.typography.labelSmall.copy(
                color = CriolloYellow,
                fontWeight = FontWeight.Bold
              )
            )
          }
        }

        Column(horizontalAlignment = Alignment.End) {
          Text(
            text = "${member.points} PTS",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Black,
              color = CriolloYellow
            )
          )
          Text(
            text = "Saldo disponible",
            style = MaterialTheme.typography.bodySmall.copy(
              color = TextSecondaryDark,
              fontSize = 10.sp
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Progress bar towards next reward
      val progress = (member.points % 500) / 500f
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "Te faltan 220 pts para tu próximo Taco Gratis",
            style = MaterialTheme.typography.bodySmall.copy(
              color = Color.White.copy(alpha = 0.85f),
              fontSize = 11.5.sp
            )
          )
          Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.labelSmall.copy(
              color = CriolloYellow,
              fontWeight = FontWeight.Bold
            )
          )
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
          progress = { progress },
          modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp)),
          color = CriolloYellow,
          trackColor = Color(0xFF333333)
        )
      }
    }
  }
}

@Composable
fun LocationQuickCard(
  location: com.example.data.model.Location,
  onOpenMaps: () -> Unit,
  onNavigateLocation: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(18.dp))
      .clickable(onClick = onNavigateLocation)
      .testTag("location_quick_card"),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = location.name,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = location.address,
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }
        Surface(
          color = SalsaGreen.copy(alpha = 0.12f),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text(
            text = "ABIERTO",
            color = SalsaGreen,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        Icon(
          imageVector = Icons.Default.DirectionsSubway,
          contentDescription = null,
          tint = CriolloRed,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = location.metro,
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurface
          )
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      Button(
        onClick = onOpenMaps,
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant,
          contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(38.dp)
          .testTag("btn_open_google_maps_home")
      ) {
        Icon(imageVector = Icons.Default.Map, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("Cómo llegar (Google Maps)", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
      }
    }
  }
}

@Composable
fun SocialCommunityCard(
  onInstagramClick: () -> Unit,
  onTikTokClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(18.dp)),
    colors = CardDefaults.cardColors(containerColor = TaqueriaBlack),
    border = androidx.compose.foundation.BorderStroke(1.dp, GrayBorderDark)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "EL CRIOLLO ESTÁ VIVO",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Black,
          letterSpacing = 1.sp,
          color = Color.White
        )
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "Sigue nuestro día a día, taquizas en directo y promociones relámpago",
        style = MaterialTheme.typography.bodySmall.copy(
          color = TextSecondaryDark,
          textAlign = TextAlign.Center
        )
      )

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Button(
          onClick = onInstagramClick,
          colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFC13584),
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .weight(1f)
            .height(40.dp)
            .testTag("btn_instagram_follow")
        ) {
          Text("Instagram", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }

        Button(
          onClick = onTikTokClick,
          colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF000000),
            contentColor = Color.White
          ),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF00F2FE)),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .weight(1f)
            .height(40.dp)
            .testTag("btn_tiktok_follow")
        ) {
          Text("TikTok", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
      }
    }
  }
}
