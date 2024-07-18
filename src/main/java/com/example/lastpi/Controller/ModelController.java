package com.example.lastpi.Controller;

import com.example.lastpi.Entity.YourInputData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ModelController {

    @PostMapping("/predict")
    public ResponseEntity<String> predict(@RequestBody YourInputData inputData) {
        // Make HTTP request to TensorFlow Serving
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://tensorflow-serving-host:port/v1/models/model_name:predict";
        ResponseEntity<String> response = restTemplate.postForEntity(url, inputData, String.class);

        // Return prediction response
        return response;
    }
}
