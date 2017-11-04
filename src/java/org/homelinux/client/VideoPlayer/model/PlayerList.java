/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.homelinux.client.VideoPlayer.model;

import java.util.ArrayList;
import java.util.HashMap;
import org.homelinux.client.Model;

/**
 *
 * @author nelson
 */
public class PlayerList extends Model{
HashMap list = new HashMap();

public void setList(HashMap players){
    if(!list.equals(players)){
        list = players;
        this.notifyObservers();
    }
}
public HashMap getList(){
    return list;
}
}
