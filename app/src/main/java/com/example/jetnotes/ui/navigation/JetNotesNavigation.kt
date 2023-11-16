package com.example.jetnotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetnotes.ui.screens.Bookmark.BookMarkScreenViewModel
import com.example.jetnotes.ui.screens.Bookmark.BookmarkScreen
import com.example.jetnotes.ui.screens.detail.DetailScreen
import com.example.jetnotes.ui.screens.detail.DetailsAssistedFactory
import com.example.jetnotes.ui.screens.home.HomeScreen
import com.example.jetnotes.ui.screens.home.HomeScreenViewModel

enum class Screens {
    Home, Detail, Bookmark
}

@Composable
fun NoteNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel,
    bookmarkScreenViewModel: BookMarkScreenViewModel,
    assistedFactory: DetailsAssistedFactory,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.Home.name
    ) {
        composable(route = Screens.Home.name) {
            val state by homeScreenViewModel.uiState.collectAsState()
            HomeScreen(
                state = state,
                onBookmarkChange = homeScreenViewModel::updateBookmark,
                onDeleteNote = homeScreenViewModel::deleteNote,
                onNoteClicked = {
                    navHostController.navigateToSingleTop(
                        route = "${Screens.Detail.name}?id=$it"
                    )
                }
            )
        }
        composable(route = Screens.Bookmark.name) {
            val state by bookmarkScreenViewModel.uiState.collectAsState()
            BookmarkScreen(
                state = state,
                modifier = modifier,
                onBookmarkChange = bookmarkScreenViewModel::onBookmarkChange,
                onDeleteNote = bookmarkScreenViewModel::deleteNote,
                onNoteClicked = {
                    navHostController.navigateToSingleTop(
                        route = "${Screens.Detail.name}?id=$it"
                    )
                }
            )
        }
        composable(
            route = "${Screens.Detail.name}?id={id}",
            arguments = listOf(
                navArgument("id") {
                    NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: -1L
            DetailScreen(
                noteId = id,
                assistedFactory = assistedFactory,
                navigateUp = { navHostController.navigateUp() }
            )
        }
    }

}

fun NavHostController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}