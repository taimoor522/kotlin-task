//noinspection UsingMaterialAndMaterial3Libraries

package com.example.switchkotlintask.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.switchkotlintask.data.models.NewsArticle
import com.example.switchkotlintask.viewmodel.NewsState
import com.example.switchkotlintask.viewmodel.NewsViewModel

@Composable
fun NewsListScreen(viewModel: NewsViewModel) {
    val newsState by viewModel.newsState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchTopHeadlines()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("News App")
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.fetchTopHeadlines()
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Clear")
                    }
                }
            )

        })
    { paddingValues ->
        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (newsState) {
                // loading or initial
                is NewsState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.width(50.dp))
                }

                is NewsState.Success -> {
                    Column {
                        // text field
                        TextField(
                            value = query,
                            onValueChange = { it ->
                                query = it
                                viewModel.filterArticles(it)
                            },
                            label = { Text("Search") },
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                if (query.isNotEmpty()) {
                                    IconButton(onClick = {
                                        query = "";
                                        viewModel.filterArticles("");
                                        keyboardController?.hide()
                                    }) {
                                        Icon(Icons.Default.Close, contentDescription = "Clear")
                                    }
                                }
                            }
                        )

                        // news list
                        NewsList((newsState as NewsState.Success).articles)
                    }
                }

                is NewsState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            (newsState as NewsState.Error).message,
                            style = TextStyle(
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun NewsList(articles: List<NewsArticle>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(articles) { article ->
            NewsItem(article)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun NewsItem(article: NewsArticle) {
    Card(elevation = 4.dp, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(10.dp)) {
            article.imageUrl?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(article.title, style = MaterialTheme.typography.h6)
            article.description?.let {
                Text(it, style = MaterialTheme.typography.body2)
            }
        }
    }
}