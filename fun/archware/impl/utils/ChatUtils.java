/*
 * noom (c) 2021.
 */

package fun.archware.impl.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

/**
 * Created by 1 on 03.04.2021.
 */
public class ChatUtils{
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static TextComponentString textComponentStringFromText(String text){
        return new TextComponentString(text);
    }

    public static void printChatMessage(TextComponentString message){
        mc.ingameGUI.getChatGUI().printChatMessage(message);
    }

    public static void printChatMessage(String message){
        mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(message));
    }
}
