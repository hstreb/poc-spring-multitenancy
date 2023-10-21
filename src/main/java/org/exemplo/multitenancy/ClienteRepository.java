package org.exemplo.multitenancy;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClienteRepository extends CrudRepository<ClienteEntity, UUID> {
}
