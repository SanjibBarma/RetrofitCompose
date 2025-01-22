package com.example.retrofitcompose.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitcompose.Helper.UIState
import com.example.retrofitcompose.Helper.formatPostsAsJson
import com.example.retrofitcompose.Model.Post
import com.example.retrofitcompose.Model.PostEntity
import com.example.retrofitcompose.Repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(
    private val repository: PostRepository,
    private val roomPostViewModel: RoomPostViewModel
) : ViewModel() {

//    private val _posts = MutableStateFlow<List<Post>>(emptyList())
//    val posts: StateFlow<List<Post>> = _posts
//
//    private val _isLoading = MutableStateFlow(false)
//    val isLoading: StateFlow<Boolean> = _isLoading
//
//    private val _errorMessage = MutableStateFlow<String?>(null)
//    val errorMessage: StateFlow<String?> = _errorMessage

    private val _posts = MutableStateFlow<UIState<List<Post>>>(UIState.Loading)
    val posts: StateFlow<UIState<List<Post>>> = _posts

    var formatPostsAsJson = ""


    fun loadPosts() {
        viewModelScope.launch {
//            _isLoading.value = true
//            _errorMessage.value = null
            _posts.value = UIState.Loading
            try {
                val response = repository.fetchPosts()
                println("API Response: $response")
//                _posts.value = response
                if (response.isNotEmpty()){
                    _posts.value = UIState.Success(response)

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
//                _errorMessage.value = "Error: ${e.message}"
//                _isLoading.value = false
                _posts.value = UIState.Error(e)
            }
        }
    }

}
