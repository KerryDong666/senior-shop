package com.kerry.senior.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQConfig {
	
	public static final String SECKILL_QUEUE = "seckill.queue";
	public static final String QUEUE = "queue";
	public static final String TOPIC_QUEUE1 = "topic.queue1";
	public static final String TOPIC_QUEUE2 = "topic.queue2";
	public static final String HEADER_QUEUE = "header.queue";
	public static final String TOPIC_EXCHANGE = "topicExchage";
	public static final String FANOUT_EXCHANGE = "fanoutxchage";
	public static final String HEADERS_EXCHANGE = "headersExchage";
	
	/**
	 * Direct模式 交换机Exchange
	 * */
	@Bean
	public Queue queue() {
		return new Queue(QUEUE, true);
	}
	
	/**
	 * Topic模式 交换机Exchange
	 * */
	@Bean
	public Queue topicQueue1() {
		return new Queue(TOPIC_QUEUE1, true);
	}
	@Bean
	public Queue topicQueue2() {
		return new Queue(TOPIC_QUEUE2, true);
	}

    /**
     * 交换机
     */
	@Bean
	public TopicExchange topicExchage(){
		return new TopicExchange(TOPIC_EXCHANGE);
	}

    /**
     * topicQueue1与交换机绑定
     * @return
     */
	@Bean
	public Binding topicBinding1() {
		return BindingBuilder.bind(topicQueue1()).to(topicExchage()).with("topic.key1");
	}
	@Bean
	public Binding topicBinding2() {
		return BindingBuilder.bind(topicQueue2()).to(topicExchage()).with("topic.#");
	}
	/**
	 * Fanout广播模式 交换机Exchange
	 *
     */
	@Bean
	public FanoutExchange fanoutExchage(){
		return new FanoutExchange(FANOUT_EXCHANGE);
	}
	@Bean
	public Binding FanoutBinding1() {
		return BindingBuilder.bind(topicQueue1()).to(fanoutExchage());
	}
	@Bean
	public Binding FanoutBinding2() {
		return BindingBuilder.bind(topicQueue2()).to(fanoutExchage());
	}
	/**
	 * Header模式(通过key或者value来匹配) 交换机Exchange
	 * */
	@Bean
	public HeadersExchange headersExchage(){
		return new HeadersExchange(HEADERS_EXCHANGE);
	}
	@Bean
	public Queue headerQueue1() {
		return new Queue(HEADER_QUEUE, true);
	}
	@Bean
	public Binding headerBinding() {
		Map<String, Object> map = new HashMap<>();
		map.put("header1", "value1");
		map.put("header2", "value2");
		return BindingBuilder.bind(headerQueue1()).to(headersExchage()).whereAll(map).match();
	}
	
	
}