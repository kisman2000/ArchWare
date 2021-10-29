package fun.archware.base.skeetgui;

import fun.archware.ArchWare;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.skeetgui.members.GUICategory;
import fun.archware.base.skeetgui.members.GUIModule;
import fun.archware.base.skeetgui.members.settings.GUIMultiString;
import fun.archware.base.skeetgui.members.settings.GUIString;
import fun.archware.impl.managers.FileManager;
import fun.archware.impl.utils.RenderUtil;
import fun.archware.impl.utils.font.CustomFontRenderer;
import fun.archware.impl.utils.font.FontUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.lwjgl.opengl.GL11.*;

public class ClickGUI extends GuiScreen {
    public static double X = 30, Y = 30, WIDTH = 295, HEIGHT = 250, X2, Y2;
    private boolean isInMove;
    public static Category category;
    public static GUIElement activeString;
    public static double scissor;
    public static final List<GUIElement> elements = new ArrayList<>();
    public static final CustomFontRenderer font = new CustomFontRenderer(FontUtils.getFontFromTTF(new ResourceLocation("categories.ttf"), 31, Font.PLAIN), true, true);
    public static final CustomFontRenderer font2 = new CustomFontRenderer(new Font("Arial", Font.PLAIN, 11), true, true);
    @Override
    public void initGui() {
        elements.clear();
        double posY = 22;
        for(final Category category : Category.values()){
            final GUICategory guiCategory = new GUICategory(posY, category);
            double x = 40;
            for(int i = 0; i < ArchWare.moduleManager.getModulesByCategory(category).size(); ++i){
                if(i <= 2){
                    guiCategory.elements.add(new GUIModule(x, 22, ArchWare.moduleManager.getModulesByCategory(category).get(i)));
                    x += 85;
                }else{
                    if(x + 110 >= 300) {
                        x = 40;
                    }else{
                        x += 85;
                    }
                    guiCategory.elements.add(new GUIModule(x, guiCategory.elements.get(i - 3).posY + 10 + guiCategory.elements.get(i - 3).height, ArchWare.moduleManager.getModulesByCategory(category).get(i)));
                }
            }
            elements.add(guiCategory);
            posY += 50;

        }
        super.initGui();
    }

    public void drawMultiString(final int mouseX, final int mouseY){
        if(activeString != null){
            RenderUtil.drawRect(ClickGUI.X + ((GUIMultiString)activeString).posX + 9, ClickGUI.Y + ((GUIMultiString)activeString).posY + 14, 61,
                    7 * ArchWare.settingManager.getSettingById(((GUIMultiString)activeString).parent.getId()).getValues().length,
                    new Color(49, 49, 49).hashCode());
            final Tessellator tessellator = Tessellator.getInstance();
            final BufferBuilder bufferbuilder = tessellator.getBuffer();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel(7425);
            GlStateManager.disableTexture2D();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferbuilder.pos(ClickGUI.X + ((GUIMultiString)activeString).posX + 70, ClickGUI.Y + ((GUIMultiString)activeString).posY + 7, 0).tex(1, 0).color(0, 0, 0, 30).endVertex();
            bufferbuilder.pos(ClickGUI.X + ((GUIMultiString)activeString).posX + 9, ClickGUI.Y + ((GUIMultiString)activeString).posY + 7, 0).tex(0, 0).color(0, 0, 0, 30).endVertex();
            bufferbuilder.pos(ClickGUI.X + ((GUIMultiString)activeString).posX + 9, ClickGUI.Y + ((GUIMultiString)activeString).posY + 14 + (7 * ((GUIMultiString)activeString).parent.getValues().length), 0).tex(0, 1).color(0, 0, 0, 125).endVertex();
            bufferbuilder.pos(ClickGUI.X + ((GUIMultiString)activeString).posX + 70, ClickGUI.Y + ((GUIMultiString)activeString).posY + 14 + (7 * ((GUIMultiString)activeString).parent.getValues().length), 0).tex(1, 1).color(0, 0, 0, 125).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            double posY = ClickGUI.Y + ((GUIMultiString)activeString).posY + 17;
            for(final String value : ArchWare.settingManager.getSettingById(((GUIMultiString)activeString).parent.getId()).getValues()){
                if(isHovered(mouseX, mouseY, ClickGUI.X + ((GUIMultiString)activeString).posX + 9, posY - 3, 61, 6)){
                    RenderUtil.drawRect(ClickGUI.X + ((GUIMultiString)activeString).posX + 9, posY - 3, 61, 7, new Color(0, 0, 0, 105).hashCode());
                }
                font2.drawStringWithOutline(value, ClickGUI.X + ((GUIMultiString)activeString).posX + 11, posY, -1);
                posY += 7;
            }
        }
    }

    public void drawString(final int mouseX, final int mouseY){
        if(activeString != null){
            if(activeString instanceof GUIString){
                RenderUtil.drawRect(ClickGUI.X + ((GUIString)activeString).posX + 9, ClickGUI.Y + ClickGUI.scissor + ((GUIString)activeString).posY + 14, 61,
                        7 * ArchWare.settingManager.getSettingById(((GUIString)activeString).parent.getId()).getValues().length,
                        new Color(49, 49, 49).hashCode());
                final Tessellator tessellator = Tessellator.getInstance();
                final BufferBuilder bufferbuilder = tessellator.getBuffer();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
                GlStateManager.disableAlpha();
                GlStateManager.shadeModel(7425);
                GlStateManager.disableTexture2D();
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferbuilder.pos(ClickGUI.X + ((GUIString)activeString).posX + 70, ClickGUI.Y + ClickGUI.scissor + ((GUIString)activeString).posY + 7, 0).tex(1, 0).color(0, 0, 0, 30).endVertex();
                bufferbuilder.pos(ClickGUI.X + ((GUIString)activeString).posX + 9, ClickGUI.Y + ClickGUI.scissor + ((GUIString)activeString).posY + 7, 0).tex(0, 0).color(0, 0, 0, 30).endVertex();
                bufferbuilder.pos(ClickGUI.X + ((GUIString)activeString).posX + 9, ClickGUI.Y + ClickGUI.scissor + ((GUIString)activeString).posY + 14 + (7 * ((GUIString)activeString).parent.getValues().length), 0).tex(0, 1).color(0, 0, 0, 125).endVertex();
                bufferbuilder.pos(ClickGUI.X + ((GUIString)activeString).posX + 70, ClickGUI.Y + ClickGUI.scissor + ((GUIString)activeString).posY + 14 + (7 * ((GUIString)activeString).parent.getValues().length), 0).tex(1, 1).color(0, 0, 0, 125).endVertex();
                tessellator.draw();
                GlStateManager.enableTexture2D();
                GlStateManager.shadeModel(7424);
                GlStateManager.enableAlpha();
                GlStateManager.disableBlend();
                double posY = ClickGUI.Y + ClickGUI.scissor + ((GUIString)activeString).posY + 17;
                for(final String value : ArchWare.settingManager.getSettingById(((GUIString)activeString).parent.getId()).getValues()){
                    if(isHovered(mouseX, mouseY, ClickGUI.X + ((GUIString)activeString).posX + 9, posY - 3, 61, 6)){
                        RenderUtil.drawRect(ClickGUI.X + ((GUIString)activeString).posX + 9, posY - 3, 61, 7, new Color(0, 0, 0, 105).hashCode());
                    }
                    font2.drawStringWithOutline(value, ClickGUI.X + ((GUIString)activeString).posX + 11, posY, -1);
                    posY += 7;
                }
            }else if(activeString instanceof GUIMultiString){
                RenderUtil.drawRect(ClickGUI.X + ((GUIMultiString)activeString).posX + 9, ClickGUI.Y + ClickGUI.scissor + ((GUIMultiString)activeString).posY + 14, 61,
                        7 * ArchWare.settingManager.getSettingById(((GUIMultiString)activeString).parent.getId()).getValues().length,
                        new Color(49, 49, 49).hashCode());
                final Tessellator tessellator = Tessellator.getInstance();
                final BufferBuilder bufferbuilder = tessellator.getBuffer();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
                GlStateManager.disableAlpha();
                GlStateManager.shadeModel(7425);
                GlStateManager.disableTexture2D();
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferbuilder.pos(ClickGUI.X + ((GUIMultiString)activeString).posX + 70, ClickGUI.Y + ClickGUI.scissor + ((GUIMultiString)activeString).posY + 7, 0).tex(1, 0).color(0, 0, 0, 30).endVertex();
                bufferbuilder.pos(ClickGUI.X + ((GUIMultiString)activeString).posX + 9, ClickGUI.Y + ClickGUI.scissor + ((GUIMultiString)activeString).posY + 7, 0).tex(0, 0).color(0, 0, 0, 30).endVertex();
                bufferbuilder.pos(ClickGUI.X + ((GUIMultiString)activeString).posX + 9, ClickGUI.Y + ClickGUI.scissor + ((GUIMultiString)activeString).posY + 14 + (7 * ((GUIMultiString)activeString).parent.getValues().length), 0).tex(0, 1).color(0, 0, 0, 125).endVertex();
                bufferbuilder.pos(ClickGUI.X + ((GUIMultiString)activeString).posX + 70, ClickGUI.Y + ClickGUI.scissor + ((GUIMultiString)activeString).posY + 14 + (7 * ((GUIMultiString)activeString).parent.getValues().length), 0).tex(1, 1).color(0, 0, 0, 125).endVertex();
                tessellator.draw();
                GlStateManager.enableTexture2D();
                GlStateManager.shadeModel(7424);
                GlStateManager.enableAlpha();
                GlStateManager.disableBlend();
                double posY = ClickGUI.Y + ClickGUI.scissor + ((GUIMultiString)activeString).posY + 17;
                for(final String value : ArchWare.settingManager.getSettingById(((GUIMultiString)activeString).parent.getId()).getValues()){
                    if(isHovered(mouseX, mouseY, ClickGUI.X + ((GUIMultiString)activeString).posX + 9, posY - 3, 61, 6)){
                        RenderUtil.drawRect(ClickGUI.X + ((GUIMultiString)activeString).posX + 9, posY - 3, 61, 7, new Color(0, 0, 0, 105).hashCode());
                    }
                    font2.drawStringWithOutline(value, ClickGUI.X + ((GUIMultiString)activeString).posX + 11, posY, -1);
                    posY += 7;
                }
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        if(isInMove){
            if(Mouse.isButtonDown(0)){
                X = X2 + mouseX;
                Y = Y2 + mouseY;
            }else{
                isInMove = false;
            }
        }
        RenderUtil.drawRect(X - 2.8, Y - 2.8, WIDTH + 5.7, HEIGHT + 5.7, new Color(16, 16, 16).hashCode());
        RenderUtil.drawRect(X - 2.5, Y - 2.5, WIDTH + 5, HEIGHT + 5, new Color(54, 54, 54, 125).hashCode());
        RenderUtil.drawRect(X - 2, Y - 2, WIDTH + 4, HEIGHT + 4, new Color(54, 54, 54).hashCode());
        RenderUtil.drawRect(X - 1.5, Y - 1.5, WIDTH + 3, HEIGHT + 3, new Color(47, 47, 47).hashCode());
        RenderUtil.drawRect(X - 1, Y - 1, WIDTH + 2, HEIGHT + 2, new Color(49, 49, 49).hashCode());
        RenderUtil.drawRect(X - 0.5, Y - 0.5, WIDTH + 1, HEIGHT + 1, new Color(45, 45, 45).hashCode());
        RenderUtil.drawRect(X, Y, WIDTH, HEIGHT, new Color(24, 24, 24).hashCode());
        RenderUtil.drawRect(X, Y, 35.5, HEIGHT, new Color(30, 30, 30).hashCode());
        RenderUtil.drawRect(X, Y, 35, HEIGHT, new Color(16, 16, 16).hashCode());
        RenderUtil.drawRect(X, Y, 34, HEIGHT, new Color(13, 13, 13).hashCode());
        glPushMatrix();
        glEnable(GL_SCISSOR_TEST);
        RenderUtil.prepareScissorBox((int)X, (int)Y + 1, (int)(X + WIDTH), (int)(Y + HEIGHT));
        elements.forEach(element -> element.drawScreen(mouseX, mouseY));
        glDisable(GL_SCISSOR_TEST);
        glPopMatrix();
        for(double i = X; i < X + WIDTH; ++i){
            float hue = (float) ((i / 4.5) % 150f);
            hue /= 150;
            RenderUtil.drawRect(i, Y, 1, 1, Color.getHSBColor(hue, 0.4f, 1f).hashCode());
            RenderUtil.drawRect(i, Y, 1, 1, new Color(255, 255, 255, 85).hashCode());
        }
        drawString(mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public static boolean isHovered(final int mouseX, final int mouseY, final double x, final double y, final double width, final double height){
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(activeString != null){
            if(activeString instanceof GUIString){
                if(!isHovered(mouseX, mouseY, ClickGUI.X + ((GUIString)activeString).posX + 9, ClickGUI.Y + ClickGUI.scissor + ((GUIString)activeString).posY + 14, 61, 7 * ArchWare.settingManager.getSettingById(((GUIString)activeString).parent.getId()).getValues().length)){
                    activeString = null;
                    return;
                }
                double posY = ClickGUI.Y + ClickGUI.scissor + ((GUIString)activeString).posY + 17;
                for(final String value : ArchWare.settingManager.getSettingById(((GUIString)activeString).parent.getId()).getValues()){
                    if(isHovered(mouseX, mouseY, ClickGUI.X + ((GUIString)activeString).posX + 9, posY - 3, 61, 6)){
                        ((GUIString)activeString).parent.setValueString(value);
                        activeString = null;
                        return;
                    }
                    posY += 7;
                }
            }else if(activeString instanceof GUIMultiString){
                double posY = ClickGUI.Y + ClickGUI.scissor + ((GUIMultiString)activeString).posY + 17;
                if(!isHovered(mouseX, mouseY, ClickGUI.X + ((GUIMultiString)activeString).posX + 9, posY - 3, 61, 7 * ((GUIMultiString)activeString).parent.getValues().length)){
                    activeString = null;
                    return;
                }
                for(final String value : ArchWare.settingManager.getSettingById(((GUIMultiString)activeString).parent.getId()).getValues()){
                    if(isHovered(mouseX, mouseY, ClickGUI.X + ((GUIMultiString)activeString).posX + 9, posY - 3, 61, 7)){
                        if(((GUIMultiString)activeString).parent.getSelectedValues().contains(value)){
                            ((GUIMultiString)activeString).parent.getSelectedValues().remove(value);
                        }else{
                            ((GUIMultiString)activeString).parent.getSelectedValues().add(value);
                        }
                    }
                    posY += 7;
                }
            }
        }
        if(isHovered(mouseX, mouseY, X + 35.5, Y, WIDTH - 35.5, 12) && mouseButton == 0){
            X2 = X - mouseX;
            Y2 = Y - mouseY;
            isInMove = true;
        }
        elements.forEach(element -> element.mouseClicked(mouseX, mouseY, mouseButton));
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public static Color getGUIColor(){
        return new Color(
                (int)ArchWare.settingManager.getSettingById("GuiRed").getValueNumeric(),
                (int)ArchWare.settingManager.getSettingById("GuiGreen").getValueNumeric(),
                (int)ArchWare.settingManager.getSettingById("GuiBlue").getValueNumeric()
        );
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        elements.forEach(element -> element.keyTyped(typedChar, keyCode));
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void handleMouseInput() throws IOException {
        final int s = Mouse.getEventDWheel();
        if(s > 0){
            if(scissor + 7 < 7) scissor += 7;
        }else if(s < 0) {
            scissor -= 7;
        }
        super.handleMouseInput();
    }

    @Override
    public void onGuiClosed() {
        try {
            FileManager.update("config.arch");
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onGuiClosed();
    }
}
