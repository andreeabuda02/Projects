package com.example.PsProiectAgentieDeTurismSERVER.Service;

import com.example.PsProiectAgentieDeTurismSERVER.Model.Administrator;
import com.example.PsProiectAgentieDeTurismSERVER.Model.Angajat;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String message) {
        try {
            MimeMessage email = javaMailSender.createMimeMessage();

            email.setFrom(new InternetAddress("hojdamatei019@gmail.com"));
            email.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            email.setSubject(subject);
            email.setContent(message, "text/html; charset=utf-8");

            javaMailSender.send(email);

        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    public String dateModificate(Administrator administrator) {
        StringBuilder html = new StringBuilder();

        try {
            FileReader fr = new FileReader("src/main/resources/templates/DateModificate.html");
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                html.append(line);
            }

            br.close();
        } catch (Exception e) {
            html.append("Date cont modificate: ").append(administrator.getEmail()).append(" ").append(administrator.getParola());
        }

        String emailBody = html.toString();
        emailBody = emailBody.replace("${nume}", administrator.getNume());
        emailBody = emailBody.replace("${prenume}", administrator.getPrenume());
        emailBody = emailBody.replace("${email}", administrator.getEmail());
        emailBody = emailBody.replace("${parola}", administrator.getParola());

        return emailBody;
    }

    public String dateModificate1(Angajat angajat) {
        StringBuilder html = new StringBuilder();

        try {
            FileReader fr = new FileReader("src/main/resources/templates/DateModificate.html");
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                html.append(line);
            }

            br.close();
        } catch (Exception e) {
            html.append("Date cont modificate: ").append(angajat.getEmail()).append(" ").append(angajat.getParola());
        }

        String emailBody = html.toString();
        emailBody = emailBody.replace("${nume}", angajat.getNume());
        emailBody = emailBody.replace("${prenume}", angajat.getPrenume());
        emailBody = emailBody.replace("${email}", angajat.getEmail());
        emailBody = emailBody.replace("${parola}", angajat.getParola());

        return emailBody;
    }
}
