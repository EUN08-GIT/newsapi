package com.example.a0903newsapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.a0903newsapi.room.ItemsDatabase
import com.example.a0903restapi.repository.NewsRepository


@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. DB, Repository, Factory, ViewModel 생성
        val dao = ItemsDatabase.getDatabase(applicationContext).itemsDao()
        val repository = NewsRepository()
        val factory = RoomDBViewModelFactory(dao, repository)

        val viewModel: RoomDBViewModel =
            androidx.lifecycle.ViewModelProvider(this, factory)
                .get(RoomDBViewModel::class.java)

        // 2. Compose UI 연결
        setContent {
            // fetchNews 호출도 Compose 안에서 LaunchedEffect로 가능
            LaunchedEffect(Unit) {
                viewModel.fetchNews("윤")
            }

            DbScreen(viewModel)
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun NewsListScreen(viewModel: NewsViewModel = viewModel()) {
//    val newslist by viewModel.newsList.collectAsState()
//    val urihandler = LocalUriHandler.current
//
//
//    Scaffold(
//        topBar = { TopAppBar(title = {Text("Search Naver News")}) }
//    ) { padding ->
//        TextField(
//            value = "",
//            onValueChange = {
//                viewModel.fetchNews(it)
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        )
//        LazyColumn (
//            modifier = Modifier
//                .padding(padding)
//        ) {
//            items(newslist) { news ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(8.dp)
//                        .clickable {
//                            urihandler.openUri(news.link)
//                        }
//                ) {
//                    Column(modifier = Modifier.padding(8.dp)) {
//                        Text(text = news.title, style = MaterialTheme.typography.titleMedium)
//                        Text(text = news.description, style = MaterialTheme.typography.bodyMedium)
//                        Text(text = news.pubDate, style = MaterialTheme.typography.bodySmall)
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
fun DbScreen(viewModel: RoomDBViewModel) {
    val newsList by viewModel.newsList.collectAsState()

    LazyColumn {
        items(newsList) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = item.title, style = MaterialTheme.typography.titleMedium)
                    Text(text = item.description, style = MaterialTheme.typography.bodyMedium)
                    Text(text = item.pubDate, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
