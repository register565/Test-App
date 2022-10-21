package com.example.testapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.models.PostsModelList
import com.example.testapp.models.UserModelList
import com.example.testapp.repository.RetrofitServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CustomViewModel : ViewModel() {
    private var newsService = RetrofitServer()

    private val _userNewsState: MutableStateFlow<UserModelList> = MutableStateFlow(
        UserModelList(listOf())
    )

    val userNewsState = _userNewsState.asStateFlow()

    private val _postState: MutableStateFlow<PostsModelList> = MutableStateFlow(
        PostsModelList(listOf())
    )

    val postState = _postState.asStateFlow()

    fun getUserNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = newsService.retrofit().getUserInfo()

            _userNewsState.update {
                it.copy(userList = response.body()!!)
            }
            Log.e("", _userNewsState.value.userList.toString())
        }
    }

    fun getPost() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = newsService.retrofit().getUserPost()

            _postState.update {
                it.copy(userList = response.body()!!)
            }
            Log.e("", _postState.value.userList.toString())
        }
    }
}