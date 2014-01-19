package helper;

import entity.Customer;
import incomingcallnotification.IncomingCallDescriptor;
import incomingcallnotification.IncomingCallListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ReportGenerator implements IncomingCallListener {

    private final static String REPORT_OUTPUT_FILE_PROPERTY = "reportoutputfile";
    private final File outputFile;

    public ReportGenerator() throws IOException {
        outputFile = new File(AppProperties.getProperty(REPORT_OUTPUT_FILE_PROPERTY));
        outputFile.createNewFile();
    }

    @Override
    public void incomingCall(IncomingCallDescriptor descriptor) {
        appendToFile(makeReportLine(descriptor));
    }

    private void appendToFile(String data) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)))) {
            out.println(data);
        } catch (IOException e) {
        }
    }

    private String makeReportLine(IncomingCallDescriptor descriptor) {
        Calendar now = GregorianCalendar.getInstance();
        List<Customer> customers = descriptor.getCustomers();
        Customer customer = (customers != null && customers.size() > 0) ? customers.get(0) : null;
        String customerName = (customer != null) ? customer.getName() : "<Cliente não encontrado>";
        String recentCallString = Formater.formatRecentCall(descriptor.getPhone(), now);
        return customerName + ", " + recentCallString.replace('@', ',');
    }
}
