package client;

import models.Friend;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

public class CallRelationshipService {
    final Logger log = LoggerFactory.getLogger(CallRelationshipService.class);
    final String uriGetAllRelationships = "http://localhost:2022/relationships";
    final String uriInsertRelationship = "http://localhost:2022/relationships/insert";
    final String uriVip = "http://localhost:2022/relationships/vip";

    public List<Friend> getAllRelationships() {
        log.info("Start");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Friend>> response = restTemplate.exchange(
                uriGetAllRelationships, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Friend>>() {
                });
        List<Friend> result = response.getBody();
        result.forEach(p -> log.info(p.toString()));
        log.info("Stop");
        return result;
    }

    public String insertRelationship(String user1, String user2) {
        log.info("Start inserting..");
        RestTemplate restTemplate = new RestTemplate();
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(uriInsertRelationship)
                .queryParam("user1", "{user1}")
                .queryParam("user2", "{user2}")
                .encode()
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        Map<String, String> params = new HashMap<>();
        params.put("user1", user1);
        params.put("user2", user2);
        ResponseEntity<String> response = restTemplate.exchange(
                urlTemplate, HttpMethod.POST, entity,
                new ParameterizedTypeReference<String>() {
                }, params);
        String result = response.getBody();
        log.info(result);
        log.info("Stop inserting..");
        return result;
    }

    public List<User> getVip(int k) {
        log.info("Start getting famous people ..");
        RestTemplate restTemplate = new RestTemplate();
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(uriVip)
                .queryParam("k", "{k}")
                .encode()
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        Map<String, Integer> params = new HashMap<>();
        params.put("k", k);
        ResponseEntity<List<User>> response = restTemplate.exchange(
                urlTemplate, HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<User>>() {
                }, params);
        List<User> result = response.getBody();
        result.forEach(user -> log.info(user.toString()));
        log.info("Stop getting famous people..");
        return result;
    }

    public static void main(String[] args) {
        CallRelationshipService callRelationshipService = new CallRelationshipService();
        callRelationshipService.getAllRelationships();
        try {
            callRelationshipService.insertRelationship("bogdan", "paul");
        } catch (HttpClientErrorException exceptionBadRequest) {
            System.err.println(exceptionBadRequest.getMessage());
        }
        callRelationshipService.getVip(3);
    }
}
