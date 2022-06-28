package com.satesilka.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public final class CSVReader {
    public interface ObjectCreator {
        Object create();
    }

    private CSVReader() { }

    public static <T extends CSVSerializable> List<T> read(final File file, final ObjectCreator creator)
            throws IOException, CSVParseException {
        List<T> objects = new ArrayList<>();
        String headers = "";

        List<String> lines = Files.readAllLines(file.toPath());
        for (String line : lines) {
            if (!line.isEmpty()) {
                if (headers.isEmpty()) {
                    headers = line;
                } else {
                    T object = (T) creator.create();
                    object.fromCSV(line);
                    objects.add(object);
                }
            }
        }

        return objects;
    }
}
