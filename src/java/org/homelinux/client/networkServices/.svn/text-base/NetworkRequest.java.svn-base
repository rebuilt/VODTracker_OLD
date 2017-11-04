/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.networkServices;

/**
 * A class for wrapping network requests so they can be executed serially
 * @author nelson
 */
public abstract class NetworkRequest {

    public VideoDataServiceAsync service = Service.getService();

    /**
     * Sends the request to the server for execution.  Each send request must call done() or execution will halt at the {@link NetworkQueue}
     * @see done
     */
    public abstract void send();

    /**
     * Mark this request as done.  Each send request must call done() or execution will halt at the {@link NetworkQueue}
     * @see send
     * @see NetworkQueue
     */
    public void done()
    {
        NetworkQueue.inTransit = false;
        NetworkQueue.stateChanged();

    }
}
