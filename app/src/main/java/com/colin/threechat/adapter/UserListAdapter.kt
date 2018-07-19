package com.colin.threechat.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.colin.threechat.R
import com.colin.threechat.bean.User

class UserListAdapter(private val context: Context) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    private var mData: MutableList<User>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = mData?.get(position)
        user?.let {
            holder.name?.text = it.name
            holder.itemView.setOnClickListener {
                mClick?.invoke(holder.itemView, position)
            }
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView? = null
        var content: TextView? = null

        init {
            name = itemView.findViewById(R.id.item_name)
            content = itemView.findViewById(R.id.item_content)
        }
    }

    fun setData(data: MutableList<User>) {
        if (mData == null) {
            mData = data
            notifyItemRangeInserted(0, data.size)
        } else {
            mData?.clear()
            notifyItemRangeRemoved(0, data.size)
            mData?.addAll(data)
            notifyItemRangeInserted(0, data.size)
        }
    }

    fun addData(data: MutableList<User>) {
        if (mData == null)
            mData = mutableListOf()
        mData?.addAll(data)
        notifyDataSetChanged()
    }

    fun getData(): MutableList<User>? {
        return mData
    }

    private var mClick: ((view: View, position: Int) -> Unit)? = null

    public fun setItemClick(listener: (view: View, position: Int) -> Unit) {
        mClick = listener
    }
}