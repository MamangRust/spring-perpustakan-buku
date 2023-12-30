package com.sanedge.perpustakaan_buku.dto.request;

import java.util.Date;

import lombok.Data;

@Data
public class BookingRequest {
    private Long bookId;
    private Date tanggalPemesanan;
    private Date tanggalPengambilan;
    private Date tanggalPengembalian;

}
