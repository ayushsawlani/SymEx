import java.util.*;

class Qtable
{
    public HashMap<String,String>map;
    public Qtable()
    {
        this.map = new HashMap<String,String>();
        System.out.println("I am inside constructor");
    }
    public void insertValue(String key,String value)
    {
        this.map.put(key, value);
    }
    public void deleteMap()
    {
        this.map.clear();
    }
    public int returnSize()
    {
        return this.map.size();
    }
    public void removeValue(String key)
    {
        this.map.remove(key);
    }
    public void printMap()
    {
        for (String i : this.map.keySet()) 
        {
            System.out.println("Key: " + i + " Value: " + this.map.get(i));
        }
    }
}

class States
{
    public String name;
    public int age;
    States(String name,int age)
    {
        this.name = name;
        this.age = age;
    }
    public void printStates()
    {
        System.out.println(this.name + " "+ this.age);
    }
}

class StateMap
{
    public HashMap<States, String>myMap;
    StateMap()
    {
        this.myMap=new HashMap<States,String>();
    }
    public void insertState(States key,String value)
    {
        this.myMap.put(key,value);
    }
    public void deleteState(States key)
    {
        this.myMap.remove(key);
    }
    public void updateState(States key,String value)
    {
        this.myMap.remove(key);
        this.myMap.put(key,value);
    }
    public void printMap()
    {
        for (States i : this.myMap.keySet()) 
        {
            i.name="no-name";
            System.out.println("Key: " + i.name + " Value: " + this.myMap.get(i));
        }
    }
    public boolean containsKey(States s)
    {
        if(this.myMap.containsKey(s))return true;
        else return false;
    }
    
}

public class Hashmaps
{
    public static void main(String[] args)
    {
        Qtable q1=new Qtable();
        q1.insertValue("India","New Delhi");
        q1.insertValue("USA","Washington DC");
        q1.insertValue("Pakistan","Islamabad");
        q1.insertValue("China","Beijing");
        q1.insertValue("Australia","Canberra");
        
        System.out.println("The size of the map is "+ q1.returnSize());
        q1.printMap();
        System.out.println(q1.map);


        States s1=new States("Aman",1);
        States s2=new States("Jimmy",2);
        States s3=new States("Aravind",3);
        s1.printStates();
        s2.printStates();   
        s3.printStates();


        StateMap myMap=new StateMap();
        myMap.insertState(s1,"Bangalore");
        myMap.insertState(s2,"Noida");
        myMap.printMap();
        System.out.println(myMap.containsKey(s1));
        System.out.println(myMap.containsKey(s2));
        System.out.println(myMap.containsKey(s3));

        //S1 and S2 will be changed in map and here also
        //Works on references
        s1.printStates();
        s2.printStates();   
        s3.printStates();
    }
}