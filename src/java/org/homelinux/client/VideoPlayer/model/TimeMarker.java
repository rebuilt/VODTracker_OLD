/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoPlayer.model;

import java.io.Serializable;
import org.homelinux.client.Model;


/**
 *
 * @author home
 */
public class TimeMarker extends Model implements Serializable{

    private String videoID;
   private  int time;
   private  int rating;

   public TimeMarker(){

   }
    public TimeMarker(String videoID, int time, int rating) {
        this.videoID = videoID;
        this.time = time;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public int getTime() {
        return time;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }
}
