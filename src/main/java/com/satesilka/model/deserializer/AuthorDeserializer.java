package com.satesilka.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.satesilka.model.Author;

import java.io.IOException;

public class AuthorDeserializer extends JsonDeserializer<Author> {
    @Override
    public Author deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        String name = jsonNode.get("name").textValue();

        return new Author(name);
    }
}
