package com.swis.cogmacproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val publicationDate: String, // Format: yyyy-MM-dd
    val genre: String,
    val isRead: Boolean
)
