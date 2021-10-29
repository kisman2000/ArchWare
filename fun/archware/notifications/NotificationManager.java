package fun.archware.notifications;


import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private static List<Notification> queue = new ArrayList<>();

    public static void addNotification(Notification notification){
        queue.add(notification);
    }

    public static void removeNotification(Notification notification){
        queue.remove(notification);
    }

    public static List<Notification> getQueue(){
        return queue;
    }
}
