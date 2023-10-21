package org.exemplo.multitenancy;

import org.exemplo.multitenancy.tenant.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);

    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    private ResponseEntity<ClienteResponse> clienteEncontrado(ClienteEntity entity) {
        var cliente = new ClienteResponse(entity.getId(), TenantContext.getTenantId(), entity.getNome());
        LOGGER.debug("Cliente encontrado: {}", cliente);
        return ResponseEntity.ok(cliente);
    }

    private ResponseEntity<ClienteResponse> clienteNaoEncontrado(UUID id) {
        LOGGER.debug("Cliente não encontrado: {}", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> criar(@RequestBody ClienteRequest cliente, UriComponentsBuilder builder) {
        var entity = repository.save(new ClienteEntity(cliente.nome()));
        LOGGER.debug("Cliente salvo: {}", entity);
        var uri = builder.pathSegment("clientes", "{id}").buildAndExpand(entity.getId().toString()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscar(@PathVariable UUID id) {
        return repository.findById(id)
                .map(this::clienteEncontrado)
                .orElseGet(() -> clienteNaoEncontrado(id));
    }

    record ClienteRequest(String nome) {
    }

    record ClienteResponse(UUID id, String tenant, String nome) {
    }
}
