package com.satesilka.model;

import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.concurrent.atomic.AtomicInteger;

import com.satesilka.io.CSVParseException;
import com.satesilka.io.CSVSerializable;
import com.satesilka.model.deserializer.MusicalLabelDeserializer;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = MusicalLabelDeserializer.class)
public class MusicalLabel implements CSVSerializable {
    private static AtomicInteger ID = new AtomicInteger(0);

    private int id;
    private String name;
    private String owner;

    public MusicalLabel(String name, String owner) {
        this(ID.getAndIncrement(), name, owner);
    }

    public static AtomicInteger getIdGenerator() {
        return ID;
    }

    public String generateCSVHeader() {
        return "id,name,owner";
    }

    public String toCSV() {
        return String.format("%d,%s,%s", id, name, owner);
    }

    public void fromCSV(String csvLine) throws CSVParseException {
        String[] tokens = csvLine.split(",");
        if (tokens.length != 3) {
            throw new CSVParseException("Expected 3 values");
        }
        id = Integer.parseInt(tokens[0]);
        name = tokens[1];
        owner = tokens[2];
    }
}
