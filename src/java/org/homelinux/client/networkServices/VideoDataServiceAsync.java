/*
 * VideoDataServiceAsync.java
 *
 * Created on February 10, 2009, 7:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.homelinux.client.networkServices;

import org.homelinux.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.homelinux.client.VideoPlayer.model.TimeMarker;
import org.homelinux.client.VideoPlayer.view.widgets.infopanel.INFOTYPE;

/**
 * Async Interface for getting video information from server.
 * @author nelson
 */
public interface VideoDataServiceAsync {

    public void addInfo(String attribute, String itemText, INFOTYPE type, AsyncCallback asyncCallback);

    public void getSimilar(String query, AsyncCallback asyncCallback);

    public void getVideos(QueryObject queryObject, AsyncCallback asyncCallback);

    public void getVideos(String IDs, AsyncCallback asyncCallback);

    public void removeInfo(String videoID, String info, INFOTYPE type, AsyncCallback asyncCallback);

    public void upVote(String id, AsyncCallback asyncCallback);

    public void countNewest(AsyncCallback asyncCallback);

    public void getPlayerList(AsyncCallback asyncCallback);

    public void getTeamList(AsyncCallback asyncCallback);

    public void getMapList(AsyncCallback asyncCallback);
    public void getTimeMarkers(String videoID,AsyncCallback asyncCallback );
    public void setTimeMarker(TimeMarker tm, AsyncCallback asyncCallback);
    public void changeTimeMarkerRating(TimeMarker tm, int delta, AsyncCallback asyncCallback);
}
