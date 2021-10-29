/*
 * noom (c) 2021.
 */

package fun.archware.impl.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

/**
 * Created by 1 on 03.04.2021.
 */
public class MathUtils{
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static double randomNumber(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }
    public static boolean isInt(final String str){
        try{
            Integer.parseInt(str);
        }catch(final NumberFormatException e){
            return false;
        }
        return true;
    }
}
