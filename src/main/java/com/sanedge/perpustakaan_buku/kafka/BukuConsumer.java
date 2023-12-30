package com.sanedge.perpustakaan_buku.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.sanedge.perpustakaan_buku.dto.request.BookingData;
import com.sanedge.perpustakaan_buku.models.Buku;
import com.sanedge.perpustakaan_buku.service.BukuService;

@Service
public class BukuConsumer {

    private static final String TOPIC = "buku_topic";
    private final BukuService bukuService;

    public BukuConsumer(BukuService bukuService) {
        this.bukuService = bukuService;
    }

    /**
     * Consume the jumlah buku booking from Kafka.
     *
     * @param bookingData the booking data containing the buku id and jumlah
     *                    dibooking
     * @return none
     */
    @KafkaListener(topics = TOPIC, groupId = "my_perpustakaan")
    public void consumeJumlahBukuBooking(BookingData bookingData) {
        Buku buku = bukuService.getBookById(bookingData.getBukuId())
                .orElseThrow(() -> new RuntimeException("Buku tidak ditemukan"));

        int jumlahDibooking = bookingData.getJumlahDibooking();
        int jumlahTersedia = buku.getJumlah_tersedia();

        if (jumlahTersedia >= jumlahDibooking) {
            buku.setJumlah_tersedia(jumlahTersedia - jumlahDibooking);
            bukuService.saveBook(buku);
        } else {
            throw new RuntimeException("Stok buku tidak mencukupi");
        }
    }
}
