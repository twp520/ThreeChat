package com.colin.threechat.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.colin.threechat.LogUtil
import com.colin.threechat.bean.User
import com.colin.threechat.data.UserRepository
import com.colin.threechat.db.AppDataBase

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private var mObservableUsers: MediatorLiveData<MutableList<User>>? = null

    init {
        val userRepo = UserRepository(AppDataBase.getInstance(application.applicationContext).userDao())
        mObservableUsers = MediatorLiveData()
        mObservableUsers?.value = null
        mObservableUsers?.addSource(userRepo.getAllUsers()) {
            LogUtil.e("addSource ---> changed  size = ${it?.size}")
            mObservableUsers?.postValue(it)
        }

    }

    fun getUsers(): LiveData<MutableList<User>>? {
        return mObservableUsers
    }

}