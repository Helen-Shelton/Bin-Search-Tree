package testbstset;

//copied from lecture slides

public class StackTNode {
    public TNode element;
    public StackTNode next;
    StackTNode(TNode e, StackTNode n)
    {
        element = e; next = n;
    }
}//end TNode class for stack
