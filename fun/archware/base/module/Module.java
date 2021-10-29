package fun.archware.base.module;

import fun.archware.impl.modules.misc.ModuleSound;
import fun.archware.ArchWare;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

/**
 * Created by 1 on 01.04.2021.
 */
public class Module {

    private String name, suffix;
    private int key;
    private boolean toggled;
    private Category category;
    private boolean isShown = true;
    private float posX = 1, posY = 0;
    private boolean isInRenderState;

    public Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, int key, boolean toggled, Category category) {
        this.name = name;
        this.key = key;
        this.toggled = toggled;
        this.category = category;
        if(toggled){
            ArchWare.eventManager.register(this);
        }
    }

    public Module(String name, Category category, int key) {
        this.name = name;
        this.category = category;
        this.key = key;
    }

    public Module(String name, Category category) {
        this.name = name;
        this.key = 0;
        this.category = category;
    }

    public void onEnable(){
        ArchWare.eventManager.register(this);
    }
    public void onDisable(){
        ArchWare.eventManager.unregister(this);
    }
    public void onToggle(){}
    public void onKey(int key){
        if(this.key == key){
            toggle();
        }
    }

    public void toggle(){
        if(ArchWare.moduleManager.getModuleByClass(ModuleSound.class).isToggled() && mc.world != null){
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1));
        }
        onToggle();
        //posX = 0;
        toggled = !toggled;
        if(toggled){
            onEnable();
        }else{
            onDisable();
        }
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isToggled() {
        return toggled;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getSuffix() { return suffix; }

    public void setSuffix(String suffix) { this.suffix = suffix; }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public boolean isInRenderState() {
        return isInRenderState;
    }

    public void setInRenderState(boolean inRenderState) {
        this.isInRenderState = inRenderState;
    }
}
