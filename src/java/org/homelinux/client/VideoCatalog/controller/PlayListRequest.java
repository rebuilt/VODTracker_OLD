package org.homelinux.client.VideoCatalog.controller;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import org.homelinux.client.networkServices.NetworkRequest;
import org.homelinux.client.PageFactory;
import org.homelinux.client.VideoCatalog.model.PlayListModel;
import org.homelinux.client.VideoCatalog.model.Video;

/**
 * Request video information for list of videos in the users playlist queue.
 * The request is performed as list of video IDs separated by '@'.  <br/>
 * For example :  <br/><code> jl34l2erlw@lkj342ljnkl@
 * @author nelson
 */
class PlayListRequest extends NetworkRequest {

    String videos;

    public PlayListRequest(String vids)
    {
        videos = vids;
    }
    int retries = 0;

    public void send()
    {
        service.getVideos(videos, new AsyncCallback() {

            public void onFailure(Throwable caught)
            {
                if (retries < 3) {
                    send();
                    retries++;
                    System.out.println("Network error getting playlist videos.  Retrying.");
                } else {
                    System.out.println("Could not get playlist videos. Giving up.");
                    done();
                }
            }

            public void onSuccess(Object result)
            {
                PlayListModel playListModel =
                        (PlayListModel) PageFactory.getModel(PageFactory.PLAYLISTMODEL);
                ArrayList<Video> videos = (ArrayList) result;
                playListModel.setVideos(videos);

                done();
            }
        });
    }
}
