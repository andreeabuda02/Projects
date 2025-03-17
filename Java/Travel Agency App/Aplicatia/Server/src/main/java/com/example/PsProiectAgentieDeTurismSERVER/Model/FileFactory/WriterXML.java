package com.example.PsProiectAgentieDeTurismSERVER.Model.FileFactory;

import com.example.PsProiectAgentieDeTurismSERVER.Model.PachetTuristic;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriterXML implements DocumentWriter{
    @Override
    public void writeFile(List<PachetTuristic> list) {
        String filePath = "D:\\FACULTATE\\Anul3\\Proiectare software (PS)\\Proiect files\\pachete_turistice.xml";

        try (FileWriter writer = new FileWriter(filePath)) {
            // Write XML declaration
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<data>\n");

            // XML content for each object in the list
            for (PachetTuristic pachet : list) {
                writer.write("  <pachet>\n    <idPachet>" + pachet.getIdPachet() + "</idPachet>\n    " +
                        "<destinatie>" + pachet.getDestinatie() + "</destinatie>\n    " +
                        "<perioada>" + pachet.getPerioada() + "</perioada>\n    " +
                        "<pret>" + pachet.getPret() + "</pret>\n  </pachet>\n");
            }

            writer.write("</data>");
            writer.close();
            System.out.println("XML file created successfully.");
        } catch (IOException e) {
            System.err.println("Error writing XML file: " + e.getMessage());
        }
    }

    @Override
    public void openFile() {
        Desktop dt = Desktop.getDesktop();
        try {
            dt.open(new File("D:\\FACULTATE\\Anul3\\Proiectare software (PS)\\Proiect files\\pachete_turistice.xml"));
        } catch (IOException ex) {
            System.out.println("Error opening file");
        }
    }
}
