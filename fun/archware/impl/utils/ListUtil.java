/*
 * noom (c) 2021.
 */

package fun.archware.impl.utils;


import java.util.List;

/**
 * Created by 1 on 09.04.2021.
 */
public class ListUtil {

    public static <T> T getRandomItemInArrayList(List<T> item){
        return item.get((int) MathUtils.randomNumber(0, item.size()));
    }

    public static <T> List<T> copy(List<T> item, List<T> item2) {
        item2.addAll(item);
        return item2;
    }
}
