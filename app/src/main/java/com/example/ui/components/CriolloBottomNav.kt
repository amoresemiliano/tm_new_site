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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination

@Composable
fun CriolloBottomNav(
  currentDestination: AppDestination,
  onNavigate: (AppDestination) -> Unit
) {
  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("bottom_navigation_bar"),
    color = TaqueriaBlack,
    shadowElevation = 16.dp,
    border = androidx.compose.foundation.BorderStroke(1.dp, GrayBorderDark)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .navigationBarsPadding()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // INICIO
      BottomNavItem(
        label = "Inicio",
        icon = if (currentDestination == AppDestination.HOME) Icons.Filled.Home else Icons.Outlined.Home,
        isSelected = currentDestination == AppDestination.HOME,
        testTag = "nav_tab_inicio",
        onClick = { onNavigate(AppDestination.HOME) }
      )

      // CARTA
      BottomNavItem(
        label = "Carta",
        icon = if (currentDestination == AppDestination.MENU) Icons.Filled.RestaurantMenu else Icons.Outlined.RestaurantMenu,
        isSelected = currentDestination == AppDestination.MENU,
        testTag = "nav_tab_carta",
        onClick = { onNavigate(AppDestination.MENU) }
      )

      // PEDIR (Prominent High-Contrast Center Button)
      Box(
        modifier = Modifier
          .offset(y = (-6).dp)
          .shadow(8.dp, RoundedCornerShape(24.dp))
          .clip(RoundedCornerShape(24.dp))
          .background(
            Brush.verticalGradient(
              colors = listOf(CriolloRedLight, CriolloRed, CriolloRedDark)
            )
          )
          .clickable { onNavigate(AppDestination.PEDIR) }
          .padding(horizontal = 18.dp, vertical = 10.dp)
          .testTag("nav_tab_pedir_hero"),
        contentAlignment = Alignment.Center
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Filled.DeliveryDining,
            contentDescription = "Pedir tacos",
            tint = Color.White,
            modifier = Modifier.size(22.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "PEDIR",
            color = Color.White,
            style = MaterialTheme.typography.labelLarge.copy(
              fontWeight = FontWeight.Black,
              letterSpacing = 1.sp
            )
          )
        }
      }

      // CLUB TAQUERO
      BottomNavItem(
        label = "Club",
        icon = if (currentDestination == AppDestination.CLUB) Icons.Filled.Loyalty else Icons.Outlined.Loyalty,
        isSelected = currentDestination == AppDestination.CLUB,
        testTag = "nav_tab_club",
        onClick = { onNavigate(AppDestination.CLUB) }
      )

      // LOCAL / RESERVAS
      BottomNavItem(
        label = "Local",
        icon = if (currentDestination == AppDestination.LOCAL || currentDestination == AppDestination.RESERVAR) Icons.Filled.Place else Icons.Outlined.Place,
        isSelected = currentDestination == AppDestination.LOCAL || currentDestination == AppDestination.RESERVAR,
        testTag = "nav_tab_local",
        onClick = { onNavigate(AppDestination.LOCAL) }
      )
    }
  }
}

@Composable
private fun BottomNavItem(
  label: String,
  icon: ImageVector,
  isSelected: Boolean,
  testTag: String,
  onClick: () -> Unit
) {
  Column(
    modifier = Modifier
      .clip(RoundedCornerShape(12.dp))
      .clickable(onClick = onClick)
      .padding(horizontal = 12.dp, vertical = 6.dp)
      .testTag(testTag),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Icon(
      imageVector = icon,
      contentDescription = label,
      tint = if (isSelected) CriolloYellow else TextSecondaryDark,
      modifier = Modifier.size(24.dp)
    )
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = label,
      color = if (isSelected) CriolloYellow else TextSecondaryDark,
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        fontSize = 11.sp
      )
    )
  }
}
