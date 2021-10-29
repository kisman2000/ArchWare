package fun.archware.impl.modules.combat;


import fun.archware.ArchWare;
import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.base.setting.MultiStringValue;
import fun.archware.base.setting.NumericValue;
import fun.archware.base.setting.StringValue;
import fun.archware.impl.events.EventPreUpdate;
import fun.archware.impl.events.EventUpdate;
import fun.archware.impl.utils.RotationUtils;
import fun.archware.impl.utils.TimeUtil;
import javafx.animation.Interpolator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.util.EnumHand;

import java.util.ArrayList;
import java.util.Comparator;

public class KillAura extends Module
{
        private final NumericValue range;
        private final BooleanValue swing;
        private final BooleanValue autoShiftTap;
        private final BooleanValue nosprint;
        private final StringValue sort;
        private final BooleanValue criticals;
        private final BooleanValue inWater;
        private final NumericValue falldistance;
        private final MultiStringValue targets = new MultiStringValue("Targets", "AuraTargets", this, "Players", "Animals", "Mobs", "Invisibles", "Friends");
        private final TimeUtil timer;
        private final StringValue criticalsType = new StringValue("Criticals type", "AuraCriticalsType", this, "Legit", new String[]{"Legit", "Packet"});
        public static Entity target;
        private final StringValue rotationsType = new StringValue("Rotations type", "AuraRotationsType", this, "Both", new String[]{"Both", "Silent", "None"});
        public static float rightYawRotation = 0;
        public static boolean xd = true;
        public KillAura() {
                super("KillAura", Category.COMBAT);
                autoShiftTap = new BooleanValue("Auto Shift Tap", "AST", this, false);
                range = new NumericValue("Range", "AuraRange", this, 3.0f, 0.0f, 6.1f);
                swing = new BooleanValue("Swing", "AuraSwing", this, true);
                nosprint = new BooleanValue("Stop sprinting", "AuraStopSprinting", this, false);
                sort = new StringValue("Sort", "AuraSort", this, "Health", new String[] { "Health", "Range" });
                criticals = new BooleanValue("Criticals", "Criticals", this, false);
                falldistance = new NumericValue("Fall Distance", "fallDistanceee", this, 0.01f, 0, 0.1f);
                inWater = new BooleanValue("In Water", "killaurainwater", this, true);
                timer = new TimeUtil();
        }
        @EventTarget
        public void onUpdate(final EventUpdate e) {
                if(target != null) {
                        if (criticals.getValueBoolean()) {
                                switch(criticalsType.getValueString()){
                                        case "Legit":
                                                if(mc.player.isInWater() && inWater.getValueBoolean()){
                                                        if(mc.player.getCooledAttackStrength(0.5f) == 1){
                                                                attack();
                                                        }
                                                }
                                                if (mc.player.fallDistance >= falldistance.getValueNumeric() && mc.player.getCooledAttackStrength(0) >= 0.9 && !mc.player.isCollidedVertically) {
                                                        attack();
                                                }
                                                break;
                                }
                        }else{
                                if(mc.player.getCooledAttackStrength(0) == 1){
                                        attack();
                                }
                        }
                }
                if (KillAura.target != null && (target.isDead || mc.player.getDistanceToEntity(target) > range.getValueNumeric())) {
                        KillAura.target = null;
                }


        }
        @EventTarget
        public void onPreUpdate(final EventPreUpdate event) {
                setSuffix(String.format("%.2f", range.getValueNumeric()));
                final ArrayList<Entity> entities = new ArrayList<>();
                for (final Entity e2 : mc.world.loadedEntityList) {
                        if (e2 instanceof EntityPlayer && !targets.getSelectedValues().contains("Players")) {
                                continue;
                        }
                        if (e2 instanceof EntityMob && !targets.getSelectedValues().contains("Mobs")) {
                                continue;
                        }
                        if ((e2 instanceof EntityAnimal || e2 instanceof EntityVillager) && !targets.getSelectedValues().contains("Animals")) {
                                continue;
                        }
                        if (e2.isInvisible() && !targets.getSelectedValues().contains("Invisibles")) {
                                continue;
                        }
                        if (ArchWare.friendManager.isFriend(e2) && !targets.getSelectedValues().contains("Friends")) {
                                continue;
                        }
                        if (e2.getDistanceToEntity(mc.player) > range.getValueNumeric() || !(e2 instanceof EntityLivingBase) || e2.isDead || e2 == mc.player) {
                                continue;
                        }
                        entities.add(e2);
                }
                final String valueString = sort.getValueString();
                switch (valueString) {
                        case "Health": {
                                entities.sort(Comparator.comparingInt(e -> (int)((EntityPlayer)e).getHealth()));
                                break;
                        }
                        case "Range": {
                                entities.sort(Comparator.comparingInt(e -> (int)mc.player.getDistanceToEntity(e)));
                                break;
                        }
                }
                if (!entities.isEmpty() && entities.get(0) != null && !entities.get(0).isDead) {
                        KillAura.target = entities.get(0);

                        final float[] rotations = RotationUtils.getRotation(target);
                        rightYawRotation = rotations[0];
                        if(!rotationsType.getValueString().equals("None")){
                                event.setYaw(rotations[0]);
                                event.setPitch(rotations[1]);
                                if(rotationsType.getValueString().equals("Both")){
                                        mc.player.rotationYawHead = rotations[0];
                                        mc.player.renderYawOffset = rotations[0];
                                        mc.player.rotationPitchHead = rotations[1];
                                }
                        }

                        if(criticalsType.getValueString().equals("Packet")){
                                if(mc.player.getCooledAttackStrength(0) == 1){
                                        mc.player.moveEntity(MoverType.PLAYER, 0f, 0.1, 0);
                                        event.setOnGround(false);
                                        mc.player.setPosition(mc.player.posX,
                                                mc.player.posY,
                                                mc.player.posZ);
                                        attack();
                                }
                        }
                }
        }


        public void onDisable() {
                KillAura.target = null;
                xd = true;
                super.onDisable();
        }

        private void attack() {
                if (nosprint.getValueBoolean()) {
                        xd = false;
                        mc.player.setSprinting(false);
                }
                if(ArchWare.moduleManager.getModuleByName("ShieldBreaker").isToggled() && target instanceof EntityPlayer && (((EntityPlayer)target).getActiveHand() == EnumHand.OFF_HAND && Item.getIdFromItem(((EntityPlayer)target).getHeldItem(EnumHand.OFF_HAND).getItem()) == 442)){
                        for(int i = 0; i < 9; ++i){
                                if(mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemAxe){
                                        mc.getConnection().sendPacket(new CPacketHeldItemChange(i));
                                        break;
                                }
                        }
                }

                mc.playerController.attackEntity(mc.player, KillAura.target);
                if (swing.getValueBoolean()) {
                        mc.player.swingArm(EnumHand.MAIN_HAND);
                }
                else {
                        mc.getConnection().sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                }
                if(nosprint.getValueBoolean()){
                        mc.gameSettings.keyBindSprint.pressed = true;
                        mc.player.setSprinting(true);
                }

                if(ArchWare.moduleManager.getModuleByName("ShieldBreaker").isToggled() && target instanceof EntityPlayer && (((EntityPlayer)target).getActiveHand() == EnumHand.OFF_HAND && Item.getIdFromItem(((EntityPlayer)target).getHeldItem(EnumHand.OFF_HAND).getItem()) == 442)){
                        mc.getConnection().sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
                }
                xd = true;
        }



}
