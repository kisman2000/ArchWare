/*
 * noom (c) 2021.
 */

package fun.archware.impl.commands;

import fun.archware.base.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;

import static fun.archware.impl.utils.ChatUtils.printChatMessage;

/**
 * Created by 1 on 02.04.2021.
 */
public class VClip extends Command {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public VClip() {
        super("Vclip", new String[]{"vclip", "hclip"});
    }

    @Override
    public void onCommand(String[] args) {
        if(args.length == 2 && args[0].equalsIgnoreCase("vclip")) {
            try {
                printChatMessage("Clipped " + Double.valueOf(args[1]) + " blocks!");
                mc.player.setPosition(mc.player.posX, mc.player.posY + Double.valueOf(args[1]), mc.player.posZ);
                mc.getConnection().sendPacket(new CPacketPlayer(true));
            } catch(Exception formatException){
                printChatMessage("Wrong usage!!!");
                formatException.printStackTrace();
            }
        } else if(args.length == 2 && args[0].equalsIgnoreCase("hclip")){
            try {
                printChatMessage("Clipped " + Double.valueOf(args[1]) + " blocks!");
                float f = mc.player.rotationYaw * 0.017453292F;
                double speed = Double.valueOf(args[1]);
                double x = -(MathHelper.sin(f) * speed);
                double z = MathHelper.cos(f) * speed;
                mc.player.setPosition(mc.player.posX + x, mc.player.posY, mc.player.posZ + z);
            } catch(Exception formatException){
                printChatMessage("Wrong usage!!!");
                formatException.printStackTrace();
            }
        }
    }
}
