package com.satesilka.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CSVWriter {
    public static <T extends CSVSerializable> void write(String fileName, List<T> objects) throws IOException {
        List<String> lines = new ArrayList<>();
        if (objects.size() > 0) {
            lines.add(objects.get(0).generateCSVHeader());
            for (T object : objects) {
                lines.add(object.toCSV());
            }
        }
        Files.write(Path.of(fileName), lines);
    }
}
