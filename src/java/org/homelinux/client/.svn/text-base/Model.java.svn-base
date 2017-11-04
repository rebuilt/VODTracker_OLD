/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.homelinux.client;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A container that holds data and updates registered observers
 * @author nelson
 */
public abstract class Model  implements Serializable {
private ArrayList<Observer> observers ;

public Model(){
    observers = new ArrayList<Observer>();
}

public void addObserver(Observer obs){
    observers.add(obs);
}
public void removeObserver(Observer obs){
    observers.remove(obs);
}

public  void notifyObservers(){
    for(Observer obs: observers){
        obs.update(this);
    }
}


}
