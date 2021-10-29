package fun.archware.base.setting;


import fun.archware.base.module.Module;

public class NumericValue extends Setting {
    public NumericValue(String name, String id, Module parent, float value, float minValue, float maxValue) {
        super(name, id, parent, value, minValue, maxValue);
    }
}
