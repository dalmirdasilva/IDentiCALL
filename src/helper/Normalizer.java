package helper;

import dao.CityDAO;
import dao.CustomerDAO;
import entity.Customer;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Normalizer {
    
    private final static String NORMALIZE_LOCK_FILE_PROPERTY = "normalizelockfile";

    public static void normalize(CustomerDAO customerDAO, CityDAO cityDAO) throws Exception {
        if (!isNormalized()) {
            normalizeCustomers(customerDAO);
            createNormalizeLockFile();
        }
    }

    public static void normalizeCustomers(CustomerDAO customerDAO) {
        List<Customer> customers = customerDAO.findAll();
        for (Customer customer : customers) {
            if (customer.getCellPhone() != null) {
                customer.setCellPhone(Formater.removeFormatation(customer.getCellPhone()));
            }
            if (customer.getResidentialPhone() != null) {
                customer.setResidentialPhone(Formater.removeFormatation(customer.getResidentialPhone()));
            }
            if (customer.getPrimaryBusinessPhone() != null) {
                customer.setPrimaryBusinessPhone(Formater.removeFormatation(customer.getPrimaryBusinessPhone()));
            }
            if (customer.getSecondaryBusinessPhone() != null) {
                customer.setSecondaryBusinessPhone(Formater.removeFormatation(customer.getSecondaryBusinessPhone()));
            }
            if (customer.getFax()!= null) {
                customer.setFax(Formater.removeFormatation(customer.getFax()));
            }
            if (customer.getCpfCnpj()!= null) {
                customer.setCpfCnpj(Formater.removeFormatation(customer.getCpfCnpj()));
            }
            System.out.println(customerDAO.saveEntity(customer));
        }
    }

    private static boolean isNormalized() throws IOException {
        File file = new File(AppProperties.getProperty(NORMALIZE_LOCK_FILE_PROPERTY));
        return file.exists();
    }

    private static void createNormalizeLockFile() throws IOException {
        File file = new File(AppProperties.getProperty(NORMALIZE_LOCK_FILE_PROPERTY));
        file.createNewFile();
    }
    
    
}
