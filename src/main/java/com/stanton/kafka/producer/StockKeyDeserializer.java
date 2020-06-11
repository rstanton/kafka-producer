package com.stanton.kafka.producer;

import org.apache.kafka.common.serialization.Deserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class StockKeyDeserializer implements Deserializer<StockKey>{

	@Override
	public StockKey deserialize(String topic, byte[] data) {
		return new Gson().fromJson(new String(data), StockKey.class);
	}
	
}
