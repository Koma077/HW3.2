package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PortService {

    private RestTemplate restTemplate;

    public PortService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Value("${server.port}")
    private int port;

    public int getPort(){
        return port;
    }
}
