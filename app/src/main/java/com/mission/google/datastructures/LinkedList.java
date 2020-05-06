package com.mission.google.datastructures;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

public class LinkedList {

	public static void main(String[] args) {

		LinkedList linkedList = new LinkedList();
		linkedList.createList1();

		// linkedList.createList2();

		// System.out.println("*******BEFORE**************");
		//linkedList.printList1();

		// System.out.println("*******BEFORE**************");
		// linkedList.printList2();

		// System.out.println("************AFTER**************");
		// linkedList.printMergeSort();

		// linkedList.startInsertionSort();

		// linkedList.printListReverse();

		// linkedList.checkPalindrome();

		//linkedList.testMergeList();
		ListNode l1 = new ListNode(1);
		l1.next = new ListNode(2);
		l1.next.next = new ListNode(3);
		l1.next.next.next = new ListNode(4);
		l1.next.next.next.next = new ListNode(5);
//		
		ListNode l2 = new ListNode(9);
		l2.next = new ListNode(9);
//l2.next.next =  new ListNode(9);
		
		//ListNode result = linkedList.addTwoNumbers(l1, l2);
		ListNode result  = linkedList.reverseBetweenSec(l1);
		
		while(result != null) {
			System.out.print("" + result.val + " , ");
			result = result.next;
		}
		

	}

	private void sumOfTwoList() {
		// TODO Auto-generated method stub
		// Node res = addTwoList(head, head2);
		Node resRecursion = addListRec(head, head2, 0);
		printOurList(resRecursion);
	}

	private Node addListRec(Node first, Node second, int carry) {
		// TODO Auto-generated method stub
		// This program uses extra space
		if (first == null && second == null) {
			return carry > 0 ? new Node(carry) : null;
		}

		int value = (first != null ? first.data : 0) + (second != null ? second.data : 0);

		int sum = (value + carry) % 10;

		// carry = value/10;

		carry = value >= 10 ? 1 : 0;

		Node currentHead = new Node(sum);
		// first = first != null ? first.next : null;
		// second = second != null ? second.next : null;

		Node node1Next = (first != null) ? first.next : null;
		Node node2Next = (second != null) ? second.next : null;

		currentHead.next = addListRec(node1Next, node2Next, carry);

		return currentHead;
	}

	Node head;

	public static class Node {
		int data;
		Node next;

		public Node(int key) {
			data = key;
			next = null;
		}
	}

	public void printList1() {
		printOurList(head);
	}

	public void printList2() {
		printOurList(head2);
	}

	// sorted list
	public void createList1() {
		Node a = new Node(9);
		head = a;
		Node b = new Node(11);
		a.next = b;
		Node c = new Node(1);
		b.next = c;
		Node d = new Node(6);
		c.next = d;
		Node e = new Node(6);
		d.next = e;
		Node f = new Node(1);
		e.next = f;
		Node g = new Node(11);
		f.next = g;
		/*
		 * Node h = new Node(9); g.next = h;
		 */
		/*
		 * Node g = new Node(12); f.next = g;
		 */
		// g.next = d; //To create loop
	}

	/*
	 * //Unsorted list public void createList1(){ Node a = new Node(6); head = a;
	 * Node b = new Node(1); a.next = b; Node c = new Node(8); b.next = c; Node d =
	 * new Node(9); c.next = d; Node e = new Node(10); d.next = e; Node f = new
	 * Node(2); e.next = f; Node g = new Node(5); f.next = g; //g.next = d; //To
	 * create loop }
	 */

	Node head2;

	public void createList2() {
		Node a = new Node(1);
		head2 = a;
		Node b = new Node(3);
		a.next = b;
		Node c = new Node(7);
		b.next = c;
		/*
		 * Node d = new Node(11); c.next = d; Node e = new Node(13); d.next = e; Node f
		 * = new Node(19); e.next = f;
		 */
		// f.next = c; //To create loop
	}

	public void printMergeSortedList() {
		Node mergedList = mergeSortedList();
		printOurList(mergedList);
	}

	public Node mergeSortedList() {
		Node a = head;
		Node b = head2;
		Node dummy = new Node(0);
		Node head = dummy;
		while (a != null && b != null) {
			if (a.data < b.data) {
				dummy.next = a;
				a = a.next;
			} else {
				dummy.next = b;
				b = b.next;
			}
			dummy = dummy.next;
		}

		if (a != null) {
			dummy.next = a;
		}
		if (b != null) {
			dummy.next = b;
		}
		return head.next;
	}

	public void printMergedSortedListIterative() {
		Node mergedList = mergeSortedListIterative();
		printOurList(mergedList);
	}

	public Node mergeSortedListIterative() {
		// This method merges two sorted list without using Extra Node
		Node list1 = head;
		Node list2 = head2;

		if (list1 == null)
			return list2;
		if (list2 == null)
			return list1;

		Node head;
		if (list1.data < list2.data) {
			head = list1;
		} else {
			// Swap both lists
			head = list2;
			list2 = list1;
			list1 = head;
		}

		while (list1.next != null) {
			if (list1.next.data > list2.data) {
				Node temp = list1.next;
				list1.next = list2;
				list2 = temp;
			}
			list1 = list1.next;
		}
		list1.next = list2;
		return head;
	}

	public void printMergeSort() {
		Node mergedList = mergeSort(head);
		System.out.println("########## After MergeSort ###############");
		printOurList(mergedList);
	}

	public Node mergeSort(Node head) {
		if (head == null || head.next == null) {
			return head;
		}

		Node median = findMiddleNode(head);

		Node nextOfMiddle = median.next;

		median.next = null;

		Node left = mergeSort(head);
		Node right = mergeSort(nextOfMiddle);

		Node sortedList = mergeSortedListRecursive(left, right);
		return sortedList;
	}

	public Node addTwoList(Node first, Node second) {
		Node prev = null;
		Node res = null;
		Node temp = null;
		int sum = 0, carry = 0;
		while (first != null || second != null) {
			sum = carry + (first != null ? first.data : 0) + (second != null ? second.data : 0);
			carry = sum >= 10 ? 1 : 0;
			sum = sum % 10;

			temp = new Node(sum);
			if (res == null) {
				res = temp;
			} else {
				prev.next = temp;
			}
			prev = temp;
			if (first != null) {
				first = first.next;
			}
			if (second != null) {
				second = second.next;
			}

		}
		if (carry > 0) {
			prev.next = new Node(carry);
		}
		return res;
	}

	public void printMergedListRecursive() {
		Node mergedList = mergeSortedListRecursive(head, head2);
		System.out.println("********* Merged list recursively ****************");
		printOurList(mergedList);
	}

	/*
	 * public Node mergeSortedListRecursive(Node list1,Node list2){ if(list1 ==
	 * null)return list2; if(list2 == null)return list1; Node result = null;
	 * if(list1.data < list2.data){ result = list1; result.next =
	 * mergeSortedListRecursive(list1.next, list2); }else{ result = list2;
	 * result.next = mergeSortedListRecursive(list1, list2.next); } return result; }
	 */

	public Node mergeSortedListRecursive(Node list1, Node list2) {
		if (list1 == null)
			return list2;
		if (list2 == null)
			return list1;
		if (list1.data < list2.data) {
			list1.next = mergeSortedListRecursive(list1.next, list2);
			return list1;
		} else {
			list2.next = mergeSortedListRecursive(list2.next, list1);
			return list2;
		}
	}

	public void reverseList() {
		Node current, previous = null, next = null;
		current = head;
		while (current != null) {
			next = current.next;
			current.next = previous;
			previous = current;
			current = next;
		}
		head = previous;
	}

	public void printListReverse() {
		printReverse(head);
	}

	public void printReverse(Node head) {
		if (head == null) {
			return;
		}
		printReverse(head.next);
		System.out.print(head.data + "\t");
	}

	public void swapNodes(int x, int y) {
		// Nothing to do if x and y are same
		if (x == y)
			return;

		// Search for x (keep track of prevX and CurrX)
		Node prevX = null, currX = head;
		while (currX != null && currX.data != x) {
			prevX = currX;
			currX = currX.next;
		}

		// Search for y (keep track of prevY and currY)
		Node prevY = null, currY = head;
		while (currY != null && currY.data != y) {
			prevY = currY;
			currY = currY.next;
		}

		// If either x or y is not present, nothing to do
		if (currX == null || currY == null)
			return;

		// If x is not head of linked list
		if (prevX != null)
			prevX.next = currY;
		else // make y the new head
			head = currY;

		// If y is not head of linked list
		if (prevY != null)
			prevY.next = currX;
		else // make x the new head
			head = currX;

		// Swap next pointers
		Node temp = currX.next;
		currX.next = currY.next;
		currY.next = temp;
		if (head == null)
			System.out.println("HEAD NULL");

	}

	private Node findMiddleNode(Node list) {
		if (list == null) {
			System.out.println("No elements in linkedlist");
			return null;
		}
		Node fastPtr = list.next;
		Node slowPtr = list;
		while (fastPtr != null) {
			fastPtr = fastPtr.next;
			if (fastPtr != null) {
				slowPtr = slowPtr.next;
				fastPtr = fastPtr.next;
			}
		}
		return slowPtr;
		/*
		 * while(fastPtr != null && slowPtr != null && fastPtr.next != null){ slowPtr =
		 * slowPtr.next; fastPtr = fastPtr.next.next; } if(fastPtr != null){
		 * System.out.println("Middle element: " + slowPtr.next.data); return
		 * slowPtr.next; }else{ System.out.println("Middle element: " + slowPtr.data);
		 * return slowPtr; }
		 */
	}

	public int countLoopLength(Node prev) {
		Node temp = prev;
		int length = 1;
		while (temp.next != prev) {
			System.out.println("data: " + temp.data);
			length++;
			temp = temp.next;
		}
		return length;
	}

	public void checkLoop() {
		Node prev = detectLoop();
		if (prev != null) {
			System.out.println("Oops ... There's a loop");
			// System.out.println("Loop length: " + countLoopLength(prev));
		} else {
			System.out.println("No loop found");
		}
	}

	public Node detectLoop() {
		Node slowPtr = head, fastPtr = head;
		while (slowPtr != null && fastPtr != null && fastPtr.next != null) {
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next.next;
			if (slowPtr == fastPtr) {
				return slowPtr;
			}
		}
		return null;
	}

	public void insertAtEnd(int key) {
		Node baby = new Node(key);
		Node temp = head;
		if (temp == null) {
			temp = baby;
			System.out.println("Inserted first element: " + key);
			return;
		}

		while (temp.next != null) {
			temp = temp.next;
		}
		temp.next = baby;
		System.out.println("Inserted : " + key);
		head = temp;
	}

	public void printLength() {
		System.out.println("Length: " + getLengthRec(head));
	}

	public void printNthNodeFromLast(int val) {
		System.out.println(val + " from last: " + getNthNodeFromLast(head, val));
	}

	void printNthFromLast(int n) {
		int len = 0;
		Node temp = head;

		// 1) count the number of nodes in Linked List
		while (temp != null) {
			temp = temp.next;
			len++;
		}

		// check if value of n is not more than length of
		// the linked list
		if (len < n)
			return;

		temp = head;

		// 2) get the (len-n+1)th node from the begining
		for (int i = 1; i < len - n + 1; i++)
			temp = temp.next;

		System.out.println(temp.data);
	}

	public int getNthNodeFromLast(Node head, int pos) {
		Node current = head, prev = head;
		for (int i = 0; i < pos; i++) {
			if (current == null) {
				return -1;
			}
			current = current.next;
		}
		while (current != null) {
			current = current.next;
			prev = prev.next;
		}
		return prev.data;
	}

	public void printFrequency(int key) {
		System.out.println(key + " occurs: " + countFrequencyRec(head, key) + " times !!");
	}

	public int countFrequencyRec(Node head, int key) {
		int frequency = 0;
		if (head == null) {
			return frequency;
		}
		if (head.data == key) {
			frequency++;
		}
		frequency += countFrequencyRec(head.next, key);
		return frequency;
	}

	public int getLengthRec(Node temp) {
		if (temp == null) {
			return 0;
		}
		return 1 + getLengthRec(temp.next);
	}

	public void printOurList(Node list) {
		Node temp = list;
		if (temp == null) {
			System.out.println("\nNothing to print");
			return;
		}
		System.out.println("**********LinkedList**********");
		while (temp != null) {
			System.out.print(temp.data + " \t");
			temp = temp.next;
		}
		System.out.println("");
	}

	// Insertion Sort
	// https://www.geeksforgeeks.org/insertion-sort-for-singly-linked-list/
	public void startInsertionSort() {
		printOurList(head);
		insertionSort(head);
	}

	Node sorted = null;

	public void insertionSort(Node head) {

		Node current = head;
		while (current != null) {
			Node next = current.next;
			sortedInsert(current);
			current = next;
		}
		head = sorted;
		printOurList(head);
	}

	private void sortedInsert(Node newNode) {
		// TODO Auto-generated method stub
		if (sorted == null || sorted.data >= newNode.data) {
			newNode.next = sorted;
			sorted = newNode;
		} else {
			Node current = sorted;
			while (current.next != null && current.next.data < newNode.data) {
				current = current.next;
			}
			newNode.next = current.next;
			current.next = newNode;
		}
	}

	private void checkPalindrome() {
		// TODO Auto-generated method stub
		System.out.println("************ PALINDROME CHECK *************");
		boolean isPalindrome = checkPalindrome(head);
		System.out.println("If Palindrome : " + isPalindrome);
		// System.out.println("isPalindrom: " + checkPalindrome(head));
	}

	private boolean checkPalindrome(Node head) {
		if (head == null) {
			return false;
		}
		Node fastPtr = head.next;
		Node slowPtr = head;
		Stack<Integer> mStack = new Stack<>();

		while (fastPtr != null && fastPtr.next != null) {
			mStack.push(slowPtr.data);
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next.next;
		}
		if (fastPtr != null) {
			mStack.push(slowPtr.data);
		}

		slowPtr = slowPtr.next;

		while (slowPtr != null) {
			if (!mStack.isEmpty()) {
				if (mStack.peek() == slowPtr.data) {
					mStack.pop();
				}
			}
			slowPtr = slowPtr.next;
		}
		if (mStack.isEmpty() && slowPtr == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void testMergeList() {
		int k = 3; // Number of linked lists  
        int n = 4; // Number of elements in each list  
    
        // an array of pointers storing the head nodes  
        // of the linked lists  
        Node arr[]=new Node[k];  
    
        arr[0] = new Node(1);  
        arr[0].next = new Node(3);  
        arr[0].next.next = new Node(5);  
        arr[0].next.next.next = new Node(7);  
    
        arr[1] = new Node(2);  
        arr[1].next = new Node(4);  
        arr[1].next.next = new Node(6);  
        arr[1].next.next.next = new Node(8);  
    
        arr[2] = new Node(0);  
        arr[2].next = new Node(9);  
        arr[2].next.next = new Node(10);  
        arr[2].next.next.next = new Node(11);  
        
        mergeKLists(arr);
	}

	// https://leetcode.com/problems/merge-k-sorted-lists/
	public Node mergeKLists(Node[] lists) {

		PriorityQueue<Node> mMinHeap = new PriorityQueue<Node>(new Comparator<Node>() {

			@Override
			public int compare(Node n1, Node n2) {
				// TODO Auto-generated method stub
				return n1.data - n2.data;
			}
			
		});

		Node head = new Node(0);
		Node p = head;
		for (Node n : lists) {
			if(n != null) {
				mMinHeap.add(n);
			}
		}
		
		while (!mMinHeap.isEmpty()) {
			Node n = mMinHeap.poll();
			System.out.print(n.data + "\t");
			p.next = n;
			p = p.next;
			if(n.next != null) {
				mMinHeap.add(n.next);
			}
		}
		
		return head.next;
	}
	
	ListNode result;

	public ListNode reverseList(ListNode head) {
		if (head == null) {
			return new ListNode(0);
		}
		result = reverseList(head.next);
		result.next = new ListNode(head.val);

		return result.next;
	}
	
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		 ListNode result = new ListNode(0);
         ListNode curr = null;
        
         int carry = 0;
         int prev = 0;
         
        while(l1 != null || l2 != null) {
        	carry = 0;
            int data = 0;
          
            if(l1 != null) {
                data = l1.val;
                l1 = l1.next;
            }
            
            if(l2 != null) {
                data += l2.val;;
                l2 = l2.next;
            }
            
            
            if(data >= 10) {
                carry = data / 10;
                data = data % 10;
            }
            
            if(prev > 0) {
                data += prev;
            }
            
            if(data >= 10) {
            	 carry = data / 10;
                 data = data % 10;
            }
            
            prev = carry;
            
            ListNode tmp = new ListNode(data);
            if(curr == null){
                curr = tmp;
                result.next = curr;
            }else {
             curr.next = tmp;
             curr = curr.next;
            } 
        }
        
        if(prev > 0) {
        	curr.next = new ListNode(prev);
        }
        
        return result.next;
    }
	
	ListNode reversed = new ListNode(0);
    ListNode tmp = reversed;
    
    public ListNode reverseListRec(ListNode head) {
        if(head == null){
            return null;
        }
        reverseList(head.next);
        tmp.next = new ListNode(head.val);
        tmp = tmp.next;
        return reversed.next;
    }
    
    //https://leetcode.com/problems/remove-linked-list-elements/submissions/
    class Solution {
        public ListNode removeElements(ListNode head, int val) {
            if(head == null){
                return null;
            }
            ListNode result = head;
            ListNode prev = null;
            while(head != null){
               if(head.val == val){
                   if(prev == null){ // Remove 1st element
                        result = head.next;
                        head = head.next;
                   }else{
                       prev.next = head.next;
                       head = head.next;    
                   }
               }else{
                   prev = head;
                   head = head.next;
               }
               
            }
            return result;
        }
    }
    
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if(head == null) return null;
        ListNode dummy = new ListNode(0); // create a dummy node to mark the head of this list
        dummy.next = head;
        ListNode pre = dummy; // make a pointer pre as a marker for the node before reversing
        for(int i = 0; i<m-1; i++) pre = pre.next;
        
        ListNode start = pre.next; // a pointer to the beginning of a sub-list that will be reversed
        ListNode then = start.next; // a pointer to a node that will be reversed
        
        // 1 - 2 -3 - 4 - 5 ; m=2; n =4 ---> pre = 1, start = 2, then = 3
        // dummy-> 1 -> 2 -> 3 -> 4 -> 5
        
        for(int i=0; i<n-m; i++)
        {
            start.next = then.next;
            then.next = pre.next;
            pre.next = then;
            then = start.next;
        }
        
        // first reversing : dummy->1 - 3 - 2 - 4 - 5; pre = 1, start = 2, then = 4
        // second reversing: dummy->1 - 4 - 3 - 2 - 5; pre = 1, start = 2, then = 5 (finish)
        
        return dummy.next;
        
    }
    
    //https://leetcode.com/problems/reverse-linked-list-ii/
    //Try with recursion from m to n similar to reverse a list
    public ListNode reverseBetweenSec(ListNode head) {
        int m = 1;
        int n = 3;
        ListNode mPtr = head;
        ListNode nPtr = head;
        
        Stack<Integer> mStack = new Stack<>();
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        int trackN = 1;
        
        while(nPtr != null && trackN <= n){
            if(trackN == m-1){//When n crosses m
            	System.out.println("trackN: " + trackN);
                mPtr = nPtr;
            }else if(trackN >= m){
                mStack.push(nPtr.val);
            }
            System.out.println("nPtr: " + nPtr.val);
            nPtr = nPtr.next;
            
            trackN++;
        }
        
        while(!mStack.isEmpty()) {
            int val = mStack.pop();
            System.out.println("Stack val: " + val);
            ListNode node = new ListNode(val);
            mPtr.next = node;  
            mPtr = mPtr.next;
            System.out.println("mPtr val: " + mPtr.val);
        }
        mPtr.next = nPtr;
        if(m == 1) {
        	 return dummy.next.next;
        }
        return dummy.next;
    }
    
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null){
            return null;
        }
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        ListNode result = dummy;
        while(head != null) {
            
            if(head.val != dummy.val){
                dummy.next = new ListNode(head.val);
                dummy = dummy.next;
            }
           
            head = head.next;
        }
        return result.next;
    }
}
