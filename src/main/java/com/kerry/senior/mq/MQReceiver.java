package com.kerry.senior.mq;

import com.alibaba.fastjson.JSONObject;
import com.kerry.senior.domain.Customer;
import com.kerry.senior.domain.SeckillOrder;
import com.kerry.senior.service.GoodsService;
import com.kerry.senior.service.OrderService;
import com.kerry.senior.service.SeckillService;
import com.kerry.senior.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MQReceiver {

		private static Logger log = LoggerFactory.getLogger(MQReceiver.class);
		
		//@Autowired
		//RedisService redisService;

		@Autowired
        GoodsService goodsService;
		
		@Autowired
        OrderService orderService;
		
		@Autowired
        SeckillService seckillService;
		
		@RabbitListener(queues=MQConfig.SECKILL_QUEUE)
		public void receive(String message) {
			log.info("开始异步下单, msg = {}"+message);
            SeckillMessage seckillMessage = JSONObject.parseObject(message, SeckillMessage.class);
            Customer user = seckillMessage.getUser();
			Long goodsId = seckillMessage.getGoodsId();
			GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
	    	int stock = goods.getStockCount();
	    	if(stock <= 0) {
	    		return;
	    	}
	    	//判断是否已经秒杀到了
	    	SeckillOrder order = orderService.getSecKillOrderByUserIdGoodsId(user.getId(), goodsId);
	    	if(order != null) {
	    	    log.error("已经秒杀过,不能重复秒杀,异步下单失败");
	    		return;
	    	}
	    	//减库存 下订单 写入秒杀订单
            seckillService.seckill(user, goods);
	    	log.info("异步下单完成");
		}
	
		@RabbitListener(queues=MQConfig.QUEUE)
		public void receive2(String message) {
			log.info("receive message:"+message);
		}

		@RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
		public void receiveTopic1(String message) {
			log.info(" topic  queue1 message:"+message);
		}

		@RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
		public void receiveTopic2(String message) {
			log.info(" topic  queue2 message:"+message);
		}

		@RabbitListener(queues=MQConfig.HEADER_QUEUE)
		public void receiveHeaderQueue(byte[] message) {
			log.info(" header  queue message:"+new String(message));
		}

}
