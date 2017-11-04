/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.networkServices;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A Collection of type Queue that accepts network requests and ensures serial execution in first-in first-out order.
 * @author nelson
 */
public class NetworkQueue {

    public static final  Queue<NetworkRequest> requests =
            new LinkedList<NetworkRequest>();
    static boolean inTransit = false;
/**
 * Add this network request to the queue to be executed serially.
 * @param request class that holds method to be executed on server
 */
    public static void addRequest(NetworkRequest request)
    {
        requests.add(request);
        stateChanged();
    }
/**
 * Checks to see if there are more {@link NetworkRequest} items left in the queue.  Executes each request by calling the
 * NetworkRequest send method.  Removes the request from the queue.
 */
    public static void stateChanged()
    {
     NetworkRequest request;
        if (!requests.isEmpty() && inTransit == false) {
            inTransit = true;
            request = requests.remove();
            request.send();
        }

    }


}
