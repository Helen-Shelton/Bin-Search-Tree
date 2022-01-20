package testbstset;

//copied from lecture slides

public class Stack 
{
    private StackTNode top;
    
    public boolean isEmpty()// check if the stack is empty
    {
        if(top == null)
            return true;
        return false;
    }
            
    public void push(TNode element) // add a new element on the top of the stack
    {
        if(isEmpty())
            top = new StackTNode(element,null);
        else
            top = new StackTNode(element,top);
    }
    
    public TNode pop()// return and remove  the most recently "pushed" element
    {
        if (isEmpty())
            System.out.println("The stack is empty, stack underflow`");
        TNode Recent = top.element;
        top = top.next;
        return Recent;
    }
    
    public TNode top()//read and return  the most recently "pushed" element
    {
        return top.element;
    }
} //end of stack class
    
