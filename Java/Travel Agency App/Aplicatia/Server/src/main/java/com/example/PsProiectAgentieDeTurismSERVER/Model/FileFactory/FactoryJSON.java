package com.example.PsProiectAgentieDeTurismSERVER.Model.FileFactory;

public class FactoryJSON implements DocumentFactory{
    @Override
    public DocumentWriter factoryMethod() {
        return new WriterJSON();
    }
}
