package com.satesilka.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.satesilka.model.Lyrics;

import java.io.IOException;

public class LyricsDeserializer extends JsonDeserializer<Lyrics> {
    @Override
    public Lyrics deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        String songName = jsonNode.get("songName").textValue();
        String lyrics = jsonNode.get("lyrics").textValue();

        return new Lyrics(songName, lyrics);
    }
}

