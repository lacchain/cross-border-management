package us.lacchain.crossborder.management.config;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.lacchain.crossborder.management.repository.MovementRepository;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import us.lacchain.crossborder.management.service.IEventService;

@Component
@EnableAsync
public class ScheduledConfig {

    @Autowired
    private IEventService eventService;

    Logger logger = LoggerFactory.getLogger(ScheduledConfig.class);

    @Async
    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        logger.info("Fixed rate task async - " + System.currentTimeMillis() / 5000);
        eventService.setFeeRate();
    }

}