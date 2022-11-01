package com.example.musicwebapp.service;

import com.example.musicwebapp.entity.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class MusicService {

    @Autowired
    private RestTemplate template = new RestTemplate();

    LocalDateTime dateNow = LocalDateTime.now();
    String dateFormated = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
            .format(dateNow);

    String url = MessageFormat.format("http://newsapi.org/v2/everything?q=music&from={0}&sortBy=publishedAt&apiKey=804282cf237c4dab811d724a9a37c673", dateFormated);

    public Music findAllMusic(){
        var music =  template.getForObject(url,
                Music.class);

        return music;
    }
}
