package org.homelinux.client.VideoCatalog.controller;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.HashMap;
import org.homelinux.client.networkServices.NetworkRequest;
import org.homelinux.client.PageFactory;
import org.homelinux.client.VideoCatalog.model.NewestVideoModel;

/**
 * Request a count the newest videos of the week grouped by youtube account
 * @author nelson
 */
class NewVidCountRequest extends NetworkRequest {

    public NewVidCountRequest()
    {
    }
int retries = 0;

    public void send()
    {
        service.countNewest(new AsyncCallback() {

            public void onFailure(Throwable caught)
            {
                if (retries < 3) {
                    send();
                    retries++;
                    System.out.println("Network error getting list of newest videos.  Retrying.");
                } else {
                    System.out.println("Could not get list of newest videos. Giving up.");
                    done();
                }
            }

            public void onSuccess(Object result)
            {
                HashMap map = (HashMap) result;
                NewestVideoModel newest =
                        (NewestVideoModel) PageFactory.getModel(PageFactory.NEWESTVIDEOMODEL);
                newest.setNewest(map);
                done();

            }
        });
    }
}
