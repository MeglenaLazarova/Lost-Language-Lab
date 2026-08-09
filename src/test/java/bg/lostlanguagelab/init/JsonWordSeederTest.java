package bg.lostlanguagelab.init;

import bg.lostlanguagelab.archaicWord.entity.ArchaicWord;
import bg.lostlanguagelab.archaicWord.service.ArchaicWordService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class JsonWordSeederTest {

    @Test
    void testRunWhenDatabaseIsEmpty() throws Exception {
        ArchaicWordService archaicWordService = mock(ArchaicWordService.class);

        when(archaicWordService.getAll()).thenReturn(List.of());

        JsonWordSeeder seeder = new JsonWordSeeder(archaicWordService);

        seeder.run();

        verify(archaicWordService, times(2)).create(any());
    }

    @Test
    void testRunWhenDatabaseIsNotEmpty() throws Exception {
        ArchaicWordService archaicWordService = mock(ArchaicWordService.class);

        ArchaicWord word = new ArchaicWord();
        when(archaicWordService.getAll()).thenReturn(List.of(word));


        JsonWordSeeder seeder = new JsonWordSeeder(archaicWordService);

        seeder.run();

        verify(archaicWordService, never()).create(any());
    }
}

