/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoPlayer.view.widgets.highlightspanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.ArrayList;
import org.homelinux.client.Model;
import org.homelinux.client.Observer;
import org.homelinux.client.VideoCatalog.model.Video;
import org.homelinux.client.VideoCatalog.model.Video.ATT;
import org.homelinux.client.VideoPlayer.controller.VideoPlayerController;
import org.homelinux.client.VideoPlayer.model.TimeMarker;
import org.homelinux.client.VideoPlayer.view.PlayerPanel;

/**
 *
 * @author home
 */
public class HighlightsPanel extends Composite implements Observer {

    DisclosurePanel disc;
    VideoPlayerController controller;
    Video video;
    PlayerPanel playerPanel;
    HTML player;
    HorizontalPanel videoPanel;
    VerticalPanel mainPanel;

    public HighlightsPanel(VideoPlayerController control, Video vid)
    {
        controller = control;
        video = vid;
        video.addObserver(this);
        disc = new DisclosurePanel("Game Highlights", false);
        mainPanel = new VerticalPanel();
        loadTimeMarkers();
        disc.add(load());
        mainPanel.add(disc);
        initWidget(mainPanel);

    }

    public HighlightsPanel(VideoPlayerController controller, Video video, PlayerPanel pp, HorizontalPanel hPanel, HTML player)
    {
        this(controller, video);
        playerPanel = pp;
        this.player = player;
        videoPanel = hPanel;
    }

    private VerticalPanel load()
    {
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(new Label("Mark the time of a major battle"));
        HorizontalPanel tmp = new HorizontalPanel();
        tmp.setSpacing(10);
        tmp.setVerticalAlignment(HorizontalPanel.ALIGN_BOTTOM);
        int duration = Integer.parseInt(video.getAttribute(ATT.DURATION));

        final ListBox minutes = new ListBox();
        final ListBox seconds = new ListBox();
        for (int j = 0; j <= duration / 60; j++) {
            minutes.addItem(j + "");
        }
        for (int k = 0; k < 60; k += 5) {
            seconds.addItem(k + "");
        }
        VerticalPanel v9 = new VerticalPanel();
        v9.add(new Label("Minutes"));
        v9.add(minutes);
        tmp.add(v9);
        VerticalPanel v8 = new VerticalPanel();
        v8.add(new Label("Seconds"));
        v8.add(seconds);
        tmp.add(v8);
        Button setTime = new Button("Set Time");
        setTime.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event)
            {
                int min =
                        Integer.parseInt(minutes.getItemText(minutes.getSelectedIndex()));
                int sec =
                        Integer.parseInt(seconds.getItemText(seconds.getSelectedIndex()));
                int startTime = min * 60 + sec;
                if (startTime > 15) {
                    TimeMarker tm =
                            new TimeMarker(video.getAttribute(ATT.VIDEOID), startTime, 1);
                    controller.setTimeMarker(tm);
                    ArrayList<TimeMarker> markers = video.getTimeMarkers();
                    markers.add(tm);
                    video.setTimeMarkers(markers);
                }
            }
        });
        tmp.add(setTime);
        vPanel.add(tmp);

        return vPanel;

    }

    private void load(ArrayList<TimeMarker> timeMarkers)
    {
        VerticalPanel vPanel = new VerticalPanel();
        HorizontalPanel hPanel;
        vPanel.add(load());
        for (final TimeMarker tm : timeMarkers) {
            int t = tm.getTime();
            int min = t / 60;
            int sec = t % 60;

            Button time = new Button(min + ":" + sec);
            final Label rating = new Label(tm.getRating() + "");
            final PushButton upVote =
                    new PushButton(new Image("images/c-digg.png"));
            final PushButton downVote =
                    new PushButton(new Image("images/c-bury.png"));
            hPanel = new HorizontalPanel();
            rating.setStyleName("rating");
            hPanel.setSpacing(5);


            hPanel.add(rating);
            hPanel.add(time);
            hPanel.add(upVote);
            hPanel.add(downVote);
            vPanel.add(hPanel);
            time.addClickHandler(new ClickHandler() {

                public void onClick(ClickEvent event)
                {
                    skipToTime(tm, video);
                }
            });
            upVote.addClickHandler(new ClickHandler() {

                public void onClick(ClickEvent event)
                {
                    controller.changeTimeMarkerRating(tm, 1);
                    upVote.setEnabled(false);
                    downVote.setEnabled(false);
                    rating.setText(tm.getRating() + 1 + "");

                }
            });
            downVote.addClickHandler(new ClickHandler() {

                public void onClick(ClickEvent event)
                {
                    controller.changeTimeMarkerRating(tm, -1);
                    upVote.setEnabled(false);
                    downVote.setEnabled(false);
                    rating.setText(tm.getRating() - 1 + "");
                }
            });

        }
        if (!timeMarkers.isEmpty()) {

            mainPanel.remove(disc);
            disc =
                    new DisclosurePanel("Game Highlights (" + timeMarkers.size() + ")", true);
            disc.add(vPanel);
            mainPanel.add(disc);
        }


    }

    public void skipToTime(TimeMarker tm, Video video)
    {
        String hq = "\"";
        boolean hqEnabled = controller.getHQ();
        if (hqEnabled) {
            hq = "&ap=%2526fmt%3D18\"";
        }

        videoPanel.remove(player);

        player = new HTML(" <object width=\"425\" height=\"344\">" +
                "<param name=\"movie\" value=\"http://www.youtube.com/v/" +
                video.getAttribute(ATT.VIDEOID) + "&hl=en&fs=1&autoplay=1&start=" + tm.getTime() + "'" + hq + "></param><param name=\"wmode\" value=\"opaque\"></param>" +
                "<param name=\"allowFullScreen\" " +
                "value=\"true\"></param><param name=\"allowscriptaccess\" value=" +
                "\"always\"></param><embed src=\"http://www.youtube.com/v/" +
                video.getAttribute(ATT.VIDEOID) + "&hl=en&fs=1&autoplay=1&start=" + tm.getTime() + "'" + hq + " type=\"application/x-shockwave-flash\" " +
                "allowscriptaccess=\"always\" allowfullscreen=\"true\" width=\"425\" " +
                "height=\"344\" wmode=\"opaque\"></embed></object>");
        videoPanel.insert(player, 0);

    }

    private void loadTimeMarkers()
    {
        controller.loadTimeMarkers(video);
    }

    public void update(Model m)
    {
        if (m instanceof Video) {
            load(video.getTimeMarkers());
        }
    }
}
