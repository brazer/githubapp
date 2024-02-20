package com.salanevich.githubapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("login") val login: String,
    @ColumnInfo("web_page") val webPage: String,
    @ColumnInfo("avatar_url") val avatarUrl: String
)