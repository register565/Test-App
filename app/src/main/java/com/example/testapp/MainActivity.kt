package com.example.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testapp.models.PostsModelList
import com.example.testapp.repo.Repo
import com.example.testapp.screens.postsScreen
import com.example.testapp.screens.usersScreen
import com.example.testapp.ui.theme.TestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val model = Repo()
        model.getUserNews()
        model.getPost()

        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            TestAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF6F975F)
                ) {
                    val currentUserNewsForDisplaying =
                        remember { mutableStateOf(PostsModelList(listOf())) }

                    NavHost(navController, startDestination = "usersScreen") {
                        composable("usersScreen", content = {
                            usersScreen(
                                model.userNewsState,
                                model.postState,
                                navController,
                                currentUserNewsForDisplaying
                            )
                        })
                        composable("postsScreen", content = {
                            postsScreen(
                                model.userNewsState,
                                currentUserNewsForDisplaying
                            )
                        })
                    }
                }
            }
        }
    }
}