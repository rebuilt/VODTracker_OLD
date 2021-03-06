/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoCatalog.model;

import com.VODTracker.shared.Video;
import com.google.gwt.user.client.Cookies;
import java.util.ArrayList;
import org.homelinux.client.Model;
import org.homelinux.client.VideoCatalog.model.Video.ATT;

/**
 * Provides a collection for holding Playlists
 * @author nelson
 */
public class PlayListModel extends Model {

    ArrayList<Video> videoList = new ArrayList<Video>();

    public void clear()
    {
        videoList.clear();
        Cookies.setCookie("playLists", "");
        notifyObservers();
    }

    public void setVideos(ArrayList videos){
       videoList = videos;
       notifyObservers();
    }
    public ArrayList getVideos()
    {
        return videoList;
    }

    public void addVideo(Video video)
    {
        if(!videoList.contains(video)){
        videoList.add(video);
        addToCookie(video);
        notifyObservers();
        }
    }

    public void removeVideo(Video video)
    {
        videoList.remove(video);
        removeFromCookie(video);
        notifyObservers();
    }

    public String getPlayListCookie()
    {
        String playLists = Cookies.getCookie("playLists");
        if (playLists == null) {
            playLists = "";
        }
        return playLists;
    }

    private void addToCookie(Video video)
    {
        String playLists = getPlayListCookie();
        if(playLists.length()>2000){
            playLists = playLists.substring(12,playLists.length());
        }
        playLists += video.getAttribute(ATT.VIDEOID) + "@";
        Cookies.setCookie("playLists", playLists);
    }

    private void removeFromCookie(Video video)
    {
        String playLists = getPlayListCookie();
        playLists =
                playLists.replaceAll(video.getAttribute(ATT.VIDEOID) + "@", "");
        Cookies.setCookie("playLists", playLists);
    }

}
