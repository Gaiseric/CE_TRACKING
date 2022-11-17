package com.cositos.cetracking.ListaEnlazadas;

public class Linked_List {
    private Node head;
    private Node last;
    private int size;

    public Linked_List(){
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

    public Object GetHead() {
        
        return this.head.getData();
    }


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
