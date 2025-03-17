package com.example.PsProiectAgentieDeTurismSERVER.Model.FileFactory;

public class FactoryDOC implements DocumentFactory{
    @Override
    public DocumentWriter factoryMethod() {
        return new WriterDOC();
    }

}
