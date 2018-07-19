package com.colin.threechat.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.colin.threechat.TYPE_SELF
import com.colin.threechat.data.ChatEntity
import com.colin.threechat.im.AbstractIMClient
import io.rong.imlib.model.Conversation
import io.rong.imlib.model.Message
import io.rong.message.TextMessage

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val sendObservable = MediatorLiveData<ChatEntity>()
    private val receiveObservable = MediatorLiveData<ChatEntity>()

    fun sendTextMessage(text: String, targetId: String): LiveData<ChatEntity> {
        sendObservable.addSource(AbstractIMClient.getInstance().sendTextMessage(text, targetId)) {
            sendObservable.postValue(it)
        }
        return sendObservable
    }

    fun getHistoryChat(id: String): LiveData<MutableList<ChatEntity>> {
        val liveData = MediatorLiveData<MutableList<ChatEntity>>()
        liveData.addSource(AbstractIMClient.getInstance().fetchHistoryChat(id)) {
            liveData.postValue(it)
        }
        return liveData
    }

    fun buildTextChat(text: String, targetId: String): ChatEntity {
        //构造 文本消息实体
        val tmsg = TextMessage.obtain(text)
        //构造 消息实体类
        val msg = Message.obtain(targetId, Conversation.ConversationType.PRIVATE, tmsg)
        msg.sentStatus = Message.SentStatus.SENDING
        return ChatEntity(TYPE_SELF, msg)
    }

    fun receiveMessage(): LiveData<ChatEntity> {
        receiveObservable.addSource(AbstractIMClient.getInstance().receiveMessage()) {
            receiveObservable.postValue(it)
        }
        return receiveObservable
    }
}