package com.colin.threechat

import android.util.Log

class LogUtil {

    companion object {
        private const val TAG = "Colin"
        fun e(msg: String) {
            Log.e(TAG, msg)
        }

        fun d(msg: String) {
            Log.d(TAG, msg)
        }
    }
}