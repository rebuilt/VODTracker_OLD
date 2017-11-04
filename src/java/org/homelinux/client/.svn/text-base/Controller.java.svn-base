
package org.homelinux.client;

/**
 * Processes and responds to events
 * @author nelson
 */
public interface Controller {

    /**
     * Requests a video list from the server.  The request for videos is executed on the server as a sql query.
     * @param query A user-customizable request for videos
     **/

    public void query(QueryObject query);

    /**
     * Increments the tally of votes by one.
     * @param id The youtube video ID.  This is the same as the ID that appears at the
     * of a youtube url.  In the link <code> http://www.youtube.com/watch?v=7vXBY6c_oBE</code> , the video id is <code> 7vXBY6c_oBE</code>.
     */
    public void upVote(String id);
    
}
