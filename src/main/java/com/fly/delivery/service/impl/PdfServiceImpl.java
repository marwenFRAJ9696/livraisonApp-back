package com.fly.delivery.service.impl;

import com.fly.delivery.entities.Package;
import com.fly.delivery.entities.PaymentMode;
import com.fly.delivery.entities.User;
import com.fly.delivery.repository.PackageRepository;
import com.fly.delivery.repository.UserRepository;
import com.fly.delivery.service.PdfService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {
    @Autowired
    PackageRepository packageRepository;

    @Override
    public void generatePDF(ByteArrayOutputStream byteArrayOutputStream, Integer id) {
        Optional<Package> packageById = this.packageRepository.findById(id);
        if (packageById.isPresent()) {
            Package p = packageById.get();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            // Ajouter le titre
            Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
            Paragraph title = new Paragraph("Bon de livraison", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            // Ajouter les informations d'expédition et de destination
            Font bodyFont = new Font(Font.HELVETICA, 12);
            User user = p.getUser();
            extractedPhrase("Nom: ", user.getFirstName() == null || user.getLastName() == null ? " " : user.getFirstName() + " " + user.getLastName(), Element.ALIGN_LEFT, document);
            extractedPhrase("Expéditeur: ", user.getCampanyName() != null ? user.getCampanyName() : " ", Element.ALIGN_LEFT, document);
            extractedPhrase("Adresse: ", user.getCity() == null || user.getAddress() == null || user.getCodePostal() == null ? "" :
                    user.getCity() + ", " + user.getAddress() + ", " + user.getCodePostal(), Element.ALIGN_LEFT, document);
            extractedPhrase("Tél: ", user.getTelNumber() != null ? user.getTelNumber().toString() : "", Element.ALIGN_LEFT, document);
            extractedPhrase("M/F: ", user.getTaxIdentificationNumber() != null ? user.getTaxIdentificationNumber() : "", Element.ALIGN_LEFT, document);

            document.add(new Paragraph("\n"));

            // Informations destinataire
            extractedPhraseTitle("BON DE LIVRAISON / FACTURE N°"+ p.getFactureNumber(), Element.ALIGN_MIDDLE, document);

            // Ajouter les détails du bon de livraison
//        extractedPhrase("M/F: ","1825398/F - Code Agence: 01",Element.ALIGN_LEFT, document);
            document.add(new Paragraph("\n"));
            extractedPhraseToLeft("Nom de destinataire: ", p.getFullName() != null ? p.getFullName() : "", 200, document);
            extractedPhraseToLeft("Adresse: ", p.getGovernorate() == null || p.getCity() == null || p.getFullAddress() == null ? "" :
                    p.getGovernorate() + ", " + p.getCity() + ", " + p.getFullAddress(), 200, document);
            extractedPhraseToLeft("Téléphone: ", p.getTelNumber() != null ? p.getTelNumber() : "", 200, document);
            if(p.getTelNumber2() != null){
                extractedPhraseToLeft("Téléphone 2: ", p.getTelNumber2() != null ? p.getTelNumber2() : "", 200, document);

            }
            document.add(new Paragraph("\n"));

            extractedPhrase("Date BLF: ",p.getCreatedDate() != null ? p.getCreatedDate().toString() : "", Element.ALIGN_LEFT, document);
            extractedPhrase("Destination: ", p.getGovernorate() != null ?p.getGovernorate():"", Element.ALIGN_LEFT, document);
            if(p.isPetit()){
                extractedPhrase("Taille du Paquet: ", "Petit", Element.ALIGN_LEFT, document);
            }else if( p.isGrand()){
                extractedPhrase("Taille du Paquet: ", "Grand", Element.ALIGN_LEFT, document);
            }else if (p.isMoyen()){
                extractedPhrase("Taille du Paquet: ", "Moyen", Element.ALIGN_LEFT, document);
            }else if (p.isExtraLarge()){
                extractedPhrase("Taille du Paquet: ", "Extra Large", Element.ALIGN_LEFT, document);
            }
            document.add(new Paragraph("\n"));



//        // Ajouter les totaux
//        document.add(new Paragraph("Montant HT: 204202 DT", bodyFont));
//        document.add(new Paragraph("TVA 19%: 38798 DT", bodyFont));
//        document.add(new Paragraph("Total en TTC: 243000 DT", bodyFont));
            document.add(new Paragraph("\n"));


            // Ajouter les autres éléments statiques du document...

            extractTableForPaymentMode(document, Element.ALIGN_MIDDLE, p);
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            extractTable(document, p);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            // Informations supplémentaires
            extractedPhrase("Société de livraison: ", "Fly Delivery", Element.ALIGN_CENTER, document);
            extractedPhrase("Adresse: ", "Monthfleury, Tunis.", Element.ALIGN_CENTER, document);
            extractedPhrase("Tel: ", "29 905 911", Element.ALIGN_CENTER, document);


            document.close();
        }

    }

    private void extractTableForPaymentMode(Document document, int alignment, Package p) {
        PdfPTable table = new PdfPTable(2);

        table.setWidthPercentage(100); // Cela définit la largeur du tableau comme un pourcentage de la page
        table.setHorizontalAlignment(alignment); // Aligner le tableau à droite

        if (p.getPaymentMode().equals(PaymentMode.CASH)) {
            table.addCell(extractCell("Espèce seuelemnt", alignment));

        } else if (p.getPaymentMode().equals(PaymentMode.CHEQUE)) {
            table.addCell(extractCell("Chèque seuelemnt", alignment));

        } else {
            table.addCell(extractCell("Chèque ou Espèce", alignment));
        }
        if (p.isPackageCanBeOpen()) {
            table.addCell(extractCell("Ouvrir le colis : Non", alignment));
        } else {
            table.addCell(extractCell("Ouvrir le colis : Non", alignment));
        }
        if(p.isEchange()){
            table.addCell(extractCell("Colis Echange : Oui", alignment));
        }else {
            table.addCell(extractCell("Colis Echange : Non", alignment));
        }
        if(p.isFragile()){
            table.addCell(extractCell("Colis Fragile : Oui", alignment));
        }else {
            table.addCell(extractCell("Colis Fragile : Non", alignment));

        }

        document.add(table);
//        document.add(new Paragraph(s, bodyFont));
//        document.add(new Paragraph(s2, bodyFont));
    }



    private void extractTable(Document document, Package p) {
        // Ajouter le tableau de désignation
        PdfPTable table = new PdfPTable(new float[]{2, 1});
        table.setWidthPercentage(100);
//        table.setWidthPercentage(5);
        table.addCell(new Paragraph("Désignation", new Font(Font.HELVETICA, 12, Font.BOLD)));
//        table.addCell(new Paragraph("Quantité", new Font(Font.HELVETICA, 12, Font.BOLD)));
        table.addCell(new Paragraph("Montant Total", new Font(Font.HELVETICA, 12, Font.BOLD)));
        // Ajouter les données du tableau
        table.addCell(extractCell(p.getDesignation() != null ? p.getDesignation() : "", Element.ALIGN_LEFT));
//        table.addCell(extractCell(p.getArticleNumber() != 0 ? String.valueOf(p.getArticleNumber()) : " ", Element.ALIGN_LEFT));
        table.addCell(extractCell(p.getPriceNet() != null ? p.getPriceNet() +" DT" :"0 DT", Element.ALIGN_LEFT));

        document.add(table);
    }

    private void extractedPhrase(String nom, String descirption, int alignement, Document document) {
        Phrase pargraphPhrase = new Phrase();
        Chunk label = new Chunk(nom, new Font(Font.HELVETICA, 12, Font.BOLD));
        pargraphPhrase.add(label);
        Chunk nameValue = new Chunk(descirption, new Font(Font.HELVETICA, 12, Font.NORMAL));
        pargraphPhrase.add(nameValue);
        Paragraph paragraph = new Paragraph(pargraphPhrase);
        paragraph.setAlignment(alignement);
        document.add(paragraph);
    }

    private void extractedPhraseToLeft(String nom, String descirption, int indation, Document document) {
        Phrase pargraphPhrase = new Phrase();
        Chunk label = new Chunk(nom, new Font(Font.HELVETICA, 12, Font.BOLD));
        pargraphPhrase.add(label);
        Chunk nameValue = new Chunk(descirption, new Font(Font.HELVETICA, 12, Font.NORMAL));
        pargraphPhrase.add(nameValue);
        Paragraph paragraph = new Paragraph(pargraphPhrase);
        paragraph.setIndentationLeft(indation);
        document.add(paragraph);
    }

    private void extractedPhraseTitle(String nom, int alignment, Document document) {

// Créez un tableau avec une seule colonne pour le cadre
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(80); // Cela définit la largeur du tableau comme un pourcentage de la page
        table.setHorizontalAlignment(alignment); // Aligner le tableau à droite

// Créer une cellule pour le tableau, sans bordure si nécessaire
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.BOX);
        cell.setPadding(10); // Ajustez le padding selon le besoin
        cell.setHorizontalAlignment(alignment);
        Phrase pargraphPhrase = new Phrase();
        Chunk label = new Chunk(nom, new Font(Font.HELVETICA, 16, Font.BOLD));
        pargraphPhrase.add(label);
        Paragraph paragraph = new Paragraph(pargraphPhrase);
        paragraph.setAlignment(alignment);
        cell.addElement(paragraph);
        table.addCell(cell);
        document.add(table);
    }

    private PdfPCell extractCell(String nom, int alignment) {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.BOX);
        cell.setPadding(10); // Ajustez le padding selon le besoin
        cell.setHorizontalAlignment(alignment);
        Phrase pargraphPhrase = new Phrase();
        Chunk label = new Chunk(nom, new Font(Font.HELVETICA, 12, Font.BOLD));
        pargraphPhrase.add(label);
        Paragraph paragraph = new Paragraph(pargraphPhrase);
        paragraph.setAlignment(alignment);
        cell.addElement(paragraph);
        return cell;
    }


}
