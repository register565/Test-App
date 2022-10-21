package com.example.testapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.testapp.models.PostsModelList
import com.example.testapp.models.UserModelList
import kotlinx.coroutines.flow.StateFlow


@Composable
fun postsScreen(
    userState: StateFlow<UserModelList>,
    userNews: MutableState<PostsModelList>
) {
    val User = userState.collectAsState()
    val userList = userNews.value.userList

    Column {
        Card(
            shape = RoundedCornerShape(bottomStartPercent = 10, bottomEndPercent = 10),
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = User.value.userList.first { it.userId == userList.first().userId }.url,
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(200.dp),
                contentScale = ContentScale.Crop

            )
        }
        LazyColumn {
            item { }
            items(userList.size) { content ->
                val currentNews = userList[content]
                userNews(
                    title = currentNews.title,
                    body = currentNews.body
                )
            }
        }
    }
}

@Composable
fun userNews(
    title: String,
    body: String,
) {
    Card(
        shape = RoundedCornerShape(10),
        border = BorderStroke(2.dp, color = Color.Black),
        modifier = Modifier
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(all = 10.dp)
            ) {
                Text(
                    text = title,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.padding(top = 20.dp))
                Text(text = body, color = Color.Black, textAlign = TextAlign.Center)
            }
        }
    }
}