package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun FreeTacoPromoBanner(
  onClaimClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .shadow(6.dp, RoundedCornerShape(20.dp))
      .clip(RoundedCornerShape(20.dp))
      .clickable(onClick = onClaimClick)
      .testTag("promo_banner_taquito_gratis"),
    colors = CardDefaults.cardColors(
      containerColor = CriolloRed
    )
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.linearGradient(
            colors = listOf(CriolloRed, CriolloRedDark, Color(0xFF6B0000))
          )
        )
        .padding(18.dp)
    ) {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Tag
        Surface(
          color = CriolloYellow,
          shape = RoundedCornerShape(20.dp),
          modifier = Modifier.padding(bottom = 8.dp)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = "Promo estrella",
              tint = TaqueriaBlack,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "PROMO EXCLUSIVA EN LOCAL",
              color = TaqueriaBlack,
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Black
            )
          }
        }

        // Headline
        Text(
          text = "¡1 TAQUITO GRATIS!",
          style = MaterialTheme.typography.displaySmall.copy(
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp,
            color = Color.White
          ),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Subheading
        Text(
          text = "Muéstranos tu reseña en Google al pedir\n¡Y llévate un taco de regalo al momento!",
          style = MaterialTheme.typography.bodyMedium.copy(
            color = CriolloYellowLight,
            lineHeight = 20.sp
          ),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Action Pill Button
        Surface(
          color = Color.White,
          shape = RoundedCornerShape(16.dp),
          shadowElevation = 4.dp
        ) {
          Row(
            modifier = Modifier
              .padding(horizontal = 20.dp, vertical = 10.dp)
              .testTag("btn_claim_taquito_gratis"),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "🌮", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Ver Promo & Escribir Reseña",
              color = CriolloRedDark,
              style = MaterialTheme.typography.labelLarge,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "1 taquito por persona · Válido en Mercado Maravillas · Promo por tiempo limitado",
          style = MaterialTheme.typography.bodySmall.copy(
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 9.5.sp
          ),
          textAlign = TextAlign.Center
        )
      }
    }
  }
}
