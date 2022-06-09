package net.alterapp.altercar.config;

import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.service.UserAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class CronConfig {

    @Autowired
    private UserAttemptService userAttemptService;


    @Autowired
    @Scheduled(cron = " 0 */1 * * * *")
    public void scheduleTaskUsingCronExpressionForNewUpdating() throws IOException, NoSuchAlgorithmException {
        userAttemptService.checkAllUsersTime();
    }
}
