package fun.archware.impl.modules.render;

import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;

public class ViewModel extends Module {
    private final NumericValue size = new NumericValue("Size", "ViewModelSize", this, 0.5f, 0.1f, 1);
    private final NumericValue x = new NumericValue("X", "ViewModelX", this, 0, 0, 10);
    private final NumericValue y = new NumericValue("Y", "ViewModelY", this, 0, 0, 10);
    private final NumericValue z = new NumericValue("Z", "ViewModelZ", this, 0, 0, 10);
    public ViewModel() {
        super("ViewModel", Category.RENDER);
    }
}
