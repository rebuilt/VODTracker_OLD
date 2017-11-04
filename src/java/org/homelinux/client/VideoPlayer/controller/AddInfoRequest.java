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
class AddInfoRequest extends NetworkRequest{

  String videoID ="";
  String itemText = "";
  INFOTYPE type ;

    AddInfoRequest(String attribute, String itemText, INFOTYPE type)
    {
       this.videoID =attribute;
       this.itemText = itemText;
       this.type  = type;
    }

    @Override
    public void send()
    {
       service.addInfo(videoID, itemText, type, new AsyncCallback(){

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
