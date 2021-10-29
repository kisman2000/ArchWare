/*
 * Created with :heart: by katch.
 * (c) 4.23.2021
 */

package fun.archware.impl.modules.misc;

import fun.archware.ArchWare;
import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventMiddleClick;
import net.minecraft.entity.player.EntityPlayer;

public class MCF extends Module {
    public MCF() {
        super("MCF", Category.MISC);
    }

    @EventTarget
    public void onMiddleClick(final EventMiddleClick event){
        if(mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityPlayer){
            if(ArchWare.friendManager.isFriend(mc.objectMouseOver.entityHit)){
                ArchWare.friendManager.removeFriend(mc.objectMouseOver.entityHit);
                ArchWare.sendChatMessage(mc.objectMouseOver.entityHit.getName() + " successfully removed from your friend list");
            }else{
                ArchWare.friendManager.addFriend(mc.objectMouseOver.entityHit);
                ArchWare.sendChatMessage(mc.objectMouseOver.entityHit.getName() + " successfully added to your friend list");
            }
        }
    }

}
