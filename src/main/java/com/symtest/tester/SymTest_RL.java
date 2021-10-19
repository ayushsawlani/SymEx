package com.symtest.tester;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;
import java.io.*;

import com.symtest.Solver.SolverResult;
import com.symtest.cfg.ICFEdge;
import com.symtest.cfg.ICFG;
import com.symtest.cfg.ICFGDecisionNode;
import com.symtest.cfg.ICFGNode;
import com.symtest.exceptions.UnSatisfiableException;
import com.symtest.expression.IExpression;
import com.symtest.expression.IIdentifier;
import com.symtest.expression.True;
import com.symtest.expression.Variable;
import com.symtest.graph.IEdge;
import com.symtest.graph.IGraph;
import com.symtest.graph.INode;
import com.symtest.graph.IPath;
import com.symtest.heuristics.ApplyHeuristics;
import com.symtest.mygraph.Path;
import com.symtest.set.SET;
import com.symtest.set.SETBasicBlockNode;
import com.symtest.set.SETNode;
import com.symtest.utilities.Pair;
import com.ibm.icu.impl.CalendarAstronomer.Ecliptic;
import com.symtest.RL.*;
import com.symtest.tester.SymTest;

public class SymTest_RL extends SymTest {

	private int edges_in_state = 1; 
	private static final int MAXIMUM_ITERATIONS = 100;
	private Qtable my_table = new Qtable();
	public SymTest_RL(ICFG cfg, Set<ICFEdge> targets) {
		super(cfg, targets);
	}

	
	public SymTest_RL(ICFG cfg, Set<ICFEdge> targets, Set<ApplyHeuristics> heuristics) {
		super(cfg,targets,heuristics);
	}

	
	public TestSequence generateTestSequence() {
		TestSequence testseq = null;
		try {
			
			//EXTRA
			Set<ICFEdge> terminalEdgeSet = new HashSet<ICFEdge>();
			terminalEdgeSet.add(mCFG.getStopNode().getOutgoingEdge());
			Set<IEdge> terminalTarget = convertTargetEdgesToGraphEdges(terminalEdgeSet);
			
			Set<IEdge> targets = convertTargetEdgesToGraphEdges(this.mTargets);
			Stack<Pair<IEdge, Boolean>> stack = new Stack<Pair<IEdge, Boolean>>();
			// Initialise the stack with start edge
			IEdge startEdge = this.mConvertor.getGraphEdge(mCFG.getStartNode()
					.getOutgoingEdgeList().get(0));
			
			stack.push(new Pair<IEdge, Boolean>(startEdge, true));
			ArrayList<IEdge> prefix = new ArrayList<IEdge>();

			IPath completePath = new Path(mGraph);
			Set<IEdge> currentTargets = new HashSet<IEdge>(targets);
			while ((!stack.isEmpty()) && !(stack.peek().getFirst().equals(startEdge) && !stack.peek()
					.getSecond())) {
				// Obtain the path that traverses the targets.
				IPath path;
				if (!hasEncounteredMaximumIterations(completePath)) {
					FindCFPathAlgorithm algorithm = new FindCFPathAlgorithm(
							this.mGraph, currentTargets,
							this.mConvertor.getGraphNode(this.mTarget));

					if (stack.size() != 1) {

						path = algorithm.findCFPath(stack.peek().getFirst()
								.getHead(), currentTargets);
					} else {
						prefix.add(startEdge);
						path = algorithm.findCFPath(stack.peek().getFirst()
								.getHead(), currentTargets);
					}
				} else {
					// If maximum iterations are done, it is only an empty path
					// that gets added
					path = new Path(mGraph);
//					FindCFPathAlgorithm algorithm = new FindCFPathAlgorithm(
//							this.mGraph, terminalTarget,
//							this.mConvertor.getGraphNode(this.mTarget));
//					path = algorithm.findLongestAcyclicPath(stack.peek().getFirst()
//								.getHead(), currentTargets);
//					System.out.println("FINAL PATH : " + path);
				}
				completePath.setPath(addprefix(prefix, path.getPath()));
				ArrayList<ICFEdge> cfPath = convertPathEdgesToCFGEdges(completePath);

				// Construct the Symbolic Execution Tree
				set = SymTestUtil.getSET(cfPath, this.mCFG);
				// Solve the predicate
				SolverResult solution;
				
				logger.fine("Complete cfPath: " + cfPath);
//				if (currentTargets.isEmpty()) {
					try {

						solution = SymTestUtil.solveSequence(set);
						return (this.convert(set.getLeafNodes().iterator().next(),
								solution));
					} catch (UnSatisfiableException e) {
						System.out.println("Unsatisfiable");
					}
//				}

				// Add heuristics
				if (this.heuristics != null) {
					for (ApplyHeuristics heuristic : heuristics) {
						try {
							solution = heuristic.performHeuristics(mGraph,
									mTargets, completePath, mCFG, mConvertor);
							return (this.convert(set.getLeafNodes().iterator()
									.next(), solution));

						} catch (UnSatisfiableException e) {
							e.printStackTrace();
						}
					}
				}
				if (!hasEncounteredMaximumIterations(completePath)) {
					logger.fine("Finding Longest Viable Prefix");

					// Get Longest Viable Prefix(LVP)
					int satisfiableIndex = SymTestUtil
							.getLongestSatisfiablePrefix(cfPath, mCFG);
					logger.finer("Satisfiable index: " + satisfiableIndex);
					logger.finer("Complete path size: " + completePath.getSize());
					logger.finer("path size: " + path.getSize());
					List<IEdge> satisfiablePrefix = new ArrayList<IEdge>();
					//TODO Figure out what this does
					satisfiablePrefix.addAll(completePath.getPath().subList(
							(completePath.getPath().size() - 1)
									- path.getPath().size(),
							satisfiableIndex + 2));

					updatestack(stack, satisfiablePrefix);
					prefix.clear();

					prefix.addAll(completePath.getPath().subList(
							0,
							completePath.getPath().indexOf(
									stack.peek().getFirst())));
				} else {
					prefix.clear();
					prefix.addAll(completePath.getPath().subList(
							0,
							completePath.getPath().lastIndexOf(
									stack.peek().getFirst())));
					logger.finer("Complete path: " + completePath);
				}

				Stack <Pair <IEdge, Boolean>> mystack = new Stack<>();
				for(IEdge e: prefix)
				{
					mystack.push(new Pair <IEdge, Boolean>(e, false));
				}
				mystack.push(new Pair <IEdge, Boolean> (stack.peek().getFirst(), false));
				stack = backtrack(mystack);
				if (!stack.isEmpty()) {
					// Add the updated edge
					if (stack.peek().getFirst().getTail().getId() == prefix.get(prefix.size()-1).getHead().getId()) {
						prefix.add(stack.peek().getFirst());
					} else {
						int index = -1;
						for (int i = prefix.size()-1; i >= 0; i--) {
							if (stack.peek().getFirst().getTail().getId() == prefix.get(i).getHead().getId()) {
								index = i;
								break;
							}
						}

						prefix.subList(index+1, prefix.size()).clear();
						prefix.add(stack.peek().getFirst());
					}
				}

				currentTargets.removeAll(prefix);
				
				logger.finest("Stack: " + Arrays.toString(stack.toArray()));

			}
			System.out.println("Unsatisfiable finally");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return testseq;
	}

	/**
	 * This function pushes only the outgoing edge of decision node contained in the path into the stack. 
	 * @param stack
	 * @param path that hets added newly
	 */
	Stack<Pair<IEdge, Boolean>> backtrack(Stack <Pair<IEdge, Boolean>> stack) 
	{
		try
		{
			System.out.println("inside Backtrack\n");
			
			/*
			if (!stack.isEmpty()) {
				Pair<IEdge, Boolean> topmostPair = stack.pop();
				if (stack.isEmpty()) {
					return stack;
				}

				// Push the other edge of the node with a false
				if (topmostPair.getSecond()) {
					IEdge newEdge = null;
					IEdge oldEdge = topmostPair.getFirst();
					newEdge = getOtherEdge(oldEdge);
					stack.push(new Pair<IEdge, Boolean>(newEdge, false));
	//				System.out.println("PUSH " + newEdge.getId());
					return stack;
				} else
					return backtrack(stack);
			} else
				return stack;
			//*/
			
			//System.out.println(stack.toString());
			List <Pair <IEdge, Boolean>> computed_path_util = new ArrayList<Pair <IEdge, Boolean>> (stack);
			//Collections.reverse(computed_path_util);
			List <IEdge> computed_path = new ArrayList <IEdge>();
			for (Pair <IEdge, Boolean> it: computed_path_util)
			{
				computed_path.add(it.getFirst());
			}
				
			// System.out.println(computed_path.toString());
			// Create and add states. 
			// inputs -> computed path, 

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


			// Find backtrack point(computed path)
			ptr2 = computed_path.size() - 1; 
			ptr1 = Math.max(ptr2 - num_edges + 1, 0);
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
				
				if((max_reward < my_table.GetValue(curr_state))&&(getOtherEdge(computed_path.get(ptr2))!=null))
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
			if(coin_toss < 0.30) // 70% go with greedy way, 30% 
			{
				while(true)
				{
					int explore_index = (int)(Math.random()*(computed_path.size() - 1));
					
					/*
					if(explore_index == computed_path.size())
						explore_index--; 
					*/

					back_track_point = explore_index; 
					if(getOtherEdge(computed_path.get(explore_index))!=null)
						break;
				}
			}

			// policy update
			//0.) compute reward from path after BTP. R1
			//0.5) Make a local copy of targets and use them to create Rewards. 
			//1.) Delete path after BTP
			//2.) concatenate findpath after BTP
			//3.) Use lsp
			//4.) compute reward of lsp R2
			//5.) R = R2 - R1
			//6.) Update Qtable as R, R/2^r, R/2^2r and so on......
			
			//prefix_path, deleted_path, added_path. 
			//System.out.println(back_track_point);
			//System.out.println(computed_path.toString());

			IPath prefix_path = new Path(mGraph); 
			IPath deleted_path = new Path (mGraph);
			IPath added_path = new Path(mGraph);

			prefix_path.setPath(new ArrayList <IEdge> (computed_path.subList(0, back_track_point)));
			deleted_path.setPath(new ArrayList <IEdge> (computed_path.subList(back_track_point, computed_path.size())));
			Set<IEdge> curr_targets = new HashSet <IEdge>();
			try{
				curr_targets = convertTargetEdgesToGraphEdges(this.mTargets); 
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			curr_targets.removeAll(prefix_path.getPath());

			FindCFPathAlgorithm algorithm = new FindCFPathAlgorithm( this.mGraph, 
			curr_targets, this.mConvertor.getGraphNode(this.mTarget));

			IPath acyclic_path = algorithm.findCFPath(getOtherEdge(computed_path.get(back_track_point)).getHead(), 
													curr_targets);
			
			
			IEdge break_edge = getOtherEdge(computed_path.get(back_track_point));
			computed_path = new ArrayList <IEdge> (computed_path.subList(0, back_track_point)); 
			
			computed_path.add(break_edge);
			
			for(IEdge edge: acyclic_path.getPath())
			{
				computed_path.add(edge); 
			}
			
			IPath newpath = new Path(mGraph); 
			newpath.setPath(computed_path);

			List <ICFEdge> cfpath = convertPathEdgesToCFGEdges(newpath);

			int satisfiableIndex = SymTestUtil.getLongestSatisfiablePrefix(cfpath, mCFG);
			
			List<IEdge> satisfiablePrefix = new ArrayList <IEdge> (computed_path.subList(back_track_point, satisfiableIndex+1));
			
			added_path.setPath(satisfiablePrefix);

			//System.out.println(prefix_path.toString());			
			//System.out.println(deleted_path.toString());			
			//System.out.println(added_path.toString());


			
			IEdge startEdge = this.mConvertor.getGraphEdge(mCFG.getStartNode().getOutgoingEdgeList().get(0));
			double Rew1 = my_table.GetReward(deleted_path, curr_targets, startEdge);
			double Rew2 = my_table.GetReward(added_path, curr_targets, startEdge);

			double net_rew = Rew2 - Rew1;

			double decay_factor = 1; 
			double factor = Math.pow(2, -decay_factor); 
			double curr_factor = 1; 
			
			ptr2 = back_track_point-1; 
			num_edges = edges_in_state;
			ptr1 = Math.max(ptr2 - num_edges + 1, 0);
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


			
			//stack update
			stack.clear();
			for(int i=0; i<back_track_point; i++)
			{
				stack.push(new Pair <IEdge, Boolean> (computed_path.get(i), false));
			}
			stack.push(new Pair <IEdge, Boolean> (computed_path.get(back_track_point), false));
			//*/
			//System.out.println(stack);
			return stack;
		}
		catch(Exception e)
		{
			//System.out.println("Backtrack exception");	
			stack.clear();
			e.printStackTrace();
			return stack;
		}
	}

}
