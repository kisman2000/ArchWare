package fun.archware.base.skeetgui.members.settings;

import fun.archware.base.setting.Setting;
import fun.archware.base.skeetgui.ClickGUI;
import fun.archware.base.skeetgui.GUIElement;
import fun.archware.impl.utils.RenderUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class GUISlider extends GUIElement {
    private double posX, posY;
    private Setting parent;
    private boolean isInMove;

    public GUISlider(double posX, double posY, Setting parent) {
        this.posX = posX;
        this.posY = posY;
        this.parent = parent;
    }
    @Override
    public void drawScreen(int mouseX, int mouseY) {
        ClickGUI.font2.drawStringWithOutline(parent.getName(), ClickGUI.X + posX + 9, ClickGUI.Y + ClickGUI.scissor + posY + 1.5, -1);
        RenderUtil.drawRect(ClickGUI.X + posX + 8.5, ClickGUI.Y + ClickGUI.scissor + posY + 6.5, 61, 4, new Color(10, 10, 10).hashCode());
        RenderUtil.drawRect(ClickGUI.X + posX + 9, ClickGUI.Y + ClickGUI.scissor + posY + 7, 60, 3, new Color(71, 71, 71).hashCode());
        RenderUtil.drawRect(ClickGUI.X + posX + 9, ClickGUI.Y + ClickGUI.scissor + posY + 7, (parent.getValueNumeric() - parent.getMinValue()) / (parent.getMaxValue() - parent.getMinValue()) * 60, 3, ClickGUI.getGUIColor().hashCode());
        ClickGUI.font2.drawStringWithOutline(String.format("%.2f", parent.getValueNumeric()),
                Math.max(
                        ClickGUI.X + posX + 9,
                        ClickGUI.X + posX + 17 + (parent.getValueNumeric() - parent.getMinValue()) / (parent.getMaxValue() - parent.getMinValue()) * 60 - ClickGUI.font2.getStringWidth(String.format("%.2f", parent.getValueNumeric()))
                ),
                ClickGUI.Y + ClickGUI.scissor + posY + 9, -1);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.disableTexture2D();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(ClickGUI.X + posX + 69, ClickGUI.Y + ClickGUI.scissor + posY + 7, 0).tex(1, 0).color(0, 0, 0, 0).endVertex();
        bufferbuilder.pos(ClickGUI.X + posX + 9, ClickGUI.Y + ClickGUI.scissor + posY + 7, 0).tex(0, 0).color(0, 0, 0, 0).endVertex();
        bufferbuilder.pos(ClickGUI.X + posX + 9, ClickGUI.Y + ClickGUI.scissor + posY + 7 + 3, 0).tex(0, 1).color(0, 0, 0, 125).endVertex();
        bufferbuilder.pos(ClickGUI.X + posX + 69, ClickGUI.Y + ClickGUI.scissor + posY + 7 + 3, 0).tex(1, 1).color(0, 0, 0, 125).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        if(Mouse.isButtonDown(0)){
            if(isInMove){
                parent.setValueNumeric((float)(parent.getMinValue() + (MathHelper.clamp((mouseX - (ClickGUI.X + posX + 9)) / 60, 0, 1)) * (parent.getMaxValue() - parent.getMinValue())));
            }
        }else{
            if(isInMove) isInMove = false;
        }
        super.drawScreen(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(ClickGUI.isHovered(mouseX, mouseY, ClickGUI.X + posX + 8.5, ClickGUI.Y + ClickGUI.scissor + posY + 6.5, 61, 4) && mouseButton == 0){
            isInMove = true;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
