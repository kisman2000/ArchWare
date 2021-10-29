package fun.archware.impl.modules.render;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.base.setting.NumericValue;
import fun.archware.base.setting.StringValue;
import fun.archware.impl.events.EventUpdate;

public class Chams extends Module {
	private NumericValue red = new NumericValue("Red", "ChamsRed", this, 255, 0, 255);
	private NumericValue green = new NumericValue("Green", "ChamsGreen", this, 255, 0, 255);
	private NumericValue blue = new NumericValue("Blue", "ChamsBlue", this, 255, 0, 255);
	private NumericValue opacity = new NumericValue("Opacity", "ChamsOpacity", this, 50, 0, 225);
	private final StringValue mode = new StringValue("Mode", "ChamsMode", this, "Static", new String[]{"Static", "Pulsive", "Rainbow"});
	private final BooleanValue fillArmor = new BooleanValue("Fill armor", "ChamsArmor", this, false);
	public Chams() {
		super("Chams", Category.RENDER);
	}

	@EventTarget
	public void onUpdate(final EventUpdate event){

	}
}
