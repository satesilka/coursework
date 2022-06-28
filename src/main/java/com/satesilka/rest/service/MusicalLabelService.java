package com.satesilka.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.satesilka.model.MusicalLabel;
import com.satesilka.rest.storage.MusicalLabelStorage;

@Service
@ApplicationScope
public class MusicalLabelService {
    @Autowired
    private MusicalLabelStorage musicalLabelStorage;
    private LocalDateTime lastSaveDate = LocalDateTime.now();

    private void checkMidnight() throws IOException {
        musicalLabelStorage.saveToCSV();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd");
        if (!dtf.format(lastSaveDate).equals(dtf.format(LocalDateTime.now()))) {
            musicalLabelStorage.clear();
            lastSaveDate = LocalDateTime.now();
        }
    }

    public List<MusicalLabel> getMusicalLabels() throws IOException {
        checkMidnight();
        return musicalLabelStorage.findAll();
    }

    public Optional<MusicalLabel> getMusicalLabel(final int id) throws IOException {
        checkMidnight();
        return musicalLabelStorage.findById(id);
    }

    public MusicalLabel createMusicalLabel(final MusicalLabel musicalLabel) throws IOException {
        musicalLabelStorage.save(musicalLabel);
        checkMidnight();
        return musicalLabel;
    }

    public Optional<MusicalLabel> updateMusicalLabel(final int id, final MusicalLabel updatedMusicalLabel) throws IOException {
        Optional<MusicalLabel> musicalLabel = musicalLabelStorage.findById(id);

        if (musicalLabel.isPresent()) {
            updatedMusicalLabel.setId(id);
            musicalLabelStorage.update(updatedMusicalLabel);

            checkMidnight();
            return Optional.of(updatedMusicalLabel);
        }

        return Optional.empty();
    }

    public Optional<MusicalLabel> deleteMusicalLabel(final int id) throws IOException {
        Optional<MusicalLabel> musicalLabel = musicalLabelStorage.findById(id);

        if (musicalLabel.isPresent()) {
            musicalLabelStorage.delete(musicalLabel.get());
            checkMidnight();
            return musicalLabel;
        }

        return Optional.empty();
    }
}
