package com.example.jetnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.jetnotes.ui.screens.Bookmark.BookMarkScreenViewModel
import com.example.jetnotes.ui.screens.detail.DetailsAssistedFactory
import com.example.jetnotes.ui.screens.home.HomeScreenViewModel
import com.example.jetnotes.ui.screens.scaffold.NotesScaffold
import com.example.jetnotes.ui.theme.JetNotesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var assistedFactory: DetailsAssistedFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    JetNotesApp()
                }
            }
        }
    }

    enum class TabsScreen {
        Home, Bookmark
    }


    @Composable
    fun JetNotesApp() {
        val homeScreenViewModel: HomeScreenViewModel = viewModel()
        val bookMarkScreenViewModel: BookMarkScreenViewModel = viewModel()
        val navController = rememberNavController()

        NotesScaffold(
            homeScreenViewModel,
            bookMarkScreenViewModel,
            assistedFactory,
            navController
        )
    }

}
