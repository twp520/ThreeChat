package com.colin.threechat.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.text.TextUtils
import android.view.MenuItem
import com.colin.threechat.R
import com.colin.threechat.adapter.ChatMessageAdapter
import com.colin.threechat.bean.User
import com.colin.threechat.toast
import com.colin.threechat.viewmodel.ChatViewModel
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var targetUser: User
    private lateinit var selfUser: User
    private lateinit var mAdapter: ChatMessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        targetUser = intent.extras.getParcelable("targetUser")
        selfUser = intent.extras.getParcelable("selfUser")
        supportActionBar?.let {
            it.title = targetUser.name
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }
        viewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        mAdapter = ChatMessageAdapter(mutableListOf())
        chat_list.adapter = mAdapter
        chat_list.itemAnimator = DefaultItemAnimator()
        viewModel.getHistoryChat(targetUser.id.toString()).observe(this, Observer {
            it?.let {
                mAdapter.addData(it)
                scrollBottom()
            }
        })

        viewModel.receiveMessage()
                .observe(this, Observer {
                    it?.let {
                        mAdapter.addData(it)
                        scrollBottom()
                    }
                })
        chat_send.setOnClickListener {
            sendMessage()
        }
    }

    //发送消息，进行检查
    private fun sendMessage() {
        val message = chat_edit.text.toString()
        if (TextUtils.isEmpty(message)) {
            toast("不能发送空消息")
            return
        }
        //先添加一个自己造的数据，再改变状态
        val chat = viewModel.buildTextChat(message, targetUser.id.toString())
        mAdapter.addData(chat)
        chat_edit.setText("")
        val position = mAdapter.data.indexOf(chat)
        viewModel.sendTextMessage(message, targetUser.id.toString())
                .observe(this, Observer {
                    it?.let {
                        chat.content.sentStatus = it.content.sentStatus
                        mAdapter.notifyItemChanged(position)
                        scrollBottom()
                    }
                })
    }

    private fun scrollBottom() {
        if (mAdapter.itemCount > 1)
            chat_list.smoothScrollToPosition(mAdapter.itemCount - 1)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when (it.itemId) {
                android.R.id.home -> finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
