package com.example.json_schema_validation.service;

import jakarta.annotation.PostConstruct;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class ValidationService {

    Schema schema;

    @PostConstruct
    public void init() {
        try (InputStream schemaStream = getClass().getResourceAsStream("/schema/User-Schema.json")) {
            if (schemaStream == null) {
                throw new RuntimeException("Schema file not found");
            }
            JSONObject jsonSchema = new JSONObject(new JSONTokener(schemaStream));
            this.schema = SchemaLoader.load(jsonSchema);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON schema", e);
        }
    }

    public void validateJson(JSONObject jsonObject) {
        schema.validate(jsonObject);
    }


}