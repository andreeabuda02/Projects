package com.example.PsProiectAgentieDeTurismSERVER.Model.FileFactory;

import com.example.PsProiectAgentieDeTurismSERVER.Model.PachetTuristic;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WriterDOC implements DocumentWriter {
    private static final String FILE_PATH = "D:\\FACULTATE\\Anul3\\Proiectare software (PS)\\Proiect files\\";

    @Override
    public void writeFile(List<PachetTuristic> list) {
        XWPFDocument document = new XWPFDocument();
        FileOutputStream out = null;

        try {
            String filePath = FILE_PATH + "pachetTuristicFile.docx";
            out = new FileOutputStream(filePath);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        //create table
        XWPFTable table = document.createTable();

        //create header
        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText("ID");
        tableRowOne.addNewTableCell().setText("Destinatie");
        tableRowOne.addNewTableCell().setText("Pret");
        tableRowOne.addNewTableCell().setText("Perioada");

        //file content
        for (PachetTuristic pachet : list) {
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(pachet.getIdPachet().toString());
            tableRow.getCell(1).setText(pachet.getDestinatie());
            tableRow.getCell(2).setText(pachet.getPret().toString());
            tableRow.getCell(3).setText(pachet.getPerioada().toString());
        }

        try {
            document.write(out);
            out.close();
            System.out.println(".DOCX file for pachete turistice created successfully");
        } catch (IOException exception) {
            System.out.println("Error writing DOCX file for pachete turistice: " + exception.getMessage());
        }
    }

    @Override
    public void openFile() {
        try {
            String filePath = FILE_PATH + "pachetTuristicFile.docx";
            Desktop.getDesktop().open(new File(filePath));
        } catch (IOException ex) {
            System.out.println("Error opening file");
        }
    }
}
