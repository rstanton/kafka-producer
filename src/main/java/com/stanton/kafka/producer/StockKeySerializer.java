package com.stanton.kafka.producer;

import org.apache.kafka.common.serialization.Serializer;

import com.google.gson.Gson;
public class StockKeySerializer implements Serializer<StockKey>{

	@Override
	public byte[] serialize(String topic, StockKey data) {
		Gson json = new Gson();
		return json.toJson(data).getBytes();		
	}

}
