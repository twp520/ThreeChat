package com.colin.threechat.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import com.colin.threechat.R
import com.colin.threechat.adapter.UserListAdapter
import com.colin.threechat.im.AbstractIMClient
import com.colin.threechat.jumpActivity
import com.colin.threechat.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.title = "登陆"
        val viewModel = ViewModelProviders.of(this)
                .get(LoginViewModel::class.java)
        val mAdapter = UserListAdapter(this)
        login_list.adapter = mAdapter
        login_list.itemAnimator = DefaultItemAnimator()
        viewModel.getUsers()?.observe(this, Observer {
            // 为adapter设置新的值
            it?.let {
                mAdapter.setData(it)
            }
        })
        mAdapter.setItemClick { _, position ->
            val user = mAdapter.getData()?.get(position)
            user?.let {
                //进行连接登陆
                AbstractIMClient.getInstance().connectServer(it.token)
                val args = Bundle()
                args.putParcelable("loginUser", it)
                jumpActivity(MainActivity::class.java, args)
            }
        }
    }
}
