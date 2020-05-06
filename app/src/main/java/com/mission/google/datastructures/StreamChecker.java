package com.mission.google.datastructures;

import java.util.LinkedList;
import java.util.Queue;

class StreamChecker {
    
    int MAXS = 500; //Max no. of state in matching machine
	int MAXC = 26; // Max no. of characters in input alphabet
	
	// OUTPUT FUNCTION IS IMPLEMENTED USING out[] 
	// Bit i in this mask is one if the word with index i 
	// appears when the machine enters this state. 
	int out[] = new int[MAXS];
	// FAILURE FUNCTION IS IMPLEMENTED USING f[] 
	int f[] = new int [MAXS];
	//GOTO FUNCTION (OR TRIE) IS IMPLEMENTED USING g[][] 
	int g[][] = new int[MAXS][MAXC];
    
    int dictionarySize = 0;
    String[] dictionary;
    
    StringBuilder builder = new StringBuilder();

    public StreamChecker(String[] words) {
        dictionary = words;
        dictionarySize = words.length;
        buildMatchingMachine(words, dictionarySize );
    }
    
    public boolean query(char letter) {
  
        builder.append(letter);
      
        int currentState = 0;
        System.out.println(builder.toString());
		for(int i = 0; i < builder.length() ; i++) {
			currentState = findNextState(currentState, builder.charAt(i));
			
			if(out[currentState] == 0)
				continue;
			
			for(int j = 0; j < dictionarySize ; j++) {
				
				if((out[currentState] & (1 << j)) != 0) {
					System.out.println("Word: " + dictionary[j] + 
                                       " appears from " + (i - dictionary[j].length() + 1 ) + " to  " + (i));	
					int start = i - dictionary[j].length() + 1;
					int end = i;
					
					if(start == end) {
						builder.deleteCharAt(start);
					}else {
						builder.delete(i - dictionary[j].length() + 1, i);
					}
                    return true;
				}
			}
		}
        return false;
    }
    
    public int buildMatchingMachine(String[] arr, int k) {
		for(int ele : out) {
			out[ele] = 0;
		}
		
		for(int row = 0; row < MAXS; row++) {
			for(int col = 0; col < MAXC; col++) {
				g[row][col] = -1;
			}
		}
		int states = 1;
		for(int i = 0; i < k ; i++) {
			String word = arr[i];
			int currentState = 0;
			for(int j = 0; j < word.length(); j++) {
				int charIndex = word.charAt(j) - 'a';
				
				if(g[currentState][charIndex] == -1) {
					g[currentState][charIndex] = states++;
				}
				
				currentState = g[currentState][charIndex];
			}
			out[currentState] |= (1 << i);
		}
		
		for(int ch = 0; ch < MAXC; ch++) {
			if(g[0][ch] == -1)
				g[0][ch] = 0;
		}
		
		for(int i = 0; i < f.length; i++) {
			f[i] = -1;
		}
		
		Queue<Integer> mQueue = new LinkedList<>();
		
		for(int ch = 0; ch < MAXC; ch++) {
			if(g[0][ch] != 0) {
				f[g[0][ch]] = 0;
				 mQueue.offer(g[0][ch]);
			}
		}
		
		while(!mQueue.isEmpty()) {
			int state = mQueue.poll();
			for(int ch = 0; ch < MAXC; ch++ ) {
				
				if(g[state][ch] != -1) {
					int failure = f[state];
					
					while(g[failure][ch] == -1) {
						failure = f[failure];
					}
					
					failure = g[failure][ch];
					f[g[state][ch]] = failure;
					
					out[g[state][ch]] |= out[failure];
					
					mQueue.offer(g[state][ch]);
				}
			}
		}
		return states;
	}
    
    public int findNextState(int currentState,char nextInput) {
		int answer = currentState;
		int ch = nextInput - 'a';
		
		while(g[answer][ch] == -1)
			answer = f[answer];
		
		return g[answer][ch];
	}
    
   
}

