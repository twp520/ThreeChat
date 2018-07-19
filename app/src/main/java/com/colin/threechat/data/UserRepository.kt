package com.colin.threechat.data

import android.arch.lifecycle.LiveData
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.colin.threechat.bean.User
import com.colin.threechat.db.UserDao
import com.colin.threechat.work.DeleteDbWork
import com.colin.threechat.work.InsertDbWork
import com.colin.threechat.work.NetUsersWork

class UserRepository(private val userDao: UserDao) {

    fun getAllUsers(): LiveData<MutableList<User>> {
//        refreshDb()
        return userDao.getAll()
    }

    private fun refreshDb() {
        val net = OneTimeWorkRequest.Builder(NetUsersWork::class.java).build()
        val insert = OneTimeWorkRequest.Builder(InsertDbWork::class.java)
                .build()
        val delete = OneTimeWorkRequest.Builder(DeleteDbWork::class.java)
                .build()
        WorkManager.getInstance()
                ?.beginWith(net)
                ?.then(delete)
                ?.then(insert)
                ?.enqueue()
    }

    fun getUsersExceptId(id: Int): LiveData<MutableList<User>> {
        return userDao.getUserListExcept(id)
    }
}