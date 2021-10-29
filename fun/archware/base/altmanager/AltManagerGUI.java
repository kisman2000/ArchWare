package fun.archware.base.altmanager;

import fun.archware.impl.utils.RenderUtil;
import fun.archware.ArchWare;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;

/**
 * Created by 1 on 01.04.2021.
 */
public class AltManagerGUI extends GuiScreen {
    Alt selectedAlt;
    public static String status;

    public void addButtons(){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        this.buttonList.add(new GuiButton(0, sr.getScaledWidth() / 2 - 78 - 80, sr.getScaledHeight() - 40, 70, 20, "Login"));
        this.buttonList.add(new GuiButton(1, sr.getScaledWidth() / 2 - 78, sr.getScaledHeight() - 40, 70, 20, "Direct Login"));
        this.buttonList.add(new GuiButton(2, sr.getScaledWidth() / 2 - 78 + 80, sr.getScaledHeight() - 40, 70, 20, "Add"));
        this.buttonList.add(new GuiButton(3, sr.getScaledWidth() / 2 - 78 + 160, sr.getScaledHeight() - 40, 70, 20, "Delete"));
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }



    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        final ScaledResolution sr = new ScaledResolution(mc);
        this.drawDefaultBackground();
        int posY = 70;
        for(final Alt alt : ArchWare.alts){
            RenderUtil.drawRect(sr.getScaledWidth() / 2 - 110, posY, 215, 35, new Color(0, 0, 0, 135).hashCode());
            mc.fontRendererObj.drawString(alt.name, sr.getScaledWidth() / 2 - 60, posY + 5, -1);
            mc.fontRendererObj.drawString(getPassword(alt.password), sr.getScaledWidth() / 2 - 60, posY + 15, -1);
            posY += 45;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public String getPassword(String pass){
        final StringBuilder stringBuilder = new StringBuilder();
        if(pass.length() >= 5){
            stringBuilder.append(pass, 0, 4);
            for(int i = 4; i < pass.length(); ++i){
                if(i == 16){
                    stringBuilder.append("...");
                    break;
                }else{
                    stringBuilder.append("*");
                }
            }
        }else{
            stringBuilder.append(pass);
        }
        return stringBuilder.toString();
    }

    @Override
    public void initGui() {
        status = ArchWare.CLIENT_NAME + " Alt manager";
        selectedAlt = null;
        addButtons();
    }

    @Override
    public void confirmClicked(boolean result, int id) {
        super.confirmClicked(result, id);
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 3){
         if(selectedAlt != null){
             ArchWare.alts.remove(selectedAlt);
         }
        }if(button.id == 2){
            mc.displayGuiScreen(new AltManagerCreateAltGui());
        }if(button.id == 1){
            mc.displayGuiScreen(new AltManagerDirect());
        }if(button.id == 0){
            if(selectedAlt != null){
                Thread authenticator = new AuthenticatorThread(selectedAlt.getName(), selectedAlt.getPassword());
                authenticator.start();
            }
        }
    }

    public boolean isHovered(int mouseX, int mouseY, int posX, int posY, int width, int height){
        return mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height;
    }
}
