package com.colin.threechat.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.MultipleItemRvAdapter
import com.colin.threechat.data.ChatEntity

class ChatMessageAdapter(data: MutableList<ChatEntity>?) : MultipleItemRvAdapter<ChatEntity, BaseViewHolder>(data) {

    init {
        finishInitialize()
    }

    override fun registerItemProvider() {
        mProviderDelegate.registerProvider(ChatSelfProvider())
        mProviderDelegate.registerProvider(ChatTargetProvider())
    }

    override fun getViewType(chat: ChatEntity): Int {
        return chat.itemType
    }
}