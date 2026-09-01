package com.example.ui.screens.local

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.config.IntegrationsConfig
import com.example.ui.theme.*

@Composable
fun EnElLocalScreen(
  onNavigateToMenu: () -> Unit,
  onOpenExternalUrl: (String, String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTable by remember { mutableIntStateOf(4) }
  var waiterCalledMessage by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("en_el_local_container"),
    contentPadding = PaddingValues(bottom = 100.dp)
  ) {
    // Header
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp)
      ) {
        Text(
          text = "Modo En El Local 🌮",
          style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
          )
        )
        Text(
          text = "Pide desde tu mesa, avisa al camarero o conéctate al Wi-Fi del puesto.",
          style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
      }
    }

    // Table Selector
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
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Selecciona tu Mesa / Puesto",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Surface(
              color = CriolloYellow,
              shape = RoundedCornerShape(8.dp)
            ) {
              Text(
                text = "Mesa #$selectedTable",
                color = TaqueriaBlack,
                fontWeight = FontWeight.Black,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            (1..8).forEach { tableNum ->
              val isSelected = selectedTable == tableNum
              Surface(
                color = if (isSelected) CriolloRed else MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(10.dp))
                  .clickable { selectedTable = tableNum }
                  .testTag("table_select_$tableNum")
              ) {
                Text(
                  text = "$tableNum",
                  color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  modifier = Modifier.padding(vertical = 8.dp),
                  textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
              }
            }
          }
        }
      }
    }

    // Direct Action: Pedir a Mesa
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
            Box(
              modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(CriolloRed.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.RestaurantMenu, contentDescription = null, tint = CriolloRed)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "Pedir Comida a Mesa #$selectedTable",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = "Los platos marcharán directo al comal para servirlos en tu mesa.",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = onNavigateToMenu,
            colors = ButtonDefaults.buttonColors(containerColor = CriolloRed),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().testTag("btn_order_to_table")
          ) {
            Text("Ver Carta y Pedir a Mesa", fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // Call Waiter Button
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
            Box(
              modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(CriolloYellow.copy(alpha = 0.25f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.NotificationsActive, contentDescription = null, tint = CriolloRedDark)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "Llamar al Camarero / Pedir Cuenta",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = "Avisa discretamente al personal del puesto sin levantarte.",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          if (waiterCalledMessage) {
            Surface(
              color = SalsaGreen.copy(alpha = 0.15f),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = SalsaGreen)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "¡Aviso enviado a barra! Un camarero se acerca enseguida a Mesa #$selectedTable.",
                  color = SalsaGreen,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          } else {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              OutlinedButton(
                onClick = { waiterCalledMessage = true },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).testTag("btn_call_waiter")
              ) {
                Text("🛎️ Atención en mesa", fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }

              OutlinedButton(
                onClick = { waiterCalledMessage = true },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).testTag("btn_ask_bill")
              ) {
                Text("💳 Traer la cuenta", fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }

    // Wi-Fi Info Card
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
          Icon(imageVector = Icons.Default.Wifi, contentDescription = null, tint = CriolloRed, modifier = Modifier.size(28.dp))
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = "Wi-Fi Clientes El Criollo",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "Red: ElCriollo_Maravillas  |  Clave: vivaeltaco2026",
              style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
          }
        }
      }
    }
  }
}
