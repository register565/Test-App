package com.example.testapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.testapp.models.PostsModelList
import com.example.testapp.models.UserModelList
import kotlinx.coroutines.flow.StateFlow


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun usersScreen(
    usersCardState: StateFlow<UserModelList>,
    userPostState: StateFlow<PostsModelList>,
    navController: NavHostController,
    currentUserNews: MutableState<PostsModelList>
) {
    val Users = usersCardState.collectAsState()
    val Posts = userPostState.collectAsState()

    LazyColumn {
        items(Users.value.userList.size) { content ->
            userCard(
                userId = Users.value.userList[content].userId,
                url = Users.value.userList[content].thumbnailUrl,
                name = Users.value.userList[content].name,
                Posts,
                click = {
                    navController.navigate("postsScreen")
                    currentUserNews.value = PostsModelList(
                        Posts.value.userList.filter {
                            it.userId == Users.value.userList[content].userId
                        }
                    )
                }
            )
        }
    }
}

fun postsCount(Posts: State<PostsModelList>, id: Int): Int {
    var q = 0
    for (i in Posts.value.userList) {
        if (id == i.userId) {
            q++
        }
    }
    return q
}

@Composable
fun userCard(
    userId: Int,
    url: String,
    name: String,
    Posts: State<PostsModelList>,
    click: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10),
        border = BorderStroke(4.dp, color = Color(0xFF6BC081)),
        modifier = Modifier
            .padding(10.dp)
            .height(160.dp)
            .clickable {
                click()
            },
        backgroundColor = Color(0xFFCAE0C6)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 50.dp)
        ) {
            Column {
                AsyncImage(
                    model = url,
                    contentDescription = "Contact profile picture",
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(80.dp)
                        .border(2.dp, Color(0x86000000), CircleShape)
                )
            }
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = name,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.padding(top = 10.dp))
                Text(
                    text = "<Posts count : ${postsCount(Posts, userId)}>",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}