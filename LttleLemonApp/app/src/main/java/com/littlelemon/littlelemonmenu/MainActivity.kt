package com.littlelemon.littlelemonmenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.room.Room
import loginPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isLoggedIn by remember { mutableStateOf(false) }
            val materialBlue700 = Color(0xFF1976D2)

            if (isLoggedIn) {
                val appDatabase = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "books"
                ).build()
                RecyclerPage(appDatabase)
            }
            else
            {
                loginPage {
                    isLoggedIn = true
                }
            }
        }
    }
}
//            val materialBlue700 = Color(0xFF1976D2)
//            val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
//            Scaffold(
//                scaffoldState = scaffoldState,
//                topBar = {
//                    TopAppBar(
//                        title = { Text(resources.getString(R.string.app_name)) },
//                        contentColor = Color.White,
//                        backgroundColor = materialBlue700
//                    )
//                },
//                content = { paddingValues ->
//                        MenuContent(paddingValues = paddingValues)
//                }
//            )
//        }
//    }
//}


//@Composable
//fun MenuContent(paddingValues: PaddingValues) {
//    Surface(modifier = Modifier.padding(paddingValues)) {
//        val menuPadding = 8.dp
//        // region Thomas, the code the student needs to add starts here.
//        val configuration = LocalConfiguration.current
//        when (configuration.orientation) {
//            ORIENTATION_LANDSCAPE -> {
//                Column {
//                    Row(modifier = Modifier.weight(0.5f)) {
//                        Text(
//                            "Appetizers",
//                            modifier = Modifier
//                                .weight(0.25f)
//                                .fillMaxHeight()
//                                .background(Purple80)
//                                .padding(menuPadding)
//                        )
//                        Text(
//                            "Salads",
//                            modifier = Modifier
//                                .weight(0.25f)
//                                .fillMaxHeight()
//                                .padding(menuPadding)
//                        )
//                    }
//                    Row(modifier = Modifier.weight(0.5f)) {
//                        Text(
//                            "Drinks",
//                            modifier = Modifier
//                                .weight(0.25f)
//                                .fillMaxHeight()
//                                .background(Pink80)
//                                .padding(menuPadding)
//                        )
//                        Text(
//                            "Mains",
//                            modifier = Modifier
//                                .weight(0.25f)
//                                .fillMaxHeight()
//                                .background(PurpleGrey80)
//                                .padding(menuPadding)
//                        )
//                    }
//                }
//            }
//
//            else -> {
//                Column {
//                    Text(
//                        "Appetizers",
//                        modifier = Modifier
//                            .weight(0.25f)
//                            .background(Purple80)
//                            .padding(menuPadding)
//                            .fillMaxWidth()
//                    )
//                    Text(
//                        "Salads",
//                        modifier = Modifier
//                            .weight(0.25f)
//                            .padding(menuPadding)
//                            .fillMaxWidth()
//                    )
//                    Text(
//                        "Drinks",
//                        modifier = Modifier
//                            .weight(0.25f)
//                            .background(Pink80)
//                            .padding(menuPadding)
//                            .fillMaxWidth()
//                    )
//                    Text(
//                        "Mains",
//                        modifier = Modifier
//                            .weight(0.25f)
//                            .background(PurpleGrey80)
//                            .padding(menuPadding)
//                            .fillMaxWidth()
//                    )
//                }
//            }
//        }
//    }
//}
