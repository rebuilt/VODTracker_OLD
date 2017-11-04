package org.homelinux.client.networkServices;

import org.homelinux.client.*;
import com.google.gwt.user.client.rpc.RemoteService;
import java.util.ArrayList;
import java.util.HashMap;
import org.homelinux.client.VideoCatalog.model.Video;
import org.homelinux.client.VideoPlayer.view.widgets.infopanel.INFOTYPE;
import org.homelinux.client.VideoPlayer.model.*;
/**
 * Interface for getting video information from server.
 * @author nelson
 */
public interface VideoDataService extends RemoteService {

    public void addInfo(String attribute, String itemText, INFOTYPE type);

    public ArrayList<Video> getVideos(QueryObject queryObject);

    public ArrayList<Video> getVideos(String id);

    public boolean upVote(String id);

    public void removeInfo(String videoID, String info, INFOTYPE type);

    public String getSimilar(String query);

    public HashMap countNewest();

    public HashMap getPlayerList();

    public HashMap getTeamList();

    public HashMap getMapList();
    public ArrayList getTimeMarkers(String videoID);
    public void setTimeMarker(TimeMarker tm);
    public void changeTimeMarkerRating(TimeMarker tm, int delta);
}
