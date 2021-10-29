package fun.archware;

import fun.archware.base.altmanager.Alt;
import fun.archware.base.altmanager.AltManagerGUI;
import fun.archware.base.event.EventManager;
import fun.archware.base.skeetgui.ClickGUI;
import fun.archware.discord.DiscordRPC;
import fun.archware.impl.managers.*;
import fun.archware.impl.utils.ChatUtils;
import fun.archware.impl.utils.FontUtil;
import fun.archware.impl.utils.SpammerUtils;
import fun.archware.impl.utils.font.CustomFontRenderer;
import fun.archware.scriptapi.ScriptManager;
import fun.archware.viaversion.ViaMCP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 01.04.2021.
 */
public class ArchWare {

    public static final String CLIENT_NAME = "ArchWare";
    public static final int VERSION = 11021;
    public static final String CLIENT_VER = "Beta Release: " + VERSION;
    public static String login = "Arch user"; // Im too lazy to remove this shit
    public static int uid = 1337; // This too
    private static final String[] changelog = {TextFormatting.GREEN + "Matrix NoFall",
            TextFormatting.GREEN + "Matrix DamageReduce. This feature reducing your damage while u falling",
            TextFormatting.GREEN + "Added Astolfo color in HUD",
            TextFormatting.GREEN + "Fixed all ClickGUI's bugs"
    };
    public static ModuleManager moduleManager;
    public static CommandManager commandManager;
    public static EventManager eventManager = new EventManager();
    public static SettingsManager settingManager;
    public static final List<Alt> alts = new ArrayList<>();
    public static FriendManager friendManager;
    public static MacroManager macroManager;
    private static final CustomFontRenderer font = new CustomFontRenderer(new Font("Verdana", Font.PLAIN, 13), true, true);

    private static final AltManagerGUI altManagerGui = new AltManagerGUI();
    public static ClickGUI clickGUI;

    public static void load() throws IOException {
        commandManager = new CommandManager();
        settingManager = new SettingsManager();
        moduleManager = new ModuleManager();
        FileManager.init();
        new ScriptManager().initializeScripts();
        FileManager.initAlts();
        altManagerGui.initGui();
        friendManager = new FriendManager();
        new SpammerUtils().init();
        FontUtil.setupFontUtils();
        macroManager = new MacroManager();
        ViaMCP.getInstance().start();
        clickGUI = new ClickGUI();
        DiscordRPC.init();
    }

    public static void unload() throws IOException {
        System.out.println(CLIENT_NAME + " b" + CLIENT_VER + " Closing...");
        FileManager.update("config.arch");
    }

    public static void sendChatMessage(String message){
        ChatUtils.printChatMessage(TextFormatting.YELLOW + "[" + CLIENT_NAME + "]" + TextFormatting.GRAY + ": " + message);
    }

    public static void drawChangelog(){
        int posY = 12;
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        font.drawStringWithShadow("Changelog:" + TextFormatting.GRAY + " (" + CLIENT_VER + ")", 2, 3, -1);
        for(String string : changelog){
            font.drawStringWithShadow("- " + string, 6, posY, -1);
            posY += 9;
        }
    }
}
