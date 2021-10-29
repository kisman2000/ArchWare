/*
 * noom (c) 2021.
 */

package fun.archware.impl.commands;

import fun.archware.ArchWare;
import fun.archware.base.command.Command;

import static fun.archware.impl.utils.ChatUtils.printChatMessage;

/**
 * Created by 1 on 11.04.2021.
 */
public class ModuleToggle extends Command {

    public ModuleToggle() {
        super("ModuleToggle", new String[]{"t", "toggle"});
    }

    @Override
    public void onCommand(String[] args) {

        if(args.length == 2 && args[0].equalsIgnoreCase("t") || args.length == 2 && args[0].equalsIgnoreCase("toggle")) {
            try{
                ArchWare.moduleManager.getModuleByName(args[1]).toggle();
            } catch(Exception e){
                printChatMessage("Wrong usage!!!");
            }
        } else {
            printChatMessage("Wrong usage!!!");
        }
    }
}