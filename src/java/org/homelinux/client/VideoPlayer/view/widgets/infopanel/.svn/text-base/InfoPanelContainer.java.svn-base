/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoPlayer.view.widgets.infopanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.homelinux.client.Model;
import org.homelinux.client.VTPanel;
import org.homelinux.client.VideoCatalog.model.Video;
import org.homelinux.client.VideoPlayer.controller.VideoPlayerController;

/**
 *
 * @author nelson
 */
public class InfoPanelContainer extends Composite implements VTPanel {

    VerticalPanel mainPanel;
    VideoPlayerController controller;
    int recognizedData = 0;
    Video video;
    VerticalPanel currentPanel;

    public InfoPanelContainer(VideoPlayerController control, Video vid)
    {
        video = vid;
        mainPanel = new VerticalPanel();
        controller = control;
        loadState("");
        initWidget(mainPanel);
    }

    private class ModeButton extends Button {

        public boolean editable = false;

        private ModeButton(String string)
        {
            super(string);
        }
    }

    public void loadState(String state)
    {
        final ModeButton mode = new ModeButton("Edit");
        mode.setTitle("Like the VODTracker? Help us out by correcting game information.");
        mainPanel.add(mode);
        mode.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event)
            {
                mainPanel.remove(currentPanel);
                if (mode.editable) {
                    mode.setText("Edit");
                    currentPanel =
                            InfoPanelFactory.makeNonEditableSection(controller, video);
                } else {
                    mode.setText("Done");
                    currentPanel =
                            InfoPanelFactory.makeEditableSection(controller, video);
                }
                mode.editable = !mode.editable;
                mainPanel.add(currentPanel);
            }
        });
        currentPanel =
                InfoPanelFactory.makeNonEditableSection(controller, video);
        mainPanel.add(currentPanel);
    }

    public void update(Model m)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
