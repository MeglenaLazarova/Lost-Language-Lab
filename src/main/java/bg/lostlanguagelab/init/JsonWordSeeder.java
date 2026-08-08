package bg.lostlanguagelab.init;

import bg.lostlanguagelab.archaicWord.service.ArchaicWordService;
import bg.lostlanguagelab.model.dto.ArchaicWordDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class JsonWordSeeder implements CommandLineRunner {

    private final ArchaicWordService archaicWordService;

    public JsonWordSeeder(ArchaicWordService archaicWordService) {
        this.archaicWordService = archaicWordService;
    }

    @Override
    public void run(String... args) throws Exception {

        if (archaicWordService.getAll().isEmpty()) {

            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getResourceAsStream("/words.json");

            List<ArchaicWordDto> words = mapper.readValue(
                    is, new TypeReference<List<ArchaicWordDto>>() {}
            );

            words.forEach(archaicWordService::create);
        }
    }
}
