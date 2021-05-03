package learn.field_agent.data;

import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SecurityClearanceJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 3;

    @Autowired
    SecurityClearanceJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        SecurityClearance secret = new SecurityClearance(1, "Secret");
        SecurityClearance topSecret = new SecurityClearance(2, "Top Secret");

        List<SecurityClearance> actualList = repository.findAll();
        assertNotNull(actualList);

        SecurityClearance actual = actualList.get(0);
        assertEquals(secret, actual);

        actual = actualList.get(1);
        assertEquals(topSecret, actual);

        actual = actualList.get(2);
        assertNull(actual);
    }

    @Test
    void shouldFindById() {
        SecurityClearance secret = new SecurityClearance(1, "Secret");
        SecurityClearance topSecret = new SecurityClearance(2, "Top Secret");

        SecurityClearance actual = repository.findById(1);
        assertEquals(secret, actual);

        actual = repository.findById(2);
        assertEquals(topSecret, actual);

        actual = repository.findById(3);
        assertNull(actual);
    }

    @Test
    void shouldAdd() {
        SecurityClearance securityClearance = makeSecurityClearance();
        SecurityClearance actual = repository.add(securityClearance);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getSecurityClearanceId());

    }
    @Test
    void shouldNotAdd() {
        SecurityClearance securityClearance = makeSecurityClearance();
        securityClearance.setName(null);
        SecurityClearance actual = repository.add(securityClearance);
        assertNull(actual);
    }

    @Test
    void shouldUpdate() {
        SecurityClearance securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(1);
        assertTrue(repository.update(securityClearance));
        securityClearance.setSecurityClearanceId(13);
        assertFalse(repository.update(securityClearance));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(2));
        assertFalse(repository.deleteById(2));
    }

    private SecurityClearance makeSecurityClearance() {
        SecurityClearance securityClearance = new SecurityClearance();
        securityClearance.setName("Super Top Secret");
        return securityClearance;
    }
}