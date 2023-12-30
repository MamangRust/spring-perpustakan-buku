package com.sanedge.perpustakaan_buku.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sanedge.perpustakaan_buku.dto.request.BookingData;

@Service
public class BukuProducer {
    private static final String TOPIC = "buku_topic";

    private final KafkaTemplate<String, BookingData> kafkaTemplate;

    @Autowired
    public BukuProducer(KafkaTemplate<String, BookingData> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Sends the jumlah buku booking data to the specified Kafka topic.
     *
     * @param bookingData The booking data to be sent.
     */
    public void sendJumlahBukuBooking(BookingData bookingData) {
        kafkaTemplate.send(TOPIC, bookingData);
    }
}
