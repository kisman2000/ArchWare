package fun.archware.impl.commands;

import fun.archware.ArchWare;
import fun.archware.base.command.Command;
import fun.archware.base.module.Module;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {
    public Bind() {
        super("Bind", new String[]{"bind", "b"});
    }

    @Override
    public void onCommand(String[] args) {
        for(Module m : ArchWare.moduleManager.modules){
            if(args.length >= 2){
                if(m.getName().equalsIgnoreCase(args[1])){
                    if(args[2].equalsIgnoreCase("0")){
                        m.setKey(0);
                        ArchWare.sendChatMessage(TextFormatting.GREEN + m.getName() + " successfully unbinded");
                    }else{
                        m.setKey(Keyboard.getKeyIndex(args[2].toUpperCase()));
                        ArchWare.sendChatMessage(TextFormatting.GREEN + m.getName() + " successfully binded to " + args[2].toUpperCase());
                    }
                    return;
                }
            }else{
                ArchWare.sendChatMessage(TextFormatting.RED + "Invalid syntax (.bind MODULE_NAME KEY)");
            }
        }
        ArchWare.sendChatMessage(TextFormatting.RED + "Module not found");
    }
}
