package identicall;

import entity.Customer;
import java.util.List;

public interface CustomerSearchListener {

    public int onCustomerSearch(List<Customer> examples, String phone, boolean appendIncomingCall);
}
