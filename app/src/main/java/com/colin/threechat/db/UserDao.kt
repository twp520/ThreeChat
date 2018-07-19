package com.colin.threechat.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.colin.threechat.bean.User

@Dao
interface UserDao {

    @Query("select * from t_user")
    fun getAll(): LiveData<MutableList<User>>

    @Query("select count(*) from t_user")
    fun getCount(): Int

    @Query("select * from t_user where id!=:loginId")
    fun getUserListExcept(loginId: Int): LiveData<MutableList<User>>

    @Insert
    fun addUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUserList(users: List<User>)

    @Update
    fun updateUserList(users: List<User>)

    @Delete
    fun deleteUser(user: User)

    @Delete
    fun deleteUserList(user: List<User>)
}