package model;

import event.SaveOrUpdateListener;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente extends AbstractModel implements SaveOrUpdateListener, Serializable {

    @Id
    @GeneratedValue
    private long id;
    String nome;
    String senha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private Set<Venda> vendas;

    public Cliente() {
    }

    public Cliente(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(Set<Venda> vendas) {
        this.vendas = vendas;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onUpdate() {
    }
}