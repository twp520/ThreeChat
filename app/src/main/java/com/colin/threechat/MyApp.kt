package com.colin.threechat

import android.app.Application
import com.colin.threechat.im.AbstractIMClient

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AbstractIMClient.getInstance().initClient(this)

    }

}