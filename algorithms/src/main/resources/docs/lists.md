# Leetcode – Linked List Cycle
 
Given a linked list, determine if it has a cycle in it.

Analysis

If we have 2 pointers - fast and slow. It is guaranteed that the fast one will meet the slow one if there exists a circle.

The problem can be demonstrated in the following diagram:
linked-list-cycle

Java Solution

public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
 
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
 
            if(slow == fast)
                return true;
        }
 
        return false;
    }
}

# Leetcode – Linked List Cycle
 
Given a linked list, determine if it has a cycle in it.

Analysis

If we have 2 pointers - fast and slow. It is guaranteed that the fast one will meet the slow one if there exists a circle.

The problem can be demonstrated in the following diagram:
linked-list-cycle

Java Solution

public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
 
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
 
            if(slow == fast)
                return true;
        }
 
        return false;
    }
}

* LeetCode – Copy List with Random Pointer
 
A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.

Return a deep copy of the list.

Java Solution 1

We can solve this problem by doing the following steps:

copy every node, i.e., duplicate every node, and insert it to the list
copy random pointers for all newly created nodes
break the list to two
public RandomListNode copyRandomList(RandomListNode head) {
 
	if (head == null)
		return null;
 
	RandomListNode p = head;
 
	// copy every node and insert to list
	while (p != null) {
		RandomListNode copy = new RandomListNode(p.label);
		copy.next = p.next;
		p.next = copy;
		p = copy.next;
	}
 
	// copy random pointer for each new node
	p = head;
	while (p != null) {
		if (p.random != null)
			p.next.random = p.random.next;
		p = p.next.next;
	}
 
	// break list to two
	p = head;
	RandomListNode newHead = head.next;
	while (p != null) {
		RandomListNode temp = p.next;
		p.next = temp.next;
		if (temp.next != null)
			temp.next = temp.next.next;
		p = p.next;
	}
 
	return newHead;
}
The break list part above move pointer 2 steps each time, you can also move one at a time which is simpler, like the following:

while(p != null && p.next != null){
    RandomListNode temp = p.next;
    p.next = temp.next;
    p = temp;
}
Java Solution 2 - Using HashMap

From Xiaomeng's comment below, we can use a HashMap which makes it simpler.

public RandomListNode copyRandomList(RandomListNode head) {
	if (head == null)
		return null;
	HashMap<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();
	RandomListNode newHead = new RandomListNode(head.label);
 
	RandomListNode p = head;
	RandomListNode q = newHead;
	map.put(head, newHead);
 
	p = p.next;
	while (p != null) {
		RandomListNode temp = new RandomListNode(p.label);
		map.put(p, temp);
		q.next = temp;
		q = temp;
		p = p.next;
	}
 
	p = head;
	q = newHead;
	while (p != null) {
		if (p.random != null)
			q.random = map.get(p.random);
		else
			q.random = null;
 
		p = p.next;
		q = q.next;
	}
 
	return newHead;
}

* LeetCode – Merge Two Sorted Lists (Java)
 
Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.

Analysis

The key to solve the problem is defining a fake head. Then compare the first elements from each list. Add the smaller one to the merged list. Finally, when one of them is empty, simply append it to the merged list, since it is already sorted.

Java Solution

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
 
        ListNode p1 = l1;
        ListNode p2 = l2;
 
        ListNode fakeHead = new ListNode(0);
        ListNode p = fakeHead;
 
        while(p1 != null && p2 != null){
          if(p1.val <= p2.val){
              p.next = p1;
              p1 = p1.next;
          }else{
              p.next = p2;
              p2 = p2.next;
          }
 
          p = p.next;
        }
 
        if(p1 != null)
            p.next = p1;
        if(p2 != null)
            p.next = p2;
 
        return fakeHead.next;
    }
}

* LeetCode – Merge k Sorted Lists (Java)
 
Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.

Analysis

The simplest solution is using PriorityQueue. The elements of the priority queue are ordered according to their natural ordering, or by a comparator provided at the construction time (in this case).

Java Solution

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
 
//  Definition for singly-linked list.
class ListNode {
	int val;
	ListNode next;
 
	ListNode(int x) {
		val = x;
		next = null;
	}
}
 
public class Solution {
	public ListNode mergeKLists(ArrayList<ListNode> lists) {
		if (lists.size() == 0)
			return null;
 
		//PriorityQueue is a sorted queue
		PriorityQueue<ListNode> q = new PriorityQueue<ListNode>(lists.size(),
				new Comparator<ListNode>() {
					public int compare(ListNode a, ListNode b) {
						if (a.val > b.val)
							return 1;
						else if(a.val == b.val)
							return 0;
						else 
							return -1;
					}
				});
 
		//add first node of each list to the queue
		for (ListNode list : lists) {
			if (list != null)
				q.add(list);
		}
 
		ListNode head = new ListNode(0);
		ListNode p = head; // serve as a pointer/cursor
 
		while (q.size() > 0) {
			ListNode temp = q.poll();
			//poll() retrieves and removes the head of the queue - q. 
			p.next = temp;
 
			//keep adding next element of each list
			if (temp.next != null)
				q.add(temp.next);
 
			p = p.next;
		}
 
		return head.next;
	}
}
Time: log(k) * n.
k is number of list and n is number of total elements.

* LeetCode – Remove Duplicates from Sorted List
 
Given a sorted linked list, delete all duplicates such that each element appear only once.

For example,

Given 1->1->2, return 1->2.
Given 1->1->2->3->3, return 1->2->3.
Thoughts

The key of this problem is using the right loop condition. And change what is necessary in each loop. You can use different iteration conditions like the following 2 solutions.

Solution 1

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null)
            return head;
 
        ListNode prev = head;    
        ListNode p = head.next;
 
        while(p != null){
            if(p.val == prev.val){
                prev.next = p.next;
                p = p.next;
                //no change prev
            }else{
                prev = p;
                p = p.next; 
            }
        }
 
        return head;
    }
}
Solution 2

public class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null)
            return head;
 
        ListNode p = head;
 
        while( p!= null && p.next != null){
            if(p.val == p.next.val){
                p.next = p.next.next;
            }else{
                p = p.next; 
            }
        }
 
        return head;
    }
}

* LeetCode – Remove Duplicates from Sorted List II (Java)
 
Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.

For example, given 1->1->1->2->3, return 2->3.

Java Solution

public ListNode deleteDuplicates(ListNode head) {
    ListNode t = new ListNode(0);
    t.next = head;
 
    ListNode p = t;
    while(p.next!=null&&p.next.next!=null){
        if(p.next.val == p.next.next.val){
            int dup = p.next.val;
            while(p.next!=null&&p.next.val==dup){
                p.next = p.next.next;
            }
        }else{
            p=p.next;
        }
 
    }
 
    return t.next;
}
 * LeetCode – Partition List (Java)
 
Given a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.

You should preserve the original relative order of the nodes in each of the two partitions.

For example, given 1->4->3->2->5->2 and x = 3, return 1->2->2->4->3->5.

Java Solution

public class Solution {
    public ListNode partition(ListNode head, int x) {
        if(head == null) return null;
 
        ListNode fakeHead1 = new ListNode(0);
        ListNode fakeHead2 = new ListNode(0);
        fakeHead1.next = head;
 
        ListNode p = head;
        ListNode prev = fakeHead1;
        ListNode p2 = fakeHead2;
 
        while(p != null){
            if(p.val < x){
                p = p.next;
                prev = prev.next;
            }else{
 
                p2.next = p;
                prev.next = p.next;
 
                p = prev.next;
                p2 = p2.next;
            } 
        }
 
        // close the list
        p2.next = null;
 
        prev.next = fakeHead2.next;
 
        return fakeHead1.next;
    }
}

# LeetCode – LRU Cache (Java)
 
Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and set.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
set(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.

Analysis

The key to solve this problem is using a double linked list which enables us to quickly move nodes.

LRU-Cache

The LRU cache is a hash table of keys and double linked nodes. The hash table makes the time of get() to be O(1). The list of double linked nodes make the nodes adding/removal operations O(1).

Java Solution

Define a double linked list node.

class Node{
    int key;
    int value;
    Node pre;
    Node next;
 
    public Node(int key, int value){
        this.key = key;
        this.value = value;
    }
}
public class LRUCache {
    int capacity;
    HashMap<Integer, Node> map = new HashMap<Integer, Node>();
    Node head=null;
    Node end=null;
 
    public LRUCache(int capacity) {
        this.capacity = capacity;
    }
 
    public int get(int key) {
        if(map.containsKey(key)){
            Node n = map.get(key);
            remove(n);
            setHead(n);
            return n.value;
        }
 
        return -1;
    }
 
    public void remove(Node n){
        if(n.pre!=null){
            n.pre.next = n.next;
        }else{
            head = n.next;
        }
 
        if(n.next!=null){
            n.next.pre = n.pre;
        }else{
            end = n.pre;
        }
 
    }
 
    public void setHead(Node n){
        n.next = head;
        n.pre = null;
 
        if(head!=null)
            head.pre = n;
 
        head = n;
 
        if(end ==null)
            end = head;
    }
 
    public void set(int key, int value) {
        if(map.containsKey(key)){
            Node old = map.get(key);
            old.value = value;
            remove(old);
            setHead(old);
        }else{
            Node created = new Node(key, value);
            if(map.size()>=capacity){
                map.remove(end.key);
                remove(end);
                setHead(created);
 
            }else{
                setHead(created);
            }    
 
            map.put(key, created);
        }
    }
}

# LeetCode – Intersection of Two Linked Lists (Java)
 
Problem

Write a program to find the node at which the intersection of two singly linked lists begins.

For example, the following two linked lists:

A:          a1 -> a2
                    ->
                      c1 -> c2 -> c3
                    ->           
B:     b1 -> b2 -> b3
begin to intersect at node c1.

Java Solution

First calculate the length of two lists and find the difference. Then start from the longer list at the diff offset, 
iterate though 2 lists and find the node.

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int len1 = 0;
        int len2 = 0;
        ListNode p1=headA, p2=headB;
        if (p1 == null || p2 == null)
            return null;
 
        while(p1 != null){
            len1++;
            p1 = p1.next;
        }
        while(p2 !=null){
            len2++;
            p2 = p2.next;
        }
 
        int diff = 0;
        p1=headA;
        p2=headB;
 
        if(len1 > len2){
            diff = len1-len2;
            int i=0;
            while(i<diff){
                p1 = p1.next;
                i++;
            }
        }else{
            diff = len2-len1;
            int i=0;
            while(i<diff){
                p2 = p2.next;
                i++;
            }
        }
 
        while(p1 != null && p2 != null){
            if(p1.val == p2.val){
                return p1;
            }else{
 
            }
            p1 = p1.next;
            p2 = p2.next;
        }
 
        return null;
    }
}

* LeetCode – Reverse Linked List (Java)
 
Reverse a singly linked list.

Java Solution 1 - Iterative

public ListNode reverseList(ListNode head) {
    if(head==null || head.next == null) 
        return head;
 
    ListNode p1 = head;
    ListNode p2 = head.next;
 
    head.next = null;
    while(p1!= null && p2!= null){
        ListNode t = p2.next;
        p2.next = p1;
        p1 = p2;
        if (t!=null){
            p2 = t;
        }else{
            break;
        }
    }
 
    return p2;
}
Java Solution 2 - Recursive

public ListNode reverseList(ListNode head) {
    if(head==null || head.next == null)
        return head;
 
    //get second node    
    ListNode second = head.next;
    //set first's next to be null
    head.next = null;
 
    ListNode rest = reverseList(second);
    second.next = head;
 
    return rest;
}

* LeetCode – Reverse Linked List II (Java)
 
Reverse a linked list from position m to n. Do it in-place and in one-pass.

For example: given 1->2->3->4->5->NULL, m = 2 and n = 4, return 1->4->3->2->5->NULL.

Analysis

Java Solution

public ListNode reverseBetween(ListNode head, int m, int n) {
    if(m==n) return head;
 
    ListNode prev = null;//track (m-1)th node
    ListNode first = new ListNode(0);//first's next points to mth
    ListNode second = new ListNode(0);//second's next points to (n+1)th
 
    int i=0;
    ListNode p = head;
    while(p!=null){
        i++;
        if(i==m-1){
            prev = p;
        }
 
        if(i==m){
            first.next = p;
        }
 
        if(i==n){
            second.next = p.next;
            p.next = null;
        }
 
        p= p.next;
    }
    if(first.next == null)
        return head;
 
    // reverse list [m, n]    
    ListNode p1 = first.next;
    ListNode p2 = p1.next;
    p1.next = second.next;
 
    while(p1!=null && p2!=null){
        ListNode t = p2.next;
        p2.next = p1;
        p1 = p2;
        p2 = t;
    }
 
    //connect to previous part
    if(prev!=null)
        prev.next = p1;
    else
        return p1;
 
    return head;
}

# LeetCode – Remove Nth Node From End of List (Java)
 
Given a linked list, remove the nth node from the end of list and return its head.

For example, given linked list 1->2->3->4->5 and n = 2, the result is 1->2->3->5.

Java Solution 1 - Naive Two Passes

Calculate the length first, and then remove the nth from the beginning.

public ListNode removeNthFromEnd(ListNode head, int n) {
    if(head == null)
        return null;
 
    //get length of list
    ListNode p = head;
    int len = 0;
    while(p != null){
        len++;
        p = p.next;
    }
 
    //if remove first node
    int fromStart = len-n+1;
    if(fromStart==1)
        return head.next;
 
    //remove non-first node    
    p = head;
    int i=0;
    while(p!=null){
        i++;
        if(i==fromStart-1){
            p.next = p.next.next;
        }
        p=p.next;
    }
 
    return head;
}
Java Solution 2 - One Pass

Use fast and slow pointers. The fast pointer is n steps ahead of the slow pointer. When the fast reaches the end, the slow pointer points at the previous element of the target element.

public ListNode removeNthFromEnd(ListNode head, int n) {
    if(head == null)
        return null;
 
    ListNode fast = head;
    ListNode slow = head;
 
    for(int i=0; i<n; i++){
        fast = fast.next;
    }
 
    //if remove the first node
    if(fast == null){
        head = head.next;
        return head;
    }
 
    while(fast.next != null){
        fast = fast.next;
        slow = slow.next;
    }
 
    slow.next = slow.next.next;
 
    return head;
}

* LeetCode – Implement Stack using Queues (Java)
 
Implement the following operations of a stack using queues.
push(x) -- Push element x onto stack.
pop() -- Removes the element on top of the stack.
top() -- Get the top element.
empty() -- Return whether the stack is empty.

Note: only standard queue operations are allowed, i.e., poll(), offer(), peek(), size() and isEmpty() in Java.

Analysis

This problem can be solved by using two queues.

Java Solution

class MyStack {
    LinkedList<Integer> queue1 = new LinkedList<Integer>();
    LinkedList<Integer> queue2 = new LinkedList<Integer>();
 
    // Push element x onto stack.
    public void push(int x) {
        if(empty()){
            queue1.offer(x);
        }else{
            if(queue1.size()>0){
                queue2.offer(x);
                int size = queue1.size();
                while(size>0){
                    queue2.offer(queue1.poll());
                    size--;
                }
            }else if(queue2.size()>0){
                queue1.offer(x);
                int size = queue2.size();
                while(size>0){
                    queue1.offer(queue2.poll());
                    size--;
                }
            }
        }
    }
 
    // Removes the element on top of the stack.
    public void pop() {
        if(queue1.size()>0){
            queue1.poll();
        }else if(queue2.size()>0){
            queue2.poll();
        }
    }
 
    // Get the top element.
    public int top() {
       if(queue1.size()>0){
            return queue1.peek();
        }else if(queue2.size()>0){
            return queue2.peek();
        }
        return 0;
    }
 
    // Return whether the stack is empty.
    public boolean empty() {
        return queue1.isEmpty() & queue2.isEmpty();
    }
}

* LeetCode – Implement Queue using Stacks (Java)
 
Implement the following operations of a queue using stacks.

push(x) -- Push element x to the back of queue.
pop() -- Removes the element from in front of queue.
peek() -- Get the front element.
empty() -- Return whether the queue is empty.

Java Solution

class MyQueue {
 
    Stack<Integer> temp = new Stack<Integer>();
    Stack<Integer> value = new Stack<Integer>();
 
    // Push element x to the back of queue.
    public void push(int x) {
        if(value.isEmpty()){
            value.push(x);
        }else{
            while(!value.isEmpty()){
                temp.push(value.pop());
            }
 
            value.push(x);
 
            while(!temp.isEmpty()){
                value.push(temp.pop());
            }    
        }
    }
 
    // Removes the element from in front of queue.
    public void pop() {
        value.pop();
    }
 
    // Get the front element.
    public int peek() {
        return value.peek();
    }
 
    // Return whether the queue is empty.
    public boolean empty() {
        return value.isEmpty();
    }
}

* LeetCode – Palindrome Linked List (Java)
 
Given a singly linked list, determine if it is a palindrome.

Java Solution 1

We can create a new list in reversed order and then compare each node. The time and space are O(n).

public boolean isPalindrome(ListNode head) {
    if(head == null)
        return true;
 
    ListNode p = head;
    ListNode prev = new ListNode(head.val);
 
    while(p.next != null){
        ListNode temp = new ListNode(p.next.val);
        temp.next = prev;
        prev = temp;
        p = p.next;
    }
 
    ListNode p1 = head;
    ListNode p2 = prev;
 
    while(p1!=null){
        if(p1.val != p2.val)
            return false;
 
        p1 = p1.next;
        p2 = p2.next;
    }
 
    return true;
}
Java Solution 2

We can use a fast and slow pointer to get the center of the list, then reverse the second list and compare two sublists. 
The time is O(n) and space is O(1).

public boolean isPalindrome(ListNode head) {
 
    if(head == null || head.next==null)
        return true;
 
    //find list center
    ListNode fast = head;
    ListNode slow = head;
 
    while(fast.next!=null && fast.next.next!=null){
        fast = fast.next.next;
        slow = slow.next;
    }
 
    ListNode secondHead = slow.next;
    slow.next = null;
 
    //reverse second part of the list
    ListNode p1 = secondHead;
    ListNode p2 = p1.next;
 
    while(p1!=null && p2!=null){
        ListNode temp = p2.next;
        p2.next = p1;
        p1 = p2;
        p2 = temp;
    }
 
    secondHead.next = null;
 
    //compare two sublists now
    ListNode p = (p2==null?p1:p2);
    ListNode q = head;
    while(p!=null){
        if(p.val != q.val)
            return false;
 
        p = p.next;
        q = q.next;
 
    }
 
    return true;
}

* LeetCode – Odd Even Linked List (Java)
 
Problem

Given a singly linked list, group all odd nodes together followed by the even nodes. Please note here we are talking 
about the node number and not the value in the nodes.

The program should run in O(1) space complexity and O(nodes) time complexity.

Example:

Given 1->2->3->4->5->NULL,
return 1->3->5->2->4->NULL.
Analysis

This problem can be solved by using two pointers. We iterate over the link and move the two pointers.

Odd Even Linked List

Java Solution

public ListNode oddEvenList(ListNode head) {
    if(head == null) 
        return head;
 
    ListNode result = head;
    ListNode p1 = head;
    ListNode p2 = head.next;
    ListNode connectNode = head.next;
 
    while(p1 != null && p2 != null){
            ListNode t = p2.next;
            if(t == null)
                break;
 
            p1.next = p2.next;
            p1 = p1.next;
 
            p2.next = p1.next;
            p2 = p2.next;
    }
 
    p1.next = connectNode;
 
    return result;
}

