package com.exemplo.produtorrural.model;

import jakarta.persistence.*;

@Entity
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // Cultivo ou Animal
    private String descricao; // ex: soja, milho, bovino

    @ManyToOne
    @JoinColumn(name = "produtor_id")
    private Produtor produtor;

    public Atividade() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Produtor getProdutor() { return produtor; }
    public void setProdutor(Produtor produtor) { this.produtor = produtor; }
}
