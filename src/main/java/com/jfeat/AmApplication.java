package com.jfeat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.jfeat.crud.plus.META;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class AmApplication {
    protected final static Logger logger = LoggerFactory.getLogger(AmApplication.class);

    public static void main(String[] args) {

        META.enabledTag(true);
        SpringApplication.run(AmApplication.class, args);
        logger.info("Application run success!");
    }

}