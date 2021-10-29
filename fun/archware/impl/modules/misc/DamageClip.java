package fun.archware.impl.modules.misc;

import fun.archware.ArchWare;
import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventPreUpdate;

public class DamageClip extends Module {
    private final BooleanValue autoFreecam = new BooleanValue("Enable FreeCam", "DamageClipFreecam", this, true);
    private final NumericValue position = new NumericValue("Position", "DamageClipPosition",this,0,-100,100);
    private final NumericValue hurtTime = new NumericValue("HurtTime", "DamageClipHurtTime",this,4,0,12);

    public DamageClip() {
        super("DamageClip", Category.MISC);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        if(mc.player.hurtTime >= hurtTime.getValueNumeric()){
            mc.player.setPosition(mc.player.posX, position.getValueNumeric(), mc.player.posZ);
            if(autoFreecam.getValueBoolean() && !ArchWare.moduleManager.getModuleByClass(Freecam.class).isToggled()){
                ArchWare.moduleManager.getModuleByClass(Freecam.class).toggle();
            }
        }
    }
}
