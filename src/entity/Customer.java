package entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "CLIENTES")
public class Customer implements Serializable {

    final public static String CELL_PHONE_COLUMN = "cellPhone";
    final public static String PRIMARY_BUSINESS_PHONE_COLUMN = "primaryBusinessPhone";
    final public static String SECONDARY_BUSINESS_PHONE_COLUMN = "secondaryBusinessPhone";
    final public static String RESIDENTIAL_PHONE_COLUMN = "residentialPhone";
    final public static String CPF_CNPJ_COLUMN = "cpfCnpj";
    final public static String NAME_COLUMN = "name";

    @Id
    @GeneratedValue
    @Column(name = "CLI_CODIGO")
    private long id;
    @Column(name = "CLI_NOME")
    private String name;
    @Column(name = "CLI_CPFCNPJ")
    private String cpfCnpj;
    @Column(name = "CLI_FONERES")
    private String residentialPhone;
    @Column(name = "CLI_FAX")
    private String fax;
    @Column(name = "CLI_CELULAR")
    private String cellPhone;
    @Column(name = "CLI_EMAIL")
    private String email;
    @Column(name = "CLI_RAZAOSOCIAL")
    private String corporateName;
    @Column(name = "CLI_ENDERECO")
    private String address;
    @Column(name = "CLI_CORRESP_CEP")
    private String district;
    @JoinColumn(name = "CLI_CID_CODIGO")
    @ManyToOne(cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    private City city;
    @Column(name = "CLI_FONECOM1")
    private String primaryBusinessPhone;
    @Column(name = "CLI_FONECOM2")
    private String secondaryBusinessPhone;
    @Column(name = "CLI_NASCTO")
    private String birthDate;
    @Column(name = "CLI_PESSOA")
    private String legalPerson;
    @Column(name = "CLI_DATACADASTRO")
    private String recortDate;
    @Column(name = "CLI_CORREIO")
    private Character post;
    @Column(name = "CLI_PROBLEMAS")
    private Character problems;
    @Column(name = "CLI_ATIVO")
    private boolean active;
    @Column(name = "CLI_OBSERVACAO", length = 2048)
    private String observation;

    public Customer() {
    }

    public Customer(String name, String cpfCnpj, String residentialPhone, String fax, String cellPhone, String email, String corporateName, String address, String district, City city, String primaryBusinessPhone, String secondaryBusinessPhone, String birthDate, String legalPerson, String recortDate, Character post, Character problems, boolean active, String observation) {
        this.name = name;
        this.cpfCnpj = cpfCnpj;
        this.residentialPhone = residentialPhone;
        this.fax = fax;
        this.cellPhone = cellPhone;
        this.email = email;
        this.corporateName = corporateName;
        this.address = address;
        this.district = district;
        this.city = city;
        this.primaryBusinessPhone = primaryBusinessPhone;
        this.secondaryBusinessPhone = secondaryBusinessPhone;
        this.birthDate = birthDate;
        this.legalPerson = legalPerson;
        this.recortDate = recortDate;
        this.post = post;
        this.problems = problems;
        this.active = active;
        this.observation = observation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getResidentialPhone() {
        return residentialPhone;
    }

    public void setResidentialPhone(String residentialPhone) {
        this.residentialPhone = residentialPhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getPrimaryBusinessPhone() {
        return primaryBusinessPhone;
    }

    public void setPrimaryBusinessPhone(String primaryBusinessPhone) {
        this.primaryBusinessPhone = primaryBusinessPhone;
    }

    public String getSecondaryBusinessPhone() {
        return secondaryBusinessPhone;
    }

    public void setSecondaryBusinessPhone(String secondaryBusinessPhone) {
        this.secondaryBusinessPhone = secondaryBusinessPhone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getRecortDate() {
        return recortDate;
    }

    public void setRecortDate(String recortDate) {
        this.recortDate = recortDate;
    }

    public Character getPost() {
        return post;
    }

    public void setPost(Character post) {
        this.post = post;
    }

    public Character getProblems() {
        return problems;
    }

    public void setProblems(Character problems) {
        this.problems = problems;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
