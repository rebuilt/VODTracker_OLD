/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoPlayer.view;

import org.homelinux.client.*;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import org.homelinux.client.VideoPlayer.controller.VideoPlayerController;
import org.homelinux.client.VideoCatalog.model.Video;

/**
 *
 * @author nelson
 */
public class VideoPlayer extends Composite implements Page {

    VideoPlayerController controller;
    VerticalPanel vPanel;
PlayerPanel pp;
    public VideoPlayer(Controller control, String initializationString) {
        controller = (VideoPlayerController) control;
        vPanel = new VerticalPanel();
        vPanel.setStyleName("blackOnWhite");
        initWidget(vPanel);
    }

    public void loadState(String state) {
        vPanel.clear();
       

       pp = new PlayerPanel(controller,state );
            vPanel.add(pp);
            
        
    }



    public Controller getController() {
        throw new UnsupportedOperationException("Not supported yet.");
    }



    public void update(Model m) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void createView(ArrayList<Video> videos) {
        pp.createView(videos);
    }
}
