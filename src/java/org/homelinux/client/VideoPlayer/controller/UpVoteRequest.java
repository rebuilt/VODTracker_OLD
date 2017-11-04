
package org.homelinux.client.VideoPlayer.controller;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.homelinux.client.networkServices.NetworkRequest;

/**
 * A class for connecting to the server to request an increment to the vote tally
 * @author nelson
 */
public class UpVoteRequest extends NetworkRequest {

    String id = "";

    public UpVoteRequest(String d)
    {
        this.id = d;
    }

    public void send()
    {
        service.upVote(id, new AsyncCallback() {

            public void onFailure(Throwable arg0)
            {
                done();
            }

            public void onSuccess(Object arg0)
            {
                done();
            }
        });
    }
}
