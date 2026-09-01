package com.example.ui.screens.club

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.config.IntegrationsConfig
import com.example.data.mock.MockDataFixtures
import com.example.data.model.GamificationMission
import com.example.data.model.Reward
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppUiState

@Composable
fun ClubScreen(
  uiState: AppUiState,
  onRedeemReward: (Reward) -> Unit,
  onCompleteGoogleReview: () -> Unit,
  onOpenExternalUrl: (String, String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) } // 0: Premios, 1: Misiones, 2: Mi Tarjeta
  val member = uiState.clubMember

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("club_screen_container"),
    contentPadding = PaddingValues(bottom = 100.dp)
  ) {
    // 1. Digital Membership Pass Card (Card Black + Gold Accent)
    item {
      MembershipPassCard(
        member = member,
        modifier = Modifier.padding(16.dp)
      )
    }

    // 2. Segmented Tabs (Premios, Misiones, Mi Tarjeta)
    item {
      Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
        ) {
          listOf("Catálogo Premios", "Misiones Taqueras", "Mi QR Digital").forEachIndexed { index, title ->
            val isSelected = selectedTab == index
            Surface(
              color = if (isSelected) CriolloRed else Color.Transparent,
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .clickable { selectedTab = index }
                .testTag("tab_club_$index")
            ) {
              Text(
                text = title,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                fontSize = 11.5.sp,
                modifier = Modifier.padding(vertical = 10.dp),
                textAlign = TextAlign.Center
              )
            }
          }
        }
      }
    }

    // Tab 0: Premios Disponibles
    if (selectedTab == 0) {
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
          Text(
            text = "Canjea tus Puntos por Comida",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Black,
              color = MaterialTheme.colorScheme.onBackground
            )
          )
          Text(
            text = "Saldo actual: ${member.points} puntos taqueros",
            style = MaterialTheme.typography.bodySmall.copy(
              color = CriolloYellow,
              fontWeight = FontWeight.Bold
            )
          )
        }
      }

      items(uiState.rewards) { reward ->
        RewardItemCard(
          reward = reward,
          memberPoints = member.points,
          onRedeem = { onRedeemReward(reward) }
        )
      }
    }

    // Tab 1: Misiones y Retos para ganar puntos
    if (selectedTab == 1) {
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
          Text(
            text = "Misiones para Subir de Nivel",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Black,
              color = MaterialTheme.colorScheme.onBackground
            )
          )
          Text(
            text = "Completa retos en el local o en redes y gana puntos directos",
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }
      }

      items(uiState.missions) { mission ->
        MissionItemCard(
          mission = mission,
          onAction = {
            if (mission.actionType == "REVIEW") {
              onCompleteGoogleReview()
              onOpenExternalUrl("Escribir Reseña Google", IntegrationsConfig.GOOGLE_WRITE_REVIEW_URL)
            }
          }
        )
      }
    }

    // Tab 2: Mi QR Digital y Beneficios de Nivel
    if (selectedTab == 2) {
      item {
        DigitalQrCard(
          member = member,
          modifier = Modifier.padding(16.dp)
        )
      }

      item {
        TierBenefitsCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp))
      }
    }
  }
}

// -------------------------------------------------------------
// Subcomponents for ClubScreen
// -------------------------------------------------------------

@Composable
fun MembershipPassCard(
  member: com.example.data.model.ClubMember,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .shadow(10.dp, RoundedCornerShape(22.dp))
      .clip(RoundedCornerShape(22.dp))
      .testTag("membership_pass_card"),
    colors = CardDefaults.cardColors(containerColor = TaqueriaBlack)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.linearGradient(
            colors = listOf(TaqueriaBlack, Color(0xFF241B18), Color(0xFF151515))
          )
        )
        .border(1.5.dp, Brush.linearGradient(listOf(CriolloYellow, CriolloRedDark)), RoundedCornerShape(22.dp))
        .padding(20.dp)
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text("🌮", fontSize = 24.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "CLUB TAQUERO",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Black,
                  color = Color.White,
                  letterSpacing = 1.sp
                )
              )
              Text(
                text = "EL CRIOLLO · MADRID",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = CriolloYellow,
                  letterSpacing = 1.5.sp
                )
              )
            }
          }

          Surface(
            color = Color(member.tier.badgeColorHex),
            shape = RoundedCornerShape(8.dp)
          ) {
            Text(
              text = member.tier.title.uppercase(),
              color = TaqueriaBlack,
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Bottom
        ) {
          Column {
            Text(
              text = "SOCIO TAQUERO",
              style = MaterialTheme.typography.labelSmall.copy(color = TextSecondaryDark, fontSize = 10.sp)
            )
            Text(
              text = member.name,
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
            )
            Text(
              text = "Miembro desde ${member.memberSince}",
              style = MaterialTheme.typography.bodySmall.copy(color = TextSecondaryDark, fontSize = 11.sp)
            )
          }

          Column(horizontalAlignment = Alignment.End) {
            Text(
              text = "SALDO TOTAL",
              style = MaterialTheme.typography.labelSmall.copy(color = CriolloYellow, fontSize = 10.sp)
            )
            Text(
              text = "${member.points}",
              style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Black,
                color = CriolloYellow,
                lineHeight = 30.sp
              )
            )
            Text(
              text = "PUNTOS",
              style = MaterialTheme.typography.labelSmall.copy(color = CriolloYellowLight, fontWeight = FontWeight.Bold)
            )
          }
        }
      }
    }
  }
}

@Composable
fun RewardItemCard(
  reward: Reward,
  memberPoints: Int,
  onRedeem: () -> Unit
) {
  val canAfford = memberPoints >= reward.pointsCost

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 6.dp)
      .testTag("reward_card_${reward.id}"),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = androidx.compose.foundation.BorderStroke(
      width = 1.dp,
      color = if (canAfford) CriolloYellow.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    ),
    shape = RoundedCornerShape(16.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        Box(
          modifier = Modifier
            .size(46.dp)
            .clip(CircleShape)
            .background(if (canAfford) CriolloRed else MaterialTheme.colorScheme.surfaceVariant),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = when (reward.category) {
              "Bebidas" -> "🍺"
              "Dulces" -> "🍬"
              else -> "🌮"
            },
            fontSize = 20.sp
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
          Text(
            text = reward.title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = reward.description,
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              lineHeight = 15.sp
            ),
            maxLines = 2
          )
          Spacer(modifier = Modifier.height(4.dp))
          Surface(
            color = CriolloYellow.copy(alpha = 0.15f),
            shape = RoundedCornerShape(6.dp)
          ) {
            Text(
              text = "${reward.pointsCost} PTS",
              color = if (canAfford) CriolloRedDark else MaterialTheme.colorScheme.onSurfaceVariant,
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.width(12.dp))

      Button(
        onClick = onRedeem,
        enabled = canAfford,
        colors = ButtonDefaults.buttonColors(
          containerColor = CriolloRed,
          disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.testTag("btn_redeem_${reward.id}")
      ) {
        Text(
          text = if (canAfford) "Canjear" else "Faltan ${reward.pointsCost - memberPoints}p",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = if (canAfford) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
          )
        )
      }
    }
  }
}

@Composable
fun MissionItemCard(
  mission: GamificationMission,
  onAction: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 6.dp)
      .testTag("mission_card_${mission.id}"),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = RoundedCornerShape(16.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        Box(
          modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(if (mission.isCompleted) SalsaGreen.copy(alpha = 0.15f) else CriolloYellow.copy(alpha = 0.2f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = if (mission.isCompleted) Icons.Default.CheckCircle else Icons.Default.Stars,
            contentDescription = null,
            tint = if (mission.isCompleted) SalsaGreen else CriolloRed,
            modifier = Modifier.size(22.dp)
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
          Text(
            text = mission.title,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = mission.description,
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              fontSize = 11.5.sp
            )
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "+${mission.pointsReward} PUNTOS",
            color = SalsaGreen,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black)
          )
        }
      }

      Spacer(modifier = Modifier.width(8.dp))

      if (mission.isCompleted) {
        Surface(
          color = SalsaGreen.copy(alpha = 0.12f),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text(
            text = "HECHO ✓",
            color = SalsaGreen,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      } else {
        Button(
          onClick = onAction,
          colors = ButtonDefaults.buttonColors(containerColor = CriolloRed),
          shape = RoundedCornerShape(10.dp),
          contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
          modifier = Modifier.testTag("btn_action_mission_${mission.id}")
        ) {
          Text("Completar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun DigitalQrCard(
  member: com.example.data.model.ClubMember,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = RoundedCornerShape(20.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "Muestra este QR en Barra",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
      Text(
        text = "Acumula 1 punto por cada euro de consumo",
        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Simulated QR Code Frame
      Surface(
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 4.dp,
        modifier = Modifier.size(170.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Default.QrCode,
            contentDescription = "QR socio",
            tint = TaqueriaBlack,
            modifier = Modifier.size(120.dp)
          )
          Text(
            text = "ID: ${member.id}",
            color = Color.DarkGray,
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = "Código de Socio: ${member.id}",
        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
      )
    }
  }
}

@Composable
fun TierBenefitsCard(modifier: Modifier = Modifier) {
  Card(
    modifier = modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    shape = RoundedCornerShape(16.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(
        text = "Niveles del Club Taquero",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
      Spacer(modifier = Modifier.height(8.dp))
      TierRow("Taquero Novato", "0 - 300 pts", "1 punto por € + bienvenida")
      TierRow("Taquero Aficionado", "301 - 800 pts", "Taco gratis en tu cumpleaños")
      TierRow("Taquero Maestro", "801 - 1400 pts", "1.25x puntos en pedidos")
      TierRow("Taquero Leyenda", "1401+ pts", "Taquiza degustación VIP + 1.5x")
    }
  }
}

@Composable
private fun TierRow(title: String, points: String, benefit: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Column {
      Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
      Text(benefit, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp)
    }
    Text(points, color = CriolloRed, fontWeight = FontWeight.Bold, fontSize = 12.sp)
  }
}
