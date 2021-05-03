package learn.field_agent.data;

import learn.field_agent.models.Agency;
import learn.field_agent.models.AgencyAgent;
import learn.field_agent.models.Alias;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AliasRepository {
    List<Alias> findByAgent(int agentId);

    Alias add(Alias alias);

    boolean update(Alias alias);

    boolean deleteById(int aliasId);
}
