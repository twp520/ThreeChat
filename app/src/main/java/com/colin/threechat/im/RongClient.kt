package com.colin.threechat.im

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.colin.threechat.LogUtil
import com.colin.threechat.TYPE_SELF
import com.colin.threechat.TYPE_TARGET
import com.colin.threechat.data.ChatEntity
import io.rong.imlib.IRongCallback
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import io.rong.imlib.model.Message
import io.rong.message.TextMessage


//融云SDK的 IMClient
class RongClient : AbstractIMClient() {

    override fun initClient(context: Context) {
        RongIMClient.init(context)
    }

    override fun connectServer(token: String) {
        RongIMClient.connect(token, object : RongIMClient.ConnectCallback() {
            override fun onSuccess(userId: String?) {
                Log.e("chat", "连接到服务器成功")
                LogUtil.e("连接到服务器成功  userID = $userId")
            }

            override fun onError(errorCode: RongIMClient.ErrorCode?) {
                LogUtil.e("连接到服务器失败  errorCode = $errorCode")
            }

            override fun onTokenIncorrect() {
                LogUtil.e("onTokenIncorrect")
            }

        })
    }


    override fun sendTextMessage(text: String, targetId: String): LiveData<ChatEntity> {
        //构造 文本消息实体
        val tmsg = TextMessage.obtain(text)
        //构造 消息实体类
        val msg = Message.obtain(targetId, Conversation.ConversationType.PRIVATE, tmsg)
        val liveData: MutableLiveData<ChatEntity> = MutableLiveData()
        RongIMClient.getInstance().sendMessage(msg, null, null, object : IRongCallback.ISendMessageCallback {
            override fun onAttached(message: Message) {
                val chat = ChatEntity(TYPE_SELF, message)
                liveData.value = chat
            }

            override fun onSuccess(message: Message) {
                val chat = ChatEntity(TYPE_SELF, message)
                liveData.value = chat
            }

            override fun onError(message: Message, errorCode: RongIMClient.ErrorCode?) {
                val chat = ChatEntity(TYPE_SELF, message)
                liveData.value = chat
            }
        })
        return liveData
    }

    override fun fetchHistoryChat(targetId: String): LiveData<MutableList<ChatEntity>> {
        val liveData: MutableLiveData<MutableList<ChatEntity>> = MutableLiveData()
        RongIMClient.getInstance().getHistoryMessages(Conversation.ConversationType.PRIVATE, targetId,
                -1, 10, object : RongIMClient.ResultCallback<MutableList<Message>>() {
            override fun onSuccess(messageList: MutableList<Message>) {
                val data = mutableListOf<ChatEntity>()
                messageList.forEach {
                    val type = when (it.messageDirection!!) {
                        Message.MessageDirection.RECEIVE -> TYPE_TARGET
                        Message.MessageDirection.SEND -> TYPE_SELF
                    }
                    val chat = ChatEntity(type, it)
                    data.add(chat)
                }
                data.reverse()
                liveData.value = data
            }

            override fun onError(errorCode: RongIMClient.ErrorCode?) {

            }
        })
        return liveData
    }

    override fun receiveMessage(): LiveData<ChatEntity> {
        val liveData = MutableLiveData<ChatEntity>()
        RongIMClient.setOnReceiveMessageListener { message, i ->
            LogUtil.e("RongClient ---》receiveMessage  收到回调  ${message.objectName}")
            val chat = ChatEntity(TYPE_TARGET, message)
            liveData.postValue(chat)
            false
        }
        return liveData
    }

}