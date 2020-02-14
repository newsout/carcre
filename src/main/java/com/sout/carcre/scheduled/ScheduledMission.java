package com.sout.carcre.scheduled;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by lzw on 2020/2/14.
 */
@Configuration
/*
定时任务
 */
public class ScheduledMission {
    static int i=0;
    @Scheduled(fixedRate=5000) //fixedRate=毫秒
    private void configureTasks() {
        System.out.println("定时任务"+i++);
    }
}
