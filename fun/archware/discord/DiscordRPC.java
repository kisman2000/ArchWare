/*
 * Created with :heart: by katch.
 * (c) 4.29.2021
 */

package fun.archware.discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import fun.archware.ArchWare;
import net.minecraft.client.Minecraft;

public class DiscordRPC {
    private static Minecraft mc = Minecraft.getMinecraft();
    public static void init(){
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> {
           
        };
        club.minnced.discord.rpc.DiscordRPC discordRPC = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
        discordRPC.Discord_Initialize("849671345654464533", handlers, false, null);
        DiscordRichPresence discordRichPresence = new DiscordRichPresence();
        discordRichPresence.largeImageKey = "logo";
        discordRichPresence.smallImageKey = "logo";

        discordRichPresence.largeImageText = "Using " + ArchWare.CLIENT_VER + " version";
        new Thread(() -> {
            while(true){
                discordRPC.Discord_UpdatePresence(discordRichPresence);
                if(mc.world != null){
                    if(mc.isSingleplayer()){
                        discordRichPresence.smallImageText = "Playing singleplayer";
                    }else{
                        discordRichPresence.smallImageText = "Playing on " + mc.getCurrentServerData().serverIP;
                    }
                }else{
                    discordRichPresence.smallImageText = "AFK";
                }
                discordRPC.Discord_RunCallbacks();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println("Discord RPC initialized");

    }
}
