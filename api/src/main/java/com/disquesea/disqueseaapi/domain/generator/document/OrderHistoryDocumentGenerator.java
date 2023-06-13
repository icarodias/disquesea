package com.disquesea.disqueseaapi.domain.generator.document;

import com.disquesea.disqueseaapi.components.RoundCustom;
import com.disquesea.disqueseaapi.components.DateCustom;
import com.disquesea.disqueseaapi.domain.generator.document.helper.DocumentHelper;
import com.disquesea.disqueseaapi.domain.model.Order;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class OrderHistoryDocumentGenerator {

    public static byte[] getDocumentInByteArray(List<Order> orders) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Document document = DocumentHelper.generateDocument();
        PdfWriter writer = null;

        try {
            writer = PdfWriter.getInstance(document, byteArrayOutputStream);

            document.open();
            DocumentHelper.insertLogo(document);

            final String date = LocalDate.now().format(DateCustom.DATE_FORMAT);
            final String title = String.format("Histórico de Pedidos - %s", date);
            DocumentHelper.insertTitle(document, title);

            insertOrdersTable(document, orders);

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    private static void insertOrdersTable(Document document, List<Order> orders ) {
        PdfPTable table = DocumentHelper.generateTable(5);

        orders.forEach(order -> addOrderInTable(table, order));
        document.add(table);
    }

    private static void addOrderInTable(PdfPTable table, Order order) {
        Paragraph paragraph = new Paragraph(order.getProduct().getName(), TextFormatter.FONT_PARAGRAPH);
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setPadding(4f);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        String type = order.getIsSell() ? "venda" : "compra";
        paragraph = new Paragraph(type, TextFormatter.FONT_PARAGRAPH);
        cell = new PdfPCell(new Paragraph(paragraph));
        cell.setPadding(4f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        paragraph = new Paragraph(order.getCreatedAt().format(DateCustom.DATE_FORMAT),
                TextFormatter.FONT_PARAGRAPH);
        cell = new PdfPCell(new Paragraph(paragraph));
        cell.setPadding(4f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        BigDecimal amount = RoundCustom.roundingAmount(order.getAmount(), order.getProduct().getScale());
        paragraph = new Paragraph(amount + " " + order.getProduct().getScale().getDescription(),
                TextFormatter.FONT_PARAGRAPH);
        cell = new PdfPCell(new Paragraph(paragraph));
        cell.setPadding(4f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        String price = valueFormatted(order.getPrice());
        paragraph = new Paragraph(price, TextFormatter.FONT_PARAGRAPH);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell(paragraph);
        cell.setPadding(4f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
    }

    private static String valueFormatted(BigDecimal value) {
        return "R$ " + value.setScale(2, RoundingMode.HALF_UP);
    }
}
