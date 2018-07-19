package com.colin.threechat.im

import android.arch.lifecycle.LiveData
import android.content.Context
import com.colin.threechat.data.ChatEntity

// 通讯的客户端接口定义
interface IMClient {

    /**
     * 对客户端初始化
     * @param context 全局Context变量
     */
    fun initClient(context: Context)

    /**
     * 连接到通讯服务器
     * @param token 用户身份鉴权
     * */
    fun connectServer(token: String)

    /**
     * 注册连接的回调监听
     */
    fun registerConnectListener()

    /**
     * 获取某个会话的历史消息
     * @param targetId 目标id
     * @return 历史消息
     */
    fun fetchHistoryChat(targetId: String): LiveData<MutableList<ChatEntity>>

    /**
     * 发送文本消息(单聊)
     * @param text 发送的文本信息
     * @param targetId 目标用户id
     *@return 发送的消息
     */
    fun sendTextMessage(text: String, targetId: String): LiveData<ChatEntity>


    /**
     * 收到消息的监听
     */
    fun receiveMessage(): LiveData<ChatEntity>

}