package org.example.lesson_5_spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class СurrenciesService {

    @Autowired
    private RestTemplate restTemplate;


}
