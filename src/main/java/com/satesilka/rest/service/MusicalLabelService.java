package com.satesilka.rest.service;

import com.satesilka.io.CSVParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.satesilka.model.MusicalLabel;
import com.satesilka.rest.storage.MusicalLabelStorage;

@Service
@ApplicationScope
public class MusicalLabelService {
    private final MusicalLabelStorage musicalLabelRepository;

    public MusicalLabelService() throws IOException, CSVParseException {
        musicalLabelRepository = MusicalLabelStorage.getInstance();
    }

    public List<MusicalLabel> getMusicalLabels() {
        return musicalLabelRepository.findAll();
    }

    public Optional<MusicalLabel> getMusicalLabel(final int id) {
        return musicalLabelRepository.findById(id);
    }

    public MusicalLabel createMusicalLabel(final MusicalLabel musicalLabel){
        musicalLabelRepository.save(musicalLabel);
        return musicalLabel;
    }

    public Optional<MusicalLabel> updateMusicalLabel(final int id, final MusicalLabel updatedMusicalLabel) {
        Optional<MusicalLabel> musicalLabel = musicalLabelRepository.findById(id);

        if (musicalLabel.isPresent()) {
            updatedMusicalLabel.setId(id);
            musicalLabelRepository.save(updatedMusicalLabel);

            return Optional.of(updatedMusicalLabel);
        }

        return Optional.empty();
    }

    public Optional<MusicalLabel> deleteMusicalLabel(final int id) {
        Optional<MusicalLabel> musicalLabel = musicalLabelRepository.findById(id);

        if (musicalLabel.isPresent()) {
            musicalLabelRepository.delete(musicalLabel.get());
            return musicalLabel;
        }

        return Optional.empty();
    }
}
