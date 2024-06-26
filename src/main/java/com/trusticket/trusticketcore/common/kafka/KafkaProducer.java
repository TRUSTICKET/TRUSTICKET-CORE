package com.trusticket.trusticketcore.common.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trusticket.trusticketcore.dto.booking.BookingCancelData;
import com.trusticket.trusticketcore.dto.booking.BookingData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    // 주입받은 KafkaTemplate을 사용하여 Kafka에 메시지를 전송하는 send 메서드
    public Long sendBookingData(String topic, BookingData data){
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(data);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        long offset = -1L;

        // KafkaTemplate을 사용하여 지정된 토픽에 JSON 문자열을 전송
        CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, jsonInString);
        try {
            SendResult<String, String> result = send.get();
            offset = result.getRecordMetadata().offset();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        log.info("Kafka Producer send data " + data);



        return offset;
    }

    public Long sendCancelBooking(String topic, BookingCancelData data){
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(data);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        long offset = -1L;

        // KafkaTemplate을 사용하여 지정된 토픽에 JSON 문자열을 전송
        CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, jsonInString);
        try {
            SendResult<String, String> result = send.get();
            offset = result.getRecordMetadata().offset();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        log.info("Kafka Producer send data " + data);



        return offset;
    }


}
