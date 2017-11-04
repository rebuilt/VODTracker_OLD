package org.homelinux.client.VideoCatalog.controller;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import org.homelinux.client.networkServices.NetworkRequest;
import org.homelinux.client.PageFactory;
import org.homelinux.client.QueryObject;
import org.homelinux.client.VideoCatalog.model.VideoModel;

/**
 * Request a list of the top 10 rated videos for the week
 * @author nelson
 */
class LoadTop10Request extends NetworkRequest {

    QueryObject queryObject;

    public LoadTop10Request(QueryObject query)
    {
        queryObject = query;
    }
    int retries = 0;

    public void send()
    {
        service.getVideos(queryObject, new AsyncCallback() {

            public void onFailure(Throwable arg0)
            {
                if (retries < 3) {
                    send();
                    retries++;
                    System.out.println("Network error getting list of top10.  Retrying.");
                } else {
                    System.out.println("Could not get list of top10. Giving up.");
                    done();
                }
            }

            public void onSuccess(Object arg0)
            {
                ArrayList top10 = (ArrayList) arg0;
                VideoModel videoModel =
                        (VideoModel) PageFactory.getModel(PageFactory.VIDEOMODEL);
                videoModel.setTop10(top10);
                done();
            }
        });
    }
}
