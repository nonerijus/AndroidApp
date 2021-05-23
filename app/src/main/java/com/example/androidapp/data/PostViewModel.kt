package com.example.androidapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Post>>
    private val repository: PostRepository

    init {
        val postDao = PostDatabase.getDatabase(application).postDao()
        repository = PostRepository(postDao)
        readAllData = repository.readAllData
    }
    
    fun addPost(post: Post){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPost(post)
        }
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun deleteAllPosts(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllPosts()
        }
    }

}