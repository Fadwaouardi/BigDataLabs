package edu.ismagi.kafka;

import java.util.Properties;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

public class WordCountApp {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> textLines = builder.stream("wordcount-input");

        KTable<String, Long> wordCounts = textLines
                .flatMapValues(value -> java.util.Arrays.asList(value.toLowerCase().split(" ")))
                .groupBy((key, word) -> word)
                .count();

        wordCounts.toStream().to("wordcount-output");

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}
