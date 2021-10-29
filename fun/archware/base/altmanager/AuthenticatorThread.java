package fun.archware.base.altmanager;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraft.util.text.TextFormatting;

import java.net.Proxy;

public class AuthenticatorThread extends Thread {
    private String username;
    private String password;

    public AuthenticatorThread(String username, String password){
        this.username = username;
        this.password = password;
    }

    private Session loadSession(){
        YggdrasilAuthenticationService yggdrasilAuthenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication yggdrasilUserAuthentication = (YggdrasilUserAuthentication)yggdrasilAuthenticationService.createUserAuthentication(Agent.MINECRAFT);
        yggdrasilUserAuthentication.setUsername(username);
        yggdrasilUserAuthentication.setPassword(password);
        System.out.println(username + ":" + password);
        try {
            yggdrasilUserAuthentication.logIn();
            return new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang");
        } catch (AuthenticationException e) {
            return null;
        }
    }

    @Override
    public void run(){
        AltManagerGUI.status = TextFormatting.YELLOW + "Authenticating...";
        if(password.isEmpty()){
            Minecraft.getMinecraft().setSession(new Session(username, "", "", "mojang"));
            AltManagerGUI.status = TextFormatting.GREEN + "Logged in ~ Cracked";
        }else{
            Session session = loadSession();
            if(session == null){
                AltManagerGUI.status = TextFormatting.RED + "Invalid login";
                return;
            }

            Minecraft.getMinecraft().setSession(session);
            AltManagerGUI.status = TextFormatting.GREEN + "Logged in ~ " + session.getUsername();
        }
    }
}
