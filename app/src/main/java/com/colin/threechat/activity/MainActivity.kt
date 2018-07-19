package com.colin.threechat.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import com.colin.threechat.R
import com.colin.threechat.adapter.UserListAdapter
import com.colin.threechat.bean.User
import com.colin.threechat.jumpActivity
import com.colin.threechat.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "好友列表"
        val loginUser = intent.extras.getParcelable<User>("loginUser")
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val mAdapter = UserListAdapter(this)
        main_list.adapter = mAdapter
        main_list.itemAnimator = DefaultItemAnimator()
        viewModel.getUsersExcept(loginUser.id)
                ?.observe(this, Observer {
                    it?.let {
                        mAdapter.setData(it)
                    }
                })
        mAdapter.setItemClick { _, position ->
            val targetUser = mAdapter.getData()?.get(position)
            val bundle = Bundle()
            bundle.putParcelable("targetUser", targetUser)
            bundle.putParcelable("selfUser", loginUser)
            jumpActivity(ChatActivity::class.java, bundle)
        }
    }


}
