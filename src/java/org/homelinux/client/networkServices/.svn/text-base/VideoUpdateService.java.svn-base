/*
 * VideoUpdateService.java
 *
 * Created on March 7, 2009, 7:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.homelinux.client.networkServices;
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Interface for starting up the VideoUpdateService.
 * @author nelson
 * @see VideoUpdateServiceImpl
 */
public interface VideoUpdateService extends RemoteService{
   /**
    * Pings the server in order to start the VideoUpdateService.  The VideoUpdateService will be called once.  Callback
    * implementation is irrelevant.  The VideoUpdateService neither listens for, nor returns data to the client.  It only needs to
    * be pinged so that the servlet container can deploy it.
    * @param callback
    */
    public boolean ping();
}
