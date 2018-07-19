package com.colin.threechat.work

import androidx.work.Data
import androidx.work.Worker
import com.colin.threechat.KEY_MSG_TEXT
import com.colin.threechat.KEY_MSG_TID
import com.colin.threechat.LogUtil
import com.google.gson.Gson
import io.rong.imlib.IRongCallback
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import io.rong.imlib.model.Message
import io.rong.message.TextMessage


/**
 * 从入门到放弃
 */
class SendTextWork : Worker() {
    override fun doWork(): Result {
        val text = inputData.getString(KEY_MSG_TEXT, "")
        val targetId = inputData.getString(KEY_MSG_TID, "")
        //构造 文本消息实体
        val tmsg = TextMessage.obtain(text)
        //构造 消息实体类
        val msg = Message.obtain(targetId, Conversation.ConversationType.PRIVATE, tmsg)
        LogUtil.e("进入 work ")
        var status = Result.RETRY
        RongIMClient.getInstance().sendMessage(msg, null, null, object : IRongCallback.ISendMessageCallback {

            override fun onAttached(message: Message?) {
                //消息本地数据库存储成功的回调
                LogUtil.e("消息本地数据库存储成功的回调 onAttached")
            }

            override fun onSuccess(message: Message?) {
                //发送成功
                outputData = Data.Builder().putString("message", Gson().toJson(message)).build()
                status = Result.SUCCESS
                LogUtil.e("发送消息 onSuccess")
            }

            override fun onError(message: Message?, errorCode: RongIMClient.ErrorCode?) {
                //发送失败
                status = Result.FAILURE
                LogUtil.e("发送消息 onError  ${errorCode?.message}")
            }
        })
        LogUtil.e("完成 work  执行到 return 前一句 ")
        while (status == Result.RETRY) {
            //没有什么意义的循环
            status = Result.RETRY
        }
        return status
    }
}