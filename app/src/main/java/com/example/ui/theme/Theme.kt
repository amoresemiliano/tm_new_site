package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = CriolloRedLight,
  onPrimary = Color.White,
  primaryContainer = CriolloRedDark,
  onPrimaryContainer = CriolloYellowLight,
  secondary = CriolloYellow,
  onSecondary = TaqueriaBlack,
  secondaryContainer = Color(0xFF3E2723),
  onSecondaryContainer = CriolloYellow,
  tertiary = SalsaGreenLight,
  onTertiary = Color.White,
  background = TaqueriaBlack,
  onBackground = TextPrimaryDark,
  surface = TaqueriaDarkSurface,
  onSurface = TextPrimaryDark,
  surfaceVariant = TaqueriaDarkSurfaceElevated,
  onSurfaceVariant = TextSecondaryDark,
  outline = GrayBorderDark,
  error = NeonRed
)

private val LightColorScheme = lightColorScheme(
  primary = CriolloRed,
  onPrimary = Color.White,
  primaryContainer = TortillaCream,
  onPrimaryContainer = CriolloRedDark,
  secondary = CriolloOrange,
  onSecondary = Color.White,
  secondaryContainer = CriolloYellowLight,
  onSecondaryContainer = TaqueriaBlack,
  tertiary = SalsaGreen,
  onTertiary = Color.White,
  background = TortillaCream,
  onBackground = TextPrimaryLight,
  surface = SubwayTileWhite,
  onSurface = TextPrimaryLight,
  surfaceVariant = Color(0xFFF0ECE4),
  onSurfaceVariant = TextSecondaryLight,
  outline = GrayBorder,
  error = CriolloRed
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // For El Criollo we use our distinct branded theme colors
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
