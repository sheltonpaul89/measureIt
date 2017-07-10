package com.paul.shelton.measureit.utils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by ecom-shelton.paul on 08/07/17.
 */
public class EnumUtility {
    public static <E extends Enum<E>> String[] getEnumString(Class<E> eClass)
    {
        List<String> returnList = new ArrayList<String>();
        returnList.add("Android");
        for (E en : EnumSet.allOf(eClass))
        {
            returnList.add(en.name().replace("_"," "));
        }
        return returnList.toArray(new String[returnList.size()]);
    }
}
