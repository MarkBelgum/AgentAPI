package learn.field_agent.models;

import java.util.Objects;

public class Alias {

    private int aliasId;
    private String name;
    private String persona;
    private int agentId;


    public int getAliasId() {
        return aliasId;
    }

    public void setAliasId(int aliasId) {
        this.aliasId = aliasId;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alias alias = (Alias) o;
        return aliasId == alias.aliasId && agentId == alias.agentId && name.equals(alias.name) && Objects.equals(persona, alias.persona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aliasId, name, persona, agentId);
    }
}
