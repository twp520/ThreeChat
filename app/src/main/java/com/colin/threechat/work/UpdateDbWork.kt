package com.colin.threechat.work

import androidx.work.Worker
import com.colin.threechat.bean.User
import com.colin.threechat.db.AppDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UpdateDbWork : Worker() {

    override fun doWork(): Result {
        val userDao = AppDataBase.getInstance(applicationContext).userDao()
        val json = inputData.getString(NET_KEY_RESULT, "")
        val type = object : TypeToken<List<User>>() {

        }.type
        val gson = Gson()
        val userList = gson.fromJson<List<User>>(json!!, type)
        userDao.updateUserList(userList)
        return Result.SUCCESS
    }
}