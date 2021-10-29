package fun.archware.impl.commands;

import fun.archware.ArchWare;
import fun.archware.base.command.Command;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

public class AddMacro extends Command {
    public AddMacro() {
        super("AddMacro", new String[]{"addmacro", "macro", "+macro"});
    }

    @Override
    public void onCommand(String[] args) {
        if(args.length < 3){
            ArchWare.sendChatMessage(TextFormatting.RED + "Invalid syntax. (Example of usage: .+macro KEY /help)");
            return;
        }else if(args[1].length() != 1){
            ArchWare.sendChatMessage(TextFormatting.RED + "Invalid syntax. (Example of usage: .+macro KEY /help)");
            return;
        }
        StringBuilder command = new StringBuilder();
        for(int i = 2; i < args.length; i++){
            command.append(args[i]).append(" ");
        }
        ArchWare.macroManager.addMacro(Keyboard.getKeyIndex(args[1].toUpperCase()), command.toString());
        ArchWare.macroManager.update();
        ArchWare.sendChatMessage("Macros successfully binded");
    }
}
