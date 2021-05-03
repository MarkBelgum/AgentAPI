package learn.field_agent.controllers;

import learn.field_agent.domain.AgentService;
import learn.field_agent.domain.Result;
import learn.field_agent.models.Alias;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080"})
@RequestMapping("/api/agent/alias")
public class AliasController {
    private final AgentService service;

    public AliasController(AgentService service) {
        this.service = service;
    }

    @GetMapping("/{agentId}")
    public List<Alias> findAliases(@PathVariable int agentId) {
        return service.findAliases(agentId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Alias alias) {
        Result<Alias> result = service.addAlias(alias);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Alias alias) {
        Result<Alias> result = service.updateAlias(alias);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{aliasId}")
    public ResponseEntity<Alias> deleteByKey(@PathVariable int aliasId) {
        if (service.deleteAlias(aliasId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
