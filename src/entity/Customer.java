package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String cpf;
    @Column(name = "residential_phone")
    private String residentialPhone;
    private String fax;
    @Column(name = "cell_phone")
    private String cellPhone;
    private String email;
    @Column(name = "corporate_name")
    private String corporateName;
    private String address;
    private String district;
    private String city;
    @Column(name = "business_phone")
    private String businessPhone;
    @Column(name = "birth_date")
    private String birthDate;
    @Column(name = "legal_person")
    private String legalPerson;
    @Column(name = "recort_date")
    private String recortDate;
    private Boolean post;
    private Boolean problems;
    @Column(name = "observation", length = 2048)
    private String observation;

    public Customer() {
    }

    public Customer(long id, String name, String cpf, String residentialPhone, String fax, String cellPhone, String email, String corporateName, String address, String city, String businessPhone, String birthDate, String legalPerson, String recortDate, Boolean post, Boolean problems, String observation) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.residentialPhone = residentialPhone;
        this.fax = fax;
        this.cellPhone = cellPhone;
        this.email = email;
        this.corporateName = corporateName;
        this.address = address;
        this.city = city;
        this.businessPhone = businessPhone;
        this.birthDate = birthDate;
        this.legalPerson = legalPerson;
        this.recortDate = recortDate;
        this.post = post;
        this.problems = problems;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
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

    public Boolean getPost() {
        return post;
    }

    public void setPost(Boolean post) {
        this.post = post;
    }

    public Boolean getProblems() {
        return problems;
    }

    public void setProblems(Boolean problems) {
        this.problems = problems;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    @Override
    public String toString() {
        return "{name: " + name + "}";
    }
}
