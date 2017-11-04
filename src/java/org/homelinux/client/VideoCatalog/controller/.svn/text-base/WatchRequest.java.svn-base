package org.homelinux.client.VideoCatalog.controller;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.homelinux.client.networkServices.NetworkRequest;

/**
 * Reqests a list of similar videos based on a videoID sent to the server
 * @author nelson
 */
class WatchRequest extends NetworkRequest {

    String query = "";

    public WatchRequest(String q)
    {
        query = q;
    }
    int retries = 0;

    public void send()
    {
        service.getSimilar(query, new AsyncCallback() {

            public void onFailure(Throwable arg0)
            {
                if (retries < 3) {
                    send();
                    retries++;
                    System.out.println("Network error getting videos.  Retrying.");
                } else {
                    System.out.println("Could not get videos. Giving up.");
                    done();
                }
            }

            public void onSuccess(Object arg0)
            {
                //              System.out.println("Making the following playlist: " + arg0);
                String st = (String) arg0;
                History.newItem("w" + st);
                done();
            }
        });
    }
}
