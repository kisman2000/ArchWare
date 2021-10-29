package fun.archware.impl.modules.render;

import fun.archware.ArchWare;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {
	private NumericValue red = new NumericValue("Red", "GuiRed", this, 255, 0, 255);
	private NumericValue green = new NumericValue("Green", "GuiGreen", this, 255, 0, 255);
	private NumericValue blue = new NumericValue("Blue", "GuiBlue", this, 255, 0, 255);

    public ClickGUI()
    {
        super("ClickGUI", Category.RENDER, Keyboard.KEY_RSHIFT);
    }


    @Override
    public void onEnable()
    {
        mc.displayGuiScreen(ArchWare.clickGUI);
    	toggle();
    	super.onEnable();
    }
}
