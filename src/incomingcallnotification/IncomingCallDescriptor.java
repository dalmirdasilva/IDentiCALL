package incomingcallnotification;

import entity.Customer;
import java.util.List;

public class IncomingCallDescriptor {

    private String phone;
    private List<Customer> customers;

    public IncomingCallDescriptor() {
    }

    public IncomingCallDescriptor(String phone, List<Customer> customers) {
        this.phone = phone;
        this.customers = customers;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
