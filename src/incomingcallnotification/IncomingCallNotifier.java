package incomingcallnotification;

import entity.Customer;
import java.util.ArrayList;
import java.util.List;

public class IncomingCallNotifier {

    private final List<IncomingCallListener> incomingCallListeners;
    
    public IncomingCallNotifier() {
        incomingCallListeners = new ArrayList<>();    
    }
    
    public void addIncomingCallListener(IncomingCallListener listener) {
        incomingCallListeners.add(listener);
    }

    protected void notifyIncomingCallListeners(IncomingCallDescriptor descriptor) {
        for (IncomingCallListener listener : incomingCallListeners) {
            listener.incomingCall(descriptor);
        }
    }
}
