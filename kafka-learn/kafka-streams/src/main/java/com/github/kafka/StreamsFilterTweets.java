package com.github.kafka;

import java.util.Properties;

import com.google.gson.JsonParser;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

/**
 * Hello world!
 */
public final class StreamsFilterTweets {
    private StreamsFilterTweets() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        // create properties
        Properties properties = new Properties();
        
        properties.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG,"demo-kafka-streams");
        properties.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,Serdes.StringSerde.class.getName());
        properties.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
        // create topology
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        // inputtopic
        KStream<String,String> inputTopic = streamsBuilder.stream("twitter_topics");
        KStream<String,String> filteredStream = inputTopic.filter(
            (k,jsonTweet) -> extractFollowersinTweet(jsonTweet)>10000
            );
        filteredStream.to("important_tweets");

        // build the topology
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(),properties);
        // start our streams application
        kafkaStreams.start();
    }

    private static Integer extractFollowersinTweet(String tweetJson){
        try{
            return JsonParser.parseString(tweetJson).getAsJsonObject().get("user").getAsJsonObject().get("followers_count").getAsInt();
        } catch(NullPointerException e){
            return 0;
        }
    }
}
