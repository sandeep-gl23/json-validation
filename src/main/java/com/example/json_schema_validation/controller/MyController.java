package com.example.json_schema_validation.controller;

import com.example.json_schema_validation.service.ValidationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MyController {
    @Autowired
    ValidationService validationService;

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody String jsonData)
    {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            validationService.validateJson(jsonObject);

            return ResponseEntity.ok("User ata is valid and saved!");
        } catch (org.everit.json.schema.ValidationException e) {
            Map<String, String> errors = new HashMap<>();
            e.getCausingExceptions().forEach(ex -> errors.put(ex.getPointerToViolation(), ex.getMessage()));
            errors.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errors);
        }
    }
}
