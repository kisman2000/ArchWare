package fun.archware.base.setting;

import fun.archware.base.module.Module;

public class SettingTextField extends Setting {
    private String text;
    private String defaultText;
    private Module parent;


    public SettingTextField(String name, String id, Module parent, final String text, final String defaultText) {
        super(name, id, parent, text, defaultText);
        this.text = text;
        this.defaultText = defaultText;
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDefaultText() {
        return defaultText;
    }

    @Override
    public Module getParent() {
        return parent;
    }
}
