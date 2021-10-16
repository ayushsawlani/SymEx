package com.symtest.tester;
import java.util.*;
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

import com.symtest.Solver.SolverResult;
import com.symtest.cfg.ICFEdge;
import com.symtest.cfg.ICFG;
import com.symtest.cfg.ICFGDecisionNode;
import com.symtest.cfg.ICFGNode;
import com.symtest.exceptions.UnSatisfiableException;
import com.symtest.expression.IExpression;
import com.symtest.expression.IIdentifier;
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

import com.symtest.tester.SymTest;
import com.symtest.RL2.Qtable;
import com.symtest.RL2.States;

public class SymTest_RL2 extends SymTest {

	private static final int MAXIMUM_ITERATIONS = 5;
	public Qtable myTable;
	public HashMap<IEdge,States>edgeStateMap;
	public double randomValue=0.5;


	public SymTest_RL2(ICFG cfg, Set<ICFEdge> targets) {
		super(cfg, targets);
		this.myTable=new Qtable();
		this.edgeStateMap=new HashMap<IEdge,States>();
	}

	
	public SymTest_RL2(ICFG cfg, Set<ICFEdge> targets, Set<ApplyHeuristics> heuristics) {
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

				stack=backtrack(stack);
				myBacktrack(stack);
				
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
	 
	Stack<Pair<IEdge, Boolean>> backtrack(Stack<Pair<IEdge, Boolean>> stack) 
	{
		System.out.println("INSIDE FUNCTION");
		if (!stack.isEmpty()) 
		{
			Pair<IEdge, Boolean> topmostPair = stack.pop();
			if (stack.isEmpty()) 
			{
				return stack;
			}

			// Push the other edge of the node with a false
			if (topmostPair.getSecond()) 
			{
				IEdge newEdge = null;
				IEdge oldEdge = topmostPair.getFirst();
				newEdge = getOtherEdge(oldEdge);
				stack.push(new Pair<IEdge, Boolean>(newEdge, false));
//				System.out.println("PUSH " + newEdge.getId());
				return stack;
			} 
			else
				return backtrack(stack);
		} 
		else
			return stack;
	}
	
	Stack<Pair<IEdge, Boolean>> myBacktrack(Stack<Pair<IEdge, Boolean>> stack)
	{
		System.out.println("Function called......................................................");
		ArrayList<Pair<IEdge, Boolean>> myList=new ArrayList<Pair<IEdge, Boolean>>(stack);	
		/*
		System.out.println("The stack Elements are : ......");
		System.out.println(stack);
		System.out.println("The List elements are : ......");
		System.out.println(myList);
		*/
		//Create States from the List we have
		createNewStates(myList);
		//this.myTable.printMap();


		//Select the edge which has maximum Reward
		Pair<IEdge,Integer> myPair=getBestEdge(myList);
		IEdge bestEdge=myPair.getFirst();
		int bestIndex=myPair.getSecond();

		if(bestEdge!=null)
		{
			System.out.println("THE BEST EDGE IS NOT NULL.........................................");
		}
		return stack;
	}
	Pair<IEdge,Integer> getBestEdge(ArrayList<Pair<IEdge, Boolean>>myList)
	{
		double reward=-1.0;
		IEdge bestEdge=null;
		int bestIndex=-1;
		
		for (int num=0;num<myList.size();num++) 
        {
			System.out.println("THE REWARD AND INDEX IS "+reward+"   "+bestIndex);
			Pair<IEdge,Boolean>myPair=myList.get(num);
			
			States i=this.edgeStateMap.get(myPair.getFirst());
			IEdge currentEdge=i.edge;
			if(currentEdge==null)
			{
				System.out.println("The edge is null");
				continue;
			}
			double currentValue=this.myTable.table.get(i);
			INode node = currentEdge.getTail();
			System.out.println("BEFOREE");
			///I AM GETTING ERROR HERE

			getOtherEdge(currentEdge);

			
			System.out.println("AFTER");
			//We can take other branch
            if(currentValue>reward)
			{
				System.out.println("HERE1");
				reward=currentValue;
				//bestEdge=getOtherEdge(currentEdge);
				bestIndex=num;
				System.out.println("HERE2");
			}
        }
		/*
		double coinToss=Math.random();
		System.out.println("THE VALUE IS ---------" + coinToss);
		if(coinToss<=this.randomValue)
		{
			System.out.println("We will do random selection from List");
			int x=10;
			while(x>0)
			{
				Random rand = new Random();
        		int randomIndex=rand.nextInt(myList.size());
				Pair<IEdge, Boolean> myPair=myList.get(randomIndex);
				IEdge presentEdge=myPair.getFirst();
				if(presentEdge!=null && getOtherEdge(presentEdge)!=null)
				{
					bestEdge=getOtherEdge(presentEdge);
					bestIndex=randomIndex;
					break;
				}
				x=x-1;
			}
		}*/
		Pair<IEdge, Integer>myAns=new Pair(bestEdge,bestIndex);
		return myAns;
	}
	void createNewStates(ArrayList<Pair<IEdge, Boolean>>myList)
	{
		System.out.println("The size of list is ..."+myList.size());
		for(int i=0;i<myList.size();i++)
		{
			Pair<IEdge, Boolean> myPair=myList.get(i);
			IEdge presentEdge=myPair.getFirst();
			//Not present
			if(!this.edgeStateMap.containsKey(presentEdge))
			{
				System.out.println("NEW STATE ADDED");
				//Insert to edgeStateMap and Qtable
				States s=new States(presentEdge);
				this.edgeStateMap.put(presentEdge,s);
				this.myTable.insertState(s,0.0);
			}

		}
	}
}