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
public final class Album implements CSVSerializable {
    private static AtomicInteger idGenerator = new AtomicInteger(0);
    private static final int CSV_TOKENS_LENGTH = 4;
    private static final int CSV_ID_INDEX = 0;
    private static final int CSV_NAME_INDEX = 1;
    private static final int CSV_AUTHOR_INDEX = 2;
    private static final int CSV_SONG_INDEX = 3;

    @EqualsAndHashCode.Exclude private int id;
    private String name;
    private String author;
    private int songCount;

    public Album(final String albumName, final String albumAuthor, final int albumSongCount) {
        this(idGenerator.getAndIncrement(), albumName, albumAuthor, albumSongCount);
    }

    public static void setIdGenerator(final int maxId) {
        idGenerator.set(maxId);
    }

    public void regenerateId() {
        id = idGenerator.getAndIncrement();
    }

    public String generateCSVHeader() {
        return "id,name,author,song_count";
    }

    public String toCSV() {
        return String.format("%d,%s,%s,%d", id, name, author, songCount);
    }

    public void fromCSV(final String csvLine) throws CSVParseException {
        final String[] tokens = csvLine.split(",");
        if (tokens.length != CSV_TOKENS_LENGTH) {
            throw new CSVParseException("Expected 4 values");
        }
        id = Integer.parseInt(tokens[CSV_ID_INDEX]);
        name = tokens[CSV_NAME_INDEX];
        author = tokens[CSV_AUTHOR_INDEX];
        songCount = Integer.parseInt(tokens[CSV_SONG_INDEX]);
    }
}
