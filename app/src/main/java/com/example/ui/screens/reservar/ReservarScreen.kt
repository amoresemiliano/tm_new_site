package com.example.ui.screens.reservar

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
import com.example.data.model.ReservationRequest
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppUiState

@Composable
fun ReservarScreen(
  uiState: AppUiState,
  onSubmitReservation: (ReservationRequest) -> Unit,
  onOpenExternalUrl: (String, String) -> Unit,
  onReturnHome: () -> Unit,
  modifier: Modifier = Modifier
) {
  var guestsCount by remember { mutableIntStateOf(2) }
  var selectedZone by remember { mutableStateOf("Mesa en Mercado") } // "Mesa en Mercado", "Barra Taquera", "Terraza Interior"
  var selectedDate by remember { mutableStateOf("Hoy (Viernes)") }
  var selectedTime by remember { mutableStateOf("20:30") }
  var customerName by remember { mutableStateOf(uiState.clubMember.name) }
  var customerPhone by remember { mutableStateOf(uiState.clubMember.phone) }
  var customerEmail by remember { mutableStateOf("socio@elcriollo.es") }
  var specialRequests by remember { mutableStateOf("") }
  var isSubmitted by remember { mutableStateOf(false) }

  val dateOptions = listOf("Hoy (Viernes)", "Mañana (Sábado)", "Domingo", "Próximo Jueves", "Próximo Viernes")
  val timeSlots = listOf("13:30", "14:00", "14:30", "15:00", "20:00", "20:30", "21:15", "22:00", "22:45")
  val zones = listOf("Mesa en Mercado", "Barra Taquera", "Terraza Interior")

  if (isSubmitted) {
    ReservationConfirmationView(
      name = customerName,
      guests = guestsCount,
      date = selectedDate,
      time = selectedTime,
      zone = selectedZone,
      onReturnHome = onReturnHome
    )
    return
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("reservar_screen_container"),
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
          text = "Reserva tu Mesa",
          style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
          )
        )
        Text(
          text = "Puesto El Criollo · Mercado Maravillas (Tetuán, Madrid)",
          style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
      }
    }

    // Number of Guests
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
          Text(
            text = "¿Cuántas personas seréis?",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(10.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            (1..6).forEach { num ->
              val isSelected = guestsCount == num
              Surface(
                color = if (isSelected) CriolloRed else MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(12.dp))
                  .clickable { guestsCount = num }
                  .testTag("btn_guests_$num")
              ) {
                Text(
                  text = if (num == 6) "6+" else num.toString(),
                  color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp,
                  modifier = Modifier.padding(vertical = 10.dp),
                  textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
              }
            }
          }
        }
      }
    }

    // Zone Selector (Mesa vs Barra)
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
          Text(
            text = "Zona de Preferencia",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(10.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            zones.forEach { zone ->
              val isSelected = selectedZone == zone
              Surface(
                color = if (isSelected) CriolloYellow else MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(12.dp))
                  .clickable { selectedZone = zone }
              ) {
                Text(
                  text = zone,
                  color = if (isSelected) TaqueriaBlack else MaterialTheme.colorScheme.onSurfaceVariant,
                  fontWeight = FontWeight.Bold,
                  fontSize = 11.5.sp,
                  modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                  textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
              }
            }
          }
        }
      }
    }

    // Date Selector
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
          Text(
            text = "Fecha",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(10.dp))
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(dateOptions) { date ->
              val isSelected = selectedDate == date
              Surface(
                color = if (isSelected) CriolloRed else MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                  .clip(RoundedCornerShape(10.dp))
                  .clickable { selectedDate = date }
              ) {
                Text(
                  text = date,
                  color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp,
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
              }
            }
          }
        }
      }
    }

    // Time Slot Selector
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
          Text(
            text = "Hora de Llegada",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(10.dp))
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(timeSlots) { time ->
              val isSelected = selectedTime == time
              Surface(
                color = if (isSelected) CriolloYellow else MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                  .clip(RoundedCornerShape(10.dp))
                  .clickable { selectedTime = time }
                  .testTag("time_slot_$time")
              ) {
                Text(
                  text = time,
                  color = if (isSelected) TaqueriaBlack else MaterialTheme.colorScheme.onSurfaceVariant,
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                )
              }
            }
          }
        }
      }
    }

    // Contact Information Form
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
          Text(
            text = "Datos de Contacto",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = customerName,
            onValueChange = { customerName = it },
            label = { Text("Nombre y Apellidos") },
            modifier = Modifier.fillMaxWidth().testTag("input_reserve_name"),
            singleLine = true,
            shape = RoundedCornerShape(10.dp)
          )

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = customerPhone,
            onValueChange = { customerPhone = it },
            label = { Text("Teléfono de Confirmación") },
            modifier = Modifier.fillMaxWidth().testTag("input_reserve_phone"),
            singleLine = true,
            shape = RoundedCornerShape(10.dp)
          )

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = specialRequests,
            onValueChange = { specialRequests = it },
            label = { Text("Peticiones especiales / Alergias") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
          )
        }
      }
    }

    // Submit Reservation Button
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Button(
          onClick = {
            val req = ReservationRequest(
              guests = guestsCount,
              date = selectedDate,
              time = selectedTime,
              customerName = customerName,
              customerPhone = customerPhone,
              customerEmail = customerEmail,
              specialRequests = specialRequests,
              zone = selectedZone
            )
            onSubmitReservation(req)
            isSubmitted = true
          },
          colors = ButtonDefaults.buttonColors(containerColor = CriolloRed, contentColor = Color.White),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("btn_confirm_reservation")
        ) {
          Icon(imageVector = Icons.Default.EventSeat, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Confirmar Reserva Instantánea",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "Cancelación gratuita en cualquier momento desde WhatsApp.",
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
          ),
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
  }
}

@Composable
fun ReservationConfirmationView(
  name: String,
  guests: Int,
  date: String,
  time: String,
  zone: String,
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
      text = "¡MESA RESERVADA!",
      style = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight.Black,
        color = MaterialTheme.colorScheme.onBackground
      )
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = "Te esperamos en El Criollo (Mercado Maravillas). Tu mesa estará lista 10 minutos antes.",
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
        ReservationDetailRow("Titular", name)
        ReservationDetailRow("Personas", "$guests comensales")
        ReservationDetailRow("Día y Hora", "$date a las $time h")
        ReservationDetailRow("Zona", zone)
        ReservationDetailRow("Referencia", "#RES-${(1000..9999).random()}")
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
        .testTag("btn_return_from_reserve_confirmation")
    ) {
      Text("Volver al Inicio", fontWeight = FontWeight.Bold)
    }
  }
}

@Composable
private fun ReservationDetailRow(label: String, value: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
    Text(value, fontWeight = FontWeight.Bold, fontSize = 13.sp)
  }
}
