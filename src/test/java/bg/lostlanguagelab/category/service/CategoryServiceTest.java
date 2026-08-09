package bg.lostlanguagelab.category.service;

import bg.lostlanguagelab.category.entity.Category;
import bg.lostlanguagelab.category.enums.CategoryType;
import bg.lostlanguagelab.category.repo.CategoryRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private CategoryServiceImpl service;

    @Test
    void testGetAllCategories() {
        Category c1 = new Category();
        Category c2 = new Category();

        when(categoryRepo.findAll()).thenReturn(List.of(c1, c2));

        List<Category> result = service.getAllCategories();

        assertEquals(2, result.size());
        verify(categoryRepo, times(1)).findAll();
    }

    @Test
    void testGetByIdSuccess() {
        UUID id = UUID.randomUUID();
        Category c = new Category();
        c.setId(id);

        when(categoryRepo.findById(id)).thenReturn(Optional.of(c));

        Category result = service.getById(id);

        assertEquals(id, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(categoryRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getById(id));
    }

    @Test
    void testCreateCategory() {
        Category c = new Category();
        c.setType(CategoryType.DIALECT);
        c.setCategoryName("Test");
        c.setDescription("Desc");

        when(categoryRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Category result = service.create(c);

        assertEquals("Test", result.getCategoryName());
        verify(categoryRepo, times(1)).save(any());
    }

    @Test
    void testUpdateCategorySuccess() {
        UUID id = UUID.randomUUID();

        Category existing = new Category();
        existing.setId(id);
        existing.setType(CategoryType.CHURCH_SLAVIC);
        existing.setCategoryName("OldName");
        existing.setDescription("OldDesc");

        Category updated = new Category();
        updated.setType(CategoryType.MEDIEVAL);
        updated.setCategoryName("NewName");
        updated.setDescription("NewDesc");

        when(categoryRepo.findById(id)).thenReturn(Optional.of(existing));
        when(categoryRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Category result = service.update(id, updated);

        assertEquals("NewName", result.getCategoryName());
        assertEquals("NewDesc", result.getDescription());
        assertEquals(CategoryType.MEDIEVAL, result.getType());
    }

    @Test
    void testUpdateCategoryEmptyDescription() {
        UUID id = UUID.randomUUID();

        Category updated = new Category();
        updated.setDescription(""); // invalid

        assertThrows(IllegalArgumentException.class, () -> service.update(id, updated));
    }

    @Test
    void testDeleteCategorySuccess() {
        UUID id = UUID.randomUUID();

        when(categoryRepo.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(categoryRepo, times(1)).deleteById(id);
    }

    @Test
    void testDeleteCategoryNotFound() {
        UUID id = UUID.randomUUID();

        when(categoryRepo.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.delete(id));
    }

    @Test
    void testGetByTypeSuccess() {
        Category c = new Category();
        c.setType(CategoryType.RENAISSANCE);

        when(categoryRepo.findByType(CategoryType.RENAISSANCE)).thenReturn(Optional.of(c));

        Category result = service.getByType(CategoryType.RENAISSANCE);

        assertEquals(CategoryType.RENAISSANCE, result.getType());
    }

    @Test
    void testGetByTypeNotFound() {
        when(categoryRepo.findByType(CategoryType.FOLKLORE)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getByType(CategoryType.FOLKLORE));
    }
}
