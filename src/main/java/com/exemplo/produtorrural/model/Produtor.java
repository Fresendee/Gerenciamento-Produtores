package com.exemplo.produtorrural.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Produtor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String cpf;

    private String telefone;
    private String localizacao;
    private String inscricaoProdutorRural;
    private double tamanhoPropriedade;
    private int numeroPessoasFamilia;

    @OneToMany(mappedBy = "produtor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atividade> atividades = new ArrayList<>();

    @OneToMany(mappedBy = "produtor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visita> visitas = new ArrayList<>();

    public Produtor() {}

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public String getInscricaoProdutorRural() { return inscricaoProdutorRural; }
    public void setInscricaoProdutorRural(String inscricaoProdutorRural) { this.inscricaoProdutorRural = inscricaoProdutorRural; }
    public double getTamanhoPropriedade() { return tamanhoPropriedade; }
    public void setTamanhoPropriedade(double tamanhoPropriedade) { this.tamanhoPropriedade = tamanhoPropriedade; }
    public int getNumeroPessoasFamilia() { return numeroPessoasFamilia; }
    public void setNumeroPessoasFamilia(int numeroPessoasFamilia) { this.numeroPessoasFamilia = numeroPessoasFamilia; }
    public List<Atividade> getAtividades() { return atividades; }
    public void setAtividades(List<Atividade> atividades) { this.atividades = atividades; }
    public List<Visita> getVisitas() { return visitas; }
    public void setVisitas(List<Visita> visitas) { this.visitas = visitas; }

    public void addAtividade(Atividade atividade){
        atividades.add(atividade);
        atividade.setProdutor(this);
    }

    public void removeAtividade(Atividade atividade){
        atividades.remove(atividade);
        atividade.setProdutor(null);
    }
}
