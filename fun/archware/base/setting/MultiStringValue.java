package fun.archware.base.setting;

import fun.archware.base.module.Module;

public class MultiStringValue extends Setting {
    public MultiStringValue(String name, String id, Module parent, String... values) {
        super(name, id, parent, values);
    }
}
