package fun.archware.base.setting;


import fun.archware.ArchWare;
import fun.archware.base.module.Module;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Setting {
    private Module parent;
    private String name;
    private String id;
    private SettingType settingType;
    private boolean valueBoolean;
    private float valueNumeric;
    private float minValue;
    private float maxValue;
    private String valueString;
    public String[] values;
    private String text, defaultText;
    private List<String> selectedValues;
    private Color selectedColor;
    private boolean isVisible = true;


    public Setting(String name, String id, Module parent, boolean value) {
        this.parent = parent;
        this.name = name;
        this.id = id;
        this.settingType = SettingType.Boolean;
        this.valueBoolean = value;
        ArchWare.settingManager.addSetting(this);
    }

    public Setting(String name, String id, Module parent, float value, float minValue, float maxValue) {
        this.parent = parent;
        this.name = name;
        this.id = id;
        this.settingType = SettingType.Numeric;
        this.valueNumeric = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
        ArchWare.settingManager.addSetting(this);
    }

    public Setting(final String name, final String id, final Module parent, final String text, final String defaultText) {
        this.parent = parent;
        this.name = name;
        this.id = id;
        this.settingType = SettingType.Field;
        this.text = text;
        this.defaultText = defaultText;
        ArchWare.settingManager.addSetting(this);
    }

    public Setting(String name, String id, Module parent, String value, String... values) {
        this.parent = parent;
        this.name = name;
        this.id = id;
        this.settingType = SettingType.String;
        this.valueString = value;
        this.values = values;
        ArchWare.settingManager.addSetting(this);
    }

    public Setting(String name, String id, Module parent, String... values) {
        this.parent = parent;
        this.name = name;
        this.id = id;
        this.settingType = SettingType.MultiString;
        selectedValues = new ArrayList<>();
        this.values = values;
        ArchWare.settingManager.addSetting(this);
    }

    public Setting(final String text, final String id, final Module parent){
        this.text = text;
        this.id = id;
        this.parent = parent;
        this.settingType = SettingType.Button;
        ArchWare.settingManager.addSetting(this);
    }

    public Setting(final String name, final String id, final Module parent, final Color selectedColor){
        this.parent = parent;
        this.id = id;
        this.name = name;
        this.selectedColor = selectedColor;
        this.settingType = SettingType.ColorPicker;
        ArchWare.settingManager.addSetting(this);
    }

    public void setParent(Module parent) {
        this.parent = parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSettingType(SettingType settingType) {
        this.settingType = settingType;
    }

    public boolean isValueBoolean() {
        return valueBoolean;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public boolean isTypeBoolean(){
        return getSettingType() == SettingType.Boolean;
    }

    public boolean isTypeNumeric(){
        return getSettingType() == SettingType.Numeric;
    }

    public boolean isTypeString(){
        return getSettingType() == SettingType.String;
    }

    public void setValueBoolean(boolean valueBoolean) {
        this.valueBoolean = valueBoolean;
    }

    public boolean getValueBoolean() { return valueBoolean; }

    public float getValueNumeric() {
        return valueNumeric;
    }

    public void setValueNumeric(float valueNumeric) {
        this.valueNumeric = valueNumeric;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public Module getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public SettingType getSettingType() {
        return settingType;
    }

    public String[] getValues() {
        return values;
    }

    public float getMinValue() {
        return minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public String getDefaultText() {
        return defaultText;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public List<String> getSelectedValues() {
        return selectedValues;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public Setting invisible(){
        isVisible = false;
        return this;
    }

    public boolean isVisible() {
        return isVisible;
    }
}
