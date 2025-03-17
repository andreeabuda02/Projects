package com.example.PsProiectAgentieDeTurismSERVER.Model.FileFactory;

import com.example.PsProiectAgentieDeTurismSERVER.Model.PachetTuristic;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriterCSV implements DocumentWriter{
    @Override
    public void writeFile(List<PachetTuristic> list) {
        String filePath = "D:\\FACULTATE\\Anul3\\Proiectare software (PS)\\Proiect files\\Info.csv";


        try (FileWriter writer = new FileWriter(filePath)) {
            //csv header
            writer.write("ID, Destinatie, Perioada, Pret\n");

            //csv content
            for (PachetTuristic pachet : list) {
                writer.write(pachet.getIdPachet() + ",");
                writer.write(pachet.getDestinatie() + ",");
                writer.write(pachet.getPerioada().toString() + ",");
                writer.write(pachet.getPret() + "\n");
            }

            writer.close();
            System.out.println("CSV file created successfully.");
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }

    @Override
    public void openFile() {
        try {
            File file = new File("D:\\FACULTATE\\Anul3\\Proiectare software (PS)\\Proiect files\\Info.csv");
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("CSV file not found.");
            }
        } catch (IOException ex) {
            System.err.println("Error opening file: " + ex.getMessage());
        }
    }
}

