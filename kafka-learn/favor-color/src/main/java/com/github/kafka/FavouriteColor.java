package com.github.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

/**
 * Hello world!
 */
public final class FavouriteColor {
    private static int MAX_TIME = 1000;
    private FavouriteColor() {
    }

    /**
    * Create topology.
    */
    public Topology createTopology() {
        StreamsBuilder builder = new StreamsBuilder();
        // 1 - stream from Kafka

        KStream<String, String> textLines = builder.stream("favourite-color-input");
        KStream<String, String> favColor = textLines
                .filter((key,value)->value.contains(","))
                .selectKey((key,value)->value.split(",")[0].toLowerCase())
                .mapValues(value->value.split(",")[1].toLowerCase())
                .filter((user,colour)->Arrays.asList("green","blue","red").contains(colour));
        
        favColor.to("user-color");

        KTable<String, String> userColor = builder.table("user-color");

        KTable<String,Long> colorCount = userColor
        .groupBy((user,colour)->new KeyValue<>(colour, colour))
        .count();
        

        colorCount.toStream().to("favourite-color-output", Produced.with(Serdes.String(), Serdes.Long()));


        return builder.build();
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "favourite-color");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        config.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG,"0");

        FavouriteColor favouriteColor = new FavouriteColor();

        KafkaStreams streams = new KafkaStreams(favouriteColor.createTopology(), config);
        streams.start();

        System.out.println(streams.toString());

        // shutdown hook to correctly close the streams application
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        // Update:
        // print the topology every 10 seconds for learning purposes
        while (true) {
            streams.localThreadsMetadata().forEach(data -> System.out.println(data));
            try {
                Thread.sleep(MAX_TIME);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
