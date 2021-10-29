package fun.archware.impl.modules.misc;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventUpdate;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

public class GuiWalk extends Module {
    private final KeyBinding[] binds = new KeyBinding[]{ mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindJump };

    public GuiWalk() {
        super("GuiWalk", Category.MISC);
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        if(mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)){
            Arrays.asList(binds).forEach(key -> KeyBinding.setKeyBindState(key.getKeyCode(), Keyboard.isKeyDown(key.getKeyCode())));
        }
    }
}
