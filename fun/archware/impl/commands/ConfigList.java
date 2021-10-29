package fun.archware.impl.commands;

import fun.archware.impl.managers.FileManager;
import fun.archware.ArchWare;
import fun.archware.base.command.Command;
import net.minecraft.util.text.TextFormatting;

import java.io.File;

public class ConfigList extends Command {
    public ConfigList() {
        super("ConfigList", new String[]{"configlist", "configs"});
    }

    @Override
    public void onCommand(String[] args) {
        StringBuilder configs = new StringBuilder();
        for(File file : new File(FileManager.HOME.getAbsolutePath() + "\\configs").listFiles()){
            if(file.isFile()){
                configs.append(TextFormatting.YELLOW + "> " + TextFormatting.GREEN + file.getName() + "\n");
            }
        }

        if(configs.toString().isEmpty()){
            ArchWare.sendChatMessage("No configs found");
        }else{
            ArchWare.sendChatMessage("List of config files:\n" + configs.toString());
        }
    }
}
