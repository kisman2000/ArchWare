package fun.archware.impl.managers;

import fun.archware.base.module.Module;
import fun.archware.base.setting.Setting;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SettingsManager {
    private ArrayList<Setting> settings = new ArrayList<>();


    public ArrayList<Setting> getSettings() { return settings; }

    public ArrayList<Setting> getSettingsByMod(Module m){
        return settings.stream().filter(s -> s.getParent().equals(m)).collect(Collectors.toCollection(ArrayList::new));
    }

    public Setting getSettingById(String id){
        for(Setting s : settings) if (s.getId().equalsIgnoreCase(id)) return s;

        return null;
    }

    public Setting getSettingByName(String name){
        for(Setting s : settings) if (s.getName().equalsIgnoreCase(name)) return s;

        return null;
    }

    public Setting getSetting(Module mod, String name){
        for(Setting s : settings) if (s.getName().replace(" ", "").equalsIgnoreCase(name) && s.getParent() == mod) return s;

        return null;
    }

    public void addSetting(Setting s) {
         settings.add(s);
    }
}
