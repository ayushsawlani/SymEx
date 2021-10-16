import java.util.*;

class List
{
    ArrayList<String>myList;
    List()
    {
        this.myList=new ArrayList<String>();
    }
    public void addItem(String s)
    {
        this.myList.add(s);
    }
    public String getItem(int i)
    {
        return this.myList.get(i);
    }
    public void setItem(String s,int i)
    {
        this.myList.set(i,s);
    }
    public void clearList()
    {
        this.myList.clear();
    }
    public int getSize()
    {
        return this.myList.size();
    }
    public void printList()
    {
        for(int i=0;i<this.myList.size();i++)
        {
            System.out.println(this.myList.get(i));
        }
    }
}

public class ArrayLists
{
    public static void main(String[] args)
    {
        List l1=new List();
        l1.addItem("Delhi");
        l1.addItem("Bangalore");
        l1.addItem("Jaipur");
        l1.addItem("Vizag");
        l1.addItem("Chennai");
   
        System.out.println("The size of the list is "+ l1.getSize());
        l1.setItem("Mangalore",4);
        l1.printList();
        System.out.println(l1.getItem(0));
        System.out.println(l1.myList);
    }
}