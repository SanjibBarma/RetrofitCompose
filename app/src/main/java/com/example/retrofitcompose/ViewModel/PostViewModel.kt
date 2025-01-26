package com.example.retrofitcompose.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitcompose.Helper.ConnectivityObserver
import com.example.retrofitcompose.Helper.UIState
import com.example.retrofitcompose.Helper.formatPostsAsJson
import com.example.retrofitcompose.Model.Post
import com.example.retrofitcompose.Model.PostEntity
import com.example.retrofitcompose.Repository.PostRepository
import kotlinx.coroutines.launch

class PostViewModel(
    private val repository: PostRepository,
    private val roomPostViewModel: RoomPostViewModel,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _posts = MutableLiveData<UIState<List<Post>>>()
    val posts: LiveData<UIState<List<Post>>> = _posts

    fun loadPosts() {
        viewModelScope.launch {
            if (connectivityObserver.checkInternetConnection()) {
                _posts.value = UIState.Loading
                try {
                    val response = repository.fetchPosts()
                    if (response.isNotEmpty()) {
                        _posts.value = UIState.Success(response)
                        for (i in 0 until response.size){
                            roomPostViewModel.upsertData(
                                PostEntity(
                                    id = response.get(i).id,
                                    userId = response.get(i).userId,
                                    title = response.get(i).title,
                                    value = response.get(i).body
                                )
                            )
                        }

                    } else {
                        _posts.value = UIState.Success(emptyList())
                    }
                } catch (e: Exception) {
                    _posts.value = UIState.Error(e)
                }
            } else {
                _posts.value = UIState.Error(Exception("No Internet Connection!"))
            }
        }
    }
}
