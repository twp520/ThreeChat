package com.colin.threechat.work

import androidx.work.Data
import androidx.work.Worker
import com.colin.threechat.LogUtil
import com.colin.threechat.bean.User
import com.colin.threechat.db.AppDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DeleteDbWork : Worker() {

    override fun doWork(): Result {
        val userDao = AppDataBase.getInstance(applicationContext).userDao()
        val json = inputData.getString(NET_KEY_RESULT, "")
        LogUtil.e("执行删除任务----json = $json")
        val type = object : TypeToken<List<User>>() {

        }.type
        val gson = Gson()
        val userList = gson.fromJson<List<User>>(json!!, type)
        userDao.deleteUserList(userList)
        outputData = Data.Builder().putString(NET_KEY_RESULT, json).build()
        return Result.SUCCESS
    }
}