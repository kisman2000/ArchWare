package fun.archware.base.skeetgui.members.settings;

import fun.archware.base.module.Module;
import fun.archware.base.skeetgui.ClickGUI;
import fun.archware.base.skeetgui.GUIElement;
import fun.archware.impl.utils.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class GUIState extends GUIElement {
    private double posX, posY;
    private Module parent;
    private boolean isBinding;

    public GUIState(double posX, double posY, Module parent) {
        this.posX = posX;
        this.posY = posY;
        this.parent = parent;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        RenderUtil.drawRect(ClickGUI.X + posX + 1.5, ClickGUI.Y + ClickGUI.scissor + posY - 0.5, 5, 5, new Color(10, 10, 10).hashCode());
        RenderUtil.drawRect(ClickGUI.X + posX + 2, ClickGUI.Y + ClickGUI.scissor + posY, 4, 4, parent.isToggled() ? ClickGUI.getGUIColor().hashCode() : new Color(71, 71, 71).hashCode());
        final boolean isHovered = ClickGUI.isHovered(mouseX, mouseY, ClickGUI.X + posX + 1.5, ClickGUI.Y + ClickGUI.scissor + posY - 0.5, 5, 5);
        final boolean isClick = ClickGUI.isHovered(mouseX, mouseY, ClickGUI.X + posX + 1.5, ClickGUI.Y + ClickGUI.scissor + posY - 0.5, 5, 5) && Mouse.isButtonDown(0);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.disableTexture2D();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(ClickGUI.X + posX + 1.5 + 5, ClickGUI.Y + ClickGUI.scissor + posY - 0.5, 0).tex(1, 0).color(0, 0, 0, isClick ? 90 : 0).endVertex();
        bufferbuilder.pos(ClickGUI.X + posX + 1.5, ClickGUI.Y + ClickGUI.scissor + posY - 0.5, 0).tex(0, 0).color(0, 0, 0, isClick ? 90 : 0).endVertex();
        bufferbuilder.pos(ClickGUI.X + posX + 1.5, ClickGUI.Y + ClickGUI.scissor + posY - 0.5 + 4.5, 0).tex(0, 1).color(0, 0, 0, isHovered ? 195 : 125).endVertex();
        bufferbuilder.pos(ClickGUI.X + posX + 1.5 + 5, ClickGUI.Y + ClickGUI.scissor + posY - 0.5 + 4.5, 0).tex(1, 1).color(0, 0, 0, isHovered ? 195 : 125).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        ClickGUI.font2.drawStringWithOutline("Enabled", ClickGUI.X + posX + 9, ClickGUI.Y + ClickGUI.scissor + posY + 1.5, -1);
        ClickGUI.font2.drawStringWithOutline("[" +(isBinding ? "..." : parent.getKey() == 0 ? "~" : Keyboard.getKeyName(parent.getKey())) + "]",
                ClickGUI.X + posX + 77 - ClickGUI.font2.getStringWidth("[" + (isBinding ? "..." : parent.getKey() == 0 ? "~" : Keyboard.getKeyName(parent.getKey())) + "]"),
                ClickGUI.Y + ClickGUI.scissor + posY + 1.5,
                ClickGUI.isHovered(mouseX, mouseY, ClickGUI.X + posX + 77 - ClickGUI.font2.getStringWidth("[" + (isBinding ? "..." : parent.getKey() == 0 ? "~" : Keyboard.getKeyName(parent.getKey())) + "]"),
                        ClickGUI.Y + ClickGUI.scissor + posY,
                        ClickGUI.font2.getStringWidth("[" +(isBinding ? "..." : parent.getKey() == 0 ? "~" : Keyboard.getKeyName(parent.getKey())) + "]"),
                        6) ? Color.GRAY.darker().hashCode() : Color.GRAY.hashCode());
        super.drawScreen(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(ClickGUI.isHovered(mouseX, mouseY, ClickGUI.X + posX + 1.5, ClickGUI.Y + ClickGUI.scissor + posY - 0.5, 5, 5) && mouseButton == 0) parent.toggle();
        if(ClickGUI.isHovered(mouseX, mouseY, ClickGUI.X + posX + 77 - ClickGUI.font2.getStringWidth("[" + (isBinding ? "..." : parent.getKey() == 0 ? "~" : Keyboard.getKeyName(parent.getKey())) + "]"),
                ClickGUI.Y + posY + ClickGUI.scissor,
                ClickGUI.font2.getStringWidth("[" +(isBinding ? "..." : parent.getKey() == 0 ? "~" : Keyboard.getKeyName(parent.getKey())) + "]"),
                6) && mouseButton == 0) isBinding = true;
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if(isBinding) {
            parent.setKey(keyCode);
            isBinding = false;
        }
        super.keyTyped(typedChar, keyCode);
    }
}
