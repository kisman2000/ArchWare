package fun.archware.impl.modules.misc;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.SettingTextField;
import fun.archware.impl.events.EventUpdate;
import fun.archware.impl.utils.TimeUtil;

public class AntiAFK extends Module {
    private TimeUtil timer = new TimeUtil();
    public AntiAFK() {
        super("AntiAFK", Category.MISC);
    }
    private SettingTextField command = new SettingTextField("AntiAFK command", "antiafkcomm",this,"/stats","/stats");
    @EventTarget
    public void onUpdate(final EventUpdate event){
        if(timer.hasReached(65000)){
            mc.player.sendChatMessage(command.getText());
            timer.reset();
        }
    }
}
