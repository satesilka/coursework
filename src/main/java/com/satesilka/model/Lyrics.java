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
public final class Lyrics implements CSVSerializable {
    private static AtomicInteger idGenerator = new AtomicInteger(0);
    private static final int CSV_TOKENS_LENGTH = 3;
    private static final int CSV_ID_INDEX = 0;
    private static final int CSV_SONG_NAME_INDEX = 1;
    private static final int CSV_LYRICS_INDEX = 2;

    @EqualsAndHashCode.Exclude private int id;
    private String songName;
    private String lyrics;

    public Lyrics(final String lyricsSongName, final String lyricsLyrics) {
        this(idGenerator.getAndIncrement(), lyricsSongName, lyricsLyrics);
    }

    public static void setIdGenerator(final int maxId) {
        idGenerator.set(maxId);
    }

    public void regenerateId() {
        id = idGenerator.getAndIncrement();
    }

    public String generateCSVHeader() {
        return "id,song_name,lyrics";
    }

    public String toCSV() {
        return String.format("%d,%s,%s", id, songName, lyrics);
    }

    public void fromCSV(final String csvLine) throws CSVParseException {
        final String[] tokens = csvLine.split(",");
        if (tokens.length != CSV_TOKENS_LENGTH) {
            throw new CSVParseException("Expected 3 values");
        }
        id = Integer.parseInt(tokens[CSV_ID_INDEX]);
        songName = tokens[CSV_SONG_NAME_INDEX];
        lyrics = tokens[CSV_LYRICS_INDEX];
    }
}
