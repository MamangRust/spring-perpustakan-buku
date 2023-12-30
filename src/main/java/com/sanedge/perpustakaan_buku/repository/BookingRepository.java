package com.sanedge.perpustakaan_buku.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanedge.perpustakaan_buku.models.Booking;
import com.sanedge.perpustakaan_buku.models.User;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUser(User user);
}
