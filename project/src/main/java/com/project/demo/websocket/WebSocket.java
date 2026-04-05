package com.project.demo.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.project.demo.dao.UserChatFriendMapper;
import com.project.demo.dao.UserChatGroupMapper;
import com.project.demo.entity.UserChatFriend;
import com.project.demo.entity.UserChatRead;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{userId}/{username}")
@Slf4j
public class WebSocket {
    /**
     * 在线人数
     */
    public static int onlineNumber = 0;
    /**
     * 以用户的id名为key，WebSocket为对象保存起来
     */
    private static Map<Integer, WebSocket> clients = new ConcurrentHashMap<Integer, WebSocket>();
    /**
     * 会话
     */
    private Session session;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名称
     */
    private String username;

    private static ApplicationContext applicationContext;

    @Autowired
    private UserChatFriendMapper userChatFriendMapper;
    @Autowired
    private UserChatGroupMapper userChatGroupMapper;

    //解决无法注入mapper问题
    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocket.applicationContext = applicationContext;
    }
    /**
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("userId") Integer userId,@PathParam("username") String username, Session session)
    {
        onlineNumber++;
        log.info("现在来连接的客户id："+session.getId()+"用户名："+username);
        this.username = username;
        this.userId = userId;
        this.session = session;
        log.info("有新连接加入！ 当前在线人数" + onlineNumber);
        try {
            userChatFriendMapper = applicationContext.getBean(UserChatFriendMapper .class);
            userChatGroupMapper = applicationContext.getBean(UserChatGroupMapper .class);
            //message_type 1代表上线 2代表下线 3代表在线名单 4代表普通消息
            //先给好友发送通知，说我上线了
            Map<String,Object> map1 = new HashMap<>();
            map1.put("message_type",1);
            map1.put("user_id",userId);
            map1.put("username",username);
            //查询好友
            List<UserChatFriend> list = userChatFriendMapper.selectUserChatFriendByUserId(userId);
            List<Integer> userIdList = new ArrayList<>();
            for (UserChatFriend user : list){
                userIdList.add(user.getFriend_user_id());
            }
            sendMessageAll(JSON.toJSONString(map1),userIdList);

            //把自己的信息加入到map当中去
            clients.put(userId, this);
            //给自己发一条消息：告诉自己现在都有谁在线
            Map<String,Object> map2 = new HashMap<>();
            map2.put("message_type",3);
            //移除掉自己
            Set<Integer> set = clients.keySet();
            map2.put("onlineUsers",set);
            sendMessageTo(JSON.toJSONString(map2),userId);
        }
        catch (IOException e){
            log.info(username+"上线的时候通知所有人发生了错误");
        }



    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.info("服务端发生了错误"+error.getMessage());
        //error.printStackTrace();
    }
    /**
     * 连接关闭
     */
    @OnClose
    public void onClose()
    {
        onlineNumber--;
        //webSockets.remove(this);
        clients.remove(username);
        try {
            //message_type 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String,Object> map1 = new HashMap<>();
            map1.put("message_type",2);
            map1.put("onlineUsers",clients.keySet());
            map1.put("user_id",userId);
            map1.put("username",username);
            //查询好友
            List<UserChatFriend> list = userChatFriendMapper.selectUserChatFriendByUserId(userId);
            List<Integer> userIdList = new ArrayList<>();
            for (UserChatFriend user : list){
                userIdList.add(user.getFriend_user_id());
            }
            sendMessageAll(JSON.toJSONString(map1),userIdList);
        }
        catch (IOException e){
            log.info(username+"下线的时候通知所有人发生了错误");
        }
        log.info("有连接关闭！ 当前在线人数" + onlineNumber);
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session)
    {
        try {
            log.info("来自客户端消息：" + message+"客户端的id是："+session.getId());
            JSONObject jsonObject = JSON.parseObject(message);
            String textMessage = jsonObject.getString("message");
            Integer fromUserId = jsonObject.getInteger("user_id");
            String fromUsername = jsonObject.getString("username");
            JSONObject toUser = jsonObject.getJSONObject("to");
            //如果不是发给所有，那么就发给某一个人
            //message_type 1代表上线 2代表下线 3代表在线名单 4代表普通消息
            Map<String,Object> map1 = new HashMap<>();
            map1.put("message_type",4);
            map1.put("text_message",textMessage);
            map1.put("from_username",fromUsername);
            if (toUser.getString("type").equals("read")){
                JSONArray readArray = toUser.getJSONArray("read_list");
                if (readArray.size()>0){
                    List<String> list = JSONObject.parseArray(readArray.toJSONString(),String.class);
                    userChatFriendMapper.deleteUserRead(list);
                }
            }else if(toUser.getString("type").equals("group")){
                map1.put("to_user_id","group");
                Integer groupId = toUser.getInteger("group_id");
                //查询群聊好友
                List<Integer> userIdList = userChatGroupMapper.selectUserByGroupId(groupId,fromUserId);
                List<UserChatRead> readList = new ArrayList<>();
                for (Integer userId:userIdList) {
                    UserChatRead read = new UserChatRead();
                    read.setUser_chat_read_id(IdWorker.get32UUID());
                    read.setGroup_id(groupId);
                    read.setUser_id(userId);
                    read.setSend_user_id(fromUserId);
                    read.setType(2);
                    read.setMessage(JSON.toJSONString(map1));
                    readList.add(read);
                }
                if (readList.size()>0){
                    userChatFriendMapper.insertUserChatRead(readList);
                }
                for (int i=0;i<userIdList.size();i++) {
                    map1.put("user_chat_read_id",readList.get(i).getUser_chat_read_id());
                    sendMessageTo(JSON.toJSONString(map1),userIdList.get(i));
                }
            }else{
                Integer userId = toUser.getInteger("user_id");
                map1.put("to_user_id",userId);
                String uuid = IdWorker.get32UUID();
                map1.put("user_chat_read_id",uuid);
                List<UserChatRead> readList = new ArrayList<>();
                UserChatRead read = new UserChatRead();
                read.setUser_chat_read_id(uuid);
                read.setUser_id(userId);
                read.setSend_user_id(fromUserId);
                read.setType(1);
                read.setMessage(JSON.toJSONString(map1));
                readList.add(read);
                if (readList.size()>0){
                    userChatFriendMapper.insertUserChatRead(readList);
                }
                sendMessageTo(JSON.toJSONString(map1),userId);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            log.info("发生了错误了");
        }

    }


    public void sendMessageTo(String message, Integer ToUserId) throws IOException {
        for (WebSocket item : clients.values()) {
            if (item.userId.equals(ToUserId) ) {
                if (item.session.isOpen()) {
                    item.session.getAsyncRemote().sendText(message);
                }
                break;
            }
        }
    }

    public void sendMessageAll(String message, List<Integer> userIdList) throws IOException {
        for (WebSocket item : clients.values()) {
            for (Integer userId: userIdList){
                if (item.userId.equals(userId)){
                    if (item.session.isOpen()){
                        item.session.getAsyncRemote().sendText(message);
                    }
                    break;
                }
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineNumber;
    }

}
