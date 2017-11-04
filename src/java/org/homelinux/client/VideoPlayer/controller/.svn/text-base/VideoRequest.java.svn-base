

package org.homelinux.client.VideoPlayer.controller;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import org.homelinux.client.networkServices.NetworkRequest;
import org.homelinux.client.PageFactory;
import org.homelinux.client.VideoPlayer.view.VideoPlayer;
import org.homelinux.client.VideoCatalog.model.Video;

/**
 * Requests a list of video information from the server based on the given video IDs separated by '@'.  For example: <br/>
 * <code>ser342vsdew@3fseadfewq53@</code>
 * @author nelson
 */
public class VideoRequest extends NetworkRequest{
String query = "";
int failCount = 0;
    VideoRequest(String q)
    {
       query = q;
    }


         public void send()
            {

                service.getVideos(query, new AsyncCallback() {

                    public void onFailure(Throwable arg0)
                    {
                        failCount++;
                        if (failCount < 3) {
                            service.getVideos(query, this);
                        }

                        System.out.println("failed to get the video requested\n" + failCount + " attempt(s) made.");
                        done();
                    }

                    public void onSuccess(Object arg0)
                    {
                        VideoPlayer videoPlayer =
                                (VideoPlayer) PageFactory.getPage("w");
                        ArrayList<Video> list = (ArrayList) arg0;
                        videoPlayer.createView(list);
                        done();
                    }
                });

            }


}
