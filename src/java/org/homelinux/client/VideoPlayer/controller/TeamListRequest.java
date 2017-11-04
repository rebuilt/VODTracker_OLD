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
class TeamListRequest extends NetworkRequest{

    public TeamListRequest()
    {
    }

    @Override
    public void send()
    {
       service.getTeamList(new AsyncCallback(){

            public void onFailure(Throwable caught)
            {
               done();
            }

            public void onSuccess(Object result)
            {
               PageFactory.getTeamList().setList((HashMap) result);
               done();
            }
        });
    }

}
