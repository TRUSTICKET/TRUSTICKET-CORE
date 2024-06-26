package com.trusticket.trusticketcore;

import com.trusticket.trusticketcore.common.kafka.KafkaProducer;
import com.trusticket.trusticketcore.config.security.SecurityUtil;
import com.trusticket.trusticketcore.dto.booking.BookingData;
import com.trusticket.trusticketcore.dto.booking.BookingRequest;
import com.trusticket.trusticketcore.service.booking.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@AutoConfigureTestDatabase
class TrusticketCoreApplicationTests {


	@Autowired
	private KafkaProducer kafkaProducer;
	@Test
	void 동시응모() throws InterruptedException{
		int threadCount = 1000;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		for(int i = 0; i < threadCount; i++){
			String id = "ID" + i;
//			executorService.submit(() -> {
//				try{
//					BookingRequest request = new BookingRequest(id);
//					Long partition = bookingService.insertBookingInQueue(request);
//					System.out.println(partition);
//				}
//				finally{
//					latch.countDown();
//				}
//			});
			BookingData data = BookingData.builder()
					.id(id)
					.memberId("1")
					.build();

			Long partition = kafkaProducer.sendBookingData("booking-request", data);
			System.out.println(partition);
		}
	}



}
