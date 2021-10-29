package fun.archware.impl.modules.render;

import fun.archware.base.module.Category;
import fun.archware.base.module.Module;

/**
 * Created by 1 on 01.04.2021.
 */
public class FullBright extends Module {

    private float oldGamma;

    public FullBright() {
        super("FullBright", Category.RENDER);
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = oldGamma;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        oldGamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 50f;
        super.onEnable();
    }
}
