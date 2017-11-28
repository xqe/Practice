package com.example.xieqe.test001.Array_Link;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashSet;

/**
 * Created by xieqe on 2017/8/29.
 */

public class Test {
    class Node{
        private int data;
        private Node next;
        public Node(int data){
            this.data = data;
        }
    }

    class Stack<T>{
        ArrayList<T> list = new ArrayList<>();
        public void push(T t){
            list.add(t);
        }

        public T pop(){
            if (list.isEmpty()) {
                throw new EmptyStackException();
            }
            return list.remove(list.size() - 1);
        }

        public T peek(){
            if (list.isEmpty()) {
                throw new EmptyStackException();
            }
            return list.get(list.size() - 1);
        }

        public boolean isEmpty(){
            return list.isEmpty();
        }
    }

    public void deleteNode(Node head,Node node){
        if (node.next == null){
            while (head.next != null) {
                head = head.next;
            }
            head.next = null;
        }

        else if (head == node){
            head = null;
        }

        else {
            Node p = node.next;
            node.data = p.data;
            node.next = p.next;
        }
    }

    public Node deleteValue1(Node head,int value){
        Stack<Node> stack = new Stack<>();
        while (head.next != null) {
            if (head.data != value){
                stack.push(head);
                head = head.next;
            }
        }

        //倒序装载
        while (!stack.isEmpty()) {
            stack.peek().next = head;
            head = stack.pop();
        }
        return head;
    }

    public Node deleteValue2(Node head,int value){
        while (head.next != null) {
            if (head.data != value) {
                break;
            }
            head = head.next;
        }

        Node pre = head;
        Node cur = head;
        while (cur.next != null) {
            if (cur.data == value) {
                pre.next = cur.next;
            }else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }

    public Node deleteDuplication(Node head) {
        HashSet<Integer> set = new HashSet<>();
        set.add(head.data);
        Node pre = null;
        Node cur = head;
        while (cur.next != null) {
            if (set.contains(cur.data)) {
                pre.next = cur.next;
            }else {
                pre = cur;
                set.add(cur.data);
            }
            cur = cur.next;
        }
        return head;
    }

}
