package com.salanevich.githubapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.salanevich.githubapp.data.database.dao.UserDao
import com.salanevich.githubapp.data.database.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun usersDao(): UserDao
}