/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.homelinux.client.VideoPlayer.controller;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.homelinux.client.VideoPlayer.view.widgets.infopanel.INFOTYPE;
import org.homelinux.client.networkServices.NetworkRequest;

/**
 *
 * @author nelson
 */
public class RemoveInfoRequest extends NetworkRequest{
String videoID;
String info;
INFOTYPE type;
    public RemoveInfoRequest(String videoID, String info, INFOTYPE type){
        this.videoID = videoID;
        this.info = info;
        this.type = type;
    }
    public void send()
    {
        service.removeInfo(videoID,info,type,new AsyncCallback(){

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
