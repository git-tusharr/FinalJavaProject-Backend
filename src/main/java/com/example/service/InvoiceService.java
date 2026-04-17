package com.example.service;

import com.example.model.Order;
import com.example.model.OrderItem;
import com.example.repository.InvoiceRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public byte[] createInvoice(Long orderId) {

        Order order = invoiceRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4, 40, 40, 40, 40);
            PdfWriter.getInstance(document, out);
            document.open();

            // =========================
            // 🎨 FONTS
            // =========================
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLACK);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11, Color.BLACK);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.BLACK);

            // =========================
            // 🏢 HEADER (LOGO + NAME)
            // =========================
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);

            // Logo
            try {
            	Image logo = Image.getInstance(
            		    getClass().getClassLoader().getResource("logo.png")
            		);
                logo.scaleToFit(120, 60);

                PdfPCell logoCell = new PdfPCell(logo);
                logoCell.setBorder(Rectangle.NO_BORDER);
                headerTable.addCell(logoCell);

            } catch (Exception e) {
                // fallback if logo missing
                PdfPCell emptyCell = new PdfPCell(new Phrase(""));
                emptyCell.setBorder(Rectangle.NO_BORDER);
                headerTable.addCell(emptyCell);
            }

            // Company Info
            PdfPCell companyCell = new PdfPCell();
            companyCell.setBorder(Rectangle.NO_BORDER);
            companyCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

            companyCell.addElement(new Paragraph("StealDeals", titleFont));
            companyCell.addElement(new Paragraph("Best Deals Everyday", normalFont));
            companyCell.addElement(new Paragraph("support@stealdeals.com", normalFont));

            headerTable.addCell(companyCell);

            document.add(headerTable);
            document.add(new Paragraph(" "));

            // =========================
            // 🧾 INVOICE TITLE
            // =========================
            Paragraph invoiceTitle = new Paragraph("INVOICE", boldFont);
            invoiceTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(invoiceTitle);

            document.add(new Paragraph(" "));

            // =========================
            // 📦 ORDER DETAILS
            // =========================
            PdfPTable orderTable = new PdfPTable(2);
            orderTable.setWidthPercentage(100);

            orderTable.addCell(getCell("Order Number", boldFont));
            orderTable.addCell(getCell(order.getOrderNumber(), normalFont));

            orderTable.addCell(getCell("Order Date", boldFont));
            orderTable.addCell(getCell(order.getCreatedAt().toString(), normalFont));

            orderTable.addCell(getCell("Status", boldFont));
            orderTable.addCell(getCell(order.getOrderStatus().toString(), normalFont));

            document.add(orderTable);
            document.add(new Paragraph(" "));

            // =========================
            // 🛒 ITEMS TABLE
            // =========================
            PdfPTable itemTable = new PdfPTable(4);
            itemTable.setWidthPercentage(100);

            // Table Header
            itemTable.addCell(getHeaderCell("Product", headerFont));
            itemTable.addCell(getHeaderCell("Price", headerFont));
            itemTable.addCell(getHeaderCell("Qty", headerFont));
            itemTable.addCell(getHeaderCell("Total", headerFont));

            ObjectMapper mapper = new ObjectMapper();

            List<OrderItem> items = mapper.readValue(
                    order.getItemsJson(),
                    new TypeReference<List<OrderItem>>() {}
            );

            double totalPrice = 0;

            for (OrderItem item : items) {
                double itemTotal = item.getPrice() * item.getQuantity();
                totalPrice += itemTotal;

                itemTable.addCell(getCell(item.getProductName(), normalFont));
                itemTable.addCell(getCell("₹" + String.format("%.2f", item.getPrice()), normalFont));
                itemTable.addCell(getCell(String.valueOf(item.getQuantity()), normalFont));
                itemTable.addCell(getCell("₹" + String.format("%.2f", itemTotal), normalFont));
            }

            document.add(itemTable);
            document.add(new Paragraph(" "));

            // =========================
            // 💰 TOTAL
            // =========================
            Paragraph total = new Paragraph(
                    "Total Amount: ₹" + String.format("%.2f", totalPrice),
                    titleFont
            );
            total.setAlignment(Element.ALIGN_RIGHT);

            document.add(total);

            // =========================
            // 🙏 FOOTER
            // =========================
            document.add(new Paragraph(" "));
            document.add(new Paragraph("----------------------------"));
            document.add(new Paragraph("Thank you for shopping with StealDeals!"));
            document.add(new Paragraph("Visit again!"));

            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate invoice", e);
        }
    }

    // =========================
    // 🔧 HELPER METHODS
    // =========================
    private PdfPCell getCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(8);
        return cell;
    }

    private PdfPCell getHeaderCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(Color.DARK_GRAY);
        cell.setPadding(8);
        return cell;
    }
}