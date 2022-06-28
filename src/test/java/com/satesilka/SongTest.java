package com.satesilka;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.satesilka.io.CSVParseException;
import com.satesilka.io.CSVReader;
import com.satesilka.model.Song;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SongTest {
    private final String url = "http://localhost:8080/song";
    private final Song entity = new Song("Сепаратіст", "Медовий полин", "В коня довший", 150);
    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testCreate() {
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testCSV() throws IOException, CSVParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        File file = new File("song-" + dtf.format(LocalDateTime.now()) + ".csv");
        List<Song> entities = CSVReader.read(file, Song::new);
        List<Song> filteredEntities = entities.stream().filter((item) -> item.equals(entity)).collect(Collectors.toList());
        assertFalse(filteredEntities.isEmpty());
    }

    @Test
    public void testGet() {
        ResponseEntity<Song[]> response = restTemplate.getForEntity(url, Song[].class);
        assertEquals(200, response.getStatusCodeValue());

        List<Song> entities = Stream.of(response.getBody()).filter((item) -> item.equals(entity)).collect(Collectors.toList());
        assertFalse(entities.isEmpty());
    }

    @Test
    public void testUpdate() {
        ResponseEntity<Song[]> response = restTemplate.getForEntity(url, Song[].class);
        assertEquals(200, response.getStatusCodeValue());

        List<Song> entities = Stream.of(response.getBody()).filter((item) -> item.equals(entity)).collect(Collectors.toList());

        entities.forEach((item) -> item.setName("ABC"));
        entities.forEach((item) -> restTemplate.put(url + "/" + item.getId(), item));

        response = restTemplate.getForEntity(url, Song[].class);
        assertEquals(200, response.getStatusCodeValue());

        List<Song> updatedEntities = Stream.of(response.getBody()).filter((item) -> item.getName().equals("ABC")).collect(Collectors.toList());

        entities.forEach((item) -> assertTrue(updatedEntities.contains(item)));
    }

    @Test
    public void testDelete() {
        ResponseEntity<Song[]> response = restTemplate.getForEntity(url, Song[].class);
        assertEquals(200, response.getStatusCodeValue());

        List<Song> entities = Stream.of(response.getBody()).filter((item) -> item.equals(entity)).collect(Collectors.toList());

        entities.forEach((item) -> restTemplate.delete(url + "/" + item.getId()));

        response = restTemplate.getForEntity(url, Song[].class);
        assertEquals(200, response.getStatusCodeValue());

        entities = Stream.of(response.getBody()).filter((item) -> item.equals(entity)).collect(Collectors.toList());

        assertTrue(entities.isEmpty());
    }

}
