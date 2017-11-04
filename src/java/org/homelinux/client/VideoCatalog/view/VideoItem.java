/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoCatalog.view;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import org.homelinux.client.FilterElement;
import org.homelinux.client.QueryObject;
import org.homelinux.client.VideoCatalog.controller.VideoGalleryController;
import org.homelinux.client.VideoCatalog.model.Video;
import org.homelinux.client.VideoCatalog.model.Video.ATT;
import com.google.gwt.user.client.ui.Composite;
import org.homelinux.client.Controller;
import org.homelinux.client.Ordering;

/**
 * GUI element that represents one video in a {@link VideoPane}
 * @author nelson
 */
class VideoItem extends Composite {

    Video video;
    VerticalPanel vPanel;
    Label newLabel;
    VideoGalleryController controller;
    ArrayList<Video> newest;
    HorizontalPanel newLabelHolder;

    public VideoItem(Controller control, Video vid)
    {
        controller = (VideoGalleryController) control;
        video = vid;

        vPanel = new VerticalPanel();
        createItem();
        initWidget(vPanel);
        vPanel.setSpacing(5);
   

    }

    void setNewest(String id)
    {
        if (id.equals(video.getAttribute(ATT.VIDEOID))) {
            newLabel.setStyleName("white");
        }
    }

    private void createItem()
    {

        Image videoIcon = new Image(video.getAttribute(ATT.THUMBNAILURL));
        PushButton pb = new PushButton(videoIcon);
        pb.addClickListener(new MyClickListener());
        VerticalPanel temp = new VerticalPanel();
        HorizontalPanel tempH = new HorizontalPanel();
        String videoID = video.getAttribute(ATT.VIDEOID);
        newLabel = new Label("Unwatched");
        newLabel.setStyleName("green");
        newLabel.setVisible(true);

        newLabelHolder = new HorizontalPanel();
        newLabelHolder.add(newLabel);
        vPanel.add(newLabelHolder);

        String watched =
                Cookies.getCookie("watched" + video.getAttribute(ATT.AUTHOR));
        if (watched == null) {
            watched = "";
        }
        if (watched.contains(videoID)) {

            newLabel.setStyleName("white");
        }


        tempH.add(new RatingWidget(controller, video));
        tempH.add(new PlayListButton("add", controller, video));
        temp.add(tempH);
        temp.add(pb);
        vPanel.add(temp);



        Hyperlink videoLink =
                new Hyperlink(video.getAttribute(ATT.TITLE), "w" + video.getAttribute(ATT.VIDEOID));

        videoLink.addClickListener(new MyClickListener());
        vPanel.add(videoLink);
        final String author = video.getAttribute(ATT.AUTHOR);
        Hyperlink authorLink = new Hyperlink(author, null);
        authorLink.addClickListener(filterByAuthor);

        vPanel.add(authorLink);
        vPanel.add(new Label("Published: " + video.getAttribute(ATT.PUBLISHED)));


    }
    ClickListener filterByAuthor = new ClickListener() {

        public void onClick(Widget sender)
        {
            QueryObject query = controller.getQueryObject();
            FilterElement filt =
                    new FilterElement(video.getAttribute(ATT.AUTHOR), FilterElement.AUTHOR);
            
            query.setFilter(filt);
            query.setOrdering(Ordering.PUBLISHED_DESC);
            controller.query(query);
            History.newItem("s" + video.getAttribute(ATT.AUTHOR));
        }
    };

    class MyClickListener implements ClickListener {

        public void onClick(Widget arg0)
        {
            newLabel.setStyleName("white");
            controller.watch(video);
        }
    };
}
