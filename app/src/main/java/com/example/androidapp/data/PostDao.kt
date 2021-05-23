package com.example.androidapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PostDao {
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPost(post: Post)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    //suspend fun getUserAndPostWith()

    @Query("SELECT * FROM post_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Post>>

    @Query("DELETE FROM post_table")
    fun deleteAllPosts()


}