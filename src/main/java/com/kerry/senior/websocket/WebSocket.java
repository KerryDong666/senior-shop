package com.kerry.senior.websocket;

import com.alibaba.fastjson.JSON;
import com.kerry.senior.domain.User;
import com.kerry.senior.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Kerry Dong
 */
@Component
@ServerEndpoint("/webSocket/{userId}")
public class WebSocket {

    private Session session;
    /**
     * WebSocket容器,不能用普通的set
     */
    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    private static ConcurrentHashMap<String, Session> clientMap = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(WebSocket.class);

    //这里无法直接注入
    //@Autowired
    //private UserService userService;

    //解决WebSocket无法注入的问题
    //@Autowired
    //private UserService userService;

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context){
        applicationContext = context;
    }

    /**
     * 对应前端onopen事件
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) throws IOException {
        this.session = session;
        //webSockets.add(this);
        clientMap.put(userId, this.session);
        logger.info("websocket消息有新的连接,连接用户id = {}, 总数:{}", userId, clientMap.size());
        UserService userService = applicationContext.getBean(UserService.class);
        User user = userService.get(Long.valueOf(userId));
        session.getBasicRemote().sendText(JSON.toJSONString(user));
    }

    /**
     * 对应前端close事件
     */
    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        clientMap.remove(userId);
        logger.info("websocket消息连接断开,断开userId = {}, 总数:{}", userId, clientMap.size());
    }

    /**
     * 接收前端消息
     */
    @OnMessage
    public void onMessage(String message, @PathParam("userId") String userId) {
        logger.info("websocket消息 收到客户端{}发来的消息:{}", userId, message);
    }

    /**
     * 发送广播消息
     */
    public void sendMessage(String message) {
        for (WebSocket webSocket : webSockets) {
            logger.info("websocket 广播消息 , message = {}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMsg(String userId) {
        Session session = clientMap.get(userId);
        if (session != null) {
            try {
                session.getBasicRemote().sendText("用户" + userId + "接受消息");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
