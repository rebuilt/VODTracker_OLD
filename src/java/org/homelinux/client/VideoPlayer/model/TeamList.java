/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.homelinux.client.VideoPlayer.model;

import java.util.HashMap;
import org.homelinux.client.Model;

/**
 *
 * @author nelson
 */
public class TeamList extends Model{
HashMap list = new HashMap();

public void setList(HashMap teams){
    if(!list.equals(teams)){
        list = teams;
        this.notifyObservers();
    }
}
public HashMap getList(){
    return list;
}
}
