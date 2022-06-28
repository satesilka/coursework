package com.satesilka.rest.storage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.satesilka.io.CSVParseException;
import com.satesilka.io.CSVReader;
import com.satesilka.io.CSVWriter;
import com.satesilka.model.MusicalLabel;

@Component
public final class MusicalLabelStorage {
    private final Map<Integer, MusicalLabel> data = new HashMap<>();

    public MusicalLabelStorage() throws IOException, CSVParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        File file = new File("musicalLabel-" + dtf.format(LocalDateTime.now()) + ".csv");
        if (file.exists()) {
            int maxId = 0;
            List<MusicalLabel> musicalLabel = CSVReader.read(file, MusicalLabel::new);
            for (MusicalLabel label : musicalLabel) {
                maxId = Integer.max(maxId, label.getId());
                data.put(label.getId(), label);
            }
            MusicalLabel.setIdGenerator(maxId + 1);
        }
    }

    public void saveToCSV() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        CSVWriter.write("musicalLabel-" + dtf.format(LocalDateTime.now()) + ".csv", new ArrayList<>(data.values()));
    }

    public void clear() {
        data.clear();
    }

    public List<MusicalLabel> findAll() {
        return new ArrayList<>(data.values());
    }

    public Optional<MusicalLabel> findById(final int id) {
        if (data.containsKey(id)) {
            return Optional.of(data.get(id));
        }
        return Optional.empty();
    }

    public void save(final MusicalLabel musicalLabel) {
        musicalLabel.regenerateId();
        data.put(musicalLabel.getId(), musicalLabel);
    }

    public void update(final MusicalLabel musicalLabel) {
        data.put(musicalLabel.getId(), musicalLabel);
    }

    public void delete(final MusicalLabel musicalLabel) {
        data.remove(musicalLabel.getId());
    }
}
