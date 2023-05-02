package ru.tinkoff.scrapper.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableScheduling
@AllArgsConstructor
public class LinkUpdaterScheduler {

    private final LinkUpdateService linkUpdateService;

    @Scheduled(fixedDelayString = "#{@interval}")
    public  void update(){
        System.out.println("Start link update");
        linkUpdateService.update();
    }



}
