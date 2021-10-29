/*
 * noom (c) 2021.
 */

package fun.archware.impl.commands;

import fun.archware.ArchWare;
import fun.archware.base.command.Command;
import fun.archware.base.module.Module;
import fun.archware.base.setting.Setting;

import static fun.archware.impl.utils.ChatUtils.printChatMessage;

/**
 * Created by 1 on 11.04.2021.
 */
public class CommandSetting extends Command {

    public CommandSetting() {
        super("CommandSetting", new String[]{"module"});
    }

    @Override
    public void onCommand(String[] args) {

        if(args.length == 4 && ArchWare.moduleManager.getModuleByName(args[1]) != null && args[0].equalsIgnoreCase("module")) {

            try{
                Module mod = ArchWare.moduleManager.getModuleByName(args[1]);
                Setting set = ArchWare.settingManager.getSettingByName(args[2]);
                if(set.isTypeNumeric()) {
                    ArchWare.settingManager.getSetting(mod, args[2]).setValueNumeric(Float.parseFloat(args[3]));
                } else if(set.isTypeBoolean()){
                    ArchWare.settingManager.getSetting(mod, args[2]).setValueBoolean(Boolean.parseBoolean(args[3]));
                } else if(set.isTypeString()){
                    ArchWare.settingManager.getSetting(mod, args[2]).setValueString(args[3]);
                }
            } catch(Exception e){

            }
        } else {

        }
    }
}