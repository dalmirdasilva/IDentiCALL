package event;

import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;

public class SaveOrUpdateDateListener extends DefaultSaveOrUpdateEventListener {

    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event) {
        if (event.getObject() instanceof SaveOrUpdateListener) {
            SaveOrUpdateListener record = (SaveOrUpdateListener) event.getObject();
            record.onCreate();
            record.onUpdate();
        }
        super.onSaveOrUpdate(event);
    }
}
