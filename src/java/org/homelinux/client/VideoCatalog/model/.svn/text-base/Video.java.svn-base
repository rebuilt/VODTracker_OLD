/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoCatalog.model;

import java.util.ArrayList;
import java.util.HashMap;
import org.homelinux.client.Model;
import org.homelinux.client.VideoPlayer.model.TimeMarker;

/**
 * A data structure for holding video information.  Video information is held in an array that contains the following :<br/><code> "VIDEOID", "PUBLISHED", "TITLE", "AUTHOR", "URI", "DESCRIPTION", "PLAYERURL", "THUMBNAILURL", "DURATION", "GOSURATING" </code>
 * @author nelson
 * 		
 */
//VIDEOID, PUBLISHED, TITLE, CONTENT, AUTHOR, URI, DESCRIPTION, PLAYERURL, THUMBNAILURL, MEDIACONTENTURL, VIEWCOUNT, FAVORITECOUNT, NUMRATERS, AVGYTRATING, DURATION, GOSURATING, DATEPROMOTED
public class Video extends Model {

    String[] data;
    String[] columnNames = {"VIDEOID", "PUBLISHED", "TITLE", "AUTHOR", "URI", "DESCRIPTION", "PLAYERURL", "THUMBNAILURL", "DURATION", "GOSURATING"};

    HashMap<String, String> players = new HashMap<String, String>();
    HashMap<String, String> teams = new HashMap<String, String>();
    HashMap<String, String> maps = new HashMap<String, String>();
    ArrayList<TimeMarker> timeMarkers = new ArrayList<TimeMarker>();
    public Video(){
        
    }
    public Video(final String[] d) {
        data = d;
    }

    public void setMaps(HashMap<String, String> maps)
    {
        this.maps = maps;
    }

    public void setPlayers(HashMap<String, String> players)
    {
      this.players = players;
    }

    public void setTeams(HashMap<String, String> teams)
    {
        this.teams = teams;
    }
    public void setTimeMarkers(ArrayList markers){
        this.timeMarkers = markers;
        this.notifyObservers();
    }
    public HashMap<String,String> getPlayers(){
        return players;
    }
    public HashMap<String,String> getTeams(){
        return teams;
    }
    public HashMap<String,String> getMaps(){
        return maps;
    }
    public ArrayList<TimeMarker> getTimeMarkers(){
        return timeMarkers;
    }
    public String getAttribute(ATT c) {
        return data[c.ordinal()];
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < columnNames.length; i++) {
            out.append( columnNames[i]);
            out.append( ": " );
            out.append( data[i]);
            out.append("\n");
        }
        return out.toString();
    }

    public int hashCode() {
        return this.getAttribute(ATT.TITLE).length();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Video) {

            return this.getAttribute(ATT.VIDEOID).equals(((Video) obj).getAttribute(ATT.VIDEOID));
        }
        return false;
    }

    public enum ATT {
        VIDEOID, PUBLISHED, TITLE, AUTHOR, URI, DESCRIPTION, PLAYERURL, THUMBNAILURL,  DURATION, GOSURATING
    }
}
