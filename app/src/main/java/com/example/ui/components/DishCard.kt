package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MenuItem
import com.example.ui.theme.*

@Composable
fun DishCard(
  item: MenuItem,
  onItemClick: (MenuItem) -> Unit,
  onQuickAdd: (MenuItem) -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .clickable { onItemClick(item) }
      .testTag("dish_card_${item.id}"),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      // Tags & Badges Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          if (item.isFavorite) {
            Surface(
              color = CriolloRed,
              shape = RoundedCornerShape(6.dp)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.Star,
                  contentDescription = "Favorito",
                  tint = CriolloYellow,
                  modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                  text = "FAVORITO",
                  color = Color.White,
                  style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }

          item.tags.firstOrNull()?.let { tag ->
            Surface(
              color = if (tag.contains("Veggie") || tag.contains("Vegetariano")) SalsaGreen else CriolloYellow,
              shape = RoundedCornerShape(6.dp)
            ) {
              Text(
                text = tag.uppercase(),
                color = if (tag.contains("Veggie") || tag.contains("Vegetariano")) Color.White else TaqueriaBlack,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        // Spicy Indicator
        if (item.spicyLevel > 0) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(item.spicyLevel) {
              Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = "Picante nivel ${item.spicyLevel}",
                tint = CriolloRed,
                modifier = Modifier.size(14.dp)
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Dish Name & Category Icon
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = item.name,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = item.description,
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              lineHeight = 16.sp
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Price & Add CTA
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "${String.format("%.2f", item.price)} €",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Black,
              color = CriolloRed
            )
          )
          Text(
            text = item.portions,
            style = MaterialTheme.typography.labelSmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              fontSize = 10.sp
            )
          )
        }

        Button(
          onClick = { onQuickAdd(item) },
          colors = ButtonDefaults.buttonColors(
            containerColor = CriolloRed,
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(12.dp),
          contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
          modifier = Modifier
            .height(38.dp)
            .testTag("btn_add_${item.id}")
        ) {
          Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Añadir",
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Añadir",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold
            )
          )
        }
      }
    }
  }
}
