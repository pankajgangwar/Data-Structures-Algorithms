package com.mission.google.datastructures;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class TrieDataStrucure {
	
	public static void main(String[] args) {
		//System.out.println(getCharIndex('X'));
		/*root = new Node();
		System.out.println("**************** TRIE INSERT **************");
		String names[] = {"pankaj","prakash","mahesh","vinodkumar"};
		for(String str : names){
			insert(str);	
		}
		
		boolean found = search("aj");
		System.out.println("**************** TRIE SEARCH **************\nFound: " + found);
		
		System.out.println("**************** TRIE DATA **************");
		printTrie(root, new char[26], 0);*/
		
		//https://www.geeksforgeeks.org/aho-corasick-algorithm-pattern-searching/
//	   	 String arr[] = {"a", "she", "hers", "his"}; 
//		 String text = "ahishers"; 
//		 int k = arr.length;
//		 TrieDataStrucure strucure = new TrieDataStrucure();
//		 strucure.searchWords(arr, k , text);
//		char charArr[] = new char[];
//	    int query = 0;
//	    int dictionarySize = 0;
//	    String[] dictionary;
//	    
//	    List<Character> charList = new ArrayList<>();
//	    StringBuilder builder = new StringBuilder();
//	    for(char ch : charList) {
//	    	builder.append(ch);
//	    }
//	    builder.toString();
	    String [] words = new String[] {"cd","f","kl"};
	    StreamChecker checker = new StreamChecker(words);
	    char[] queries = new char[] {'a','b','c','d','e','f','g','h','i','j','k','l'};
	    for(char ch : queries) {
	    	boolean exists = checker.query(ch);	
	    	//System.out.println(exists);
	    }
	    
	}
	
	
	private static int getCharIndex(char c){
		return c -'a';
	}
	
	static Node root;
	static void insert(String key){
		int level;
		int length = key.length();
		int index;
		
		Node pCrawl = root;
		for(level = 0; level < length; level ++ ){
			index = key.charAt(level) - 'a';
			if(pCrawl.children[index] == null){
				pCrawl.children[index] = new Node();
			}
			pCrawl = pCrawl.children[index];
		}
		pCrawl.isEndOfWord = true;
	}
	
	static boolean search(String key){
		int level;
		int length = key.length();
		Node pCrawl = root;
		for(level = 0 ; level < length; level++){
			int index = key.charAt(level) - 'a';
			System.out.println(pCrawl.children[index]);
			if(pCrawl.children[index] == null)
				return false;
			pCrawl = pCrawl.getNode(key.charAt(level));
			//int index = key.charAt(level) - 'a';
			//pCrawl = pCrawl.children[index];
		}
		return pCrawl != null && pCrawl.isEndOfWord; 
		
	}
	
	public static void printTrie(Node trieNode,char str[],int level){
		
		if(trieNode.isEndOfWord){
			System.out.println(str);
		}
		
		Node[] childrens = trieNode.children;
		for(int i=0; i < childrens.length; i++){
			if(childrens[i] != null){
				str[level] = (char)(i + 97);
				printTrie(childrens[i],str,level+1);
			}
		}
	}
	
	public static class Node {
		private static int NUMBER_OF_CHARACTER = 26;
		Node[] children = new Node[NUMBER_OF_CHARACTER];
		boolean isEndOfWord = false;
		
		public Node(){
			isEndOfWord = false;
			for(int i=0; i< NUMBER_OF_CHARACTER ; i++)
				children[i]=null;
		}
		
		private Node getNode(char c){
			return children[getCharIndex(c)];
		}
		
		private static int getCharIndex(char c){
			return c-'a';
		}
	}
	
	//Aho-Corasick algo
	static int MAXS = 500; //Max no. of state in matching machine
	static int MAXC = 26; // Max no. of characters in input alphabet
	
	// OUTPUT FUNCTION IS IMPLEMENTED USING out[] 
	// Bit i in this mask is one if the word with index i 
	// appears when the machine enters this state. 
	int out[] = new int[MAXS];
	// FAILURE FUNCTION IS IMPLEMENTED USING f[] 
	int f[] = new int [MAXS];
	//GOTO FUNCTION (OR TRIE) IS IMPLEMENTED USING g[][] 
	int g[][] = new int[MAXS][MAXC];
	
	private int buildMatchingMachine(String[] arr, int k) {
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
	
	private int findNextState(int currentState,char nextInput) {
		int answer = currentState;
		int ch = nextInput - 'a';
		
		while(g[answer][ch] == -1)
			answer = f[answer];
		
		return g[answer][ch];
	}
			
	private void searchWords(String[] arr, int k, String text) {
		// TODO Auto-generated method stub
		buildMatchingMachine(arr, k);
		
		int currentState = 0;
		for(int i = 0 ; i < text.length(); i++) {
			currentState = findNextState(currentState, text.charAt(i));
			
			if(out[currentState] == 0)
				continue;
			
			for(int j = 0; j < k; j++) {
				
				if((out[currentState] & (1 << j)) != 0) {
					System.out.println("Word: " + arr[j] + " appears from " + (i - arr[j].length() + 1 ) + " to  " + (i));	
				}
			}
		}
	}

}
