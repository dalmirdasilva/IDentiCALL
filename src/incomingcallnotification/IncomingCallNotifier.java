package incomingcallnotification;

import entity.Customer;
import java.util.ArrayList;
import java.util.List;

public class IncomingCallNotifier {

    private List<IncomingCallListener> incomingCallListeners;
    
    public void addIncomingCallListener(IncomingCallListener listener) {
        checkList();
        incomingCallListeners.add(listener);
    }

    protected void notifyIncomingCallListeners(IncomingCallDescriptor descriptor) {
        checkList();
        for (IncomingCallListener listener : incomingCallListeners) {
            listener.incomingCall(descriptor);
        }
    }

    private void checkList() {
        if (incomingCallListeners == null) {
            incomingCallListeners = new ArrayList<>();
        }
    }
}
