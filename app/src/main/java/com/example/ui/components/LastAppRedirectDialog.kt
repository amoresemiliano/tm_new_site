package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun LastAppRedirectDialog(
  title: String?,
  url: String?,
  onDismiss: () -> Unit,
  onProceed: (String) -> Unit
) {
  if (title == null || url == null) return

  AlertDialog(
    onDismissRequest = onDismiss,
    containerColor = MaterialTheme.colorScheme.surface,
    shape = RoundedCornerShape(20.dp),
    modifier = Modifier.testTag("lastapp_redirect_dialog"),
    title = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(text = "🌮", fontSize = 36.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = title,
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
      }
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = "Te estamos conectando con la plataforma oficial de pedidos y reservas de El Criollo.",
          style = MaterialTheme.typography.bodyMedium.copy(
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Surface(
          color = TortillaCream,
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = "Destino: $url",
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 11.sp,
              color = TextSecondaryLight
            ),
            modifier = Modifier.padding(8.dp),
            maxLines = 2
          )
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          onProceed(url)
          onDismiss()
        },
        colors = ButtonDefaults.buttonColors(
          containerColor = CriolloRed,
          contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.testTag("btn_proceed_redirect")
      ) {
        Icon(imageVector = Icons.Default.Launch, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text("Continuar", fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      TextButton(
        onClick = onDismiss,
        modifier = Modifier.testTag("btn_cancel_redirect")
      ) {
        Text("Volver", color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
    }
  )
}
