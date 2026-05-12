package com.ambar.ecommerce_backend.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Existing simple email
    public void sendOrderEmail(
            String toEmail,
            String subject,
            String body
    ){
        System.out.println("EMAIL METHOD STARTED");
        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setFrom("ambaragarkar0411@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        System.out.println("EMAIL SENT SUCCESSFULLY");
    }

//    // NEW METHOD FOR PDF ATTACHMENT

    public void sendOrderReceipt(
            String toEmail,
            ByteArrayInputStream pdfStream,
            String subject,
            String body
    ) {

        try {

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setFrom("ambaragarkar0411@gmail.com");

            helper.setTo(toEmail);

            helper.setSubject(subject);

            helper.setText(body);

            ByteArrayResource resource =
                    new ByteArrayResource(
                            pdfStream.readAllBytes()
                    );

            helper.addAttachment(
                    "receipt.pdf",
                    resource
            );

            mailSender.send(message);

        } catch (Exception e) {

            System.out.println("EMAIL ERROR:");

            e.printStackTrace();
        }
    }}
//    public void sendOrderReceipt(
//            String toEmail,
//            byte[] pdfBytes
//    ){
//
//        try {
//
//            MimeMessage message =
//                    mailSender.createMimeMessage();
//
//            MimeMessageHelper helper =
//                    new MimeMessageHelper(message, true);
//
//            helper.setFrom("ambaragarkar0411@gmail.com");
//
//            helper.setTo(toEmail);
//
//            helper.setSubject("Order Receipt");
//
//            helper.setText(
//                    "Thank you for your order. Please find attached receipt."
//            );
//
//            ByteArrayResource resource =
//                    new ByteArrayResource(pdfBytes);
//
//            helper.addAttachment(
//                    "receipt.pdf",
//                    resource
//            );
//
//            mailSender.send(message);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}