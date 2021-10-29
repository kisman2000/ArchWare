package fun.archware.impl.modules.misc;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.base.setting.NumericValue;
import fun.archware.base.setting.StringValue;
import fun.archware.impl.events.EventPreUpdate;
import fun.archware.impl.utils.TimeUtil;

public class AntiAim extends Module {
    StringValue pitch = new StringValue("Pitch", "AntiAimPitch", this, "down", new String[]{"Down", "Up", "Zero", "Fake down","Fake up"});
    StringValue yawmode = new StringValue("Yaw", "AntiAimYaw", this, "backwards", new String[]{"backwards", "local view", "forward"});
    NumericValue yawAdd = new NumericValue("Yaw add","addyaw",this, 0 ,-180,180);

    StringValue YawModifier = new StringValue("Yaw modifier", "AntiAimyawmod", this, "none", new String[]{"none", "jitter"});
    NumericValue yawJitter = new NumericValue("Jitter range","yawjitter",this, 0 ,0,180);
    BooleanValue local = new BooleanValue("Local","ssLocal",this,true);
    private boolean kek;

    private TimeUtil timer = new TimeUtil();
    public AntiAim() {
        super("AntiAim", Category.MISC);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        switch(pitch.getValueString()){
            case "down":
                mc.player.rotationPitchHead = 90;
                break;
            case "up":
                mc.player.rotationPitchHead = -90;
                break;
            case "zero":
                mc.player.rotationPitchHead = 0;
                break;
            case "fake down":
                mc.player.rotationPitchHead = 360;
                break;
            case "fake up":
                mc.player.rotationPitchHead = -360;
                break;
        }
        switch(yawmode.getValueString()){
            case "backwards":
                mc.player.rotationYawHead = mc.player.rotationYaw-180;
                mc.player.renderYawOffset = mc.player.rotationYaw-180;
                break;
            case "local view":
                mc.player.rotationYawHead = mc.player.rotationYaw;
                mc.player.renderYawOffset = mc.player.rotationYaw;
                break;
        }
        switch(YawModifier.getValueString()){
            case "jitter":
                float niggerz = yawJitter.getValueNumeric();
                mc.player.rotationYawHead = mc.player.rotationYaw + (kek ? -niggerz - niggerz : niggerz);
                mc.player.renderYawOffset = mc.player.rotationYaw + (kek ? -niggerz - niggerz : niggerz);
                kek = !kek;
                break;
        }
        mc.player.rotationYawHead += yawAdd.getValueNumeric();
        mc.player.renderYawOffset += yawAdd.getValueNumeric();

    }
}
