package com.example.androidapp.data.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.androidapp.data.Post
import com.example.androidapp.data.User

data class UserAndPost(

        @Embedded val user: User,
        @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val post: Post
)