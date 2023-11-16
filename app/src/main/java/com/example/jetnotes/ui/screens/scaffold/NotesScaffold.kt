package com.example.jetnotes.ui.screens.scaffold

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetnotes.MainActivity
import com.example.jetnotes.R
import com.example.jetnotes.ui.navigation.NoteNavigation
import com.example.jetnotes.ui.navigation.Screens
import com.example.jetnotes.ui.navigation.navigateToSingleTop
import com.example.jetnotes.ui.screens.Bookmark.BookMarkScreenViewModel
import com.example.jetnotes.ui.screens.detail.DetailsAssistedFactory
import com.example.jetnotes.ui.screens.home.HomeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScaffold(
    homeScreenViewModel: HomeScreenViewModel,
    bookMarkScreenViewModel: BookMarkScreenViewModel,
    assistedFactory: DetailsAssistedFactory,
    navController: NavHostController,
) {
    var currentTab by remember {
        mutableStateOf(MainActivity.TabsScreen.Home)
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(actions = {
                Row(horizontalArrangement = Arrangement.Center) {
                    InputChip(
                        selected = currentTab == MainActivity.TabsScreen.Home,
                        onClick = {
                            currentTab = MainActivity.TabsScreen.Home
                            navController.navigateToSingleTop(route = Screens.Home.name)
                        },
                        label = { Text(text = "Home") },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_home),
                                contentDescription = "Tab"
                            )
                        }
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    InputChip(
                        selected = currentTab == MainActivity.TabsScreen.Bookmark,
                        onClick = {
                            currentTab = MainActivity.TabsScreen.Bookmark
                            navController.navigateToSingleTop(route = Screens.Bookmark.name)
                        },
                        label = { Text(text = "Bookmarked") },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_bookmarked),
                                contentDescription = "Tab"
                            )
                        }
                    )
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigateToSingleTop(route = Screens.Detail.name) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_floating),
                    contentDescription = "Add"
                )

            }
        }
    ) { innerPadding ->
        NoteNavigation(
            modifier = Modifier.padding(innerPadding),
            navHostController = navController,
            homeScreenViewModel = homeScreenViewModel,
            bookmarkScreenViewModel = bookMarkScreenViewModel,
            assistedFactory = assistedFactory
        )
    }
}