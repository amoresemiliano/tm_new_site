package com.example.ui.screens.pedir

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.config.IntegrationsConfig
import com.example.data.model.CartItem
import com.example.data.model.MenuItem
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.AppUiState

enum class OrderDeliveryMode {
  DELIVERY,
  PICKUP
}

@Composable
fun PedirScreen(
  uiState: AppUiState,
  onAddToCart: (MenuItem, String, Int) -> Unit,
  onRemoveFromCart: (MenuItem, String) -> Unit,
  onClearCart: () -> Unit,
  onNavigateToMenu: () -> Unit,
  onProceedCheckout: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var deliveryMode by remember { mutableStateOf(OrderDeliveryMode.DELIVERY) }
  var deliveryAddress by remember { mutableStateOf("Calle de Bravo Murillo 140, 3ºB, Madrid") }
  var deliveryNotes by remember { mutableStateOf("") }
  var selectedTip by remember { mutableDoubleStateOf(1.50) }
  var orderPlacedSuccess by remember { mutableStateOf(false) }

  val subtotal = uiState.cart.sumOf { it.subtotal }
  val deliveryFee = if (deliveryMode == OrderDeliveryMode.DELIVERY) 2.50 else 0.00
  val total = subtotal + deliveryFee + selectedTip

  if (orderPlacedSuccess) {
    OrderSuccessView(
      deliveryMode = deliveryMode,
      total = total,
      onReturnHome = onNavigateToMenu
    )
    return
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("pedir_screen_container"),
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
          text = "Tu Pedido Taquero",
          style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
          )
        )
        Text(
          text = "Mercado Maravillas · Entrega caliente y con totopos",
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        )
      }
    }

    // Delivery vs Pickup Mode Toggle
    item {
      Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 4.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
        ) {
          Surface(
            color = if (deliveryMode == OrderDeliveryMode.DELIVERY) CriolloRed else Color.Transparent,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(12.dp))
              .clickable { deliveryMode = OrderDeliveryMode.DELIVERY }
              .testTag("toggle_order_delivery")
          ) {
            Row(
              modifier = Modifier.padding(vertical = 10.dp),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Moped,
                contentDescription = null,
                tint = if (deliveryMode == OrderDeliveryMode.DELIVERY) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "A Domicilio",
                color = if (deliveryMode == OrderDeliveryMode.DELIVERY) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
              )
            }
          }

          Surface(
            color = if (deliveryMode == OrderDeliveryMode.PICKUP) CriolloRed else Color.Transparent,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(12.dp))
              .clickable { deliveryMode = OrderDeliveryMode.PICKUP }
              .testTag("toggle_order_pickup")
          ) {
            Row(
              modifier = Modifier.padding(vertical = 10.dp),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Storefront,
                contentDescription = null,
                tint = if (deliveryMode == OrderDeliveryMode.PICKUP) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Para Recoger",
                color = if (deliveryMode == OrderDeliveryMode.PICKUP) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
              )
            }
          }
        }
      }
    }

    // Address or Pickup Point Info
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
        shape = RoundedCornerShape(16.dp)
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          if (deliveryMode == OrderDeliveryMode.DELIVERY) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = CriolloRed)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Dirección de Entrega", fontWeight = FontWeight.Bold, fontSize = 14.sp)
              }
              Text(text = "30-40 min", color = SalsaGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
              value = deliveryAddress,
              onValueChange = { deliveryAddress = it },
              modifier = Modifier.fillMaxWidth().testTag("input_delivery_address"),
              singleLine = true,
              shape = RoundedCornerShape(10.dp)
            )
          } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.Store, contentDescription = null, tint = CriolloRed)
              Spacer(modifier = Modifier.width(6.dp))
              Text(text = "Punto de Recogida", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "El Criollo · Puesto Mercado Maravillas (C/ Bravo Murillo 122)",
              style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
              text = "Listo en aprox. 15-20 minutos",
              style = MaterialTheme.typography.bodySmall.copy(color = SalsaGreen, fontWeight = FontWeight.Bold)
            )
          }
        }
      }
    }

    // Cart Items List
    if (uiState.cart.isEmpty()) {
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          shape = RoundedCornerShape(16.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text("🌮", fontSize = 40.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Tu canasta está vacía",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Agrega tus tacos, quesadillas y micheladas favoritas de la carta.",
              style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
              textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(14.dp))
            Button(
              onClick = onNavigateToMenu,
              colors = ButtonDefaults.buttonColors(containerColor = CriolloRed),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.testTag("btn_go_to_menu_from_cart")
            ) {
              Text("Explorar Carta", fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    } else {
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Platos Seleccionados",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          TextButton(onClick = onClearCart) {
            Text("Vaciar", color = CriolloRed, fontSize = 12.sp)
          }
        }
      }

      items(uiState.cart) { cartItem ->
        CartItemRow(
          cartItem = cartItem,
          onAdd = { onAddToCart(cartItem.menuItem, cartItem.selectedSauce, 1) },
          onRemove = { onRemoveFromCart(cartItem.menuItem, cartItem.selectedSauce) }
        )
      }

      // Add more items button
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
          OutlinedButton(
            onClick = onNavigateToMenu,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = CriolloRed)
          ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Añadir más tacos o bebidas", fontWeight = FontWeight.SemiBold)
          }
        }
      }

      // Special Notes
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          shape = RoundedCornerShape(16.dp)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(
              text = "Instrucciones de Cocina / Alergias",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
              value = deliveryNotes,
              onValueChange = { deliveryNotes = it },
              placeholder = { Text("Ej: Cilantro aparte, salsa muy picante...", fontSize = 13.sp) },
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(10.dp)
            )
          }
        }
      }

      // Taquero Tip Selector
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          shape = RoundedCornerShape(16.dp)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Propina para el Equipo 🌶️",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = "${String.format("%.2f", selectedTip)} €",
                color = CriolloRed,
                fontWeight = FontWeight.Bold
              )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              listOf(0.00, 1.00, 1.50, 2.50, 4.00).forEach { tip ->
                val isSelected = selectedTip == tip
                Surface(
                  color = if (isSelected) CriolloYellow else MaterialTheme.colorScheme.surfaceVariant,
                  shape = RoundedCornerShape(10.dp),
                  modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { selectedTip = tip }
                ) {
                  Text(
                    text = if (tip == 0.00) "0€" else "${tip.toInt()}€",
                    color = if (isSelected) TaqueriaBlack else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                  )
                }
              }
            }
          }
        }
      }

      // Order Summary & Totals
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          shape = RoundedCornerShape(16.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "Resumen de Cuenta",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(10.dp))

            SummaryLine(label = "Subtotal comida", value = "${String.format("%.2f", subtotal)} €")
            if (deliveryMode == OrderDeliveryMode.DELIVERY) {
              SummaryLine(label = "Gastos de envío", value = "${String.format("%.2f", deliveryFee)} €")
            }
            if (selectedTip > 0) {
              SummaryLine(label = "Propina taquera", value = "${String.format("%.2f", selectedTip)} €")
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "TOTAL",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black)
              )
              Text(
                text = "${String.format("%.2f", total)} €",
                style = MaterialTheme.typography.headlineSmall.copy(
                  fontWeight = FontWeight.Black,
                  color = CriolloRed
                )
              )
            }
          }
        }
      }

      // Checkout Primary Action
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Button(
            onClick = {
              orderPlacedSuccess = true
            },
            colors = ButtonDefaults.buttonColors(
              containerColor = CriolloRed,
              contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp)
              .testTag("btn_confirm_order_checkout")
          ) {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Confirmar Pedido · ${String.format("%.2f", total)} €",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Last.app direct integration link
          TextButton(
            onClick = {
              onProceedCheckout(IntegrationsConfig.LAST_SHOP_URL)
            },
            modifier = Modifier.fillMaxWidth().testTag("btn_open_lastapp_order")
          ) {
            Text(
              text = "O pedir directamente en Last.app Shop →",
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              fontSize = 12.5.sp
            )
          }
        }
      }
    }
  }
}

@Composable
private fun CartItemRow(
  cartItem: CartItem,
  onAdd: () -> Unit,
  onRemove: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 4.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = RoundedCornerShape(14.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = cartItem.menuItem.name,
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        Text(
          text = cartItem.selectedSauce,
          style = MaterialTheme.typography.bodySmall.copy(
            color = SalsaGreen,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Medium
          )
        )
        Text(
          text = "${String.format("%.2f", cartItem.subtotal)} €",
          style = MaterialTheme.typography.labelMedium.copy(
            color = CriolloRed,
            fontWeight = FontWeight.Bold
          )
        )
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .clip(RoundedCornerShape(10.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant)
          .padding(horizontal = 4.dp, vertical = 2.dp)
      ) {
        IconButton(onClick = onRemove, modifier = Modifier.size(30.dp)) {
          Icon(imageVector = Icons.Default.Remove, contentDescription = "Menos", modifier = Modifier.size(14.dp))
        }
        Text(
          text = cartItem.quantity.toString(),
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
          modifier = Modifier.padding(horizontal = 6.dp)
        )
        IconButton(onClick = onAdd, modifier = Modifier.size(30.dp)) {
          Icon(imageVector = Icons.Default.Add, contentDescription = "Más", modifier = Modifier.size(14.dp))
        }
      }
    }
  }
}

@Composable
private fun SummaryLine(label: String, value: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 3.dp),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
    )
  }
}

@Composable
fun OrderSuccessView(
  deliveryMode: OrderDeliveryMode,
  total: Double,
  onReturnHome: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Box(
      modifier = Modifier
        .size(80.dp)
        .clip(CircleShape)
        .background(SalsaGreen),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = Icons.Default.Check,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.size(44.dp)
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = "¡PEDIDO MARCHANDO!",
      style = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight.Black,
        color = MaterialTheme.colorScheme.onBackground
      )
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = if (deliveryMode == OrderDeliveryMode.DELIVERY)
        "Los taqueros del Mercado Maravillas ya están picando y dorando tus tacos al comal."
      else
        "Tu pedido estará listo para recoger en el puesto en aprox. 15-20 min.",
      style = MaterialTheme.typography.bodyMedium.copy(
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
      )
    )

    Spacer(modifier = Modifier.height(20.dp))

    Card(
      modifier = Modifier.fillMaxWidth(),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      shape = RoundedCornerShape(16.dp)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text("Número de Ticket", color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text("#CRIOLLO-8492", fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text("Total abonado", color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text("${String.format("%.2f", total)} €", fontWeight = FontWeight.Bold, color = CriolloRed)
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Button(
      onClick = onReturnHome,
      colors = ButtonDefaults.buttonColors(containerColor = CriolloRed),
      shape = RoundedCornerShape(14.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .testTag("btn_return_after_order")
    ) {
      Text("Volver al Inicio", fontWeight = FontWeight.Bold)
    }
  }
}
