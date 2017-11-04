/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoPlayer.controller;

import org.homelinux.client.VideoPlayer.view.widgets.infopanel.EditableInfoSection;
import org.homelinux.client.networkServices.Service;
import org.homelinux.client.networkServices.NetworkQueue;
import org.homelinux.client.networkServices.NetworkRequest;
import org.homelinux.client.networkServices.VideoDataServiceAsync;
import com.google.gwt.user.client.Cookies;
import org.homelinux.client.*;
import org.homelinux.client.VideoCatalog.model.NewestVideoModel;
import org.homelinux.client.VideoCatalog.model.Video;
import org.homelinux.client.VideoCatalog.model.Video.ATT;
import org.homelinux.client.VideoCatalog.model.VideoModel;
import org.homelinux.client.VideoCatalog.view.VideoCatalog;
import org.homelinux.client.VideoPlayer.model.TimeMarker;
import org.homelinux.client.VideoPlayer.view.widgets.infopanel.INFOTYPE;

/**
 * Provides classes that process and respond to events initialized by a <code>VideoPlayer</code> page or related panel
 * @author nelson
 */
public class VideoPlayerController implements Controller {

    Video video;
    VideoModel videoModel;
    VideoDataServiceAsync service = Service.getService();

    public VideoPlayerController(VideoModel videoList, Page page)
    {
        videoModel = videoList;
    }

    public void addInfo(String videoID, String itemText, INFOTYPE type)
    {
        NetworkRequest nr = new AddInfoRequest(videoID, itemText, type);
        NetworkQueue.addRequest(nr);
    }

    public void changeTimeMarkerRating(TimeMarker tm, int i)
    {
        NetworkRequest nr = new TimeMarkerRatingRequest(tm, i);
        NetworkQueue.addRequest(nr);
    }

    public boolean getHQ()
    {
        return videoModel.getHQ();
    }

    public void loadMapList()
    {
        NetworkRequest nr = new MapListRequest();
        NetworkQueue.addRequest(nr);
    }

    public void loadPlayerList()
    {
        NetworkRequest nr = new PlayerListRequest();
        NetworkQueue.addRequest(nr);
    }

    public void loadTeamList()
    {
        NetworkRequest nr = new TeamListRequest();
        NetworkQueue.addRequest(nr);
    }

    public void query(QueryObject query)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void markAsWatched(Video video)
    {
        String watched =
                Cookies.getCookie("watched" + video.getAttribute(ATT.AUTHOR));
        String cookieName = "watched" + video.getAttribute(ATT.AUTHOR);
        int days = Defaults.dateCode(video.getAttribute(ATT.PUBLISHED));

        if (watched != null) {
            if (!watched.contains(video.getAttribute(ATT.VIDEOID))) {
                if (watched.length() > 2000) {
                    watched = watched.substring(17, watched.length());
                }
                watched += video.getAttribute(ATT.VIDEOID) + "/" + days + "@";
            }
        } else {
            watched += video.getAttribute(ATT.VIDEOID) + "/" + days + "@";
        }

        // System.out.println("Setting watched cookie as: " + watched);

        Cookies.setCookie(cookieName, watched, Defaults.getExpiration());
        NewestVideoModel newest =
                (NewestVideoModel) PageFactory.getModel(PageFactory.NEWESTVIDEOMODEL);
        newest.videosWatched();
        VideoCatalog videoGallery = (VideoCatalog) PageFactory.getPage("v");
        videoGallery.setNewest(video.getAttribute(ATT.VIDEOID));
    }

    public void remove(String videoID, String info, INFOTYPE type)
    {
        NetworkRequest rq = new RemoveInfoRequest(videoID, info, type);
        NetworkQueue.addRequest(rq);
    }

    public void upVote(String id)
    {
        NetworkRequest rq = new UpVoteRequest(id);
        NetworkQueue.addRequest(rq);
    }

    public void getVideos(final String query)
    {

        NetworkRequest nr = new VideoRequest(query);
        NetworkQueue.addRequest(nr);
    }

    public VideoModel getVideoModel()
    {
        return videoModel;
    }

    public void setVideoModel(VideoModel videoModel)
    {
        this.videoModel = videoModel;
    }

    public void loadTimeMarkers(Video video)
    {
        NetworkRequest rq = new GetTimeMarkerRequest(video);
        NetworkQueue.addRequest(rq);
    }

    public void setTimeMarker(TimeMarker marker)
    {
        NetworkRequest rq = new SetTimeMarkerRequest(marker);
        NetworkQueue.addRequest(rq);
    }
}
