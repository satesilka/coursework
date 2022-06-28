package com.satesilka.model;

import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.concurrent.atomic.AtomicInteger;

import com.satesilka.io.CSVParseException;
import com.satesilka.io.CSVSerializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class Song implements CSVSerializable {
    private static AtomicInteger idGenerator = new AtomicInteger(0);
    private static final int CSV_TOKENS_LENGTH = 5;
    private static final int CSV_ID_INDEX = 0;
    private static final int CSV_NAME_INDEX = 1;
    private static final int CSV_AUTHOR_INDEX = 2;
    private static final int CSV_ALBUM_NAME_INDEX = 3;
    private static final int CSV_DURATION_INDEX = 4;

    @EqualsAndHashCode.Exclude private int id;
    private String name;
    private String authorName;
    private String albumName;
    private int duration;

    public Song(final String songName, final String songAuthorName, final String songAlbumName, final int songDuration) {
        this(idGenerator.getAndIncrement(), songName, songAuthorName, songAlbumName, songDuration);
    }

    public static void setIdGenerator(final int maxId) {
        idGenerator.set(maxId);
    }

    public void regenerateId() {
        id = idGenerator.getAndIncrement();
    }

    public String generateCSVHeader() {
        return "id,name,author_name,album_name,duration";
    }

    public String toCSV() {
        return String.format("%d,%s,%s,%s,%d", id, name, authorName, albumName, duration);
    }

    public void fromCSV(final String csvLine) throws CSVParseException {
        final String[] tokens = csvLine.split(",");
        if (tokens.length != CSV_TOKENS_LENGTH) {
            throw new CSVParseException("Expected 5 values");
        }
        id = Integer.parseInt(tokens[CSV_ID_INDEX]);
        name = tokens[CSV_NAME_INDEX];
        authorName = tokens[CSV_AUTHOR_INDEX];
        albumName = tokens[CSV_ALBUM_NAME_INDEX];
        duration = Integer.parseInt(tokens[CSV_DURATION_INDEX]);
    }
}
