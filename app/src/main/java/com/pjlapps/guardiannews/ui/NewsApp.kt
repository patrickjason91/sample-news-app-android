package com.pjlapps.guardiannews.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.pjlapps.guardiannews.MainViewModel
import com.pjlapps.guardiannews.R
import com.pjlapps.guardiannews.ui.theme.ColorTopBar
import com.pjlapps.guardiannews.ui.theme.ColorTopBarIcons

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsApp(
    viewModel: MainViewModel
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                colors = topAppBarColors(
                    containerColor = ColorTopBar,
                    titleContentColor = Color.White,
                    navigationIconContentColor = ColorTopBarIcons,
                    actionIconContentColor = ColorTopBarIcons
                )
            )
        }
    ) { innerPadding ->
        NewsNav(
            innerPadding,
            navController,
            viewModel
        )
    }
}