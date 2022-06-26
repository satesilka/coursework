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
public final class Author implements CSVSerializable {
    private static AtomicInteger idGenerator = new AtomicInteger(0);
    private static final int CSV_TOKENS_LENGTH = 2;
    private static final int CSV_ID_INDEX = 0;
    private static final int CSV_NAME_INDEX = 1;

    @EqualsAndHashCode.Exclude private int id;
    private String name;

    public Author(final String authorName) {
        this(idGenerator.getAndIncrement(), authorName);
    }

    public static void setIdGenerator(final int maxId) {
        idGenerator.set(maxId);
    }

    public void regenerateId() {
        id = idGenerator.getAndIncrement();
    }

    public String generateCSVHeader() {
        return "id,name";
    }

    public String toCSV() {
        return String.format("%d,%s", id, name);
    }

    public void fromCSV(final String csvLine) throws CSVParseException {
        final String[] tokens = csvLine.split(",");
        if (tokens.length != CSV_TOKENS_LENGTH) {
            throw new CSVParseException("Expected 2 values");
        }
        id = Integer.parseInt(tokens[CSV_ID_INDEX]);
        name = tokens[CSV_NAME_INDEX];
    }
}
