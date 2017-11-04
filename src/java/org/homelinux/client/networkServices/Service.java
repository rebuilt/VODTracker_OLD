/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.homelinux.client.networkServices;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Provides utility methods for creating VideoData and VideoUpdate service objects
 * @author nelson
 */
public class Service {

    
    public static VideoDataServiceAsync getService() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
        VideoDataServiceAsync service = (VideoDataServiceAsync) GWT.create(VideoDataService.class);
        // Specify the URL at which our service implementation is running.
        // Note that the target URL must reside on the same domain and port from
        // which the host page was served.
        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "videodataservice";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }
       public static VideoUpdateServiceAsync initUpdater() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
        VideoUpdateServiceAsync service = (VideoUpdateServiceAsync) GWT.create(VideoUpdateService.class);
        // Specify the URL at which our service implementation is running.
        // Note that the target URL must reside on the same domain and port from
        // which the host page was served.
        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "videoupdateservice";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }
}
