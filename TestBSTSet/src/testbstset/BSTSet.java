package testbstset;

//note: some methods were modified from lecture notes

public class BSTSet {
    private TNode root;
    
    public BSTSet()
    {
        root = null;
    }
    
    public BSTSet(int[] input)
    {
        if(input == null)//check for null array
        {
            root = null;
            return;
        }
        input = sortArray(input); //sort array 
        makeBST(input, input.length-1, 0); //make into BST
    }
    
    private int[] sortArray(int[] input)//bubble sort algorithm, sorted in ascending order. runtime: n!
    {
        int temp;  
        for(int i = 0; i < input.length; i++)
        {
            for(int j = 1; j<(input.length-i); j++)
            {
                if(input[j-1] > input[j])
                { 
                    temp = input[j-1];  
                    input[j-1] = input[j];  
                    input[j] = temp; 
                }
            }//end inner for loop
        }//end outer for loop
        return input;
    }
    
    private void makeBST(int[] array, int top, int bot)
    {
        if(bot <= top)
        {
            int mid = (bot + top + 1)/2; //calculate middle of every sub array
            this.add(array[mid]); //add middle element to tree, add() will not add duplicates
            makeBST(array, mid-1,bot); //calculate new subtree for left half
            makeBST(array, top, mid+1); //calculate new subtree for right half
        }
    }
    
    public boolean isIn(int v)
    {
        if(root == null) //checks for null BST
            return false;
        TNode p = root;//pointer to elements in tree (will traverse the tree)
        while (p != null) //while p is not null
        {
            if(p.element == v)//if they are equal, stop looking
                return true;
            else if(p.element < v) //if pointer is less than v, move right
                p = p.right;
            else if (p.element > v) //if pointer is greater than v, move left
                p = p.left;
        }
        return false; //if while loop exits, v was not found
    }
    
    public void add(int v)
    {
        if(root == null) //if its an empty set
            root = new TNode(v, null, null); //add v as parent node
        else
        {
            TNode p = root; //pointer traverses the tree
            while(p.element != v)//while the value of p is not v
            {
                if(p.element < v) //if pointer is less than v, move right
                {
                    if(p.right == null)//if the right position is empty
                    {
                        p.right = new TNode(v, null, null); //add v to the right spot
                        break;
                    }
                    else p = p.right; //if not move right
                }
                else if(p.element > v) //if pointer is greater than v, move left
                {
                    if(p.left == null)//if the left position is empty
                    {
                        p.left = new TNode(v, null, null); //add v to the left spot
                        break;
                    }
                    else p = p.left; //if not move left
                }
            }//end while
        }//end else
    }
    
    public boolean remove(int v)
    {
        if (isIn(v) == true)
        {
            remove2(v, root);
            return true;
        }
        else 
            return false;
    }
    
    private TNode remove2(int v, TNode p)
    {
        if(p == null)
            System.out.println("The tree is empty");
        else 
        {
            if(p.element > v) //moves through the tree to find v
                p.left = remove2(v, p.left);
            else if(p.element < v){
                p.right = remove2(v, p.right);}
            else if((p.left != null) && (p.right != null)) //if v is found and it has 2 children
            {
                p.element = (findMin(p.right)).element; //overwrites the v value with the smallest node in the right subtree
                p.right = deleteMin(p.right);//deletes min by overwriting it with its right sub child
            }
            else
                p = (p.left != null) ? p.left : p.right;//if only 1 child or no children, replace with only child or null
        }//end else
        return p;
    }
    
    private TNode findMin(TNode p1)//goes to the smallest node of any subtree
    {
        while (p1.left != null) //exits at the lowest number
            p1 = p1.left;
        return p1;
    }
    private TNode deleteMin(TNode p1) //traverses subtree to find right child of min node
    {
        if(p1.left != null)
        {
            p1.left = deleteMin(p1.left);
            return p1;
        }
        else
            return p1.right;
    }
    
    //end of remove methods
    
    public BSTSet union(BSTSet s)
    {
        if (this.root == null)//checks if this set is empty
            return s;
        if (s.root==null)//checks if s set is empty
            return this;
        
        BSTSet unionSet = new BSTSet();//stores all the elements of this set
        copySet(this.root, unionSet); //makes a copy of this set
        
        TNode p = s.root;//pointer to s set
        unionSet.union2(p);//adds all elements from set s into new this set copy, recursively. Add() ensures no repeats
        return unionSet;//return set
    }
    
    private void copySet(TNode p1, BSTSet set)
    {
        if(p1 != null)//if set is not empty
        {
            set.add(p1.element);//adds the current node to the tree
            if(p1.left != null) //if there is a left child
                copySet(p1.left,set); //traverse left subtree, recursively
            if(p1.right != null)//if there is a right child
                copySet(p1.right,set);//traverse right subtree recursively
        }
   }
    
    private BSTSet union2(TNode p1)
    {
        if (p1 != null)//if set is not empty
        {
            this.add(p1.element);//traverse tree recursively and add current node to the tree
            this.union2(p1.left);//travel down the left subtree, recursively
            this.union2(p1.right);//travel down the right subtree, recursively
        }
        return this;
    }
    
    //end of union methods
    
    public BSTSet intersection(BSTSet s)
    {
        if (this.root == null || s.root == null)//If either set is empty, then the intersection set is empty
            return new BSTSet();
        
        TNode p = s.root;//new node points at the root of set s
        BSTSet interSet = new BSTSet();//creates a new tree to Store the intersection of this and s
        intersection2(p,interSet); //put into recursive intersection function

        return interSet;//returns the new set
    }
    
    private void intersection2(TNode p1, BSTSet set)//recursive function
    {
        if (p1 != null)//if the set is not empty
        {
            if (this.isIn(p1.element)) //if element at p1 is also in this
                set.add(p1.element); //add p1 to set
            if(p1.left != null) //traverse the left subtree
                this.intersection2(p1.left,set);
            if(p1.right != null) //traverse the right sub tree
                this.intersection2(p1.right,set);
        }
    }
    
    //end of intersection methods
    
    public BSTSet difference(BSTSet s)
    {
        TNode p = s.root;
        BSTSet diffSet = new BSTSet();
        difference2(p,diffSet);
        
        return diffSet;
    }
    
    private void difference2(TNode p,BSTSet set) //recursive function
    {
        if (p != null)//if the set is not empty
        {
            if (!this.isIn(p.element)) //if node at p1 is also not in this
                set.add(p.element); //add p to set
            if(p.left != null) //traverse the left subtree
                this.difference2(p.left,set);
            if(p.right != null) //traverse the right sub tree
                this.difference2(p.right,set);
        }
    }
    
    //end of difference methods
    
    public int size()
    {
        return size2(root);
    }
    
    private int size2(TNode p)//used for recursive method of finding number of nodes
    {
        if(p == null)
            return 0;
        else 
            return (size2(p.left) + size2(p.right) + 1);
    }
    
    //end of size methods
    
    public int height()
    {
        return height2(root);
    }
    
    private int height2(TNode p) //used for recursive method of finding height
    {
        if(p == null)//no nodes means a non-existent height
            return -1;
        else 
            return Math.max(height2(p.left)+1, height2(p.right)+1); //as it traverses the tree, the max height wil always be returned, thus the final product will always be the accumulation of the max height of the subtrees
    }
    
    //end of height methods
    
    public void printBSTSet() //given in lab
    {
        if(root==null)
            System.out.println("The set is empty");
        else{
            System.out.print("The set elements are: ");
            printBSTSet(root);
            System.out.print("\n");
        }
    }
    
    private void printBSTSet(TNode t) //given in lab
    {
        if(t!=null)
        {
            printBSTSet(t.left);
            System.out.print(" "+t.element+", ");
            printBSTSet(t.right);
        }
    }
    
    public void printNonRec()
    {
        Stack stk = new Stack();//make empty stack
        TNode p = root;
        
        while (p != null || stk.isEmpty() == false) //while node is not null and the stack is not empty
        {
            if (p != null) //if not null
            {
                stk.push(p); //push p onto the stack
                p = p.left;
            } 
            else if (stk.isEmpty() == false) //if not an empty stack
            {
                p = stk.pop(); //pop the top element
                System.out.print(p.element + ", "); //print element
                p = p.right; //move right
            }
        }
        System.out.println("\n");
    } //end BSTSet
    
}
