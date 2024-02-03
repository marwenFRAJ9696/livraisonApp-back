package com.fly.delivery.controller;

import com.fly.delivery.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/v1/pdf")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://102.211.210.49:4200","http://www.flydelivery.com.tn:4200","http://www.flydelivery.com.tn"
        ,"https://www.flydelivery.com.tn:4200","https://www.flydelivery.com.tn"}, allowedHeaders = "*")
public class PdfController {
    @Autowired
    private PdfService pdfService;

    @GetMapping("/download-pdf/{id}")
    public ResponseEntity<InputStreamResource> downloadPdf(@PathVariable Integer id) {
        // Générer le PDF dans un ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Utiliser le service pour générer le PDF
        pdfService.generatePDF(byteArrayOutputStream,id);

        // Convertir le ByteArrayOutputStream en ByteArrayInputStream
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        // Définir les en-têtes pour indiquer qu'il s'agit d'un fichier téléchargeable
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=bonlivraison.pdf");

        // Retourner la réponse avec le fichier PDF en tant que flux
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(byteArrayInputStream));
    }
}
