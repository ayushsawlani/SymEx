package com.symtest.RL;
import java.util.*;
import com.symtest.utilities.Pair;
import com.symtest.graph.*;
import com.symtest.mygraph.*;
import com.symtest.tester.*;
import com.symtest.cfg.*;
import com.symtest.mycfg.*;
import com.symtest.tester.SymTestUtil;
public class Utilities{
    
    private int edges_in_state;
    private IGraph mGraph;
    private ICFG mCFG;
    private SymTest mytest;
    public Utilities(int iedges, IGraph imGraph, ICFG imcfg, SymTest imytest)
    {
        edges_in_state = iedges;
        mGraph = imGraph;
        mCFG = imcfg;
        mytest = imytest;
    }
    public void initialize_table(List <IEdge> computed_path, Qtable my_table)
    {
        int ptr2 = computed_path.size() - 1; 
        int num_edges = edges_in_state;
        int ptr1 = Math.max(ptr2 - num_edges + 1, 0);
        while(ptr2>=0)
        {
            //sublist of edges. 
            // use that to create path
            //use that to create a state. 
            List <IEdge> state_edges = new ArrayList <IEdge> (computed_path.subList(ptr1, ptr2 + 1));
            
            
            IPath state_path = new Path(mGraph);
            state_path.setPath(state_edges);
            
            //System.out.println(ptr1);
            State curr_state = new State(state_path);
            //System.out.println(ptr2);
            //System.out.println(curr_state.hashCode());
            //System.out.println(curr_state.Getpath().getPath().toString());

            if(my_table.CheckState(curr_state)==false)
            {
                my_table.AddState(curr_state, 0.0);
            }
            
            ptr2--;
            if(ptr1>0)
                ptr1--;
        }
    }
    public int find_backtrack_point(List <IEdge> computed_path, Qtable my_table, double explore_probability)
    {
        int num_edges = edges_in_state;
        int ptr2 = computed_path.size() - 1; 
        int ptr1 = Math.max(ptr2 - num_edges + 1, 0);
        double max_reward = -100000; 
        int back_track_point = -1; 
        
        //System.out.println("\n" + my_table.GetTable().size() + "\n");


        while(ptr2>=0)
        {
            //sublist of edges. 
            // use that to create path
            //use that to create a state.
            
            List <IEdge> state_edges = new ArrayList <IEdge> (computed_path.subList(ptr1, ptr2 + 1));
            IPath state_path = new Path(mGraph);
            state_path.setPath(state_edges);
            
            

            State curr_state = new State(state_path);
            //System.out.println(ptr2);
            //System.out.println(curr_state.hashCode());
            //System.out.println(Boolean.toString(my_table.CheckState(curr_state))+"\n");
            //System.out.println(ptr1);
            //System.out.println(curr_state.Getpath().getPath().toString());
            
            if((max_reward < my_table.GetValue(curr_state))&&(mytest.getOtherEdge(computed_path.get(ptr2))!=null))
            {
                System.out.println(my_table.GetValue(curr_state));
                max_reward = my_table.GetValue(curr_state);
                back_track_point = ptr2; 
            }
            ptr2 = ptr2 - 1;
            if(ptr1>0)
                ptr1 = ptr1 - 1;

            
        }
        double coin_toss = Math.random();
        if(coin_toss < explore_probability) // 70% go with greedy way, 30% 
        {
            while(true)
            {
                int explore_index = (int)(Math.random()*(computed_path.size() - 1));
                
                /*
                if(explore_index == computed_path.size())
                    explore_index--; 
                */

                back_track_point = explore_index; 
                if(mytest.getOtherEdge(computed_path.get(explore_index))!=null)
                    break;
            }
        }
        return back_track_point;
    }
    public void update_policy(List <IEdge> computed_path, Qtable my_table, int back_track_point)
    {
        try{
            IPath prefix_path = new Path(mGraph); 
			IPath deleted_path = new Path (mGraph);
			IPath added_path = new Path(mGraph);

			prefix_path.setPath(new ArrayList <IEdge> (computed_path.subList(0, back_track_point)));
			deleted_path.setPath(new ArrayList <IEdge> (computed_path.subList(back_track_point, computed_path.size())));
			Set<IEdge> curr_targets = new HashSet <IEdge>();
			try{
				curr_targets = mytest.convertTargetEdgesToGraphEdges(mytest.mTargets); 
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			curr_targets.removeAll(prefix_path.getPath());

			FindCFPathAlgorithm algorithm = new FindCFPathAlgorithm( mGraph, 
			curr_targets, mytest.mConvertor.getGraphNode(mytest.mTarget));

			IPath acyclic_path = algorithm.findCFPath(mytest.getOtherEdge(computed_path.get(back_track_point)).getHead(), 
													curr_targets);
			
			
			IEdge break_edge = mytest.getOtherEdge(computed_path.get(back_track_point));
			computed_path = new ArrayList <IEdge> (computed_path.subList(0, back_track_point)); 
			
			computed_path.add(break_edge);
			
			for(IEdge edge: acyclic_path.getPath())
			{
				computed_path.add(edge); 
			}
			
			IPath newpath = new Path(mGraph); 
			newpath.setPath(computed_path);

			List <ICFEdge> cfpath = mytest.convertPathEdgesToCFGEdges(newpath);

			int satisfiableIndex = SymTestUtil.getLongestSatisfiablePrefix(cfpath, mCFG);
			
			List<IEdge> satisfiablePrefix = new ArrayList <IEdge> (computed_path.subList(back_track_point, satisfiableIndex+1));
			
			added_path.setPath(satisfiablePrefix);

			//System.out.println(prefix_path.toString());			
			//System.out.println(deleted_path.toString());			
			//System.out.println(added_path.toString());


			
			IEdge startEdge = mytest.mConvertor.getGraphEdge(mCFG.getStartNode().getOutgoingEdgeList().get(0));
			double Rew1 = my_table.GetReward(deleted_path, curr_targets, startEdge);
			double Rew2 = my_table.GetReward(added_path, curr_targets, startEdge);

			double net_rew = Rew2 - Rew1;

			double decay_factor = 1; 
			double factor = Math.pow(2, -decay_factor); 
			double curr_factor = 1; 
			
			int ptr2 = back_track_point-1; 
			int num_edges = edges_in_state;
			int ptr1 = Math.max(ptr2 - num_edges + 1, 0);
			while(ptr2>=0)
			{
				//sublist of edges. 
				// use that to create path
				//use that to create a state. 
				List <IEdge> state_edges = new ArrayList <IEdge> (computed_path.subList(ptr1, ptr2+1));
				IPath state_path = new Path(mGraph);
				state_path.setPath(state_edges);
				
				State curr_state = new State(state_path);
				
				my_table.UpdateState(curr_state, my_table.GetValue(curr_state) +  net_rew*curr_factor);

				ptr2--;
				if(ptr1>0)
					ptr1--;

				curr_factor = curr_factor * factor;
			}
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void stack_update(List <IEdge> computed_path, int backtrackpoint, Stack <Pair<IEdge, Boolean>> stack, int back_track_point)
    {
        stack.clear();
        for(int i=0; i<back_track_point; i++)
        {
            stack.push(new Pair <IEdge, Boolean> (computed_path.get(i), false));
        }
        stack.push(new Pair <IEdge, Boolean> (mytest.getOtherEdge(computed_path.get(back_track_point)), false));
    }
}