package fun.archware.impl.modules.misc;

import fun.archware.ArchWare;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;

public class Macros extends Module {


    public Macros() {
        super("Macros", Category.MISC);
    }


    @Override
    public void onKey(int key) {
        ArchWare.macroManager.getMacroByKey(key).forEach(macro -> mc.player.sendChatMessage(macro));
        super.onKey(key);
    }
}
