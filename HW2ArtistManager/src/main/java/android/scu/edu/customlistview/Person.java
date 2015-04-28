package android.scu.edu.customlistview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Person implements Serializable{
    // NOTE:
    // 1. used LinkedHashSet to keep the order the same with insertion order.
    // 2. Serializable interface is used to ensure that its instance can be passed as a extra data.
    public static LinkedHashSet<String> favLinkedHashSet = new LinkedHashSet();
    private String name;
    private int resIDThumbnail;
    private int resIDlargeImage;
    private String detail;

    public Person(String n, int idSmall, int idLarge, String d){
        super();
        this.name = n;
        this.resIDThumbnail = idSmall;
        this.resIDlargeImage = idLarge;
        this.detail = d;
    }
    public String getName() {
        return this.name;
    }
    public int getResIDThumbnail() {
        return this.resIDThumbnail;
    }
    public int getResIDlargeImage() {
        return this.resIDlargeImage;
    }
    public String getDetail() {
        return this.detail;
    }
}
