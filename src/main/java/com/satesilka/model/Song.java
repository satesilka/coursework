package com.satesilka.model;

import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.concurrent.atomic.AtomicInteger;

import com.satesilka.io.CSVParseException;
import com.satesilka.io.CSVSerializable;
import com.satesilka.model.deserializer.SongDeserializer;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = SongDeserializer.class)
public class Song implements CSVSerializable {
    private static AtomicInteger ID = new AtomicInteger(0);

    private int id;
    private String name;
    private String authorName;
    private String albumName;
    private int duration;

    public Song(String name, String authorName, String albumName, int duration) {
        this(ID.getAndIncrement(), name, authorName, albumName, duration);
    }

    public static AtomicInteger getIdGenerator() {
        return ID;
    }

    public String generateCSVHeader() {
        return "id,name,author_name,album_name,duration";
    }

    public String toCSV() {
        return String.format("%d,%s,%s,%s,%d", id, name, authorName, albumName, duration);
    }

    public void fromCSV(String csvLine) throws CSVParseException {
        String[] tokens = csvLine.split(",");
        if (tokens.length != 5) {
            throw new CSVParseException("Expected 5 values");
        }
        id = Integer.parseInt(tokens[0]);
        name = tokens[1];
        authorName = tokens[2];
        albumName = tokens[3];
        duration = Integer.parseInt(tokens[4]);
    }
}
