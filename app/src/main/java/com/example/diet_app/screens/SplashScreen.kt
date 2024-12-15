package com.example.diet_app.screens

// 앱 시작 시 잠시 띄워지는 화면

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.diet_app.R
import androidx.navigation.compose.rememberNavController
import com.example.diet_app.ui.theme.JuaFont

@Composable
fun SplashScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 중앙의 이미지

        Image(
            painter = painterResource(R.drawable.sitdown_acco),
            contentDescription = "Splash Image",
            modifier = Modifier.size(200.dp)
        )


        Spacer(modifier = Modifier.height(10.dp))

        // 텍스트
        Text(
            text = "dgu 개인 식사 관리 어플",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontFamily = JuaFont,
                color = Color.Gray
            )
        )
        Image(
            painter = painterResource(R.drawable.dgu_logo),
            contentDescription = "Logo Image",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 자동 홈 화면 전환 로직
        LaunchedEffect(key1 = true) {
            delay(1000)  // 1초 대기
            navController.navigate("home") {
                // 스플래시 화면을 스택에서 제거하여 뒤로 가기 버튼을 눌렀을 때 다시 보이지 않게 함
                popUpTo("splash") { inclusive = true }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    // NavController를 모킹
    val navController = rememberNavController()

    // SplashScreen 호출
    SplashScreen(navController = navController)
}
