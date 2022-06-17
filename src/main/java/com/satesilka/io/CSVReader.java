package com.satesilka.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVReader {
    public interface ObjectCreator {
        Object create();
    }

    public static <T extends CSVSerializable> List<T> read(File file, ObjectCreator creator) throws IOException, CSVParseException {
        List<T> objects = new ArrayList<>();
        String headers;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();

            if (line != null) {
                headers = line;
            }

            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    T object = (T) creator.create();
                    object.fromCSV(line);
                    objects.add(object);
                }
            }
        }

        return objects;
    }
}
