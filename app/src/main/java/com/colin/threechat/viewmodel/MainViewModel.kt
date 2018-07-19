package com.colin.threechat.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.colin.threechat.bean.User
import com.colin.threechat.data.UserRepository
import com.colin.threechat.db.AppDataBase

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var userRepo: UserRepository? = null

    init {
        userRepo = UserRepository(AppDataBase.getInstance(application.applicationContext).userDao())
    }


    fun getUsersExcept(id: Int): LiveData<MutableList<User>>? {
        return userRepo?.getUsersExceptId(id)
    }
}