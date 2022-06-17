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
public class Lyrics implements CSVSerializable {
    private static AtomicInteger ID = new AtomicInteger(0);

    private int id;
    private String songName;
    private String lyrics;

    public Lyrics(String songName, String lyrics) {
        this(ID.getAndIncrement(), songName, lyrics);
    }

    public static AtomicInteger getIdGenerator() {
        return ID;
    }
    public String generateCSVHeader() {
        return "id,song_name,lyrics";
    }

    public String toCSV() {
        return String.format("%d,%s,%s", id, songName, lyrics);
    }

    public void fromCSV(String csvLine) throws CSVParseException {
        String[] tokens = csvLine.split(",");
        if (tokens.length != 2) {
            throw new CSVParseException("Expected 3 values");
        }
        id = Integer.parseInt(tokens[0]);
        songName = tokens[1];
        lyrics = tokens[2];
    }
}
