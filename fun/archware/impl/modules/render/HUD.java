package fun.archware.impl.modules.render;

import fun.archware.ArchWare;
import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.*;
import fun.archware.impl.events.EventRender2D;
import fun.archware.impl.utils.FontUtil;
import fun.archware.impl.utils.RenderUtil;
import fun.archware.impl.utils.font.CustomFontRenderer;
import javafx.animation.Interpolator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static org.lwjgl.opengl.GL11.*;


public class HUD extends Module {
    private final CustomFontRenderer fontRenderer = new CustomFontRenderer(new Font("Arial", Font.PLAIN, 17), true, true);
    private final CustomFontRenderer fontRenderer2 = new CustomFontRenderer(new Font("Arial", Font.PLAIN, 14), true, true);
    private final CustomFontRenderer fontRenderer3 = new CustomFontRenderer(new Font("Verdana", Font.PLAIN, 14), true, true);
    private final MultiStringValue additions = new MultiStringValue("Additions", "HUDAdditions", this, new String[]{"Font", "Line", "Outline", "Bold", "Client version", "BPS", "Coordinates"});
    private final StringValue position = new StringValue("Position", "HUDPosition", this, "Right", new String[]{"Right", "Left"});
    private final StringValue watermark = new StringValue("Watermark", "HUDWatermark", this, "Default", new String[]{"Default", "ArchWare", "Skeet"});
    private final BooleanValue archwareWatermark = new BooleanValue("Watermark Opacity", "HUDArchWareWatermark", this, true);
    private final MultiStringValue watermarkAdditions = new MultiStringValue("Watermark additions", "HUDWatermarkAdditions", this, new String[]{"Time", "Ping", "FPS", "Current IP", "UID", "Username"});
    private final StringValue color = new StringValue("Color", "HUDColor", this, "Static", new String[]{"Static", "Rainbow", "Pulsive", "Category", "Astolfo"});
    private final NumericValue red = new NumericValue("Red", "HUDRed", this, 0, 0, 255);
    private final NumericValue green = new NumericValue("Green", "HUDGreen", this, 0, 0, 255);
    private final NumericValue blue = new NumericValue("Blue", "HUDBlue", this, 0, 0, 255);
    private final NumericValue rainbowSpeed = new NumericValue("Rainbow Speed", "HUDSpeed", this, 3500, 10000, 1500);
    public HUD() {
        super("HUD", Category.RENDER);
    }

    @EventTarget
    public void onRender(final EventRender2D event) {
        //RenderUtil.drawBlurRect(50, 50, 150, 150, 2, 2);
        if(additions.getSelectedValues().contains("Bold")){
            if(fontRenderer.getFont().getStyle() != Font.BOLD) fontRenderer.setFont(new Font("Arial", Font.BOLD, 17));
        }else{
            if(fontRenderer.getFont().getStyle() != Font.PLAIN) fontRenderer.setFont(new Font("Arial", Font.PLAIN, 17));
        }
        String text;
        switch(watermark.getValueString()){
            case "Default":
                text = ArchWare.CLIENT_NAME
                        + (watermarkAdditions.getSelectedValues().contains("Time") ? " [" + TextFormatting.GRAY + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())  + TextFormatting.WHITE + "]" : "")
                        + (watermarkAdditions.getSelectedValues().contains("Ping") ? " [MS: " + TextFormatting.GRAY + (mc.isSingleplayer() ? "0" : mc.getCurrentServerData().pingToServer) + TextFormatting.WHITE + "]" : "")
                        + (watermarkAdditions.getSelectedValues().contains("FPS") ? " [FPS: " + TextFormatting.GRAY + Minecraft.getDebugFPS() + TextFormatting.WHITE + "]" : "")
                        + (watermarkAdditions.getSelectedValues().contains("Current IP") ? " [" + TextFormatting.GRAY + mc.getCurrentServerData().serverIP + TextFormatting.WHITE + "]" : "")
                        + (watermarkAdditions.getSelectedValues().contains("UID") ? " [" + TextFormatting.GRAY +  "UID: " + ArchWare.uid + TextFormatting.WHITE + "]" : "")
                        + (watermarkAdditions.getSelectedValues().contains("Username") ? " [" +  "User: " + TextFormatting.GRAY + ArchWare.login + TextFormatting.WHITE + "]" : "");
                if(additions.getSelectedValues().contains("Font")){
                    fontRenderer.drawStringWithShadow(String.valueOf(text.charAt(0)) + TextFormatting.WHITE + text.substring(1), 1, 2, getColor(1, 1, 1).hashCode());
                }else{
                    mc.fontRendererObj.drawStringWithShadow(String.valueOf(text.charAt(0)) + TextFormatting.WHITE + text.substring(1), 1, 1, getColor(1, 1, 1).hashCode());
                }
                break;
            case "ArchWare":
                text = ArchWare.CLIENT_NAME
                        + (watermarkAdditions.getSelectedValues().contains("Time") ? " | " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) : "")
                        + (watermarkAdditions.getSelectedValues().contains("Ping") ? " | MS: " + (mc.isSingleplayer() ? "0" : mc.getCurrentServerData().pingToServer) : "")
                        + (watermarkAdditions.getSelectedValues().contains("FPS") ? " | FPS: " + Minecraft.getDebugFPS() : "")
                        + (watermarkAdditions.getSelectedValues().contains("Current IP") ? " | " + mc.getCurrentServerData().serverIP: "")
                        + (watermarkAdditions.getSelectedValues().contains("UID") ? " | " +  "UID: " + ArchWare.uid : "")
                        + (watermarkAdditions.getSelectedValues().contains("Username") ? " | " +  "User: " + ArchWare.login : "");
                RenderUtil.drawRect(4, 4, fontRenderer2.getStringWidth(text.toLowerCase()) + 4, 10, archwareWatermark.getValueBoolean() ? Integer.MIN_VALUE : new Color(27, 25, 30, 215).hashCode());
                for(int i = 0; i < fontRenderer2.getStringWidth(text.toLowerCase()) + 4; ++i){
                    RenderUtil.drawRect(4 + i, 4, 1, 1, RenderUtil.getRainbow(i * 6, 6500).hashCode());

                }
                fontRenderer2.drawString(text.toLowerCase(), 6, 8, -1);
                break;
            case "Skeet":
                text = "archware"
                        + (watermarkAdditions.getSelectedValues().contains("Time") ? " | " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) : "")
                        + (watermarkAdditions.getSelectedValues().contains("Ping") ? " | MS: " + (mc.isSingleplayer() ? "0" : mc.getCurrentServerData().pingToServer) : "")
                        + (watermarkAdditions.getSelectedValues().contains("FPS") ? " | FPS: " + Minecraft.getDebugFPS() : "")
                        + (watermarkAdditions.getSelectedValues().contains("Current IP") ? " | " + mc.getCurrentServerData().serverIP : "")
                        + (watermarkAdditions.getSelectedValues().contains("UID") ? " | UID: " + ArchWare.uid : "")
                        + (watermarkAdditions.getSelectedValues().contains("Username") ? " | User: " + ArchWare.login : "");
                RenderUtil.drawRect(3, 3, fontRenderer3.getStringWidth(text.toLowerCase()) + 14, 19, new Color(33, 32, 31).hashCode());
                RenderUtil.drawRect(5, 5, fontRenderer3.getStringWidth(text.toLowerCase()) + 10, 15, new Color(40, 38, 38).hashCode());
                RenderUtil.drawRect(7.5, 7, fontRenderer3.getStringWidth(text.toLowerCase()) + 5, 11, new Color(21, 21, 21).hashCode());
                for(double i = 7.8; i < 3 + fontRenderer3.getStringWidth(text.toLowerCase()) + 8.7; ++i){
                    RenderUtil.drawRect(i, 7, 1, 1, RenderUtil.getRainbow((int)i * 13, 3500).hashCode());
                }
                fontRenderer3.drawString("arch" + TextFormatting.DARK_GREEN + "ware" + TextFormatting.RESET + text.replace("archware", "").toLowerCase(), 10, 10.5, -1);

                break;
        }

        final List<Module> modules = new ArrayList<>(ArchWare.moduleManager.modules);

        if(additions.getSelectedValues().contains("Font")){
            modules.sort((m, m1) -> fontRenderer.getStringWidth(
                    m1.getName() + (m1.getSuffix() == null ? "" : " " + m1.getSuffix())) - fontRenderer.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + m.getSuffix())
            ));
        }else{
            modules.sort((m, m1) -> mc.fontRendererObj.getStringWidth(
                    m1.getName() + (m1.getSuffix() == null ? "" : " " + m1.getSuffix())) - mc.fontRendererObj.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + m.getSuffix())
            ));
        }

        double posY = 0;
        switch(position.getValueString()){
            case "Right":
                posY = 1.5;
                break;
            case "Left":
                switch(watermark.getValueString()){
                    case "Default":
                        posY = 13;
                    case "ArchWare":
                        posY = 18;
                        break;
                    case "Skeet":
                        posY = 25;
                        break;
                }
                break;
        }
        final ScaledResolution sr = new ScaledResolution(mc);
        for(final Module m : modules){
            if(String.valueOf(m.getPosX()).equals("NaN")) m.setPosX(new ScaledResolution(mc).getScaledWidth() * 2);
            if(String.valueOf(m.getPosY()).equals("NaN")) m.setPosY((float)posY);
            if(m.isToggled()){
                m.setPosY((float)Interpolator.EASE_OUT.interpolate(m.getPosY(), posY, (0.13*100) / Minecraft.getDebugFPS()));
                m.setInRenderState(true);
                if(additions.getSelectedValues().contains("Font")){
                    if(position.getValueString().equals("Right")){
                        m.setPosX((float)Interpolator.EASE_OUT.interpolate(m.getPosX(), sr.getScaledWidth() - fontRenderer.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + m.getSuffix())) - 1.3f, (0.1 * 100) / Minecraft.getDebugFPS()));
                    }else{
                        m.setPosX((float)Interpolator.EASE_OUT.interpolate(m.getPosX(), additions.getSelectedValues().contains("Line") ? 1.5 : 1, (0.1 * 100) / Minecraft.getDebugFPS()));
                    }
                }else{
                    if(position.getValueString().equals("Right")){
                        m.setPosX((float)Interpolator.EASE_OUT.interpolate(m.getPosX(), sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + m.getSuffix())) - 1.3f, (0.1 * 100) / Minecraft.getDebugFPS()));
                    }else{
                        m.setPosX((float)Interpolator.EASE_OUT.interpolate(m.getPosX(), additions.getSelectedValues().contains("Line") ? 1 : 0.5, (0.1 * 100) / Minecraft.getDebugFPS()));
                    }
                }
            }else{
                if(additions.getSelectedValues().contains("Font")){
                    if(position.getValueString().equals("Right")){
                        m.setPosX((float)Interpolator.EASE_OUT.interpolate(m.getPosX(), sr.getScaledWidth() + fontRenderer.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + m.getSuffix())) - 1.3f, (0.1 * 100) / Minecraft.getDebugFPS()));
                        if((int)m.getPosX() >= sr.getScaledWidth() + fontRenderer.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + m.getSuffix())) - 15){
                            m.setInRenderState(false);
                        }
                    }else{
                        m.setPosX((float)Interpolator.EASE_OUT.interpolate(m.getPosX(), -fontRenderer.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + m.getSuffix())) - 5, (0.1 * 100) / Minecraft.getDebugFPS()));
                        if((int)m.getPosX() <= -fontRenderer.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + m.getSuffix())) - 2){
                            m.setInRenderState(false);
                        }
                    }
                }else{
                    if(position.getValueString().equals("Right")){
                        m.setPosX((float)Interpolator.EASE_OUT.interpolate(m.getPosX(), sr.getScaledWidth() + mc.fontRendererObj.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + m.getSuffix())) - 1.3f, (0.1 * 100) / Minecraft.getDebugFPS()));
                        if((int)m.getPosX() >= sr.getScaledWidth() + mc.fontRendererObj.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + m.getSuffix())) - 15){
                            m.setInRenderState(false);
                        }
                    }else{
                        m.setPosX((float)Interpolator.EASE_OUT.interpolate(m.getPosX(), -mc.fontRendererObj.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + m.getSuffix())) - 5, (0.1 * 100) / Minecraft.getDebugFPS()));
                        if((int)m.getPosX() <= -mc.fontRendererObj.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + m.getSuffix())) - 2){
                            m.setInRenderState(false);
                        }
                    }
                }
            }
            if(m.isInRenderState()){
                switch(position.getValueString()){
                    case "Right":
                        if(additions.getSelectedValues().contains("Font")){
                            if(additions.getSelectedValues().contains("Outline")){
                                RenderUtil.drawRect(m.getPosX() + fontRenderer.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + m.getSuffix())) + 1.5, m.getPosY() - 2, -fontRenderer.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + TextFormatting.GRAY + m.getSuffix())) - (additions.getSelectedValues().contains("Line") ? 4.5 : 3), fontRenderer.getHeight() + 3, Integer.MIN_VALUE);
                            }
                            if(additions.getSelectedValues().contains("Line")){
                                RenderUtil.drawRect(m.getPosX() + fontRenderer.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + m.getSuffix())), m.getPosY() - 2, 1, fontRenderer.getHeight() + 3, getColor(-15 * (int)posY, ArchWare.moduleManager.modules.size(), modules.indexOf(m)).hashCode());
                            }
                            fontRenderer.drawStringWithShadow(m.getName() + (m.getSuffix() == null ? "" : " " + TextFormatting.GRAY + m.getSuffix()),
                                    m.getPosX() - (additions.getSelectedValues().contains("Line") ? 1.5 : 0),
                                    m.getPosY(),
                                    getColor(-15 * (int)m.getPosY(), ArchWare.moduleManager.modules.size(), modules.indexOf(m)).hashCode());
                            posY += fontRenderer.getHeight() + 3;
                        }else{
                            if(additions.getSelectedValues().contains("Outline")){
                                RenderUtil.drawRect(m.getPosX() + mc.fontRendererObj.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + m.getSuffix())) + 2, m.getPosY() - 2, -mc.fontRendererObj.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + TextFormatting.GRAY + m.getSuffix())) - (additions.getSelectedValues().contains("Line") ? 4.3 : 3.3), mc.fontRendererObj.FONT_HEIGHT + 1.7, Integer.MIN_VALUE);
                            }
                            if(additions.getSelectedValues().contains("Line")){
                                RenderUtil.drawRect(m.getPosX() + mc.fontRendererObj.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + m.getSuffix())) + 0.3, m.getPosY() - 2.3, 1, mc.fontRendererObj.FONT_HEIGHT + 2, getColor(-15 * (int)posY, ArchWare.moduleManager.modules.size(), modules.indexOf(m)).hashCode());
                            }
                            mc.fontRendererObj.drawStringWithShadow(m.getName() + (m.getSuffix() == null ? "" : " " + TextFormatting.GRAY + m.getSuffix()),
                                    m.getPosX(),
                                    m.getPosY(),
                                    getColor(-15 * (int)posY, ArchWare.moduleManager.modules.size(), modules.indexOf(m)).hashCode());
                            posY += mc.fontRendererObj.FONT_HEIGHT + 1.7;
                        }
                        break;
                    case "Left":
                        if(additions.getSelectedValues().contains("Font")){
                            if(additions.getSelectedValues().contains("Outline")){
                                RenderUtil.drawRect(m.getPosX() - 1, m.getPosY() - 2, fontRenderer.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + TextFormatting.GRAY + m.getSuffix())) + 3, fontRenderer.getHeight() + 3, Integer.MIN_VALUE);
                            }
                            if(additions.getSelectedValues().contains("Line")){
                                RenderUtil.drawRect(m.getPosX() - 1.5, m.getPosY() - 2, 1, fontRenderer.getHeight() + 3, getColor(-15 * (int)posY, ArchWare.moduleManager.modules.size(), modules.indexOf(m)).hashCode());
                            }
                            fontRenderer.drawStringWithShadow(m.getName() + (m.getSuffix() == null ? "" : " " + TextFormatting.GRAY + m.getSuffix()),
                                    m.getPosX() + (additions.getSelectedValues().contains("Line") ? 0.5 : 0),
                                    m.getPosY(),
                                    getColor(-15 * (int)posY, ArchWare.moduleManager.modules.size(), modules.indexOf(m)).hashCode());
                            posY += fontRenderer.getHeight() + 3;
                        }else{
                            if(additions.getSelectedValues().contains("Outline")){
                                RenderUtil.drawRect(m.getPosX() - 1, m.getPosY() - 2, mc.fontRendererObj.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + TextFormatting.GRAY + m.getSuffix())) + 3.3, mc.fontRendererObj.FONT_HEIGHT + 1.7, Integer.MIN_VALUE);
                            }
                            if(additions.getSelectedValues().contains("Line")){
                                RenderUtil.drawRect(m.getPosX() - 1, m.getPosY() - 2, 1, mc.fontRendererObj.FONT_HEIGHT + 1.7, getColor(-15 * (int)posY, ArchWare.moduleManager.modules.size(), modules.indexOf(m)).hashCode());
                            }
                            mc.fontRendererObj.drawStringWithShadow(m.getName() + (m.getSuffix() == null ? "" : " " + TextFormatting.GRAY + m.getSuffix()),
                                    m.getPosX() + 1 + (additions.getSelectedValues().contains("Line") ? 0.5f : 0),
                                    m.getPosY(),
                                    getColor(-15 * (int)posY, ArchWare.moduleManager.modules.size(), modules.indexOf(m)).hashCode());
                            posY += mc.fontRendererObj.FONT_HEIGHT + 1.5;
                        }
                        break;
                }
            }
        }

        if(additions.getSelectedValues().contains("Client version")){
            fontRenderer.drawStringWithShadow(ArchWare.CLIENT_VER.replace(String.valueOf(ArchWare.VERSION), "") + TextFormatting.GRAY + TextFormatting.BOLD + ArchWare.VERSION + TextFormatting.WHITE + " - " + ArchWare.login, sr.getScaledWidth() - fontRenderer.getStringWidth(ArchWare.CLIENT_VER.replace(String.valueOf(ArchWare.VERSION), "") + ArchWare.VERSION + " - " + ArchWare.login) - 1, sr.getScaledHeight() - 8, -1);
        }
        if(additions.getSelectedValues().contains("BPS")){
            if(additions.getSelectedValues().contains("Font")){
                fontRenderer.drawStringWithShadow("BPS: " + TextFormatting.GRAY + String.format("%.1f", Math.abs(Math.hypot(mc.player.posX - mc.player.prevPosX, mc.player.posZ - mc.player.prevPosZ)) * 15), 1,
                        new ScaledResolution(mc).getScaledHeight() -
                        (additions.getSelectedValues().contains("Coordinates") ? 19 : 9), -1);
            }else{
                mc.fontRendererObj.drawStringWithShadow("BPS: " + TextFormatting.GRAY + String.format("%.1f", Math.abs(Math.hypot(mc.player.posX - mc.player.prevPosX, mc.player.posZ - mc.player.prevPosZ)) * 15), 1,
                        new ScaledResolution(mc).getScaledHeight() -
                                (additions.getSelectedValues().contains("Coordinates") ? 22 : 11), -1);
            }
        }
        if(additions.getSelectedValues().contains("Coordinates")){
            if(additions.getSelectedValues().contains("Font")){
                fontRenderer.drawStringWithShadow("X: " + TextFormatting.GRAY + (int)mc.player.posX + TextFormatting.RESET
                + " Y: " + TextFormatting.GRAY + (int)mc.player.posY + TextFormatting.RESET
                + " Z: " + TextFormatting.GRAY + (int)mc.player.posZ + TextFormatting.RESET,
                        1, new ScaledResolution(mc).getScaledHeight() - 9, -1);
            }else{
                mc.fontRendererObj.drawStringWithShadow("X: " + TextFormatting.GRAY + (int)mc.player.posX + TextFormatting.RESET
                                + " Y: " + TextFormatting.GRAY + (int)mc.player.posY + TextFormatting.RESET
                                + " Z: " + TextFormatting.GRAY + (int)mc.player.posZ + TextFormatting.RESET,
                        1, new ScaledResolution(mc).getScaledHeight() - 11, -1);
            }
        }

    }

    private Color getColor(final int offset, final int count, final int index){
        if(color.getValueString().equals("Static")){
            return new Color(
                    (int)red.getValueNumeric(),
                    (int)green.getValueNumeric(),
                    (int)blue.getValueNumeric()
            );
        }else if(color.getValueString().equals("Pulsive")){
            return RenderUtil.fade(new Color(
                    (int)red.getValueNumeric(),
                    (int)green.getValueNumeric(),
                    (int)blue.getValueNumeric()
            ), index, count);
        }else if(color.getValueString().equals("Rainbow")){
            return RenderUtil.getRainbow(offset, (int)(rainbowSpeed.getValueNumeric()));
        }else if(color.getValueString().equals("Astolfo")){
            final double colorOffset = (Math.abs(((System.currentTimeMillis()) / 20D)) / 50) + (index / (mc.fontRendererObj.FONT_HEIGHT + 50D));
            return RenderUtil.getGradientOffset(Color.CYAN, Color.MAGENTA, colorOffset, 255);
        }
        return null;
    }
}