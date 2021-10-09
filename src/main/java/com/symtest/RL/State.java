package com.symtest.RL;
import com.symtest.graph.*;
import com.symtest.mygraph.*;
public class State{
    private IPath mypath;
    private static int num_edges_in_state = 1;
    public State(IPath inpath)
    {
        this.mypath = new Path(inpath.getGraph());
        this.mypath.setPath(inpath.getPath());
    }
    public IPath Getpath()
    {
        return this.mypath;
    }
    public static int Get_num_edges()
    {
        return num_edges_in_state;
    }
    public static void main(String[] args)
    {
        System.out.println("testing State");
    }
    //*/
}

