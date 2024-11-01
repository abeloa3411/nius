package com.example.nius

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kwabenaberko.newsapilib.models.Article

@Composable
fun Homepage(NewViewModel: NewsViewModek) {

    val articles by NewViewModel.articles.observeAsState(emptyList())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        CategoriesBar(NewViewModel)

        LazyColumn (
            modifier = Modifier.fillMaxSize()
        ){
            items(articles){article->
               ArticleItem(article)

            }
        }
    }

}
@Composable
fun ArticleItem(article: Article){
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

    ) {
        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
        AsyncImage(
            model = article.urlToImage?:"https://www.google.com/imgres?q=no%20image%20found&imgurl=https%3A%2F%2Ft4.ftcdn.net%2Fjpg%2F04%2F70%2F29%2F97%2F360_F_470299797_UD0eoVMMSUbHCcNJCdv2t8B2g1GVqYgs.jpg&imgrefurl=https%3A%2F%2Fstock.adobe.com%2Fsearch%2Fimages%3Fk%3Dno%2Bimage%2Bavailable&docid=1vyMlkbWAVkc5M&tbnid=nlhazLcE-1yJzM&vet=12ahUKEwia7ZrP1JeJAxWJXfEDHZxaDk8QM3oECG4QAA..i&w=480&h=360&hcb=2&ved=2ahUKEwia7ZrP1JeJAxWJXfEDHZxaDk8QM3oECG4QAA",
            contentDescription = "Article Image",
            modifier = Modifier.size(80.dp).aspectRatio(1f),
            contentScale = ContentScale.Crop
        )

            Column (
                modifier = Modifier.fillMaxSize().padding(start = 8.dp)
            ){
                Text(article.title,
                    fontWeight = FontWeight.Bold
                )
                Text(article.source.name, maxLines = 1,fontSize = 14.sp)
            }
        }


    }
}


@Composable
fun CategoriesBar(NewViewModel: NewsViewModek){

    var searchQuery by remember {
        mutableStateOf("")
    }

    var searchBarEpanded by remember {
        mutableStateOf(false)
    }

    val categoriesList = listOf(
        "GENERAL",
        "BUSINESS",
        "ENTERTAINMENT",
        "WEALTH",
        "SCIENCE",
        "SPORTS",
        "TECHNOLOGY"
    )


    Row (
        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())
    ){

        if(searchBarEpanded){
            OutlinedTextField(
                modifier = Modifier.padding(8.dp)
                    .height(48.dp)
                    .border(1.dp, Color.Gray, CircleShape)
                    .clip(CircleShape),
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                trailingIcon = {
                    IconButton(onClick = {
                        searchBarEpanded = true
                        if(searchQuery.isNotEmpty()){
                            NewViewModel.getEverything(searchQuery)
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
                    }
                }
            )
        }else{
            IconButton(onClick = {
                searchBarEpanded = true
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
            }
        }

        categoriesList.forEach { category ->
            Button(onClick = {
                NewViewModel.getTopNews(category)
            },
                modifier = Modifier.padding(4.dp)
                ) {
                Text(category)
            }
        }
    }
}