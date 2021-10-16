package com.symtest.RL2;
import com.symtest.graph.*;
import com.symtest.mygraph.*;
import java.util.*;

public class States
{
    public IEdge edge;
    public States(IEdge edge)
    {
        this.edge = edge;
    }
    public void printDetails()
    {
        System.out.println("State information printed here");
        //We have edge with tailNode->headNode and we branch at tailNode
    }
}