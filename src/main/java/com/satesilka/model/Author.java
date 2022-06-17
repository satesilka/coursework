package com.satesilka.model;

import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.concurrent.atomic.AtomicInteger;

import com.satesilka.io.CSVParseException;
import com.satesilka.io.CSVSerializable;
import com.satesilka.model.deserializer.AuthorDeserializer;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = AuthorDeserializer.class)
public class Author implements CSVSerializable {
    private static AtomicInteger ID = new AtomicInteger(0);

    private int id;
    private String name;

    public Author(String name) {
        this(ID.getAndIncrement(), name);
    }

    public static AtomicInteger getIdGenerator() {
        return ID;
    }

    public String generateCSVHeader() {
        return "id,name";
    }

    public String toCSV() {
        return String.format("%d,%s", id, name);
    }

    public void fromCSV(String csvLine) throws CSVParseException {
        String[] tokens = csvLine.split(",");
        if (tokens.length != 2) {
            throw new CSVParseException("Expected 2 values");
        }
        id = Integer.parseInt(tokens[0]);
        name = tokens[1];
    }
}
