package com.cositos.LinkedList;

public class LinkedList {
    private Node head;
    private Node last;
    private int size;
    private int totalweighted;

    public LinkedList(){
        this.head= null;
        this.last= null;
        this.size= 0;
    }

    
    /** 
     * @return boolean
     */
    public boolean isEmpty(){
        return this.head==null;
    }
    
    
    /** 
     * @return int
     */
    public int size(){
        return this.size;
    }

    public void resetWithHead() {
        this.last= this.head;
        this.size= 1;
        this.totalweighted= 0;
    }
    
    /** 
     * @param data
     */
    public void insertFirst(Object data){
        Node newNode= new Node(data);
        if (this.isEmpty()) {
            this.head= this.last= newNode;
        } else {
            newNode.setNext(this.head);
            this.head= newNode;
            this.size++;
        }
    }

    public void insertLast(Object data, int weigth){
        Node newNode= new Node(data);

        if (this.isEmpty()){
            this.head = this.last = newNode;
        } else {
            this.last.setNext(newNode);
            this.last= newNode;
        }
        this.totalweighted= this.totalweighted + weigth;
        this.size++;
    }

    
    /** 
     * @return Node
     */
    public Node deleFirts(){
        if(this.head!=null){
            Node temp= this.head;
            this.head= this.head.getNext();
            this.size--;
            return temp;
        } else{
            return null;
        }
    }

    public void displayList(){
        Node current= this.head;
        String lista= "";
        while (current!=null){
            lista= lista+ current.getData() + ", ";
            current= current.getNext();
        }
        System.out.println(lista + "con un peso total de: " + this.totalweighted);
    }

    public Object GetHead() {
        
        return this.head.getData();
    }


    public Object GetNext (Object searchValue) {
        Node current = this.head;

        while(current!=null) {
            if (current.getData().equals(searchValue)) {
                break;
            } else {
                current = current.getNext();
            }
        }

        if (current.getNext() != null){
            return current.getNext().getData();
        } else {
            return null;
        } 
        
        
    }
}
