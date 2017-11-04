/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoCatalog.model;

import org.homelinux.client.*;
import java.util.*;
import org.homelinux.client.VideoCatalog.model.Video.ATT;

import com.VODTracker.shared.Video;

/**
 * Provides a collection to hold a list of videos
 * @author nelson
 */
public class VideoModel extends Model {

    ArrayList<Video> videos;
    ArrayList<Video> top10;
    boolean hq = false;

    public boolean getHQ()
    {
        return hq;
    }

    public void setHQ(boolean highQuality)
    {
        hq = highQuality;
        this.notifyObservers();
    }

    public void setVideos(ArrayList<Video> videos)
    {
        this.videos = videos;
      printVideoInfo(videos);
        this.notifyObservers();
    }

    public ArrayList<Video> getVideos()
    {
        return videos;
    }
    QueryObject query;

    public void setQuery(QueryObject query)
    {
        this.query = query;
    }

    public void setTop10(ArrayList<Video> tops)
    {
        top10 = tops;
        this.notifyObservers();
    }

    public ArrayList<Video> getTop10()
    {
        return top10;
    }

    public QueryObject getQuery()
    {
        return query;
    }

    public VideoModel()
    {
        videos = new ArrayList<Video>();
        query = new QueryObject();
    }

    public void addVideo(Video v)
    {
        videos.add(v);
        this.notifyObservers();
    }

    public Iterator iterator()
    {
        return videos.iterator();
    }

    private void printVideoInfo(ArrayList<Video> videos)
    {
         for (Video vid : videos) {
            System.out.println(vid.getAttribute(ATT.VIDEOID));
            System.out.println(vid.getAttribute(ATT.TITLE));
            System.out.println(vid.getAttribute(ATT.DESCRIPTION));
            HashMap<String, String> list = vid.getPlayers();
            System.out.println("Players");
            for (String player : list.values()) {
                System.out.println("\t" + player);
            }
            list = vid.getMaps();
            System.out.println("Maps:");
            for (String map : list.values()) {

                System.out.println("\t" + map);
            }

            list = vid.getTeams();
            System.out.println("Teams:");
            for (String team : list.values()) {

                System.out.println("\t" + team);
            }

        }
    }
}
