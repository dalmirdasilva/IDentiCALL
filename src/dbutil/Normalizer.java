package dbutil;

import dao.CityDAO;
import dao.CustomerDAO;
import entity.Customer;
import java.util.List;

public class Normalizer {

    public static void normalize(CustomerDAO customerDAO, CityDAO cityDAO) throws Exception {
        normalizeCustomers(customerDAO);
    }

    public static void normalizeCustomers(CustomerDAO customerDAO) {
        List<Customer> customers = customerDAO.findAll();
        for (Customer customer : customers) {
            String regex = "[()-]";
            if (customer.getCellPhone() != null) {
                customer.setCellPhone(customer.getCellPhone().replaceAll(regex, ""));
            }
            if (customer.getResidentialPhone() != null) {
                customer.setResidentialPhone(customer.getResidentialPhone().replaceAll(regex, ""));
            }
            if (customer.getPrimaryBusinessPhone() != null) {
                customer.setPrimaryBusinessPhone(customer.getPrimaryBusinessPhone().replaceAll(regex, ""));
            }
            if (customer.getSecondaryBusinessPhone() != null) {
                customer.setSecondaryBusinessPhone(customer.getSecondaryBusinessPhone().replaceAll(regex, ""));
            }
            System.out.println(customerDAO.saveEntity(customer));
        }
    }
}
