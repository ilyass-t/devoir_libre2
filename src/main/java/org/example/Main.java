package org.example;

public class Main {
    public static void main(String[] args) {
        Object key =new Object();
        InsertOrdersThread insertOrdersThread=new InsertOrdersThread(key);
        AfficheOrdersThread afficheOrdersThread=new AfficheOrdersThread(key);
        insertOrdersThread.start();
        afficheOrdersThread.start();

        
        
    }
}