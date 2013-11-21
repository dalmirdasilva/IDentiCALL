package identicall;

import entity.Customer;
import java.util.List;
import java.util.Map;

public interface CustomerSearcher {

    public List<Customer> searchCustomer(Map<String, String> propertiesMap, boolean apeendRecentCall);
}
