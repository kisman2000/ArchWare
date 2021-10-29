package fun.archware.impl.modules.render;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.impl.events.EventRender3D;
import fun.archware.impl.utils.font.CustomFontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumHand;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class ESP extends Module {
    private final BooleanValue hunger = new BooleanValue("Hunger", "ESPHunger", this, false);
    private final BooleanValue health = new BooleanValue("Health", "ESPHealth", this, true);
    private final BooleanValue healthValue = new BooleanValue("Health value", "ESPHealth", this, true);
    private final BooleanValue box = new BooleanValue("Box", "ESPBox", this, true);
    private final BooleanValue tag = new BooleanValue("Tag", "ESPTag", this, true);
    private final BooleanValue currentItem = new BooleanValue("Show current item", "ESPCurrentItem", this, false);
    private final BooleanValue local = new BooleanValue("Local", "ESPLocal", this, false);
    private final CustomFontRenderer font = new CustomFontRenderer(new Font("Verdana", Font.PLAIN, 45), false, true);
    public ESP() {
        super("2DESP", Category.RENDER);
    }

    @EventTarget
    public void onRender(final EventRender3D event) {
        for(final Entity e : mc.world.loadedEntityList){
            if(e instanceof EntityPlayer){
                if(e == mc.player){
                    if(!local.getValueBoolean()) continue;
                    else{
                        if(mc.gameSettings.thirdPersonView != 1) continue;
                    }
                }
                final double x = (e.lastTickPosX + (e.posX - e.lastTickPosX) * event.getPartialTicks()) - mc.getRenderManager().renderPosX;
                final double y = (e.lastTickPosY + (e.posY - e.lastTickPosY) * event.getPartialTicks()) - mc.getRenderManager().renderPosY;
                final double z = (e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * event.getPartialTicks()) - mc.getRenderManager().renderPosZ;
                glPushMatrix();
                glLineWidth(1.3f);
                glTranslated(x, y, z);
                glDisable(GL_TEXTURE_2D);
                glDisable(GL_DEPTH_TEST);
                glRotated(-mc.getRenderManager().playerViewY, 0, 1, 0);
                //glRotated(mc.getRenderManager().playerViewX, 1, 0, 0);
                if(box.getValueBoolean()){
                    glColor4d(0.8, 0.8, 0.8, 255);
                    glBegin(GL_LINE_STRIP);
                    glVertex3d(0.55, -0.2, 0);
                    glVertex3d(0.55, e.height + 0.2, 0);
                    glVertex3d(e.width - 1.15, e.height + 0.2, 0);
                    glVertex3d(e.width - 1.15, -0.2, 0);
                    glVertex3d(0.55, -0.2, 0);
                    glEnd();
                }
                if(health.getValueBoolean()){

                    Color health = Color.GREEN.darker();
                    if(((EntityPlayer)e).getHealth() >= 16){
                        health = Color.GREEN.darker();
                    }else if(((EntityPlayer)e).getHealth() >= 8 && ((EntityPlayer)e).getHealth() <= 16){
                        health = Color.YELLOW;
                    }else if(((EntityPlayer)e).getHealth() > 0 && ((EntityPlayer)e).getHealth() <= 8){
                        health = Color.RED;
                    }
                    glBegin(GL_LINE_STRIP);
                    glColor4d(1, 1, 1, 1);
                    glVertex3d(0.6, -0.2, 0);
                    glVertex3d(0.6, e.height + 0.2, 0);
                    glEnd();
                    glBegin(GL_LINE_STRIP);
                    glColor4d(health.getRed() / 255f, health.getGreen() / 255f, health.getBlue() / 255f, health.getAlpha() / 255f);
                    glVertex3d(0.6, -0.2, 0);
                    glVertex3d(0.6, (((EntityLivingBase) e).getHealth() / ((EntityLivingBase) e).getMaxHealth()) * (e.height + 0.2), 0);
                    glVertex3d(0.6, -0.2, 0);


                    glEnd();
                }if(hunger.getValueBoolean()){
                    glBegin(GL_LINE_STRIP);
                    glVertex3d(e.width - 1.20, e.height + 0.2, 0);
                    glVertex3d(e.width - 1.20, -0.2, 0);
                    glColor4d(Color.ORANGE.getRed(), Color.ORANGE.getGreen(), Color.ORANGE.getBlue(), 255);
                    glVertex3d(e.width - 1.20, e.height + 0.2, 0);
                    glVertex3d(e.width - 1.20, -0.2, 0);
                    glColor4d(255, 255, 255, 255);
                    glEnd();
                }
                final float size = 0.013f;
                glScaled(-size, -size, -size);
                if(tag.getValueBoolean()){
                    glEnable(GL_TEXTURE_2D);
                    mc.fontRendererObj.drawStringWithShadow(e.getName(), 1 - (mc.fontRendererObj.getStringWidth(e.getName()) / 2), -170, -1);
                    glDisable(GL_TEXTURE_2D);
                }if(healthValue.getValueBoolean() && health.getValueBoolean()){
                    glEnable(GL_TEXTURE_2D);
                    mc.fontRendererObj.drawStringWithShadow(String.valueOf((int)((((EntityPlayer)e).getHealth() / ((EntityPlayer)e).getMaxHealth()) * 100)), -50 - mc.fontRendererObj.getStringWidth(String.valueOf((int)((((EntityPlayer)e).getHealth() / ((EntityPlayer)e).getMaxHealth()) * 100))), (int)((((EntityLivingBase) e).getHealth() / ((EntityLivingBase) e).getMaxHealth()) * (e.height + 0.2)), -1);
                    glDisable(GL_TEXTURE_2D);
                }if(currentItem.getValueBoolean()){
                    if(!(((EntityPlayer)e).getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock) && !(((EntityPlayer)e).getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemAir)){
                        glEnable(GL_TEXTURE_2D);
                        mc.fontRendererObj.drawStringWithShadow(((EntityPlayer)e).getHeldItem(EnumHand.MAIN_HAND).getDisplayName(),
                                1 - (mc.fontRendererObj.getStringWidth(((EntityPlayer)e).getHeldItem(EnumHand.MAIN_HAND).getDisplayName()) / 2), 20, -1);
                        glDisable(GL_TEXTURE_2D);
                    }
                }
                glEnable(GL_DEPTH_TEST);
                glEnable(GL_TEXTURE_2D);
                glPopMatrix();
            }
        }
    }
}
