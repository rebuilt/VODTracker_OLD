/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.homelinux.client.VideoPlayer.controller;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.HashMap;
import org.homelinux.client.PageFactory;
import   org.homelinux.client.networkServices.*;
/**
 *
 * @author nelson
 */
class MapListRequest extends NetworkRequest{

    public MapListRequest()
    {
    }

    @Override
    public void send()
    {
       service.getMapList(new AsyncCallback(){

            public void onFailure(Throwable caught)
            {
                done();
            }

            public void onSuccess(Object result)
            {
                PageFactory.getMapList().setList((HashMap) result);
               done();
            }
        });
    }

}
