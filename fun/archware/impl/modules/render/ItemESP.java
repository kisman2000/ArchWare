/*
 * Created with :heart: by katch.
 * (c) 4.25.2021
 */

package fun.archware.impl.modules.render;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.base.setting.StringValue;
import fun.archware.impl.events.EventRender3D;
import fun.archware.impl.utils.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class ItemESP extends Module {
    private final StringValue mode = new StringValue("Mode", "ItemESPMode", this, "Box", new String[]{"Box", "Corners"});
    private final BooleanValue tag = new BooleanValue("Tag", "ItemESPTag", this, true);
    public ItemESP() {
        super("ItemESP", Category.RENDER);
    }

    @EventTarget
    public void onRender(final EventRender3D event){
        setSuffix(mode.getValueString());
        for(Entity e : mc.world.loadedEntityList){
            if(e instanceof EntityItem){

                double x = (e.lastTickPosX + (e.posX - e.lastTickPosX) * event.getPartialTicks()) - mc.getRenderManager().renderPosX - 0.1;
                double y = (e.lastTickPosY + (e.posY - e.lastTickPosY) * event.getPartialTicks()) - mc.getRenderManager().renderPosY;
                double z = (e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * event.getPartialTicks()) - mc.getRenderManager().renderPosZ - 0.15;
                if(tag.getValueBoolean()){
                    glPushMatrix();
                    glDisable(GL_DEPTH_TEST);
                    glDisable(GL_TEXTURE_2D);
                    glNormal3f(0, 1, 0);
                    GlStateManager.disableLighting();
                    GlStateManager.enableBlend();
                    float size = Math.min(Math.max(1.2f * (mc.player.getDistanceToEntity(e) * 0.15f), 1.25f), 6f) * (0.014f);
                    glTranslatef((float)(x), (float)(y) + e.height + 0.5f, (float)(z));
                    GlStateManager.glNormal3f(0, 1, 0);
                    GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0, 1, 0);
                    GlStateManager.rotate(mc.getRenderManager().playerViewX, 1, 0, 0);
                    glScalef(-size, -size, size);
                    Gui.drawRect((-mc.fontRendererObj.getStringWidth(((EntityItem)e).getEntityItem().getDisplayName()) / 2) - 2, -2, (mc.fontRendererObj.getStringWidth(((EntityItem)e).getEntityItem().getDisplayName()) / 2) + 2, 9, Integer.MIN_VALUE);
                    mc.fontRendererObj.drawString(((EntityItem)e).getEntityItem().getDisplayName(), -mc.fontRendererObj.getStringWidth(((EntityItem)e).getEntityItem().getDisplayName()) / 2, 0, -1);
                    glEnable(GL_TEXTURE_2D);
                    glEnable(GL_DEPTH_TEST);
                    glPopMatrix();
                }
                switch(mode.getValueString()){
                    case "Box":
                        RenderUtil.drawOutline(new AxisAlignedBB(x, y, z, x + 0.5, y + 0.5, z + 0.5), 1.6f, RenderUtil.rainbow(2500));
                        break;
                    case "Corners":
                        glPushMatrix();
                        glDisable(GL_DEPTH_TEST);
                        glDisable(GL_TEXTURE_2D);
                        glTranslated(x + 0.1, y, z);
                        glNormal3f(0, 1, 0);
                        glRotatef(-mc.getRenderManager().playerViewY, 0, 1, 0);
                        glRotatef(mc.getRenderManager().playerViewX, 1, 0, 0);
                        glLineWidth(3);
                        glColor3f(0, 0, 0);
                        glBegin(GL_LINE_STRIP);
                        glVertex3d(e.width, 0.1, 0);
                        glVertex3d(e.width, -0.1, 0);
                        glVertex3d(0.15, -0.1, 0);
                        glEnd();
                        glBegin(GL_LINE_STRIP);
                        glVertex3d(-e.width, 0.1, 0);
                        glVertex3d(-e.width, -0.1, 0);
                        glVertex3d(-0.15, -0.1, 0);
                        glEnd();
                        glBegin(GL_LINE_STRIP);
                        glVertex3d(0.15, e.height + 0.1, 0);
                        glVertex3d(e.width, e.height + 0.1, 0);
                        glVertex3d(e.width, e.height - 0.1, 0);
                        glEnd();
                        glBegin(GL_LINE_STRIP);
                        glVertex3d(-0.15, e.height + 0.1, 0);
                        glVertex3d(-e.width, e.height + 0.1, 0);
                        glVertex3d(-e.width, e.height - 0.1, 0);
                        glEnd();
                        glLineWidth(1);
                        glColor3f(Color.CYAN.darker().getRed(), Color.CYAN.darker().getGreen(), Color.CYAN.darker().getBlue());
                        glBegin(GL_LINE_STRIP);
                        glVertex3d(e.width, 0.1, 0);
                        glVertex3d(e.width, -0.1, 0);
                        glVertex3d(0.15, -0.1, 0);
                        glEnd();
                        glBegin(GL_LINE_STRIP);
                        glVertex3d(-e.width, 0.1, 0);
                        glVertex3d(-e.width, -0.1, 0);
                        glVertex3d(-0.15, -0.1, 0);
                        glEnd();
                        glBegin(GL_LINE_STRIP);
                        glVertex3d(0.15, e.height + 0.1, 0);
                        glVertex3d(e.width, e.height + 0.1, 0);
                        glVertex3d(e.width, e.height - 0.1, 0);
                        glEnd();
                        glBegin(GL_LINE_STRIP);
                        glVertex3d(-0.15, e.height + 0.1, 0);
                        glVertex3d(-e.width, e.height + 0.1, 0);
                        glVertex3d(-e.width, e.height - 0.1, 0);
                        glEnd();
                        glColor3f(1, 1, 1);
                        glDisable(GL_STENCIL_TEST);
                        glEnable(GL_DEPTH_TEST);
                        glEnable(GL_TEXTURE_2D);
                        glPopMatrix();
                        break;
                }
            }
        }
    }
}
