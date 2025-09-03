package com.example.a0903newsapi

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a0903newsapi.ui.theme._0903NewsApiTheme



@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: NewsViewModel = viewModel()

            LaunchedEffect(Unit) {

                viewModel.fetchNews(query = "코로나")
            }

            NewsListScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(viewModel: NewsViewModel = viewModel()) {
    val newslist by viewModel.newsList.collectAsState()
    val urihandler = LocalUriHandler.current


    Scaffold(
        topBar = { TopAppBar(title = {Text("Search Naver News")}) }
    ) { padding ->
        TextField(
            value = "",
            onValueChange = {
                viewModel.fetchNews(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        LazyColumn (
            modifier = Modifier
                .padding(padding)
        ) {
            items(newslist) { news ->
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .clickable {
                            urihandler.openUri(news.link)
                        }
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = news.title, style = MaterialTheme.typography.titleMedium)
                        Text(text = news.description, style = MaterialTheme.typography.bodyMedium)
                        Text(text = news.pubDate, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}