package com.disquesea.disqueseaapi.domain.generator.document.helper;

import com.disquesea.disqueseaapi.components.DateCustom;
import com.disquesea.disqueseaapi.domain.generator.document.TextFormatter;
import com.disquesea.disqueseaapi.domain.model.Product;
import com.disquesea.disqueseaapi.domain.model.enums.Category;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CatalogDocumentGenerator {
    public static byte[] getDocumentInByteArray(Map<Category, List<Product>> mapping) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Document document = DocumentHelper.generateDocument();
        PdfWriter writer = null;

        try {
            writer = PdfWriter.getInstance(document, byteArrayOutputStream);

            document.open();
            DocumentHelper.insertLogo(document);

            final String date = LocalDate.now().format(DateCustom.DATE_FORMAT);
            final String title = String.format("Catálogo - %s", date);
            DocumentHelper.insertTitle(document, title);

            mapping.keySet().forEach( category -> {
                final boolean thereIsProduct = !mapping.get(category).isEmpty();
                if (thereIsProduct) {
                    insertTableTitle(document, category);
                    insertTableProducts(document, mapping.get(category));
                }
            });

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    private static void insertTableTitle(Document document, Category category) {
        Paragraph tableTitle = new Paragraph(category.getDescription(), TextFormatter.FONT_PARAGRAPH);
        tableTitle.setSpacingAfter(8f);
        document.add(tableTitle);
    }

    private static void insertTableProducts(Document document, List<Product> products ) {
        PdfPTable table = DocumentHelper.generateTable(3);

        products.forEach(product -> addProductInTable(table, product));
        insertTableFooter(table);

        document.add(table);
    }

    private static void insertTableFooter(PdfPTable table) {

        Paragraph content = new Paragraph("* preços sujeitos a alterações", TextFormatter.FONT_FOOTER);
        content.setAlignment(Element.ALIGN_RIGHT);
        final PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(content);

        table.addCell(emptyCell());
        table.addCell(emptyCell());
        table.addCell(cell);
    }

    private static void addProductInTable(PdfPTable table, Product product) {
        Paragraph paragraph = new Paragraph(product.getName(), TextFormatter.FONT_PARAGRAPH);
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setPadding(4f);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        String price = DocumentHelper.priceFormatted(product.getPrice());
        paragraph = new Paragraph(price, TextFormatter.FONT_PARAGRAPH);
        cell = new PdfPCell(new Paragraph(paragraph));
        cell.setPadding(4f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        paragraph = new Paragraph(  product.getScale().getDescription(),
                TextFormatter.FONT_PARAGRAPH);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell(paragraph);
        cell.setPadding(4f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
    }

    private static PdfPCell emptyCell() {
        final PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);

        return cell;
    }
}
