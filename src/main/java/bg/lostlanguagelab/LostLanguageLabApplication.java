package bg.lostlanguagelab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LostLanguageLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(LostLanguageLabApplication.class, args);
    }

}
