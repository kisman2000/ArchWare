package fun.archware.impl.managers;

import fun.archware.impl.modules.combat.*;
import fun.archware.impl.modules.misc.*;
import fun.archware.impl.modules.misc.Disabler;
import fun.archware.impl.modules.movement.*;
import fun.archware.impl.modules.player.*;
import fun.archware.impl.modules.player.NoFall;
import fun.archware.impl.modules.render.*;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.modules.movement.NoPush;

import java.util.ArrayList;

/**
 * Created by 1 on 01.04.2021.
 */
public class ModuleManager {

    public final ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        modules.add(new HUD());
        modules.add(new ClickGUI());
        modules.add(new Speed());
        modules.add(new KillAura());
        modules.add(new FullBright());
        modules.add(new TargetHUD());
        modules.add(new Velocity());
        modules.add(new Sprint());
        modules.add(new GuiWalk());
        modules.add(new NoSlow());
        modules.add(new AutoRespawn());
        modules.add(new Spammer());
        modules.add(new CrossHair());
        modules.add(new AntiAim());
        modules.add(new ESP());
        modules.add(new AntiBot());
        modules.add(new Flight());
        modules.add(new Jesus());
        modules.add(new SlotClicker());
        modules.add(new Xray());
        modules.add(new MCF());
        modules.add(new Freecam());
        modules.add(new AutoTotem());
        modules.add(new NoHurt());
        modules.add(new NoScoreboard());
        modules.add(new NameTags());
        modules.add(new ChestESP());
        modules.add(new ItemESP());
        modules.add(new Timer());
        modules.add(new TriggerBot());
        modules.add(new CrystalAura());
        modules.add(new Chams());
        modules.add(new AntiAFK());
        modules.add(new DeathCoordinates());
        modules.add(new KeyPearl());
        modules.add(new NoInteract());
        modules.add(new NoRender());
        modules.add(new ViewModel());
        modules.add(new NoClip());
        modules.add(new AirJump());
        modules.add(new NoPush());
        modules.add(new FastPlace());
        modules.add(new AutoGapple());
        modules.add(new KillSult());
        modules.add(new TargetStrafe());
        modules.add(new Macros());
        modules.add(new ViewClip());
        modules.add(new ChestStealer());
        modules.add(new FlagDetector());
        modules.add(new ShieldBreaker());
        modules.add(new NoFall());
        modules.add(new GroundSpoof());
        modules.add(new ArmorHUD());
        modules.add(new CoolFeatureBtw());
        modules.add(new ItemPhysics());
        modules.add(new SmoothCamera());
        modules.add(new CustomTime());
        modules.add(new SelfDamage());
        modules.add(new SmartWClip());
        modules.add(new NameProtect());
        modules.add(new BlockOverlay());
        modules.add(new CustomHotbar());
        modules.add(new NoRotate());
        modules.add(new InventoryCleaner());
        modules.add(new GhostModel());
        modules.add(new Disabler());
        modules.add(new AutoPot());
        modules.add(new HighJump());
        modules.add(new AntiCrystal());
        modules.add(new DamageClip());
        modules.add(new PotionHUD());
        modules.add(new LongJump());
        modules.add(new ModuleSound());
        modules.add(new WaterLeave());
        modules.add(new CrimWalk());
        modules.add(new FogColor());
        modules.add(new Keybinds());
        modules.add(new GlowESP());
        modules.add(new DamageReduce());
        modules.add(new NoFall());
        modules.add(new AntiVoid());
    }

    public Module getModuleByClass(Class clazz) {
        return modules.stream().filter(module -> module.getClass() == clazz).findFirst().orElse(null);
    }

    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public ArrayList<Module> getModulesByCategory(Category category){
        ArrayList<Module> out = new ArrayList<>();
        for(Module m : modules){
            if(m.getCategory() == category) out.add(m);
        }
        return out;
    }
}
