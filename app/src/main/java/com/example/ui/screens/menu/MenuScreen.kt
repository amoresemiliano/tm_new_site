package com.example.ui.screens.menu

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.mock.MockDataFixtures
import com.example.data.model.MenuItem
import com.example.ui.components.DishCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.AppUiState

@Composable
fun MenuScreen(
  uiState: AppUiState,
  onCategorySelected: (String) -> Unit,
  onSearchQueryChanged: (String) -> Unit,
  onQuickAdd: (MenuItem) -> Unit,
  onItemClick: (MenuItem) -> Unit,
  onNavigateToPedir: () -> Unit,
  modifier: Modifier = Modifier
) {
  var onlyFavorites by remember { mutableStateOf(false) }
  var onlyVeggie by remember { mutableStateOf(false) }
  var onlySpicy by remember { mutableStateOf(false) }

  // Filter items
  val filteredItems = remember(
    uiState.selectedCategory,
    uiState.searchQuery,
    onlyFavorites,
    onlyVeggie,
    onlySpicy
  ) {
    MockDataFixtures.menuItems.filter { item ->
      val matchesCategory = uiState.selectedCategory == "todos" || item.categoryId == uiState.selectedCategory
      val matchesSearch = uiState.searchQuery.isEmpty() ||
        item.name.contains(uiState.searchQuery, ignoreCase = true) ||
        item.description.contains(uiState.searchQuery, ignoreCase = true)
      val matchesFav = !onlyFavorites || item.isFavorite
      val matchesVeggie = !onlyVeggie || item.tags.any { it.contains("Veg", ignoreCase = true) }
      val matchesSpicy = !onlySpicy || item.spicyLevel > 0

      matchesCategory && matchesSearch && matchesFav && matchesVeggie && matchesSpicy
    }
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .testTag("menu_screen_container"),
      contentPadding = PaddingValues(bottom = 120.dp)
    ) {
      // 1. Search Bar Header
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
          Text(
            text = "Nuestra Carta",
            style = MaterialTheme.typography.headlineLarge.copy(
              fontWeight = FontWeight.Black,
              color = MaterialTheme.colorScheme.onBackground
            )
          )
          Text(
            text = "Tacos al pastor, birria, carnitas y antojitos al estilo Mercado Maravillas",
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = onSearchQueryChanged,
            placeholder = { Text("Buscar taco, consomé, michelada...", fontSize = 14.sp) },
            leadingIcon = {
              Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = CriolloRed)
            },
            trailingIcon = {
              if (uiState.searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChanged("") }) {
                  Icon(imageVector = Icons.Default.Clear, contentDescription = "Limpiar")
                }
              }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("menu_search_input"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = CriolloRed,
              unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
              focusedContainerColor = MaterialTheme.colorScheme.surface,
              unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
          )
        }
      }

      // 2. Category Chips Row
      item {
        LazyRow(
          modifier = Modifier.fillMaxWidth(),
          contentPadding = PaddingValues(horizontal = 16.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // "Todos" pill
          item {
            FilterChip(
              selected = uiState.selectedCategory == "todos",
              onClick = { onCategorySelected("todos") },
              label = { Text("Todos") },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = CriolloRed,
                selectedLabelColor = Color.White
              ),
              shape = RoundedCornerShape(20.dp),
              modifier = Modifier.testTag("category_chip_todos")
            )
          }

          items(MockDataFixtures.categories) { cat ->
            FilterChip(
              selected = uiState.selectedCategory == cat.id,
              onClick = { onCategorySelected(cat.id) },
              label = { Text(cat.name) },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = CriolloRed,
                selectedLabelColor = Color.White
              ),
              shape = RoundedCornerShape(20.dp),
              modifier = Modifier.testTag("category_chip_${cat.id}")
            )
          }
        }
      }

      // 3. Quick Tag Filters (Favoritos, Veggie, Picante)
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          FilterChip(
            selected = onlyFavorites,
            onClick = { onlyFavorites = !onlyFavorites },
            label = { Text("⭐ Favoritos") },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = CriolloYellow,
              selectedLabelColor = TaqueriaBlack
            ),
            shape = RoundedCornerShape(12.dp)
          )

          FilterChip(
            selected = onlyVeggie,
            onClick = { onlyVeggie = !onlyVeggie },
            label = { Text("🥑 Veggie") },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = SalsaGreen,
              selectedLabelColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
          )

          FilterChip(
            selected = onlySpicy,
            onClick = { onlySpicy = !onlySpicy },
            label = { Text("🌶️ Picante") },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = CriolloRedDark,
              selectedLabelColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
          )
        }
      }

      // 4. Results Count
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "${filteredItems.size} productos disponibles",
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              fontWeight = FontWeight.SemiBold
            )
          )
        }
      }

      // 5. Dish Items List
      items(filteredItems) { dish ->
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
          DishCard(
            item = dish,
            onItemClick = onItemClick,
            onQuickAdd = onQuickAdd
          )
        }
      }

      if (filteredItems.isEmpty()) {
        item {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(text = "🌮", fontSize = 48.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "No encontramos resultados",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "Prueba con otra búsqueda o categoría",
              style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
          }
        }
      }
    }

    // 6. Floating Cart Bar if cart is not empty
    if (uiState.cart.isNotEmpty()) {
      val totalItems = uiState.cart.sumOf { it.quantity }
      val totalPrice = uiState.cart.sumOf { it.subtotal }

      Surface(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(horizontal = 16.dp, vertical = 70.dp)
          .fillMaxWidth()
          .shadow(12.dp, RoundedCornerShape(20.dp))
          .clip(RoundedCornerShape(20.dp))
          .clickable(onClick = onNavigateToPedir)
          .testTag("floating_cart_bar"),
        color = CriolloRed
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 12.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(CriolloYellow),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = totalItems.toString(),
                color = TaqueriaBlack,
                fontWeight = FontWeight.Black,
                fontSize = 13.sp
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "Ver Pedido",
                color = Color.White,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = "Toca para revisar y confirmar",
                color = CriolloYellowLight,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.5.sp)
              )
            }
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "${String.format("%.2f", totalPrice)} €",
              color = Color.White,
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
              imageVector = Icons.Default.ArrowForward,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }
  }
}
