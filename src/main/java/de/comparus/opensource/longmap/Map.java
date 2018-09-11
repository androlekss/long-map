package de.comparus.opensource.longmap;

import java.util.Arrays;

public class Map {

    public static void main(String[] args) {
        LongMap longMap = new LongMapImpl();
        longMap.put(5345345, "Value1");
        longMap.put(5345668, "Value2");//
        longMap.put(5345669, "Value3");//
        longMap.put(5345610, "Value4");//
        longMap.put(5345656, "Value5");//

        System.out.println(longMap.get(5045345));
        System.out.println(longMap.remove(5045345));
        System.out.println(longMap.get(5045345));
        System.out.println(longMap.get(5377777));

        System.out.println(Arrays.toString(longMap.keys()));
        System.out.println(Arrays.toString(longMap.values()));
        System.out.println(longMap.size());

        longMap.put(5345699, "Value6");//
        longMap.put(5345101, "Value7");//
        longMap.put(5377777, "Value8");//
        longMap.put(5433456, "Value9");//
        longMap.put(5387878, "Value10");//
        longMap.put(5045345, "Value11");//
        longMap.put(5045345, "Value12");

        System.out.println(Arrays.toString(longMap.keys()));
        System.out.println(Arrays.toString(longMap.values()));
        System.out.println(longMap.size());
        System.out.println(longMap.containsValue("Value12"));
        System.out.println(longMap.containsKey(5045345));

    }
}
