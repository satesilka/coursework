package com.satesilka.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.satesilka.model.Song;

import java.io.IOException;

public class SongDeserializer extends JsonDeserializer<Song> {
    @Override
    public Song deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        String name = jsonNode.get("name").textValue();
        String authorName = jsonNode.get("authorName").textValue();
        String albumName = jsonNode.get("albumName").textValue();
        int duration = jsonNode.get("duration").asInt();

        return new Song(name, authorName, albumName, duration);
    }
}
