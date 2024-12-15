//package com.example.diet_app.ui.theme
//
//import android.app.Activity
//import android.os.Build
//import androidx.compose.foundation.isSystemInDarkTheme
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.darkColorScheme
//import androidx.compose.material3.dynamicDarkColorScheme
//import androidx.compose.material3.dynamicLightColorScheme
//import androidx.compose.material3.lightColorScheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.platform.LocalContext
//
//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)
//
//@Composable
//fun Diet_appTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
//
//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = Typography,
//        content = content
//    )
//}

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.diet_app.ui.theme.JuaFont

private val DietAppColorScheme = lightColorScheme(
    primary = Color(0xFFf2aa79),      // 상단 앱바 배경색
    onPrimary = Color.White,          // 앱바 텍스트 색상을 흰색으로 변경
    secondary = Color(0xFFE0E0E0),    // 밝은 회색 (버튼 배경색)
    onSecondary = Color(0xFF333333),  // 어두운 회색 (버튼 텍스트 색)
    surface = Color.White,      // 흰색 (카드 배경색)
    onSurface = Color(0xFF333333),    // 어두운 회색 (카드 내용 텍스트 색)
    background = Color(0xFFF5F5F5),   // 밝은 회색 (앱 배경색)
    onBackground = Color(0xFF333333)  // 어두운 회색 (일반 텍스트 색)
)

//private val DietAppTypography = Typography(
//    headlineLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Bold,
//        fontSize = 32.sp,
//        lineHeight = 40.sp,
//    ),
//    headlineMedium = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 24.sp,
//        lineHeight = 32.sp,
//    ),
//    titleLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 22.sp,
//        lineHeight = 28.sp,
//    ),
//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//    )
//)

val DietAppTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = JuaFont,
        fontWeight = FontWeight.Normal,  // Jua는 Regular 400만 있음
        fontSize = 32.sp,
        lineHeight = 40.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = JuaFont,
        fontWeight = FontWeight.Normal,  // Jua는 Regular 400만 있음
        fontSize = 24.sp,
        lineHeight = 32.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = JuaFont,
        fontWeight = FontWeight.Normal,  // Jua는 Regular 400만 있음
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
       fontSize = 16.sp,
        lineHeight = 24.sp,
    )
)


@Composable
fun DietAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DietAppColorScheme,
        typography = DietAppTypography,
        content = content
    )
}
