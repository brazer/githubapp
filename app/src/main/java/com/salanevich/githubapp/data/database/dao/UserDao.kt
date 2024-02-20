package com.salanevich.githubapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.salanevich.githubapp.data.database.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun putUsers(users: List<UserEntity>)

    @Query("SELECT * FROM userentity")
    suspend fun getUsers(): List<UserEntity>

}