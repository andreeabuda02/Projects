package com.example.PsProiectAgentieDeTurismSERVER.Model.FileFactory;

public class FactoryCSV implements DocumentFactory{
    @Override
    public DocumentWriter factoryMethod() {
        return new WriterCSV();
    }

}
