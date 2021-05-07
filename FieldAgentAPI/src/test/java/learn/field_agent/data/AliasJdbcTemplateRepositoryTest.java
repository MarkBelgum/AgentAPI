package learn.field_agent.data;

import learn.field_agent.models.Alias;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AliasJdbcTemplateRepositoryTest {

    @Autowired
    AliasJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldAdd() {
        Alias alias = makeAlias();
        assertEquals(repository.add(alias), alias);
    }

    @Test
    void shouldUpdate() {
        Alias alias = makeAlias();
        assertEquals(repository.add(alias), alias);

        alias.setPersona("008"); // avoid duplicates
        assertTrue(repository.update(alias));
    }

    @Test
    void shouldDelete() {
        Alias alias = makeAlias();
        assertEquals(repository.add(alias), alias);

        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(1));
    }

    Alias makeAlias() {
        Alias alias = new Alias();
        alias.setAliasId(1);
        alias.setName("Super Trooper");
        alias.setPersona("007");
        alias.setAgentId(3);

        return alias;
    }
}
