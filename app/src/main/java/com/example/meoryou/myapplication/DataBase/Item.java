package com.example.meoryou.myapplication.DataBase;


/**
 * Created by me or you on 9/26/2017.
 */


// DATA CLASS OF ITEM
public class Item {
    // variables
    private String name;
    private String description;
    private String dateFormat;
    private Boolean flag = false;

    // get variable
    public String getName() {return name;}
    public String getDescription() {return description;}
    public String getDateFormat() {return dateFormat;}
    public Boolean getFlag() { return flag;  }
    // set variable
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDateFormat(String dateFormat) {this.dateFormat = dateFormat; }
    public void setFlag(Boolean flag) {this.flag = flag; }
}
