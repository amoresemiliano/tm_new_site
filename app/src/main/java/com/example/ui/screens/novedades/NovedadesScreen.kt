package com.example.ui.screens.novedades

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.config.IntegrationsConfig
import com.example.data.mock.MockDataFixtures
import com.example.data.model.NewsItem
import com.example.ui.theme.*

@Composable
fun NovedadesScreen(
  onOpenExternalUrl: (String, String) -> Unit,
  onClaimReviewPromo: () -> Unit,
  modifier: Modifier = Modifier
) {
  val newsList = MockDataFixtures.newsItems

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("novedades_screen_container"),
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
          text = "Noticias & Eventos",
          style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
          )
        )
        Text(
          text = "Lo último de El Criollo en Tetuán · Promociones, nuevas recetas y novedades",
          style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
      }
    }

    items(newsList) { article ->
      NewsArticleCard(
        article = article,
        onAction = {
          if (article.tag.contains("Google") || article.title.contains("Reseña") || article.isPromo) {
            onClaimReviewPromo()
          } else {
            onOpenExternalUrl(article.title, IntegrationsConfig.INSTAGRAM_URL)
          }
        }
      )
    }
  }
}

@Composable
private fun NewsArticleCard(
  article: NewsItem,
  onAction: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .testTag("news_card_${article.id}"),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = RoundedCornerShape(18.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.35f))
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          color = if (article.isPromo || article.tag.contains("PROMO")) CriolloRed else CriolloYellow,
          shape = RoundedCornerShape(8.dp)
        ) {
          Text(
            text = article.tag,
            color = if (article.isPromo || article.tag.contains("PROMO")) Color.White else TaqueriaBlack,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }

        Text(
          text = article.date,
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 11.sp
          )
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = article.title,
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = article.body,
        style = MaterialTheme.typography.bodyMedium.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          lineHeight = 20.sp
        )
      )

      Spacer(modifier = Modifier.height(14.dp))

      Button(
        onClick = onAction,
        colors = ButtonDefaults.buttonColors(
          containerColor = if (article.isPromo) CriolloRed else MaterialTheme.colorScheme.surfaceVariant,
          contentColor = if (article.isPromo) Color.White else MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = if (article.isPromo) "⭐ Escribir Reseña y Conseguir Taco" else "Saber Más",
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}
