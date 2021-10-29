package fun.archware.impl.modules.render;

import fun.archware.ArchWare;
import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventRender2D;
import fun.archware.impl.utils.RenderUtil;

import java.awt.*;

public class CrossHair extends Module {
    private NumericValue width = new NumericValue("Width", "CrossHairWidth", this, 2, 1.5f, 20);
    private NumericValue distance = new NumericValue("Distance", "CrossHairDistance", this, 3, 2.5f, 12);
    private BooleanValue dot = new BooleanValue("Dot", "CrossHairDot", this, true);
    private BooleanValue outline = new BooleanValue("Outline", "CrossHairOutline", this, true);
    private NumericValue red = new NumericValue("Red", "CrossHairRed", this, 255, 0, 255);
    private NumericValue green = new NumericValue("Green", "CrossHairGreen", this, 0, 0, 255);
    private NumericValue blue = new NumericValue("Blue", "CrossHairBlue", this, 0, 0, 255);
    public CrossHair() {
        super("CrossHair", Category.RENDER);
    }

    @EventTarget
    public void onRender(final EventRender2D event){
        if(ArchWare.moduleManager.getModuleByClass(CoolFeatureBtw.class).isToggled()) return;
        double x = event.getSR().getScaledWidth() / 2;
        double y = event.getSR().getScaledHeight() / 2;
        int color = (mc.objectMouseOver.entityHit == null ? new Color((int)(red.getValueNumeric()), (int)(green.getValueNumeric()), (int)(blue.getValueNumeric())).hashCode() : new Color((int)(red.getValueNumeric()), (int)(green.getValueNumeric()), (int)(blue.getValueNumeric())).darker().hashCode());

        if(dot.getValueBoolean()){
            if(outline.getValueBoolean()){
                RenderUtil.drawRect(x - 2.3, y - 1.7, 3.3, 2.8, Color.BLACK.hashCode());
            }
            RenderUtil.drawRect(x - 1.3, y - 0.7, 1.3, 0.8, color);
        }

        if(outline.getValueBoolean()){
            RenderUtil.drawRect(x - distance.getValueNumeric() - 2, y - 1.7, -width.getValueNumeric() - 2, .5 + 2, Color.BLACK.hashCode());
        }
        RenderUtil.drawRect(x - distance.getValueNumeric() - 3, y - 0.7, -width.getValueNumeric(), .5, color);

        if(outline.getValueBoolean()){
            RenderUtil.drawRect(x - 2, y - distance.getValueNumeric() - 1, .5 + 2, -width.getValueNumeric() - 2, Color.BLACK.hashCode());
        }
        RenderUtil.drawRect(x - 1, y - distance.getValueNumeric() - 2, .5, -width.getValueNumeric(), color);

        if(outline.getValueBoolean()){
            RenderUtil.drawRect(x + distance.getValueNumeric() + 1, y - 1.7, width.getValueNumeric() + 2, .5 + 2, Color.BLACK.hashCode());
        }
        RenderUtil.drawRect(x + distance.getValueNumeric() + 2, y - 0.7, width.getValueNumeric(), .5, color);

        if(outline.getValueBoolean()){
            RenderUtil.drawRect(x - 2, y + distance.getValueNumeric(), .5 + 2, width.getValueNumeric() + 2, Color.BLACK.hashCode());
        }
        RenderUtil.drawRect(x - 1, y + distance.getValueNumeric() + 1, .5, width.getValueNumeric(), color);
    }
}
