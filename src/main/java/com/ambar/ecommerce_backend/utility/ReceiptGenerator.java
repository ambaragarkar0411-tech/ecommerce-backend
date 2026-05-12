package com.ambar.ecommerce_backend.utility;

import com.ambar.ecommerce_backend.model.Order;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Component
public class ReceiptGenerator {

    public ByteArrayInputStream generateReceipt(Order order){

        Document document = new Document();

        ByteArrayOutputStream out =
                new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);

            document.open();

            document.add(
                    new Paragraph("Order Receipt")
            );

            document.add(
                    new Paragraph(
                            "Customer Name: "
                                    + order.getCustomerName()
                    )
            );

            document.add(
                    new Paragraph(
                            "Email: "
                                    + order.getEmail()
                    )
            );

            document.add(
                    new Paragraph(
                            "Product: "
                                    + order.getProductName()
                    )
            );

            document.add(
                    new Paragraph(
                            "Total Price: ₹"
                                    + order.getTotalPrice()
                    )
            );

            document.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return new ByteArrayInputStream(
                out.toByteArray()
        );
    }
}