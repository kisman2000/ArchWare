package fun.archware.impl.modules.render;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventRender2D;
import fun.archware.impl.utils.font.CustomFontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;

public class PotionHUD extends Module {
    private final CustomFontRenderer font = new CustomFontRenderer(new Font("Verdana", Font.PLAIN, 17), true, true);
    public PotionHUD() {
        super("PotionHUD", Category.RENDER);
    }

    @EventTarget
    public void onRender(final EventRender2D event){
        final ScaledResolution sr = new ScaledResolution(mc);
        int posY = sr.getScaledHeight() - 20;
        for(final PotionEffect potionEffect : mc.player.getActivePotionEffects()){
            String name = I18n.format(potionEffect.getPotion().getName());
            switch(potionEffect.getAmplifier()){
                case 1:
                    name += " " + TextFormatting.GRAY + I18n.format("enchantment.level.2");
                    break;

                case 2:
                    name += " " + TextFormatting.GRAY + I18n.format("enchantment.level.3");
                    break;

                case 3:
                    name += " " + TextFormatting.GRAY + I18n.format("enchantment.level.4");
                    break;
            }
            name += TextFormatting.RESET + ": " + TextFormatting.GRAY + Potion.getPotionDurationString(potionEffect, 1);
            font.drawStringWithShadow(name, sr.getScaledWidth() - font.getStringWidth(name) - 2, posY, -1);
            posY -= 8;
        }
    }
}
