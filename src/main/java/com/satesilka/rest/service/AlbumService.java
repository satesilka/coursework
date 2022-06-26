package com.satesilka.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.satesilka.model.Album;
import com.satesilka.rest.storage.AlbumStorage;

@Service
@ApplicationScope
public class AlbumService {
    @Autowired
    private AlbumStorage albumStorage;
    private LocalDateTime lastSaveDate = LocalDateTime.now();

    private void checkMidnight() throws IOException {
        albumStorage.saveToCSV();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd");
        if (!dtf.format(lastSaveDate).equals(dtf.format(LocalDateTime.now()))) {
            albumStorage.clear();
            lastSaveDate = LocalDateTime.now();
        }
    }

    public List<Album> getAlbums() throws IOException {
        checkMidnight();
        return albumStorage.findAll();
    }

    public Optional<Album> getAlbum(final int id) throws IOException {
        checkMidnight();
        return albumStorage.findById(id);
    }

    public Album createAlbum(final Album album) throws IOException {
        albumStorage.save(album);
        checkMidnight();
        return album;
    }

    public Optional<Album> updateAlbum(final int id, final Album updatedAlbum) throws IOException {
        Optional<Album> album = albumStorage.findById(id);

        if (album.isPresent()) {
            updatedAlbum.setId(id);
            albumStorage.update(updatedAlbum);

            checkMidnight();
            return Optional.of(updatedAlbum);
        }

        return Optional.empty();
    }

    public Optional<Album> deleteAlbum(final int id) throws IOException {
        Optional<Album> album = albumStorage.findById(id);

        if (album.isPresent()) {
            albumStorage.delete(album.get());
            checkMidnight();
            return album;
        }

        return Optional.empty();
    }
}
