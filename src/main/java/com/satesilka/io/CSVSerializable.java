package com.satesilka.io;

public interface CSVSerializable {
    String generateCSVHeader();
    String toCSV();
    void fromCSV(String csvLine) throws CSVParseException;
}
