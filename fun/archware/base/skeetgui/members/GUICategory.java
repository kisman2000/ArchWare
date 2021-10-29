package fun.archware.base.skeetgui.members;

import fun.archware.base.module.Category;
import fun.archware.base.skeetgui.ClickGUI;
import fun.archware.base.skeetgui.GUIElement;
import fun.archware.impl.utils.RenderUtil;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUICategory extends GUIElement {
    private final double posY;
    private final Category parent;
    public List<GUIModule> elements = new ArrayList<>();

    public GUICategory(double posY, Category parent) {
        elements.clear();
        this.posY = posY;
        this.parent = parent;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        if(ClickGUI.category != null && ClickGUI.category == parent){
            ClickGUI.font2.drawStringWithOutline(ClickGUI.category.name().charAt(0) + ClickGUI.category.name().substring(1).toLowerCase(), ClickGUI.X + 40, ClickGUI.Y + 10, -1);
            RenderUtil.drawRect(ClickGUI.X, ClickGUI.Y + posY - 22.5, 35.5, 51, new Color(30, 30, 30).hashCode());
            RenderUtil.drawRect(ClickGUI.X, ClickGUI.Y + posY - 22, 35.5, 50, new Color(24, 24, 24).hashCode());
            elements.forEach(element -> element.drawScreen(mouseX, mouseY));
        }if(ClickGUI.isHovered(mouseX, mouseY, ClickGUI.X, ClickGUI.Y + posY - 22, 35.5, 49)){
            RenderUtil.drawRect(ClickGUI.X, ClickGUI.Y + posY - 22, 35.5, 50, new Color(42, 42, 42, 150).hashCode());
        }
        ClickGUI.font.drawString(parent.getChar(), (int)ClickGUI.X + 10, ClickGUI.Y + posY - 2, new Color(205, 205, 205).hashCode());
        super.drawScreen(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(ClickGUI.isHovered(mouseX, mouseY, ClickGUI.X, ClickGUI.Y + posY - 22, 35.5, 49) && mouseButton == 0){
            ClickGUI.category = parent;
            ClickGUI.scissor = 0;
        }
        if(ClickGUI.category != null && ClickGUI.category == parent) elements.forEach(element -> element.mouseClicked(mouseX, mouseY, mouseButton));
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if(ClickGUI.category != null && ClickGUI.category == parent) elements.forEach(element -> element.keyTyped(typedChar, keyCode));
        super.keyTyped(typedChar, keyCode);
    }
}
