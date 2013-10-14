package model;

import event.SaveOrUpdateListener;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "venda")
public class Venda extends AbstractModel implements SaveOrUpdateListener, Serializable {

    @Id
    @GeneratedValue
    private long id;
    @Temporal(TemporalType.DATE)
    private Date quando;
    @OneToOne(cascade = CascadeType.ALL)
    private Cliente cliente;
    @OneToOne(cascade = CascadeType.ALL)
    private Produto produto;

    public Venda() {
    }

    public Venda(Date quando, Cliente cliente, Produto produto) {
        this.quando = quando;
        this.cliente = cliente;
        this.produto = produto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getQuando() {
        return quando;
    }

    public void setQuando(Date quando) {
        this.quando = quando;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onUpdate() {
    }
}