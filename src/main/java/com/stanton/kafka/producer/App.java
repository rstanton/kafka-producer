/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.stanton.kafka.producer;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.google.gson.Gson;

/**
 * Every 10 seconds this will generate a random stock adjustment for a range of 25 products in 25 stores
 * @author ross
 *
 */
public class App {
	private final static Gson json = new Gson();
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private String bsServers;

	public App() {
		bsServers = System.getenv("KAFKA_BOOTSTRAP_SERVERS");
		
		logger.info("Connecting to Kafka Servers via "+bsServers);
		
		if(bsServers==null) {
			logger.log(Level.SEVERE, "KAFKA_BOOTSTRAP_SERVERS environment variable NOT set");
			System.exit(-1);
		}

	}
	
	private void sendComplexKey() {
		try{
			Properties props = new Properties();
	   	 	props.put("bootstrap.servers", bsServers);
	   	 	props.put("acks", "all");
	   	 	props.put("batch.size","5");
	   	 	props.put("key.serializer", "com.stanton.kafka.producer.StockKeySerializer");
	   	 	props.put("value.serializer", "org.apache.kafka.common.serialization.DoubleSerializer");
	
	   	 	Producer<StockKey, Double> producer = new KafkaProducer<>(props);	
	   	 	
	   	 	while(true) {
	   	 		StockKey key = new StockKey();
	   	 		key.setEAN("3663602942986");
	   	 		key.setLocation("1000");;
	   	 		key.setStore("1233");
	   	 		
				 ProducerRecord<StockKey, Double> r = new ProducerRecord<StockKey, Double>("storestock", key, 5.0);
				 
				 Callback cb = new Callback() {
					 @Override
					 public void onCompletion(RecordMetadata metadata, Exception e) {
						 if(e!=null) {
							 logger.warning(e.toString());
						 }
						 else {
							 logger.info("Sent message with id "+metadata.offset());
						 }
						 
					 }
				 };
				 producer.send(r, cb);
				 
				 Thread.sleep(10000);   	 		
	 		}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
    public void start() {
    	try {
    		logger.info("Starting");
    		
	    	 Properties props = new Properties();
	    	 props.put("bootstrap.servers", bsServers);
	    	 props.put("acks", "all");
	    	 props.put("batch.size","5");
	    	 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	    	 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	
	    	 Producer<String, String> producer = new KafkaProducer<>(props);
	    	 
	    	 while(true) {
	    		 for (int store =0; store<=2;store++) {
	    			 for(int productCode =0; productCode<=2; productCode++) {
	    				 
	    				 //work out whether stock adjustment is positive or negative
	    				 int multiplier = 1;
	    				 if(Math.random()<0.5)
	    					 multiplier = -1;
	    			 
	    				 double qty = 100 * Math.random() * multiplier;
	    			 
	    				 Stock s = new Stock();
	    				 s.setEAN("EAN"+productCode);;
	    				 s.setLocation("1001");
	    				 s.setQty(Math.floor(qty));
	    				 s.setStoreCode(""+(1000+store));

	    				 ProducerRecord<String, String> r = new ProducerRecord<String, String>("stock", s.getEAN(), json.toJson(s));
	    			 
	    				 Callback cb = new Callback() {
	    					 @Override
	    					 public void onCompletion(RecordMetadata metadata, Exception e) {
	    						 if(e!=null) {
	    							 logger.warning(e.toString());
	    						 }
	    						 else {
	    							 logger.info("Sent message with id "+metadata.offset());
	    						 }
	    						 
	    					 }
	    				 };
	    				 producer.send(r, cb);
	    			 }
	    		 }
	    		 Thread.sleep(10000);
	    	 }
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		System.exit(-1);
    	}
    }

    public static void main(String[] args) {
        new App().sendComplexKey();
    }
}
