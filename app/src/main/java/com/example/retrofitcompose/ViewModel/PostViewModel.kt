package com.example.retrofitcompose.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitcompose.Helper.ConnectivityObserver
import com.example.retrofitcompose.Helper.UIState
import com.example.retrofitcompose.Helper.formatPostsAsJson
import com.example.retrofitcompose.Model.Post
import com.example.retrofitcompose.Model.PostEntity
import com.example.retrofitcompose.Repository.PostRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(
    private val repository: PostRepository,
    private val roomPostViewModel: RoomPostViewModel,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _posts = MutableStateFlow<UIState<List<Post>>>(UIState.Loading)
    val posts: StateFlow<UIState<List<Post>>> = _posts

    var formatPostsAsJson = ""


    fun loadPosts() {
        viewModelScope.launch {

            if (connectivityObserver.checkInternetConnection()){
                _posts.value = UIState.Loading
//                delay(1000)
                try {
                    val response = repository.fetchPosts()
                    println("API Response: $response")
                    if (response.isNotEmpty()){
                        _posts.value = UIState.Success(response)
                        println("Api calling randomly...")

                        formatPostsAsJson = formatPostsAsJson(response)

                        if (formatPostsAsJson.isNotEmpty()) {
                            var title = ""
                            if (roomPostViewModel.getPostDataSize() == 0){
                                title = "Post Title: 1"
                            }else{
                                title = "Post Title: ${roomPostViewModel.getPostDataSize()+1}"
                            }

                            roomPostViewModel.addData(PostEntity(value = formatPostsAsJson, title = title))
                        }
                    }else{
                        _posts.value = UIState.Success(emptyList())
                    }
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                    _posts.emit(UIState.Error(e))
                }
            }else{
                _posts.emit(UIState.Error(Exception("No Internet Connection!")))
            }
        }
    }
}
