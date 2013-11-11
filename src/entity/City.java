package entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CIDADES")
public class City implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "CID_CODIGO")
    private long id;
    @Column(name = "CID_MUNICIPIO")
    private String municipality;
    @Column(name = "CLI_CORRESP_CEP")
    private String stateAbbreviation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "city")
    private Set<Customer> customers;

    public City() {
    }

    public City(String municipality, String stateAbbreviation, Set<Customer> customers) {
        this.municipality = municipality;
        this.stateAbbreviation = stateAbbreviation;
        this.customers = customers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }
}
