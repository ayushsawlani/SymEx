package com.symtest.RL2;

import com.symtest.RL2.States;
import com.symtest.graph.*;
import com.symtest.mygraph.*;
import java.util.*;


public class Qtable{
    //Stores References
    public HashMap<States,Double>table;
    public Qtable()
    {
        System.out.println("INSIDE CLASS");
        this.table= new HashMap<States, Double>();
    }
    
    public void insertState(States s,Double value)
    {
        this.table.put(s,value);
    }
    public void updateState(States s,Double value)
    {
        this.table.remove(s);
        this.table.put(s,value);
    }
    public void deleteState(States s)
    {
        this.table.remove(s);
    }
   
    public void printMap()
    {
        System.out.println("THE Qtable Details are :::");
        for (States i : this.table.keySet()) 
        {
            System.out.println(i+" Value: " + this.table.get(i));
        }
    }
}