package com.cositos.cetracking.ListaEnlazadas;


public class Node {
    private Object data;
    private Node next;
    private Node previous;
    private int times;

    public Node(Object data){
        this.next = null;
        this.previous = null; 
        this.data = data;
        this.times=1;
    } 

    
    /** 
     * @return Object
     */
    public Object getData(){
        return this.data;
    }

    
    /** 
     * @param data
     */
    public void setData(Object data){
        this.data= data;
    }

    public void add(){
        this.times++;
    }

    public void eliminate(){
        this.times--;
    }

    
    /** 
     * @return int
     */
    public int gettime(){
        return this.times;
    }

    
    /** 
     * @return Node
     */
    public Node getNext(){
        return this.next;
    }

    
    /** 
     * @return Node
     */
    public Node getPrevious(){
        return this.previous;
    }

    
    /** 
     * @param node
     */
    public void setNext(Node node){
        this.next= node;
    }

    
    /** 
     * @param node
     */
    public void setPrevious(Node node){
        this.previous= node;
    }
        
    
}
