package com.winphyoethu.entain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.winphyoethu.entain.racing.navigation.RacingScreen
import com.winphyoethu.entain.racing.navigation.createRacingScreen
import com.winphyoethu.entain.designsystem.ui.theme.EntainTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EntainTheme(dynamicColor = false) {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = RacingScreen) {
                        createRacingScreen()
                    }
//                }
            }
        }
    }
}