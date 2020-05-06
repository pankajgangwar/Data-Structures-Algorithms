package com.mission.google.codility.tower;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import com.mission.google.codility.graph.DFS;
import com.mission.google.codility.graph.Node;

public class Solution {

    public static void main(String[] args) {

                int[] A = new int[]{1, 1, 2, 4, 3};
                int[] B = new int[]{2, 2, 2, 3, 2};

//        int[] A = new int[] {1, 5};
//        int[] B = new int[] {3, 3};

        State start = new State(A);
        DFS dfs = new DFS(new Node(start));

        State end = new State(B);
        Node result = dfs.search(new Node(end));


        Stack<Node> resultStack = new Stack();
        resultStack.push(result);
        while (result.getParent() != null) {
            resultStack.push(result.getParent());
            result = result.getParent();
        }

        while (!resultStack.isEmpty()){
            Node pop = resultStack.pop();
            System.out.println(pop.getKey());
        }
//        System.out.println(result);
        
        System.out.println(solution(1, ""));
		System.out.println(solution(2, "1A 2F 1C"));
		System.out.println(solution(50, "1A 3C 2B 40G 5A"));
    }
    
    
    public static int solution(int n, String s) {
		Set<String> reserved = new HashSet<String>(Arrays.asList(s.split(" ")));
		int count = 0;
		int N = n + 1;

		for (int i = 0; i < N - 1; i++) {

			if (!(reserved.contains(i + 1 + "A") || reserved.contains(i + 1 + "B") || reserved.contains(i + 1 + "C")))
				count++;

			if (!(reserved.contains(i + 1 + "D") && reserved.contains(i + 1 + "G"))
					&& (!(reserved.contains(i + 1 + "E") || reserved.contains(i + 1 + "F"))))
				count++;

			if (!(reserved.contains(i + 1 + "H") || reserved.contains(i + 1 + "J") || reserved.contains(i + 1 + "K")))
				count++;
		}
		return count;
	}


}
