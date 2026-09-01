package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.config.IntegrationsConfig
import com.example.data.model.Review
import com.example.ui.theme.*

@Composable
fun GoogleReviewsSection(
  reviews: List<Review>,
  rating: Double = IntegrationsConfig.GOOGLE_RATING,
  reviewCount: Int = IntegrationsConfig.GOOGLE_REVIEWS_COUNT,
  onWriteReviewClick: () -> Unit,
  onViewAllClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .testTag("google_reviews_section")
  ) {
    // Header & Rating Banner
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "Los Taqueros Hablan",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
          )
        )
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(top = 2.dp)
        ) {
          Text(
            text = "$rating",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Black,
              color = CriolloRed
            )
          )
          Spacer(modifier = Modifier.width(4.dp))
          Row {
            repeat(5) {
              Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = CriolloYellow,
                modifier = Modifier.size(16.dp)
              )
            }
          }
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "($reviewCount opiniones en Google)",
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }
      }

      Button(
        onClick = onWriteReviewClick,
        colors = ButtonDefaults.buttonColors(
          containerColor = CriolloYellow,
          contentColor = TaqueriaBlack
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
        modifier = Modifier.testTag("btn_write_google_review")
      ) {
        Icon(
          imageVector = Icons.Default.Edit,
          contentDescription = null,
          modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "Dejar Reseña",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Horizontal Scroll Reviews
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      contentPadding = PaddingValues(horizontal = 2.dp)
    ) {
      items(reviews) { review ->
        ReviewCard(review = review)
      }
    }
  }
}

@Composable
fun ReviewCard(
  review: Review,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .width(260.dp)
      .height(150.dp)
      .testTag("review_card_${review.id}"),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
    shape = RoundedCornerShape(16.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(14.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(CriolloRed),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = review.avatarInitial,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = review.authorName,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
              Text(
                text = review.relativeTime,
                style = MaterialTheme.typography.bodySmall.copy(
                  fontSize = 10.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
            }
          }

          // Stars
          Row {
            repeat(review.rating) {
              Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = CriolloYellow,
                modifier = Modifier.size(13.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = review.text,
          style = MaterialTheme.typography.bodySmall.copy(
            lineHeight = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
          ),
          maxLines = 3,
          overflow = TextOverflow.Ellipsis
        )
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
      ) {
        Text(
          text = "Google Maps Verified ✓",
          style = MaterialTheme.typography.bodySmall.copy(
            color = SalsaGreen,
            fontSize = 9.5.sp,
            fontWeight = FontWeight.SemiBold
          )
        )
      }
    }
  }
}
