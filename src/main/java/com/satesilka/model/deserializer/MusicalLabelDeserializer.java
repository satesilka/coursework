package com.satesilka.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.satesilka.model.MusicalLabel;

import java.io.IOException;

public class MusicalLabelDeserializer extends JsonDeserializer<MusicalLabel> {
    @Override
    public MusicalLabel deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        String name = jsonNode.get("name").textValue();
        String owner = jsonNode.get("owner").textValue();

        return new MusicalLabel(name, owner);
    }
}

