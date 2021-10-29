/*
 * noom (c) 2021.
 */

package fun.archware.impl.managers;

import fun.archware.base.command.Command;
import fun.archware.impl.commands.*;
import java.util.ArrayList;
/**
 * Created by 1 on 02.04.2021.
 */
public class CommandManager {

    public final ArrayList<Command> commands = new ArrayList<>();

    public CommandManager() {
        commands.add(new VClip());
        commands.add(new ModuleToggle());
        commands.add(new CommandSetting());
        commands.add(new ConfigList());
        commands.add(new ConfigLoad());
        commands.add(new Bind());
        commands.add(new AddMacro());
        commands.add(new RemoveMacro());
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}
