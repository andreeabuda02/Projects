package com.example.PsProiectAgentieDeTurismSERVER.Model.FileFactory;

import com.example.PsProiectAgentieDeTurismSERVER.Model.PachetTuristic;

import java.util.List;

public interface DocumentWriter {
    public abstract void writeFile(List<PachetTuristic> list);
    public abstract void openFile();
}
