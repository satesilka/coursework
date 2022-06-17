package com.satesilka.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.satesilka.model.Album;

import java.io.IOException;

public class AlbumDeserializer extends JsonDeserializer<Album> {
    @Override
    public Album deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        String name = jsonNode.get("name").textValue();
        String author = jsonNode.get("author").textValue();
        int songCount = jsonNode.get("songCount").asInt();

        return new Album(name, author, songCount);
    }
}
