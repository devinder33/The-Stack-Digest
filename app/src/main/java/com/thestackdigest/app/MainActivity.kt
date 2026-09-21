package com.thestackdigest.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.thestackdigest.app.ui.feed.FeedRoute
import com.thestackdigest.app.ui.theme.TheStackDigestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheStackDigestTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    FeedRoute(innerPadding)
                }
            }
        }
    }
}

