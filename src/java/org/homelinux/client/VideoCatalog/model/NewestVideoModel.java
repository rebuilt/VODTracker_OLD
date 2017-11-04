/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoCatalog.model;


import java.util.HashMap;
import org.homelinux.client.Model;

/**
 * Provides a collection to hold the newest videos
 * @author nelson
 */
public class NewestVideoModel extends Model {

    HashMap<String, Integer> newest;

    public NewestVideoModel()
    {
        newest = new HashMap<String, Integer>();
    }

    public void setNewest(HashMap newestVids)
    {
        if (newest == null)
        {
            newest = newestVids;
            notifyObservers();
        } else if (newest != newestVids)
        {
            newest = newestVids;
            notifyObservers();

        }
    }

    public HashMap getNewest()
    {
        return newest;
    }
/**
 * Notifies all observers of a change
 */
    public void videosWatched()
    {
        notifyObservers();
    }
}
