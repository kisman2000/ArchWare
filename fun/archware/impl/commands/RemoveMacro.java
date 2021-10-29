package fun.archware.impl.commands;

import fun.archware.ArchWare;
import fun.archware.base.command.Command;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

public class RemoveMacro extends Command {
    public RemoveMacro() {
        super("RemoveMacro", new String[]{"removemacro", "-macro"});
    }

    @Override
    public void onCommand(String[] args) {
        if(args.length < 2){
            ArchWare.sendChatMessage(TextFormatting.RED + "Invalid syntax. (Example of usage: .-macro key)");
            return;
        }
        ArchWare.macroManager.removeMacro(Keyboard.getKeyIndex(args[1].toUpperCase()));
        ArchWare.macroManager.update();
        ArchWare.sendChatMessage(TextFormatting.GREEN + "Macros successfully removed!");
    }
}
