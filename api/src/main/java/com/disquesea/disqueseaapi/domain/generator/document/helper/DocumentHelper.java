package com.disquesea.disqueseaapi.domain.generator.document.helper;

import com.disquesea.disqueseaapi.domain.generator.document.TextFormatter;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class DocumentHelper {

    public static Document generateDocument() {
        Rectangle pageSize = new Rectangle(595, 842);
        pageSize.setBackgroundColor(TextFormatter.BACKGROUND_COLOR);

        return new Document(pageSize);
    }

    public static void insertLogo(Document document) {
        Paragraph title = new Paragraph();
        final Chunk disque = new Chunk("Disque", TextFormatter.FONT_TITLE);
        final Chunk sea = new Chunk("Sea", TextFormatter.FONT_DETAIL_TITLE);
        title.add(disque);
        title.add(sea);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(32f);
        document.add(title);
    }

    public static void insertTitle(Document document, String title) {
        Paragraph paragraph = new Paragraph(title, TextFormatter.FONT_PARAGRAPH);
        paragraph.setSpacingAfter(32f);
        document.add(paragraph);
    }

    public static PdfPTable generateTable(int columnsNumber) {
        PdfPTable table = new PdfPTable(columnsNumber);
        table.setSpacingAfter(16f);
        table.setWidthPercentage(100f);

        return table;
    }

    public static String priceFormatted(BigDecimal value) {
        return "R$ " + value.setScale(2, RoundingMode.HALF_UP);
    }


}
