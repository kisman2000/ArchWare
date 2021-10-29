package fun.archware.base.skeetgui.members.settings;

import fun.archware.base.setting.Setting;
import fun.archware.base.skeetgui.ClickGUI;
import fun.archware.base.skeetgui.GUIElement;
import fun.archware.impl.utils.RenderUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;


public class GUIString extends GUIElement {
    public double posX, posY;
    public Setting parent;

    public GUIString(double posX, double posY, Setting parent) {
        this.posX = posX;
        this.posY = posY;
        this.parent = parent;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        ClickGUI.font2.drawStringWithOutline(parent.getName(), ClickGUI.X + posX + 9, ClickGUI.Y + ClickGUI.scissor + posY + 2.5, -1);
        RenderUtil.drawRect(ClickGUI.X + posX + 8.5, ClickGUI.Y + ClickGUI.scissor + posY + 6.5, 62, 8 + (ClickGUI.activeString != null && ClickGUI.activeString == this ? (7 * parent.getValues().length) : 0), new Color(10, 10, 10).hashCode());
        RenderUtil.drawRect(ClickGUI.X + posX + 9, ClickGUI.Y + ClickGUI.scissor + posY + 7, 61, 7, new Color(49, 49, 49).hashCode());
        final boolean isHovered = ClickGUI.isHovered(mouseX, mouseY, ClickGUI.X + posX + 9, ClickGUI.Y + ClickGUI.scissor + posY + 7, 61, 7);
        if(ClickGUI.activeString == null || ClickGUI.activeString != this){
            final Tessellator tessellator = Tessellator.getInstance();
            final BufferBuilder bufferbuilder = tessellator.getBuffer();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel(7425);
            GlStateManager.disableTexture2D();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferbuilder.pos(ClickGUI.X + posX + 70, ClickGUI.Y + ClickGUI.scissor + posY + 7, 0).tex(1, 0).color(0, 0, 0, isHovered ? 30 : 0).endVertex();
            bufferbuilder.pos(ClickGUI.X + posX + 9, ClickGUI.Y + ClickGUI.scissor + posY + 7, 0).tex(0, 0).color(0, 0, 0, isHovered ? 30 : 0).endVertex();
            bufferbuilder.pos(ClickGUI.X + posX + 9, ClickGUI.Y + ClickGUI.scissor + posY + 7 + 7, 0).tex(0, 1).color(0, 0, 0, 125).endVertex();
            bufferbuilder.pos(ClickGUI.X + posX + 70, ClickGUI.Y + ClickGUI.scissor + posY + 7 + 7, 0).tex(1, 1).color(0, 0, 0, 125).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }
        ClickGUI.font2.drawStringWithOutline(parent.getValueString(), ClickGUI.X + posX + 11, ClickGUI.Y + ClickGUI.scissor + posY + 10, -1);

        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glBegin(GL_TRIANGLE_FAN);
        glColor4d(225 / 255f, 225 / 255f, 225 / 255f, 1);
        glVertex2d(ClickGUI.X + posX + 62, ClickGUI.Y + ClickGUI.scissor + posY + 9.3 + 0.5);
        glVertex2d(ClickGUI.X + posX + 64, ClickGUI.Y + ClickGUI.scissor + posY + 11.5);
        glVertex2d(ClickGUI.X + posX + 66, ClickGUI.Y + ClickGUI.scissor + posY + 9.3 + 0.5);
        glEnd();
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
        super.drawScreen(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(ClickGUI.activeString == null && ClickGUI.isHovered(mouseX, mouseY, ClickGUI.X + posX + 9, ClickGUI.Y + ClickGUI.scissor + posY + 7, 61, 7) && mouseButton == 0){
            ClickGUI.activeString = this;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
