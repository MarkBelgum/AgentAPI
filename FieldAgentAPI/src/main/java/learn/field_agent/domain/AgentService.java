package learn.field_agent.domain;

import learn.field_agent.data.AgencyAgentRepository;
import learn.field_agent.data.AgentRepository;
import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AgentService {

    private final AgentRepository repository;
    private final AliasRepository aliasRepository;

    public AgentService(AgentRepository repository, AliasRepository aliasRepository) {
        this.repository = repository;
        this.aliasRepository = aliasRepository;
    }

    public List<Agent> findAll() {
        return repository.findAll();
    }

    public Agent findById(int agentId) {
        return repository.findById(agentId);
    }

    public List<Alias> findAliases(int agentId) {
        return aliasRepository.findByAgent(agentId);
    }

    public Result<Agent> add(Agent agent) {
        Result<Agent> result = validate(agent);
        if (!result.isSuccess()) {
            return result;
        }

        if (agent.getAgentId() != 0) {
            result.addMessage("agentId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        agent = repository.add(agent);
        result.setPayload(agent);
        return result;
    }

    public Result<Agent> update(Agent agent) {
        Result<Agent> result = validate(agent);
        if (!result.isSuccess()) {
            return result;
        }

        if (agent.getAgentId() <= 0) {
            result.addMessage("agentId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(agent)) {
            String msg = String.format("agentId: %s, not found", agent.getAgentId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int agentId) {
        return repository.deleteById(agentId);
    }

    public Result<Alias> addAlias(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() != 0) {
            result.addMessage("aliasId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        alias = aliasRepository.add(alias);
        return result;
    }

    public Result<Alias> updateAlias(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() <= 0) {
            result.addMessage("aliasId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!aliasRepository.update(alias)) {
            String msg = String.format("aliasId: %s, not found", alias.getAliasId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteAlias(int aliasId) {
        return aliasRepository.deleteById(aliasId);
    }

    private Result<Agent> validate(Agent agent) {
        Result<Agent> result = new Result<>();
        if (agent == null) {
            result.addMessage("agent cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(agent.getFirstName())) {
            result.addMessage("firstName is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(agent.getLastName())) {
            result.addMessage("lastName is required", ResultType.INVALID);
        }

        if (agent.getDob() != null && agent.getDob().isAfter(LocalDate.now().minusYears(12))) {
            result.addMessage("agents younger than 12 are not allowed", ResultType.INVALID);
        }

        if (agent.getHeightInInches() < 36 || agent.getHeightInInches() > 96) {
            result.addMessage("height must be between 36 and 96 inches", ResultType.INVALID);
        }

        return result;
    }

    private Result<Alias> validate(Alias alias) {
        Result<Alias> result = new Result<>();
        if (alias == null) {
            result.addMessage("alias cannot be null", ResultType.INVALID);
            return result;
        }

        Agent agent = findById(alias.getAgentId());
        if (agent == null) {
            result.addMessage("Agent not found by that id", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(alias.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }

        //ensure that duplicate name has persona
        for (Alias a : agent.getAliases()) {
            if ((a.getName().equals(alias.getName())) && (alias.getPersona().isBlank())) {
                result.addMessage("Alias name already exists for this agent; persona is required.", ResultType.INVALID);
            }
        }

        return result;
    }
}
