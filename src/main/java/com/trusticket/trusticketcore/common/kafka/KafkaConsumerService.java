package com.trusticket.trusticketcore.common.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KafkaConsumerService {
    private final String topic = "booking-request";
    private KafkaConsumer<String, String> consumer;
    public KafkaConsumerService(ConsumerFactory<String, String> consumerFactory){
        consumer = (KafkaConsumer<String, String>) consumerFactory.createConsumer();
    }

    public void fetchOffsets() {

        System.out.printf("Beginning offset: %d, End offset: %d%n",
                getCommittedOffset(0), getEndOffset(0));

    }

    private long getCommittedOffset(int partition) {
        TopicPartition topicPartition = new TopicPartition(topic, partition);
        consumer.assign(Collections.singletonList(topicPartition));
        return consumer.position(topicPartition) - 1;
    }

    private long getEndOffset(int partition) {
        TopicPartition topicPartition = new TopicPartition(topic, partition);
        consumer.assign(Collections.singletonList(topicPartition));
        consumer.seekToEnd(Collections.singletonList(topicPartition));
        return consumer.position(topicPartition) - 1;
    }


}
