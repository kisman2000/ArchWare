package fun.archware.impl.modules.combat;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventPreUpdate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;

public class Hitbox extends Module {
    private final NumericValue offset = new NumericValue("Offset", "HitBoxOffset", this, 1, 0.2f, 10f);
    public Hitbox() {
        super("Hitbox", Category.COMBAT);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        for(final Entity e : mc.world.loadedEntityList){
            if(!(e instanceof EntityItem) && e != mc.player){
                final float width = e.width;
                final float height = e.height;
                e.setEntityBoundingBox(new AxisAlignedBB(e.posX - width - offset.getValueNumeric(), e.posY, e.posZ + width + offset.getValueNumeric(), e.posX + width + offset.getValueNumeric(), e.posY + height + offset.getValueNumeric(), e.posZ - width - offset.getValueNumeric()));
            }
        }
    }
}
