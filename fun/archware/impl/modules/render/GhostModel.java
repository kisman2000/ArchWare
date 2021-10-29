package fun.archware.impl.modules.render;

import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;

public class GhostModel extends Module {
    private final BooleanValue killaura = new BooleanValue("KillAura", "GhostModelKillAura", this, true);
    private final BooleanValue antiaim = new BooleanValue("AntiAim", "GhostModelAntiAim", this, true);
    private final BooleanValue scaffold = new BooleanValue("Scaffold", "GhostModelScaffold", this, true);
    public GhostModel() {
        super("GhostModel", Category.RENDER);
    }
}
