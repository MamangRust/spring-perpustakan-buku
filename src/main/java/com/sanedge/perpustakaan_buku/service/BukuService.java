package com.sanedge.perpustakaan_buku.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.perpustakaan_buku.models.Buku;
import com.sanedge.perpustakaan_buku.repository.BukuRepository;

@Service
public class BukuService {
    private final BukuRepository bukuRepository;

    @Autowired
    public BukuService(BukuRepository bukuRepository) {
        this.bukuRepository = bukuRepository;
    }

    public List<Buku> getAllBooks() {
        return bukuRepository.findAll();
    }

    public Optional<Buku> getBookById(Long bookId) {
        return bukuRepository.findById(bookId);
    }

    public Buku saveBook(Buku buku) {
        return bukuRepository.save(buku);
    }

    public void deleteBook(Long bookId) {
        bukuRepository.deleteById(bookId);
    }
}
