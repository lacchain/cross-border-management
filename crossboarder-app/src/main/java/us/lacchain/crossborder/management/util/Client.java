package us.lacchain.crossborder.management.util;

import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Client{
    Logger logger = LoggerFactory.getLogger(Client.class);

    public Client(){

    }

    public HttpEntity<String> getEntity(JSONObject body){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<String>(body.toString(), headers);
        return request;    
    }

}