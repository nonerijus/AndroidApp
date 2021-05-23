package com.example.androidapp.data

import androidx.lifecycle.LiveData

class PostRepository(private val postDao: PostDao) {

    val readAllData: LiveData<List<Post>> = postDao.readAllData()

    suspend fun addPost(post: Post){
        postDao.addPost(post)
    }

    suspend fun addUser(user: User){
        postDao.addUser(user)
    }

    suspend fun deleteAllPosts(){
        postDao.deleteAllPosts()
    }
}