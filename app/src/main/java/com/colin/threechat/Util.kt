package com.colin.threechat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast


const val KEY_MSG_TEXT = "text"
const val KEY_MSG_TID = "targetId"

const val TYPE_SELF = 1
const val TYPE_TARGET = 2

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.jumpActivity(clazz: Class<*>, args: Bundle?) {
    val intent = Intent(this, clazz)
    args?.let {
        intent.putExtras(it)
    }
    startActivity(intent)
}
