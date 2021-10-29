package fun.archware.base.setting;

import fun.archware.base.module.Module;

public abstract class SettingButton extends Setting{
    public SettingButton(String name, String id, Module parent) {
        super(name, id, parent);
    }

    public abstract void onClicked();
}
