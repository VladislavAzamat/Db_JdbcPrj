
import aston_org.example.dto.CourceDto;
import aston_org.example.entity.Cource;
import aston_org.example.repository.CourceRepository;
import aston_org.example.service.CourceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourceServiceTest {

    private CourceService courceService;
    private CourceRepository courceRepository;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        courceRepository = new CourceRepository(connection);
        courceService = new CourceService(courceRepository);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void getAllCources() {
        Cource cource1 = new Cource("Java", 4.0f, 1000);
        Cource cource2 = new Cource("Python", 3.0f, 800);
        courceRepository.addCource(cource1);
        courceRepository.addCource(cource2);

        List<CourceDto> cources = courceService.getAllCources();

        assertEquals(2, cources.size());
        assertEquals("Java", cources.get(0).getTitle());
        assertEquals(4.0f, cources.get(0).getDuration());
        assertEquals(1000, cources.get(0).getPrice());
        assertEquals("Python", cources.get(1).getTitle());
        assertEquals(3.0f, cources.get(1).getDuration());
        assertEquals(800, cources.get(1).getPrice());
    }

    @Test
    void getCourceById() {
        Cource cource = new Cource("Java", 4.0f, 1000);
        courceRepository.addCource(cource);

        CourceDto courceDto = courceService.getCourceById(1);

        assertEquals("Java", courceDto.getTitle());
        assertEquals(4.0f, courceDto.getDuration());
        assertEquals(1000, courceDto.getPrice());
    }

    @Test
    void addCource() {
        CourceDto courceDto = new CourceDto("Java", 4.0f, 1000);

        long courceId = courceService.addCource(courceDto);

        CourceDto addedCource = courceService.getCourceById((int) courceId);
        assertEquals("Java", addedCource.getTitle());
        assertEquals(4.0f, addedCource.getDuration());
        assertEquals(1000, addedCource.getPrice());
    }

    @Test
    void updateCource() {
        Cource cource = new Cource("Java", 4.0f, 1000);
        courceRepository.addCource(cource);

        CourceDto updatedCourceDto = new CourceDto("Python", 3.0f, 800);
        courceService.updateCource(1, updatedCourceDto);

        CourceDto updatedCource = courceService.getCourceById(1);

        assertEquals("Python", updatedCource.getTitle());
        assertEquals(3.0f, updatedCource.getDuration());
        assertEquals(800, updatedCource.getPrice());
    }

    @Test
    void deleteCource() {
        Cource cource = new Cource("Java", 4.0f, 1000);
        courceRepository.addCource(cource);

        courceService.deleteCource(1);

        List<CourceDto> cources = courceService.getAllCources();
        assertEquals(0, cources.size());
    }
}

