/*
 * Created with :heart: by katch.
 * (c) 4.25.2021
 */

package fun.archware.impl.modules.render;

import fun.archware.impl.utils.FontUtil;
import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.base.setting.StringValue;
import fun.archware.impl.events.EventRender3D;
import fun.archware.impl.utils.font.CustomFontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;


public class NameTags extends Module {
    private final StringValue tagMode = new StringValue("Tag", "NameTagsTagMode", this, "Simple", new String[]{"Simple", "Box"});
    private final BooleanValue items = new BooleanValue("Items", "NameTagsItems", this, true);
    private final CustomFontRenderer fontRenderer = new CustomFontRenderer(new Font("Arial", Font.BOLD, 1), true, true);
    public NameTags() {
        super("NameTags", Category.RENDER);
    }

    @EventTarget
    public void onRender(final EventRender3D event){
        for(Entity e : mc.world.loadedEntityList){
            if(e instanceof EntityPlayer && e != mc.player){
                double x = (e.lastTickPosX + (e.posX - e.lastTickPosX) * event.getPartialTicks()) - mc.getRenderManager().renderPosX;
                double y = (e.lastTickPosY + (e.posY - e.lastTickPosY) * event.getPartialTicks()) - mc.getRenderManager().renderPosY;
                double z = (e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * event.getPartialTicks()) - mc.getRenderManager().renderPosZ;
                glPushMatrix();
                glDisable(GL_DEPTH_TEST);
                glDisable(GL_TEXTURE_2D);
                glNormal3f(0, 1, 0);
                GlStateManager.disableLighting();
                GlStateManager.enableBlend();
                float size = Math.min(Math.max(1.2f * (mc.player.getDistanceToEntity(e) * 0.15f), 1.25f), 6f) * (0.015f);
                glTranslatef((float)(x), (float)(y) + e.height + 0.6f, (float)(z));
                GlStateManager.glNormal3f(0, 1, 0);
                GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0, 1, 0);
                GlStateManager.rotate(mc.getRenderManager().playerViewX, 1, 0, 0);
                glScalef(-size, -size, -size);
                if(fontRenderer.getFont().getSize() != 11){
                    fontRenderer.setFont(new Font("Arial", Font.BOLD, 11));
                }
                int health = (int)((((EntityPlayer)e).getHealth() / ((EntityPlayer)e).getMaxHealth()) * 100);
                Gui.drawRect((-mc.fontRendererObj.getStringWidth(e.getName() + " " + health + "%") / 2) - 2, -2, (mc.fontRendererObj.getStringWidth(e.getName()) / 2) + 16, 10, Integer.MIN_VALUE);
                mc.fontRendererObj.drawCenteredString(e.getName() + " " + TextFormatting.GREEN + health + "%", 0, 1, -1);
                int posX = (-mc.fontRendererObj.getStringWidth(e.getName()) / 2) - 8;
                if(Item.getIdFromItem(((EntityPlayer) e).inventory.getCurrentItem().getItem()) != 0){
                    mc.getRenderItem().zLevel = -100.0f;
                    mc.getRenderItem().renderItemIntoGUI(new ItemStack(((EntityPlayer) e).inventory.getCurrentItem().getItem()), posX - 2, -20);
                    mc.getRenderItem().zLevel = 0.0f;
                    int posY = -30;
                    final Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(((EntityPlayer) e).inventory.getCurrentItem());
                    for(final Enchantment enchantment : enchantments.keySet()){
                        final int level = EnchantmentHelper.getEnchantmentLevel(enchantment, ((EntityPlayer) e).inventory.getCurrentItem());
                        FontUtil.drawCenteredStringWithShadow(String.valueOf(enchantment.getName().substring(12).charAt(0)).toUpperCase() + level, posX + 6, posY, -1);
                        posY -= 12;

                    }


                    posX += 15;
                }
                for(final ItemStack item : e.getArmorInventoryList()){
                    mc.getRenderItem().zLevel = -100.0f;
                    mc.getRenderItem().renderItemIntoGUI(new ItemStack(item.getItem()), posX, -20);
                    mc.getRenderItem().zLevel = 0.0f;
                    int posY = -30;
                    final Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(item);
                    for(final Enchantment enchantment : enchantments.keySet()){
                        final int level = EnchantmentHelper.getEnchantmentLevel(enchantment, item);
                        FontUtil.drawCenteredStringWithShadow(String.valueOf(enchantment.getName().substring(12).charAt(0)).toUpperCase() + level, posX + 9, posY, -1);
                        posY -= 12;
                    }
                    posX += 17;
                }
                int gapples = 0;
                //322
                if(Item.getIdFromItem(((EntityPlayer) e).inventory.getCurrentItem().getItem()) == 322){
                    gapples = ((EntityPlayer) e).inventory.getCurrentItem().stackSize;
                }else if(Item.getIdFromItem(((EntityPlayer) e).getHeldItemOffhand().getItem()) == 322){
                    gapples = ((EntityPlayer) e).getHeldItemOffhand().stackSize;
                }
                if(gapples > 0){
                    mc.getRenderItem().zLevel = -100.0f;
                    mc.getRenderItem().renderItemIntoGUI(new ItemStack(Items.GOLDEN_APPLE), posX, -20);
                    mc.getRenderItem().zLevel = 0.0f;
                    FontUtil.drawCenteredStringWithShadow(String.valueOf(gapples), posX + 9, -30, -1);
                }
                glEnable(GL_DEPTH_TEST);
                glPopMatrix();
            }
        }
    }
}
