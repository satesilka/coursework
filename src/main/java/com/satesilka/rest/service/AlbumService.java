package com.satesilka.rest.service;

import com.satesilka.io.CSVParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.satesilka.model.Album;
import com.satesilka.rest.storage.AlbumStorage;

@Service
@ApplicationScope
public class AlbumService {
    private final AlbumStorage albumRepository;

    public AlbumService() throws IOException, CSVParseException {
        albumRepository = AlbumStorage.getInstance();
    }

    public List<Album> getAlbums() {
        return albumRepository.findAll();
    }

    public Optional<Album> getAlbum(final int id) {
        return albumRepository.findById(id);
    }

    public Album createAlbum(final Album album){
        albumRepository.save(album);
        return album;
    }

    public Optional<Album> updateAlbum(final int id, final Album updatedAlbum) {
        Optional<Album> album = albumRepository.findById(id);

        if (album.isPresent()) {
            updatedAlbum.setId(id);
            albumRepository.save(updatedAlbum);

            return Optional.of(updatedAlbum);
        }

        return Optional.empty();
    }

    public Optional<Album> deleteAlbum(final int id) {
        Optional<Album> album = albumRepository.findById(id);

        if (album.isPresent()) {
            albumRepository.delete(album.get());
            return album;
        }

        return Optional.empty();
    }
}
