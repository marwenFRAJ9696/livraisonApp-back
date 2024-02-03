package com.fly.delivery.service;


import java.io.ByteArrayOutputStream;

public interface PdfService {
    void generatePDF(ByteArrayOutputStream byteArrayOutputStream, Integer id);
}
