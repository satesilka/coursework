package com.satesilka.rest.storage;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import com.satesilka.io.CSVParseException;
import com.satesilka.io.CSVReader;
import com.satesilka.model.MusicalLabel;

public class MusicalLabelStorage {
    private final Map<Integer, MusicalLabel> data = new HashMap<>();
    private static MusicalLabelStorage instance;

    private MusicalLabelStorage() throws IOException, CSVParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        File file = new File("musicalLabel-" + dtf.format(LocalDateTime.now()) + ".csv");
        if (file.exists()) {
            int maxId = 0;
            List<MusicalLabel> musicalLabel = CSVReader.read(file, MusicalLabel::new);
            for (MusicalLabel label : musicalLabel) {
                maxId = Integer.max(maxId, label.getId());
                data.put(label.getId(), label);
            }
            MusicalLabel.getIdGenerator().set(maxId + 1);
        }
    }

    public static MusicalLabelStorage getInstance() throws IOException, CSVParseException {
        if (instance == null) {
            instance = new MusicalLabelStorage();
        }
        return instance;
    }

    public List<MusicalLabel> findAll() {
        return new ArrayList<>(data.values());
    }

    public Optional<MusicalLabel> findById(int id) {
        if (data.containsKey(id)) {
            return Optional.of(data.get(id));
        }
        return Optional.empty();
    }

    public void save(MusicalLabel musicalLabel) {
        data.put(musicalLabel.getId(), musicalLabel);
    }

    public void delete(MusicalLabel musicalLabel) {
        data.remove(musicalLabel.getId());
    }
}
