package com.colin.threechat.work

import androidx.work.Data
import androidx.work.Worker
import com.colin.threechat.LogUtil
import com.colin.threechat.bean.User
import com.google.gson.Gson

// ...and the result key:
const val NET_KEY_RESULT = "userList"

class NetUsersWork : Worker() {

    override fun doWork(): Result {
        val userList: MutableList<User> = mutableListOf()
        val userName = arrayOf("Colin", "猪八戒", "孙悟空")
        val token = arrayOf("fGTKLAzQF+8jGSk1nyAvnRQAiJI+bgFMB9ytQGNk5tVr83vzvDJlHwfQGpRzT4dvHnvp9wHIGheSmaswU4zC9g==",
                "Gg3lim3AVbXVGL/7vW7i3xQAiJI+bgFMB9ytQGNk5tVr83vzvDJlH6FWR2lnHaHYzxsRr4O1/4GSmaswU4zC9g==",
                "q6GNW12hGtJ7Z08sy0RsDoAmeyaZEnNAMcO9WK9bkc3wT/jlf9s8fIQc4If54MDyVbiYRm+jOA4YydwsOTN2TQ==")
        for (i in 0 until userName.size) {
            userList.add(User((i + 1), token[i], userName[i]))
        }
        //模拟网络请求
        Thread.sleep(1500)
        outputData = Data.Builder().putString(NET_KEY_RESULT, Gson().toJson(userList)).build()
        LogUtil.e("网络请求完成----")
        return Result.SUCCESS
    }
}