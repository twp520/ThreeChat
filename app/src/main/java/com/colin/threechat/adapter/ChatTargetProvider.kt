package com.colin.threechat.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.colin.threechat.R
import com.colin.threechat.TYPE_TARGET
import com.colin.threechat.data.ChatEntity
import io.rong.message.TextMessage

class ChatTargetProvider : BaseItemProvider<ChatEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_chat_target
    }

    override fun viewType(): Int {
        return TYPE_TARGET
    }

    override fun convert(helper: BaseViewHolder, data: ChatEntity, position: Int) {
        val message = data.content
        val mc = message.content
        if (mc is TextMessage) { //文本消息
            helper.setText(R.id.item_chat_target_text, mc.content)
//            helper.setText(R.id.item_chat_self_status, message.receivedStatus.)
        }
    }
}