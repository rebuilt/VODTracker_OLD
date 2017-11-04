/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.homelinux.client.VideoPlayer.controller;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import org.homelinux.client.VideoCatalog.model.Video;
import org.homelinux.client.VideoCatalog.model.Video.ATT;
import org.homelinux.client.networkServices.NetworkRequest;

/**
 *
 * @author home
 */
class GetTimeMarkerRequest extends NetworkRequest{
Video video;
    public GetTimeMarkerRequest(Video vid) {
        video = vid;
    }

    @Override
    public void send() {
            service.getTimeMarkers(video.getAttribute(ATT.VIDEOID), new AsyncCallback(){

            public void onFailure(Throwable caught) {
               done();
            }

            public void onSuccess(Object result) {
                if(result instanceof ArrayList){
                video.setTimeMarkers((ArrayList) result);

                }
               done();
            }
        });
    }

}
