package com.example.xieqe.test001.aidl;

import java.util.Date;
import java.util.LinkedList;  
import java.util.List;  
  
public class EventStorage {  
      
    private int maxSize;  
      
    private LinkedList<Date> storage;
      
    public EventStorage() {  
        maxSize = 10;  
        storage = new LinkedList<>();
    }  
      
    public synchronized void set() {  
        while(storage.size() == maxSize) {  
            try {  
                wait();  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
          
        storage.add(new Date());
        notifyAll();
    }  
      
    public synchronized Date get() {
        while(storage.size() == 0) {  
            try {  
                wait();  
            } catch (InterruptedException e) {  
                e.printStackTrace();
            }  
        }
        Date date = storage.poll();
        notifyAll();
        return date;
    }  
}  
