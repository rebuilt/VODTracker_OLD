/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.homelinux.client.VideoPlayer.controller;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.homelinux.client.VideoPlayer.model.TimeMarker;
import org.homelinux.client.networkServices.NetworkRequest;

/**
 *
 * @author nelson
 */
class SetTimeMarkerRequest extends NetworkRequest{
TimeMarker marker ;
    public SetTimeMarkerRequest(TimeMarker marker)
    {
        this.marker = marker;
    }

    @Override
    public void send()
    {
        service.setTimeMarker(marker, new AsyncCallback(){

            public void onFailure(Throwable caught)
            {
               done();
            }

            public void onSuccess(Object result)
            {

               done();
            }
        });
    }

}
