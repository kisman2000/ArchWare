package fun.archware.impl.modules.render;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventRender2D;
import fun.archware.impl.modules.combat.KillAura;
import fun.archware.impl.utils.RenderUtil;
import fun.archware.impl.utils.font.CustomFontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

import java.awt.*;

/**
 * Created by 1 on 01.04.2021.
 */
public class TargetHUD extends Module {
    public TargetHUD() {
        super("TargetHUD", Category.RENDER);
    }
    private float healthWidth = 20;
    private static final CustomFontRenderer fontRenderer = new CustomFontRenderer(new Font("Arial", Font.BOLD, 15), true, true);
    private static final CustomFontRenderer fontRenderer1 = new CustomFontRenderer(new Font("Arial", Font.PLAIN, 11), true, true);
    @EventTarget
    public void onRender2D(final EventRender2D event){
        if(KillAura.target instanceof EntityPlayer){
            final ScaledResolution scaledResolution = new ScaledResolution(mc);
            final EntityPlayer target = (EntityPlayer) KillAura.target;
            RenderUtil.drawRect(scaledResolution.getScaledWidth() / 2 + 17.5, scaledResolution.getScaledHeight() / 2 + 42.5, target.getName().length() > 15 ? fontRenderer.getStringWidth(target.getName()) + 48 : 115, 45, new Color(31, 31, 31).hashCode());
            RenderUtil.drawRect(scaledResolution.getScaledWidth() / 2 + 20, scaledResolution.getScaledHeight() / 2 + 45, target.getName().length() > 15 ? fontRenderer.getStringWidth(target.getName()) + 43 : 110, 40, new Color(18, 18, 18).hashCode());
            mc.getTextureManager().bindTexture(mc.getConnection().getPlayerInfo(target.getName()).getLocationSkin());
            GL11.glColor4f(1, 1, 1, 1);
            Gui.drawScaledCustomSizeModalRect(scaledResolution.getScaledWidth() / 2 + 25, scaledResolution.getScaledHeight() / 2 + 50, 8.0F,8, 8, 8, 25, 25, 64.0F, 64.0F);
            RenderUtil.drawRect(scaledResolution.getScaledWidth() / 2 + 53, scaledResolution.getScaledHeight() / 2 + 59, 70, 2, new Color(27, 27, 27).hashCode());
            Color health = Color.GREEN;
            if(target.getHealth() >= 16){
                health = Color.GREEN;
            }else if(target.getHealth() >= 8 && target.getHealth() <= 16){
                health = Color.YELLOW;
            }else if(target.getHealth() > 0 && target.getHealth() <= 8){
                health = Color.RED;
            }
            RenderUtil.drawRect(scaledResolution.getScaledWidth() / 2 + 53, scaledResolution.getScaledHeight() / 2 + 59, (target.getHealth() / target.getMaxHealth()) * 70, 2, health.hashCode());
            fontRenderer1.drawString("Health: " + (int)target.getHealth() + " | Range: " + (int)mc.player.getDistanceToEntity(target), scaledResolution.getScaledWidth() / 2 + 53, scaledResolution.getScaledHeight() / 2 + 65, -1);
            fontRenderer.drawString(target.getName(), scaledResolution.getScaledWidth() / 2 + 53, scaledResolution.getScaledHeight() / 2 + 52, -1);
            int posX = scaledResolution.getScaledWidth() / 2 + 53;
            for(final ItemStack item : target.getArmorInventoryList()){
                glPushMatrix();
                glTranslated(posX, scaledResolution.getScaledHeight() / 2 + 69, 0);
                glScaled(0.8, 0.8, 0.8);
                mc.getRenderItem().renderItemIntoGUI(item, 0, 0);
                glPopMatrix();
                posX += 12;
            }
        }
    }
}
