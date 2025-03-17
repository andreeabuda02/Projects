package com.example.PsProiectAgentieDeTurismSERVER.Model.FileFactory;

import com.example.PsProiectAgentieDeTurismSERVER.Model.PachetTuristic;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriterJSON implements DocumentWriter{
    @Override
    public void writeFile(List<PachetTuristic> list) {
        String filePath = "D:\\FACULTATE\\Anul3\\Proiectare software (PS)\\Proiect files\\pachete_turistice.json";

        try (FileWriter writer = new FileWriter(filePath)) {
            // Start the JSON array
            writer.write("[\n");

            // JSON representation for each object in the list
            for (int i = 0; i < list.size(); i++) {
                PachetTuristic pachet = list.get(i);
                writer.write(" { \"idPachet\": \"" + pachet.getIdPachet() + "\", " +
                        "\"destinatie\": \"" + pachet.getDestinatie() + "\", " +
                        "\"perioada\": \"" + pachet.getPerioada() + "\", " +
                        "\"pret\": \"" + pachet.getPret() + "\" }");
                if (i != list.size()-1) {
                    writer.write(",\n");
                }
            }

            // End the JSON array
            writer.write("\n]");
            writer.close();
            System.out.println("JSON file created successfully.");
        } catch (IOException e) {
            System.err.println("Error writing JSON file: " + e.getMessage());
        }
    }

    @Override
    public void openFile() {
        Desktop dt = Desktop.getDesktop();
        try {
            dt.open(new File("D:\\FACULTATE\\Anul3\\Proiectare software (PS)\\Proiect files\\pachete_turistice.json"));
        } catch (IOException ex) {
            System.out.println("Error opening file");
        }
    }

}
