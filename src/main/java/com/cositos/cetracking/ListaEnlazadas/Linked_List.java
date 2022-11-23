package com.cositos.cetracking.ListaEnlazadas;

public class Linked_List {
    private Node head;
    private Node last;
    private int size;

    // The constructor of the class Linked_List.
    public Linked_List(){
        this.head= null;
        this.last= null;
        this.size= 0;
    }

    /**
     * 
     * @return The method isEmpty() returns a boolean value.
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

    
    /** 
     * @param data
     */
    public void insertLast(Object data){
        Node newNode= new Node(data);

        if (this.isEmpty()){
            this.head = this.last = newNode;
        } else {
            this.last.setNext(newNode);
            this.last= newNode;
        }
        this.size++;
    }

    /** 
     * @param data
     * @return boolean
     * Checking if the data is already in the list.
     */
    public boolean InsertLastUnique(Object data) {
        Node current= this.head;
        while(current!=null){
            if(current.getData().equals(data)){
                current.add();
                return false;
            } else {
                current = current.getNext();
            }
        }
        insertLast(data);
        return true;
    }

    
    /** 
     * @param data
     * @return boolean
     */
    public boolean eliminate(Object data){
        Node current= this.head;
        Node previous= current;
        while(current!=null){
            if(current.getData().equals(data)){
                if(current.gettime()<=1){
                    previous.setNext(current.getNext());
                    return true;
                } else{
                    current.eliminate();
                    return false;
                }
            } else{
                previous= current;
                current= current.getNext();
            }
        }

        return false;
    }

    
    /** 
     * @param data
     */
    public void eliminateall(Object data){
        Node current= this.head;
        Node previous= current;
        while(current!=null){
            if(current.getData().equals(data)){
                previous.setNext(current.getNext());
            } else{
                previous= current;
                current= current.getNext();
            }
        }
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
        System.out.println(lista);
    }

    
    /** 
     * @return Object
     */
    public Object GetHead() {
        
        return this.head.getData();
    }


    
    /** 
     * @param searchValue
     * @return Object
     */
    public Object GetNext (Object searchValue) {
        Node current = this.head;

        while(current!=null) {
            if (current.getData().equals(searchValue)) {
                return current.getNext().getData();
            } else {
                current = current.getNext();
            }
        }
        return null; 
    }
}
