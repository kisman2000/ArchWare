package fun.archware.impl.modules.render;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventRender2D;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static org.lwjgl.opengl.GL11.*;

public class ArmorHUD extends Module {
    public ArmorHUD() {
        super("ArmorHUD", Category.RENDER);
    }

    @EventTarget
    public void onRender(final EventRender2D event){
        final ScaledResolution sr = new ScaledResolution(mc);
        int posX = sr.getScaledWidth() / 2 + 56;
        for(final ItemStack item : mc.player.getArmorInventoryList()){
            if(Item.getIdFromItem(item.getItem()) != 0){
                final String durability = GuiScreen.func_191927_a(item)
                        .stream()
                        .filter(str -> str.contains("Durability"))
                        .findFirst()
                        .orElse(null);
                glPushMatrix();
                glTranslatef(posX, sr.getScaledHeight() - 57, 0);
                glScaled(1, 1, 0);
                mc.getRenderItem().renderItemIntoGUI(item, 0, 0);
                if(durability != null){
                    glScaled(0.7, 0.7, 0);
                    mc.fontRendererObj.drawCenteredStringWithShadow(durability.split(" ")[1], 12, -11, -1);

                }
                glPopMatrix();
                posX -= 16;
            }
        }
    }
}
