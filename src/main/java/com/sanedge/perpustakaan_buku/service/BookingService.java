package com.sanedge.perpustakaan_buku.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.perpustakaan_buku.models.Booking;
import com.sanedge.perpustakaan_buku.models.User;
import com.sanedge.perpustakaan_buku.repository.BookingRepository;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Retrieves a list of bookings associated with a given user.
     *
     * @param user the optional user object for which to retrieve bookings
     * @return the list of bookings associated with the user
     */
    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param id the ID of the booking
     * @return an optional containing the booking, or empty if not found
     */
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    /**
     * Saves a booking to the database.
     *
     * @param booking the booking to be saved
     * @return the saved booking
     */
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    /**
     * Deletes a booking by its ID.
     *
     * @param id the ID of the booking to be deleted
     */
    public void deleteBookingById(Long id) {
        bookingRepository.deleteById(id);
    }
}
