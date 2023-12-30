package com.sanedge.perpustakaan_buku.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanedge.perpustakaan_buku.dto.request.BookingData;
import com.sanedge.perpustakaan_buku.dto.request.BookingRequest;
import com.sanedge.perpustakaan_buku.kafka.BukuProducer;
import com.sanedge.perpustakaan_buku.models.Booking;
import com.sanedge.perpustakaan_buku.models.Buku;
import com.sanedge.perpustakaan_buku.models.User;
import com.sanedge.perpustakaan_buku.service.BookingService;
import com.sanedge.perpustakaan_buku.service.BukuService;
import com.sanedge.perpustakaan_buku.service.UserService;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;
    private final BukuProducer bukuProducer;
    private final BukuService bukuService;

    @Autowired
    public BookingController(BookingService bookingService, UserService userService, BukuService bukuService,
            BukuProducer bukuProducer) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.bukuService = bukuService;
        this.bukuProducer = bukuProducer;
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param id the ID of the booking
     * @return a ResponseEntity object containing the booking if found, or a
     *         NOT_FOUND status if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable("id") Long id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        return booking.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves a list of bookings for a given user.
     *
     * @param userId the ID of the user
     * @return a ResponseEntity containing a list of bookings if the user exists,
     *         otherwise a ResponseEntity with a NOT_FOUND status
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable("userId") Long userId) {
        // Assuming you have a method to get User by userId
        User user = userService.getUserById(userId);
        if (user != null) {
            List<Booking> bookings = bookingService.getBookingsByUser(user);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Adds a new booking to the system.
     *
     * @param booking the booking object to be added
     * @return the response entity containing the saved booking and the HTTP status
     *         code
     */
    @PostMapping
    public ResponseEntity<Booking> addBooking(@RequestBody BookingRequest bookingRequest) {
        Buku buku = bukuService.getBookById(bookingRequest.getBookId())
                .orElseThrow(() -> new RuntimeException("Buku tidak ditemukan"));

        Booking booking_2 = new Booking();

        booking_2.setBuku(buku);
        booking_2.setTanggal_pemesanan(bookingRequest.getTanggalPemesanan());
        booking_2.setTanggal_pengambilan(bookingRequest.getTanggalPengambilan());
        booking_2.setTanggal_pengembalian(bookingRequest.getTanggalPengembalian());

        Booking savedBooking = bookingService.saveBooking(booking_2);

        BookingData bookingData = new BookingData();

        bookingData.setBukuId(buku.getBook_id());
        bookingData.setJumlahDibooking(1);

        // kafka producer
        bukuProducer.sendJumlahBukuBooking(bookingData);

        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

    /**
     * Deletes a booking by ID.
     *
     * @param id the ID of the booking to be deleted
     * @return a ResponseEntity with HTTP status NO_CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable("id") Long id) {
        bookingService.deleteBookingById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
