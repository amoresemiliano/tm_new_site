package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination

@Composable
fun CriolloTopBar(
  currentDestination: AppDestination,
  locationName: String,
  tableNumber: Int,
  cartCount: Int,
  points: Int,
  onNavigate: (AppDestination) -> Unit,
  onOpenCart: () -> Unit
) {
  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("top_bar_header"),
    color = TaqueriaBlack,
    shadowElevation = 4.dp
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        // Brand Title & Tagline
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clickable { onNavigate(AppDestination.HOME) }
            .testTag("brand_logo_click")
        ) {
          // Mascot / Rooster Badge
          Box(
            modifier = Modifier
              .size(38.dp)
              .clip(CircleShape)
              .background(
                Brush.linearGradient(
                  colors = listOf(CriolloRed, CriolloRedDark)
                )
              ),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "🌮",
              fontSize = 20.sp
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "EL CRIOLLO",
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Black,
                  letterSpacing = 1.sp,
                  color = Color.White
                )
              )
              Spacer(modifier = Modifier.width(6.dp))
              // Location micro tag
              Surface(
                color = CriolloRedDark,
                shape = RoundedCornerShape(4.dp)
              ) {
                Text(
                  text = "TETUÁN",
                  color = CriolloYellow,
                  style = MaterialTheme.typography.labelSmall,
                  modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                  fontWeight = FontWeight.Bold
                )
              }
            }
            Text(
              text = "Sazón & Redención · Madrid",
              style = MaterialTheme.typography.bodySmall.copy(
                color = CriolloYellowLight,
                fontSize = 10.5.sp
              )
            )
          }
        }

        // Action Buttons: Club Points & Cart / Table
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Points Pill
          Surface(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .clickable { onNavigate(AppDestination.CLUB) }
              .testTag("top_bar_points_pill"),
            color = Color(0xFF261D12),
            border = androidx.compose.foundation.BorderStroke(1.dp, CriolloYellow.copy(alpha = 0.6f)),
            shape = RoundedCornerShape(20.dp)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("⭐", fontSize = 12.sp)
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "$points pts",
                color = CriolloYellow,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
              )
            }
          }

          // Cart / Bag Button
          IconButton(
            onClick = onOpenCart,
            modifier = Modifier
              .size(40.dp)
              .clip(CircleShape)
              .background(if (cartCount > 0) CriolloRed else TaqueriaDarkSurfaceElevated)
              .testTag("top_bar_cart_button")
          ) {
            BadgedBox(
              badge = {
                if (cartCount > 0) {
                  Badge(
                    containerColor = CriolloYellow,
                    contentColor = TaqueriaBlack
                  ) {
                    Text(
                      text = cartCount.toString(),
                      fontWeight = FontWeight.Bold,
                      fontSize = 10.sp
                    )
                  }
                }
              }
            ) {
              Icon(
                imageVector = Icons.Default.ShoppingBag,
                contentDescription = "Carrito de tacos",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
              )
            }
          }
        }
      }

      // Quick Subheader if in table mode
      if (currentDestination == AppDestination.EN_EL_LOCAL) {
        Surface(
          color = Color(0xFF1B5E20),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text("📍", fontSize = 12.sp)
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Pedido en Mesa $tableNumber · Mercado Maravillas",
                color = Color.White,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
              )
            }
            Text(
              text = "SERVICIO ACTIVO",
              color = CriolloYellow,
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Black
            )
          }
        }
      }
    }
  }
}
