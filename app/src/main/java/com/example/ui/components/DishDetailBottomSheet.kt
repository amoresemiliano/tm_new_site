package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
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
import com.example.data.model.MenuItem
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishDetailBottomSheet(
  item: MenuItem?,
  onDismiss: () -> Unit,
  onAddToCart: (MenuItem, String, Int) -> Unit
) {
  if (item == null) return

  var quantity by remember { mutableIntStateOf(1) }
  var selectedSauce by remember { mutableStateOf("Salsa Verde Suave") }
  val sauceOptions = listOf(
    "Salsa Verde (Suave)",
    "Salsa Roja Chipotle (Media)",
    "Salsa Habanero Criollo (Muy Picante 🔥)",
    "Sin Picante / Al Lado"
  )

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    containerColor = MaterialTheme.colorScheme.surface,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    dragHandle = { BottomSheetDefaults.DragHandle() },
    modifier = Modifier.testTag("dish_detail_bottom_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .padding(bottom = 32.dp)
    ) {
      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = item.name,
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          ),
          modifier = Modifier.weight(1f)
        )
        IconButton(
          onClick = onDismiss,
          modifier = Modifier.testTag("btn_close_sheet")
        ) {
          Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar")
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = item.description,
        style = MaterialTheme.typography.bodyMedium.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Price and Portion
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          color = TortillaCream,
          shape = RoundedCornerShape(8.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, GrayBorder)
        ) {
          Text(
            text = item.portions,
            color = TextPrimaryLight,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            fontWeight = FontWeight.SemiBold
          )
        }

        Text(
          text = "${String.format("%.2f", item.price * quantity)} €",
          style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.Black,
            color = CriolloRed
          )
        )
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Sauce selection
      Text(
        text = "Elige tu salsa:",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      )

      Spacer(modifier = Modifier.height(8.dp))

      sauceOptions.forEach { sauce ->
        val isSelected = selectedSauce == sauce
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) CriolloYellowLight.copy(alpha = 0.3f) else Color.Transparent)
            .border(
              width = if (isSelected) 1.5.dp else 1.dp,
              color = if (isSelected) CriolloYellow else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
              shape = RoundedCornerShape(12.dp)
            )
            .clickable { selectedSauce = sauce }
            .padding(horizontal = 14.dp, vertical = 10.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = sauce,
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
          RadioButton(
            selected = isSelected,
            onClick = { selectedSauce = sauce },
            colors = RadioButtonDefaults.colors(
              selectedColor = CriolloRed
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Quantity selector & Add Button
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          IconButton(
            onClick = { if (quantity > 1) quantity-- },
            modifier = Modifier.size(36.dp)
          ) {
            Icon(imageVector = Icons.Default.Remove, contentDescription = "Menos")
          }
          Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 12.dp)
          )
          IconButton(
            onClick = { quantity++ },
            modifier = Modifier.size(36.dp)
          ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Más")
          }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
          onClick = {
            onAddToCart(item, selectedSauce, quantity)
            onDismiss()
          },
          colors = ButtonDefaults.buttonColors(
            containerColor = CriolloRed,
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("btn_confirm_add_cart")
        ) {
          Text(
            text = "Añadir · ${String.format("%.2f", item.price * quantity)} €",
            style = MaterialTheme.typography.labelLarge.copy(
              fontWeight = FontWeight.Bold
            )
          )
        }
      }
    }
  }
}
