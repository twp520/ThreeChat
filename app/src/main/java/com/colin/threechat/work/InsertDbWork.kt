package com.colin.threechat.work

import androidx.work.Worker
import com.colin.threechat.LogUtil
import com.colin.threechat.bean.User
import com.colin.threechat.db.AppDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class InsertDbWork : Worker() {

    override fun doWork(): Result {
        val userDao = AppDataBase.getInstance(applicationContext).userDao()
        val json = inputData.getString(NET_KEY_RESULT, "")
        LogUtil.e("执行插入任务----json = $json")
        val type = object : TypeToken<List<User>>() {

        }.type
        val gson = Gson()
        val userList = gson.fromJson<List<User>>(json!!, type)
        LogUtil.e("执行插入任务----格式化后大小 = ${userList.size}")
        userDao.addUserList(userList)
        return Result.SUCCESS
    }
}