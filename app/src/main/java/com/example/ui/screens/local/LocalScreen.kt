package com.example.ui.screens.local

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.data.config.IntegrationsConfig
import com.example.data.mock.MockDataFixtures
import com.example.ui.theme.*

@Composable
fun LocalScreen(
  onOpenExternalUrl: (String, String) -> Unit,
  onNavigateToReservas: () -> Unit,
  onNavigateToEnElLocal: () -> Unit,
  modifier: Modifier = Modifier
) {
  val restaurant = MockDataFixtures.primaryLocation

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("local_screen_container"),
    contentPadding = PaddingValues(bottom = 100.dp)
  ) {
    // 1. Hero Market Banner
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = TaqueriaBlack)
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .background(
              Brush.verticalGradient(
                colors = listOf(TaqueriaBlack, Color(0xFF2B1612), CriolloRedDark)
              )
            )
            .padding(20.dp)
        ) {
          Column {
            Surface(
              color = CriolloYellow,
              shape = RoundedCornerShape(8.dp)
            ) {
              Text(
                text = "MERCADO MARAVILLAS · TETUÁN",
                color = TaqueriaBlack,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = "El Criollo Taquería",
              style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Black,
                color = Color.White
              )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = "Auténticos tacos mexicanos al comal y tortillas recién hechas en el corazón del barrio de Tetuán.",
              style = MaterialTheme.typography.bodyMedium.copy(
                color = TextSecondaryDark,
                lineHeight = 18.sp
              )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Button(
                onClick = onNavigateToReservas,
                colors = ButtonDefaults.buttonColors(containerColor = CriolloRed),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).testTag("btn_reserve_from_local")
              ) {
                Icon(imageVector = Icons.Default.EventSeat, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Mesa / Barra", fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
              }

              OutlinedButton(
                onClick = onNavigateToEnElLocal,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = CriolloYellow),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, CriolloYellow),
                modifier = Modifier.weight(1f).testTag("btn_in_venue_mode")
              ) {
                Icon(imageVector = Icons.Default.QrCodeScanner, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("En el Local", fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
              }
            }
          }
        }
      }
    }

    // 2. Direct Contact & Navigation Actions
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        QuickContactButton(
          title = "Cómo Llegar",
          subtitle = "Google Maps",
          icon = Icons.Default.Directions,
          accentColor = CriolloRed,
          modifier = Modifier.weight(1f),
          onClick = {
            onOpenExternalUrl("Google Maps", IntegrationsConfig.GOOGLE_MAPS_URL)
          }
        )

        QuickContactButton(
          title = "WhatsApp",
          subtitle = "Chat directo",
          icon = Icons.Default.Chat,
          accentColor = SalsaGreen,
          modifier = Modifier.weight(1f),
          onClick = {
            onOpenExternalUrl("WhatsApp El Criollo", IntegrationsConfig.WHATSAPP_URL)
          }
        )

        QuickContactButton(
          title = "Llamar",
          subtitle = restaurant.phone,
          icon = Icons.Default.Call,
          accentColor = CriolloYellow,
          modifier = Modifier.weight(1f),
          onClick = {
            onOpenExternalUrl("Llamar El Criollo", "tel:${restaurant.phone}")
          }
        )
      }
    }

    // 3. Location & Public Transport Detail
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Place, contentDescription = null, tint = CriolloRed)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Ubicación y Accesos",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "${restaurant.address} (${restaurant.neighborhood}, ${restaurant.city})",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
          )
          Text(
            text = "Acceso por puerta principal de Bravo Murillo o por Calle Palencia.",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )

          Divider(modifier = Modifier.padding(vertical = 10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            TransportBadge(name = "Metro Alvarado", line = "Línea 1", color = Color(0xFF00A3E0), modifier = Modifier.weight(1f))
            TransportBadge(name = "Cuatro Caminos", line = "L1, L2, L6", color = CriolloRed, modifier = Modifier.weight(1f))
          }
        }
      }
    }

    // 4. Hours / Horarios
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = CriolloYellow)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Horarios de Taquería",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          ScheduleRow("Lunes a Jueves", "13:00 - 16:30 | 20:00 - 23:30")
          ScheduleRow("Viernes y Sábados", "13:00 - 00:00 (Ininterrumpido)")
          ScheduleRow("Domingos", "13:00 - 17:00")
        }
      }
    }

    // 5. Future Expansion Teaser (Malasaña)
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp)
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("🌮", fontSize = 28.sp)
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = "Próxima Apertura: Malasaña",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "Estamos preparando un segundo punto en Madrid centro. ¡Atentos a las novedades!",
              style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.5.sp
              )
            )
          }
        }
      }
    }
  }
}

@Composable
private fun QuickContactButton(
  title: String,
  subtitle: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  accentColor: Color,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Card(
    modifier = modifier
      .clip(RoundedCornerShape(14.dp))
      .clickable(onClick = onClick),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = RoundedCornerShape(14.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .background(accentColor.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(18.dp))
      }
      Spacer(modifier = Modifier.height(6.dp))
      Text(text = title, fontWeight = FontWeight.Bold, fontSize = 12.sp)
      Text(text = subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp, maxLines = 1)
    }
  }
}

@Composable
private fun TransportBadge(
  name: String,
  line: String,
  color: Color,
  modifier: Modifier = Modifier
) {
  Surface(
    color = MaterialTheme.colorScheme.surfaceVariant,
    shape = RoundedCornerShape(10.dp),
    modifier = modifier
  ) {
    Row(
      modifier = Modifier.padding(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(10.dp)
          .clip(CircleShape)
          .background(color)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Column {
        Text(name, fontWeight = FontWeight.Bold, fontSize = 11.sp)
        Text(line, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
      }
    }
  }
}

@Composable
private fun ScheduleRow(days: String, hours: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(text = days, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium))
    Text(
      text = hours,
      style = MaterialTheme.typography.bodySmall.copy(
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontWeight = FontWeight.Normal
      )
    )
  }
}
