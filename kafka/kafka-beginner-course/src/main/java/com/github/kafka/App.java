package com.github.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        //create Producer properties
        Properties properties = new Properties();
        String bootstrapServers = "127.0.0.1:9092";

        Logger logger = LoggerFactory.getLogger(App.class);

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        
        //create the producer
        KafkaProducer<String,String> producer = new KafkaProducer<String,String>(properties);

        String topic = "first_topic";

        for(int i=0;i<10;i++){
            String key = "id_"+Integer.toString(i);

            //create producer record
            ProducerRecord<String,String> record = new ProducerRecord<String,String>(topic,key,"hello world!");

            //send data
            producer.send(record, new Callback(){
                    public void onCompletion(RecordMetadata recordMetadata,Exception e){
                        //executes every time a record is successfully sent
                        if(e==null){
                            logger.info("Received new metadata. \n"+
                            "Topic:"+recordMetadata.topic()+'\n'+
                            "Partition:"+recordMetadata.partition()+'\n'+
                            "Offset:"+recordMetadata.offset()+'\n'+
                            "Timestamp:"+recordMetadata.timestamp());
                        }else{
                            logger.error("Error while producing", e);
                        }
                    }
            });;
        }
        producer.close();
    }

}
