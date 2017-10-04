package com.example.meoryou.myapplication.DataBase;

import java.util.ArrayList;

/**
 * Created by me or you on 9/26/2017.
 */

public class ItemsCollection {
    // data source
    static ArrayList<Item> items = new ArrayList<>();
    public static ArrayList<Item> getItem(){return items; }
}
