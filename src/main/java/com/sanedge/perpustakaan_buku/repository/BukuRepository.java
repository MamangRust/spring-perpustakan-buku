package com.sanedge.perpustakaan_buku.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanedge.perpustakaan_buku.models.Buku;

@Repository
public interface BukuRepository extends JpaRepository<Buku, Long> {
    List<Buku> findBykategori(String kategori);
}
