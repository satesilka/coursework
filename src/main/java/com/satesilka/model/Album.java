package com.satesilka.model;

import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.concurrent.atomic.AtomicInteger;

import com.satesilka.io.CSVParseException;
import com.satesilka.io.CSVSerializable;
import com.satesilka.model.deserializer.AlbumDeserializer;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = AlbumDeserializer.class)
public class Album implements CSVSerializable {
    private static AtomicInteger ID = new AtomicInteger(0);

    private int id;
    private String name;
    private String author;
    private int songCount;

    public Album(String name, String author, int songCount) {
        this(ID.getAndIncrement(), name, author, songCount);
    }

    public static AtomicInteger getIdGenerator() {
        return ID;
    }

    public String generateCSVHeader() {
        return "id,name,author,song_count";
    }

    public String toCSV() {
        return String.format("%d,%s,%s,%d", id, name, author, songCount);
    }

    public void fromCSV(String csvLine) throws CSVParseException {
        String[] tokens = csvLine.split(",");
        if (tokens.length != 4) {
            throw new CSVParseException("Expected 4 values");
        }
        id = Integer.parseInt(tokens[0]);
        name = tokens[1];
        author = tokens[2];
        songCount = Integer.parseInt(tokens[3]);
    }
}
