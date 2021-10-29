package fun.archware.impl.modules.render;

import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;

public class CameraDistance extends Module {
    public static final NumericValue distance = new NumericValue("Distance", "CameraDistance", new CameraDistance(), 6, 5, 25);
    public CameraDistance() {
        super("CameraDistance", Category.RENDER);
    }


}
