package fun.archware.base.skeetgui.members;

import fun.archware.ArchWare;
import fun.archware.base.module.Module;
import fun.archware.base.setting.Setting;
import fun.archware.base.skeetgui.ClickGUI;
import fun.archware.base.skeetgui.GUIElement;
import fun.archware.base.skeetgui.members.settings.*;
import fun.archware.impl.utils.RenderUtil;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUIModule extends GUIElement {
    public double posX, posY;
    public Module parent;
    public double height;
    private List<GUIElement> elements = new ArrayList<>();

    public GUIModule(double posX, double posY, Module parent) {
        elements.clear();
        double y = posY + 6;
        elements.add(new GUIState(posX, y, parent));
        y += 10;
        height += 10;
        for(final Setting s : ArchWare.settingManager.getSettingsByMod(parent)){
            if(s.isVisible()){
                switch(s.getSettingType()){
                    case Boolean:
                        elements.add(new GUIBoolean(posX, y, s));
                        y += 10;
                        height += 10;
                        break;
                    case Numeric:
                        elements.add(new GUISlider(posX, y, s));
                        y += 18;
                        height += 18;
                        break;
                    case String:
                        elements.add(new GUIString(posX, y, s));
                        y += 19;
                        height += 19;
                        break;
                    case MultiString:
                        elements.add(new GUIMultiString(posX, y, s));
                        y += 19;
                        height += 19;
                        break;
                }
            }
        }
        this.posX = posX;
        this.posY = posY;
        this.parent = parent;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        RenderUtil.drawRect(ClickGUI.X + posX - 0.5, ClickGUI.Y + ClickGUI.scissor + posY - 0.5, 81, height + 5, new Color(43, 43, 43).hashCode());
        RenderUtil.drawRect(ClickGUI.X + posX, ClickGUI.Y + ClickGUI.scissor + posY, 80, height + 4, new Color(30, 30, 30).hashCode());
        ClickGUI.font2.drawStringWithOutline(parent.getName(), ClickGUI.X + posX + 1, ClickGUI.Y + ClickGUI.scissor + posY - 1, -1);
        elements.forEach(element -> element.drawScreen(mouseX, mouseY));
        super.drawScreen(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        elements.forEach(element -> element.mouseClicked(mouseX, mouseY, mouseButton));
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        elements.forEach(element -> element.keyTyped(typedChar, keyCode));
        super.keyTyped(typedChar, keyCode);
    }
}
