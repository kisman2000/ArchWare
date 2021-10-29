package fun.archware.scriptapi.features;

import net.minecraft.client.Minecraft;

public interface Feature {
    public Minecraft mc = Minecraft.getMinecraft();
    public String getName();
}
