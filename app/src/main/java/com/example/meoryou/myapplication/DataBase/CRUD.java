package com.example.meoryou.myapplication.DataBase;

import java.util.ArrayList;

/**
 * Created by me or you on 9/26/2017.
 */

public class CRUD {
    ArrayList<Item> items;

    // constructor
    public CRUD (ArrayList<Item> items){
        this.items =items;
    }
    // Get items
    public ArrayList<Item> getItems(){ return items;}
    // ADD item
    public boolean addNew(Item item){
        // booleans are to test the database resembling the connection method by mysql
        try{
            items.add(item); // add item to list
            return true; // item success returns true
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false; // no item
    }
 //DELETE item
    public boolean delete(int pos) { // remove item from the list
        try {
            items.remove(pos);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    return false;
    }

    // Update item
    public boolean update(int pos,Item newitem){ // delete initial and add new
        try {
            items.remove(pos); // remove item at that position in list
            items.add(pos, newitem); // add item to the same position in the list
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
    return false;
    }
}
