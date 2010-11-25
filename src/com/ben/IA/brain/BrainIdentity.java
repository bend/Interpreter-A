/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ben.IA.brain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author benoitdaccache
 */
public class BrainIdentity implements Serializable {
    private String name;
    private Date birthdate;

    public BrainIdentity(String name){
        this.name = name;
        this.birthdate = Calendar.getInstance().getTime();
    }

    public String getAge(){
        long sum =  (Calendar.getInstance().getTime().getTime() - birthdate.getTime())/1000;
        int age = (int) (sum / 31557600);
        int days = (int) ((sum - age * 31557600) / 86400);
        int hours = (int)(sum - ((age *31557600 ) - days*86400))/3600;
        int minutes = (int)(sum - ((age *31557600 ) - days*86400 - hours*3600))/60;
        int seconds = (int)(sum - age *31557600  - days*86400 - hours*3600 - minutes*60);
        return age +" years "+days+" days " +hours +" hours "+minutes+" minutes "+ seconds+ " seconds";
    }

    public String getName(){
        return name;
    }
}
