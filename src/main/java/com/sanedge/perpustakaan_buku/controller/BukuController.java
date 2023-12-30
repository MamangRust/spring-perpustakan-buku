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

import com.sanedge.perpustakaan_buku.models.Buku;
import com.sanedge.perpustakaan_buku.service.BukuService;

@RestController
@RequestMapping("/api/buku")
public class BukuController {

    private final BukuService bukuService;

    @Autowired
    public BukuController(BukuService bukuService) {
        this.bukuService = bukuService;
    }

    @GetMapping
    public ResponseEntity<List<Buku>> getAllBooks() {
        List<Buku> bukuList = bukuService.getAllBooks();
        return new ResponseEntity<>(bukuList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Buku> getBookById(@PathVariable("id") Long id) {
        Optional<Buku> buku = bukuService.getBookById(id);
        return buku.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Buku> addBook(@RequestBody Buku buku) {
        Buku savedBuku = bukuService.saveBook(buku);
        return new ResponseEntity<>(savedBuku, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        bukuService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
