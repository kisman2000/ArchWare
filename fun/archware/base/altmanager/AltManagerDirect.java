package fun.archware.base.altmanager;

import fun.archware.impl.utils.FontUtil;
import net.minecraft.client.gui.*;

import java.awt.*;
import java.io.IOException;

public class AltManagerDirect extends GuiScreen {
    GuiButton add;
    GuiTextField login;
    GuiTextField password;

    int mode = 0;


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        this.drawDefaultBackground();
        Gui.drawRect(sr.getScaledWidth() / 2 - 80, sr.getScaledHeight() / 2 - 100, sr.getScaledWidth() / 2 + 80, sr.getScaledHeight() / 2 + 70, new Color(0, 0, 0, .60f).hashCode());
        FontUtil.drawCenteredStringWithShadow(AltManagerGUI.status, sr.getScaledWidth() / 2 - 1, sr.getScaledHeight() / 2 - 95, -1);
        login.drawTextBox();
        password.drawTextBox();
        if(!login.isFocused() && login.getText().isEmpty()){
            FontUtil.drawStringWithShadow("Login", sr.getScaledWidth() / 2 - 55, sr.getScaledHeight() / 2 - 34, Color.DARK_GRAY.hashCode());
        }if(!password.isFocused() && password.getText().isEmpty()){
            FontUtil.drawStringWithShadow("Password", sr.getScaledWidth() / 2 - 55, sr.getScaledHeight() / 2 - 4, Color.DARK_GRAY.hashCode());
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.login.textboxKeyTyped(typedChar, keyCode);
        this.password.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void initGui() {
        AltManagerGUI.status = "Direct Login";
        ScaledResolution sr = new ScaledResolution(mc);
        add = new GuiButton(0, sr.getScaledWidth() / 2 - 60, sr.getScaledHeight() / 2 + 30, 120, 20, "Login");
        login = new GuiTextField(2, mc.fontRendererObj, sr.getScaledWidth() / 2 - 60, sr.getScaledHeight() / 2 - 40, 120, 20);
        password = new GuiTextField(2, mc.fontRendererObj, sr.getScaledWidth() / 2 - 60, sr.getScaledHeight() / 2 - 10, 120, 20);
        this.buttonList.add(add);
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 0){
            Thread authenticator = new AuthenticatorThread(login.getText(), password.getText());
            authenticator.start();
        }
        if(button.id == 1){
            mc.displayGuiScreen(new AltManagerGUI());
        }
        super.actionPerformed(button);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.login.mouseClicked(mouseX, mouseY, mouseButton);
        this.password.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void confirmClicked(boolean result, int id) {
        super.confirmClicked(result, id);
    }
}
