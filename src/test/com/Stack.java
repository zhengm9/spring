package com;

import org.junit.Test;

import java.util.Comparator;

/**
 * Created by zhengming on 17/2/22.
 */
public class Stack<T> {
    class Node
    {
        T value;
        Node next;
        Node(T value, Node next)
        {
            this.value = value;
            this.next = next;
        }
    }

    private Stack.Node top;
    private T min;
    private Comparator<T> comparator;

    public T pop()
    {
        if(top == null)
            return null;
        Node popNode = top;
        top = top.next;
        return popNode.value;
    }

    public void push(T t)
    {
        Node newTopNode = new Node(t, top);

        if(comparator != null && (min == null || comparator.compare(min, t) >= 0))
        {
            min = t;
        }
        this.top = newTopNode;
    }

    public T getMin()
    {
        return (min == null)?null:min;
    }

    public boolean isEmpty()
    {
        return (top == null);
    }

    public Stack(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public Stack()
    {

    }


    public static void main(String[] args)
    {
        //stack基本功能测试
        Stack<Integer> stack = new Stack(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                if(o1 > o2)
                    return 1;
                else if(o1 < o2)
                    return -1;
                else
                    return 0;
            }
        });
        System.out.println("min:" + stack.getMin());

        stack.push(1);
        stack.push(2);
        stack.push(4);
        stack.push(3);
        System.out.println("min:" + stack.getMin());

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

        //递归reverse
        Stack<String> stackString = new Stack<String>();
        stackString.push("3");
        stackString.push("2");
        stackString.push("1");
        reverse(stackString);
        System.out.println("reverse pop:"+stackString.pop());
        System.out.println("reverse pop:"+stackString.pop());
        System.out.println("reverse pop:"+stackString.pop());

    }

    public static void reverse(Stack<String> stack)
    {
        if(stack.isEmpty())
        {
            return;
        }

        String lastStr = getAndRemoveLast(stack);
        reverse(stack);

        stack.push(lastStr);

    }

    public static String getAndRemoveLast(Stack<String> stack)
    {
        if(stack.isEmpty())
        {
            return null;
        }


        String top = stack.pop();
        String last = getAndRemoveLast(stack);
        if(last == null)
        {
            return top;
        }else {
            stack.push(top);
            return last;
        }
    }
}
