package com.example.PsProiectAgentieDeTurismSERVER.Model.FileFactory;

public class FactoryXML implements DocumentFactory{
    @Override
    public DocumentWriter factoryMethod() {
        return new WriterXML();
    }
}
