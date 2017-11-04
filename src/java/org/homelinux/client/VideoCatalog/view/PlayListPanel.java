/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoCatalog.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import org.homelinux.client.Controller;
import org.homelinux.client.Model;
import org.homelinux.client.VTPanel;
import org.homelinux.client.VideoCatalog.controller.VideoGalleryController;
import org.homelinux.client.VideoCatalog.model.PlayListModel;
import org.homelinux.client.VideoCatalog.model.Video;
import org.homelinux.client.VideoCatalog.model.Video.ATT;
import org.homelinux.client.VideoCatalog.view.VideoItem.MyClickListener;

/**
 *
 * @author nelson
 */
public class PlayListPanel extends Composite implements VTPanel {

    VideoGalleryController controller;
    VerticalPanel mainPanel;

    public PlayListPanel(Controller control)
    {
        if (control instanceof VideoGalleryController) {
            controller = (VideoGalleryController) control;
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        mainPanel = new VerticalPanel();
        mainPanel.setStyleName("whiteOnBlack");
        initWidget(mainPanel);
    }

    public void loadState(String state)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }



    public void createView(ArrayList<Video> videos)
    {
        for (Video video : videos) {
            mainPanel.add(new PlayListItem(video));
        }
    }

    public void update(Model m)
    {
        if (m instanceof PlayListModel) {
            final PlayListModel model = (PlayListModel) m;
            mainPanel.clear();
            mainPanel.add(new Label("Playlist:"));
            Button playAll = new Button("Play All");
            playAll.addClickListener(new ClickListener(){

                public void onClick(Widget sender)
                {
                    controller.playAll(model.getPlayListCookie());
                }
            });
            mainPanel.add(playAll);
            Button clear = new Button("Clear");
            clear.addClickListener(new ClickListener(){

                public void onClick(Widget sender)
                {
                    controller.clearPlaylist();
                }
            });
            mainPanel.add(clear);
            createView(model.getVideos());
        }

    }

    class PlayListItem extends HorizontalPanel {

        Video video;

        private PlayListItem(Video vid)
        {
            super();
            video = vid;
            createItem();
        }

        private void createItem()
        {


            Image videoIcon = new Image(video.getAttribute(ATT.THUMBNAILURL));
            PushButton pb = new PushButton(videoIcon);
            pb.addClickListener(play);
            HorizontalPanel hPanel = new HorizontalPanel();
            hPanel.add(pb);
            Label rating = new Label(video.getAttribute(ATT.GOSURATING));
            rating.setStyleName("rating");
            hPanel.add(rating);
            Button remove = new Button("X");
            remove.addClickListener(new ClickListener(){

                public void onClick(Widget sender)
                {
                   controller.removePlayListItem(video);
                }
            });
            hPanel.add(remove);

            Label videoLink = new Label(video.getAttribute(ATT.TITLE));
            videoLink.addClickListener(play);
            videoIcon.setSize("60px", "50px");
            mainPanel.add(hPanel);
            mainPanel.add(videoLink);
        }
        ClickListener play = new ClickListener() {

            public void onClick(Widget sender)
            {
                controller.watch(video);
            }
        };
    }
}
