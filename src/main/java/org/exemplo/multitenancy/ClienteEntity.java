package org.exemplo.multitenancy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("cliente")
public class ClienteEntity {
    @Id
    private UUID id;
    @JsonIgnore
    @Version
    private Integer versao;
    private String nome;

    public ClienteEntity() {
    }

    public ClienteEntity(String nome) {
        this.id = UUID.randomUUID();
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getVersao() {
        return versao;
    }

    public void setVersao(Integer versao) {
        this.versao = versao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
