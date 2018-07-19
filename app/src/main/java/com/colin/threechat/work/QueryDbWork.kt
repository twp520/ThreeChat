package com.colin.threechat.work

import androidx.work.Data
import androidx.work.Worker
import com.colin.threechat.db.AppDataBase
import com.google.gson.Gson

class QueryDbWork : Worker() {

    override fun doWork(): Result {
        val userDao = AppDataBase.getInstance(applicationContext).userDao()
        val userList = userDao.getAll()
        outputData = Data.Builder().putString(NET_KEY_RESULT, Gson().toJson(userList)).build()
        return Result.SUCCESS
    }
}