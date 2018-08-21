/***
 * 
 * Topic: Homework 3 @ CS 540, UW-Madison
 * Name : Alan He
 * ID	: 9079668670
 * Email	: zhe92@wisc.edu
 * 
 ***/


import java.util.*;

class State {
	int[] board;
	State parentPt;
	int depth;

	
	public State(int[] arr) {
		this.board = Arrays.copyOf(arr, arr.length);
		this.parentPt = null;
		this.depth = 0;
	}
	
	
	//	get all four successors and return them in sorted order
	public State[] getSuccessors() {
		State[] successors = new State[4];
		
		int pos = findZero(this.board);
		
		// 'zero' turns to the above
		successors[0] = changePos(pos, negativemod( (pos-3)%9 ) );
		
		// 'zero' turns to the right
		if (pos % 3 == 2)
			successors[1] = changePos(pos, pos-2);
		else
			successors[1] = changePos(pos, pos+1);
		
		// 'zero' turns to the left
		if (pos % 3 == 0)
			successors[2] = changePos(pos, pos+2);
		else
			successors[2] = changePos(pos, pos-1);
		
		// 'zero' turns to the downside
		successors[3] = changePos(pos, (pos+3)%9 );
		
		// get parent pointer
		for (int i=0; i<4; i++) {
			successors[i].parentPt = this;
			successors[i].depth = this.depth + 1;
		}
		
		
		return successors;
	}
	
	
	// implement a function to change the position of the Torus
	public State changePos( int a,int b) {
		State tempState = new State(this.board);
		int temp = tempState.board[a];
		tempState.board[a] = tempState.board[b];
		tempState.board[b] = temp;
		return tempState;
	}
	
	
	// findZero helps us to find the empty tile
	public int findZero(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == 0)
				return i;
		}
		return 0;
	}
	
	// get negative model
	// for '%' applied in the negative model, it returns somehow the negative value
	int negativemod(int num) {
		if (num < 0)
			return num + 9;
		else
			return num;
	}
	public void printState(int option,int time) {
		
		// TO DO: print a torus State based on option (flag)
		if(option == 5) {
			System.out.println("for the "+ Integer.toString(time)+ "th time:");
			System.out.println("| "+ Integer.toString(this.board[0])+" | "+ Integer.toString(this.board[1])+" | "+ Integer.toString(this.board[2])+ " |");
			System.out.println("| "+ Integer.toString(this.board[3])+" | "+ Integer.toString(this.board[4])+" | "+ Integer.toString(this.board[5])+ " |");
			System.out.println("| "+ Integer.toString(this.board[6])+" | "+ Integer.toString(this.board[7])+" | "+ Integer.toString(this.board[8])+ " |");
			System.out.println(" ");
		}
		else
			System.out.println(this.getBoard());
	}
	
	
	// getBoard allows us to print properly
	public String getBoard() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 9; i++) {
			builder.append(this.board[i]).append(" ");
		}
		return builder.toString().trim();
	}
	
	
	//	examine the goal state
	public boolean isGoalState() {
		for (int i = 0; i < 9; i++) {
			if (this.board[i] != (i + 1) % 9)
				return false;
		}
		return true;
	}
	
	
	// check if the 2 states are equal
	public boolean equals(State src) {
		for (int i = 0; i < 9; i++) {
			if (this.board[i] != src.board[i])
				return false;
		}
		return true;
	}
}

public class Torus {

	public static void main(String args[]) {
		
		// initialize the input
		if (args.length < 10) {
			System.out.println("Invalid Input");
			return;
		}
		int flag = Integer.valueOf(args[0]);
		int[] board = new int[9];
		for (int i = 0; i < 9; i++) {
			board[i] = Integer.valueOf(args[i + 1]);
		}
		
		int option = flag / 100;
		int cutoff = flag % 100;
		
		// for different options, we have different successors
		if (option == 1) {
			State init = new State(board);
			State[] successors = init.getSuccessors();
			for (State successor : successors) {
				successor.printState(option,0);
			}
		}
		
		
		else {
			State init = new State(board);
			Stack<State> stack = new Stack<>();
			List<State> prefix = new ArrayList<>();
			
			int goalChecked = 0;
			int maxStackSize = Integer.MIN_VALUE;
			

			// Implement the part B
			if(option == 2) {
				int counter = 0;			
				stack.push(init);
				while (!stack.isEmpty()) {
					State s = stack.pop();
					s.printState(cutoff,0);
					
					// Goalstate check
					if (s.isGoalState()) {
						System.out.println("Found the final Goal!");
						break;
					}
					
					if (counter < cutoff) {
						counter++;
						for (int i=0; i<4; i++) {
							stack.push(s.getSuccessors()[i]);
						}
					}	
				}
			}
			
			
			// Implement the part C
			// with back-pointer
				if(option == 3) {
					int counter = 0;			
					stack.push(init);
					while (!stack.isEmpty()) {
						State s = stack.pop();
						s.printState(cutoff,0);
						System.out.print(" Parent: ");
						if (counter == 0) {
							System.out.println("[0, 0, 0, 0, 0, 0, 0, 0, 0]");
						}
						else {
							s.parentPt.printState(cutoff,0);
						}
						
						
						// Goalstate check
						if (s.isGoalState()) {
								System.out.println("Found the final Goal!");
								break;
							}
						
						if (counter < cutoff) {
							counter++;
							for (int i=0; i<4; i++) {
								stack.push(s.getSuccessors()[i]);
							}
						}	
					}
				}
				
				
				// Implement the part D
				if(option == 4) {
					int counter = 0;			
					stack.push(init);
					while (!stack.isEmpty()) {
						State s = stack.pop();
						s.printState(cutoff,0);
						
						// Goalstate check
						if (s.isGoalState()) {
							System.out.println("Found the final Goal!");
							break;
						}
						
						if (counter < cutoff) {
							counter++;
							for (int i=0; i<4; i++) {
								stack.push(s.getSuccessors()[i]);
							}
						}	
					}
				}
			
			
			
			// needed for Part E
			// TO DO: perform iterative deepening; implement prefix list
			if (option == 5) {			
				stack.push(init);
				while (!stack.isEmpty()) {
					State s = stack.pop();
					
					// If s in prefix, skip to next pop
					boolean containFlag1 = false;
					for(int i = 0; i < prefix.size(); i++) {
						if ( prefix.get(i).equals(s) ){
							containFlag1 = true;
						}
					}
					if (containFlag1)
						continue;
					
					// s comes with a back-pointer to its parent p. The prefix should contain p somewhere as in initial, ..., p, ...
					// Remove everything after p and put s there, so prefix is now initial, ..., p, s.
					int pos = 0;
					boolean contain_in_prefix = false;
					if (s != init) {
						for (int i = 0; i < prefix.size(); i++) {
							if( s.parentPt.equals(prefix.get(i) )) {
								pos = i;
								contain_in_prefix = true;
								break;
							}
						}
					}
					if (contain_in_prefix == true ) {
						for (int i = prefix.size()-1; i > pos+1; i--) {
							prefix.remove(i);
						}
					}
					
					prefix.add(s);
					
					// Goal state check
					goalChecked++;
					if (s.isGoalState()) {
						System.out.println("Found the final Goal!");
						System.out.println("Ater " + Integer.toString(goalChecked) + " times.");
						
						// implement the whole prefix path
						for(int i = 0; i < prefix.size(); i++) {
							prefix.get(i).printState(option,i);
						}
						System.out.println("Goal-check " + Integer.toString(goalChecked) );
						System.out.println("Max-stack-size " + Integer.toString(maxStackSize) );
						
						
						break;
					}
					
					
					State[] tempsuccessors = s.getSuccessors();
					for (int i=0; i<4; i++) {
						boolean containFlag = false;
						for(int j = 0; j < prefix.size(); j++) {
							if (prefix.get(j).equals(tempsuccessors[i])){
								containFlag = true;
							}
						}
						
						for(int j = 0; j < stack.size(); j++) {
							if (stack.get(j).equals(tempsuccessors[i])){
								containFlag = true;
							}
						}
						if(!containFlag) {
							stack.push(tempsuccessors[i]);
						}
					}
					if(maxStackSize < stack.size()) {
						maxStackSize = stack.size();
					}
				}
			}
		}
	}
}