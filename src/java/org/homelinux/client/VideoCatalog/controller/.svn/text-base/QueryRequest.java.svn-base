package org.homelinux.client.VideoCatalog.controller;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import org.homelinux.client.networkServices.NetworkRequest;
import org.homelinux.client.PageFactory;
import org.homelinux.client.QueryObject;
import org.homelinux.client.VideoCatalog.model.VideoModel;

/**
 * Request a list of videos from the server using the provided QueryObject
 * @author nelson
 */
class QueryRequest extends NetworkRequest {

    QueryObject query = new QueryObject();

    public QueryRequest(QueryObject q)
    {
        query = q;
    }
    int retries = 0;

    public void send()
    {
        service.getVideos(query, new AsyncCallback() {

            public void onSuccess(Object result)
            {
                //     System.out.println("got msg from server");
                if (result instanceof ArrayList) {
                    VideoModel videoModel =
                            (VideoModel) PageFactory.getModel(PageFactory.VIDEOMODEL);
                    videoModel.setVideos((ArrayList) result);
                }
                done();

            }

            public void onFailure(Throwable caught)
            {
                if (retries < 3) {
                    send();
                    retries++;
                    System.out.println("Network error getting  videos.  Retrying.");
                } else {
                    System.out.println("Could not get videos. Giving up.");
                    done();
                }
            }
        });
    }
}
