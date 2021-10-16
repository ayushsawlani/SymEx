import java.util.*;

class StackStructure
{
    Stack<Integer>s;
    StackStructure()
    {
        this.s=new Stack<Integer>();
    }
    public Integer getTop()
    {
        return this.s.peek();
    }
    public void addItem(Integer s)
    {
        this.s.push(s);
    }
    public void popItem()
    {
        this.s.pop();
    }
}

public class Stacks
{
    public static void main(String[] args)
    {
        StackStructure s1=new StackStructure();
        s1.addItem(1);
        s1.addItem(2);
        s1.addItem(3);
        s1.addItem(4);
        s1.addItem(5);
        System.out.println(s1.s);
        System.out.println("The top element is "+ s1.getTop());
        s1.popItem();
        System.out.println(s1.s);
    }
}