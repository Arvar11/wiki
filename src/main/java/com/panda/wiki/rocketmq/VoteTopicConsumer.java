package com.panda.wiki.rocketmq;

import com.panda.wiki.service.WebSocketService;
import com.panda.wiki.websocket.WebSocketServer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 投票主题消息消费者
 * 用于接收和处理投票相关的RocketMQ消息
 * 
 * 使用说明：
 * 1. 本消费者会自动订阅 VOTE_TOPIC 主题的消息
 * 2. 消费者组为 default，确保同一消费者组的多个实例负载均衡消费消息
 * 3. 实现 RocketMQListener<MessageExt> 接口以获取完整的消息元数据
 */
@Service
// RocketMQ消息监听器注解，配置消费者属性和订阅关系
@RocketMQMessageListener(
    consumerGroup = "default",  // 消费者组名称，用于集群消费和负载均衡
    topic = "VOTE_TOPIC"       // 订阅的主题名称，接收该主题的所有消息
)
public class VoteTopicConsumer implements RocketMQListener<MessageExt> {
    
    // 日志记录器，用于输出消费日志
    private static final Logger LOG = LoggerFactory.getLogger(VoteTopicConsumer.class);

    @Resource
    private WebSocketServer webSocketServer;
    
    /**
     * 消息处理回调方法
     * 当消费者收到消息时，RocketMQ会自动调用此方法
     * 
     * @param messageExt RocketMQ消息对象，包含消息体、属性、标签等元数据
     * 
     * 方法执行流程：
     * 1. 从MessageExt中提取消息体(byte[])
     * 2. 将字节数组转换为字符串
     * 3. 记录消息消费日志
     * 4. 实际业务中可在此处添加投票业务处理逻辑
     */
    @Override
    public void onMessage(MessageExt messageExt) {
        // 获取消息内容字节数组
        byte[] body = messageExt.getBody();
        
        // 将字节数组转换为字符串并记录日志
        // 实际项目中建议指定字符编码，如：new String(body, "UTF-8")
        LOG.info("ROCKETMQ收到消息: {}", new String(body));

        webSocketServer.sendInfo(new String(body));
        // 例如：投票统计、数据入库、通知其他系统等
        
        // 可以通过 messageExt 获取更多消息属性：
        // - messageExt.getMsgId()        // 消息ID
        // - messageExt.getTopic()        // 主题名称
        // - messageExt.getTags()         // 消息标签（用于过滤）
        // - messageExt.getKeys()         // 业务键（用于消息查询）
        // - messageExt.getBornTimestamp()// 消息产生时间
        // - messageExt.getBornHost()     // 消息来源地址
    }
}