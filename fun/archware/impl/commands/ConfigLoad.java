package fun.archware.impl.commands;

import fun.archware.impl.managers.FileManager;
import fun.archware.base.command.Command;
import fun.archware.notifications.Notification;
import fun.archware.notifications.NotificationManager;
import fun.archware.notifications.NotificationType;

import java.io.File;
import java.io.IOException;

public class ConfigLoad extends Command {
    public ConfigLoad() {
        super("ConfigLoad", new String[]{"cfg", "load"});
    }

    @Override
    public void onCommand(String[] args) {
        if(new File(FileManager.HOME + "\\configs\\" + args[1]).exists()){
            NotificationManager.addNotification(new Notification("Configuration manager", "Loading configuration", NotificationType.WARNING));
            try {
                FileManager.initConfig(args[1]);
            } catch (IOException e) {
                NotificationManager.addNotification(new Notification("Configuraton manager", "Something get wrong while loading config", NotificationType.ERROR));
            }
        }else{
            NotificationManager.addNotification(new Notification("Configuraton manager", "Configuration file not found", NotificationType.ERROR));
        }
    }
}
