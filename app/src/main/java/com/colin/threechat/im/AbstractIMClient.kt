package com.colin.threechat.im

import android.content.Context


//IMClient的抽象实现类
abstract class AbstractIMClient internal constructor() : IMClient {

    companion object {
        private var mInstance: IMClient? = null
        fun getInstance(): IMClient {
            if (mInstance == null)
                mInstance = RongClient()
            return mInstance!!
        }
    }

    override fun initClient(context: Context) {

    }

    override fun connectServer(token: String) {

    }


    override fun registerConnectListener() {

    }

}