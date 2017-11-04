/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoPlayer.view;

import org.homelinux.client.VideoCatalog.view.RatingWidget;
import org.homelinux.client.VideoCatalog.view.PlayListButton;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import org.homelinux.client.Controller;
import org.homelinux.client.PageFactory;
import org.homelinux.client.VideoPlayer.controller.VideoPlayerController;
import org.homelinux.client.VideoCatalog.model.Video;
import org.homelinux.client.VideoCatalog.model.Video.ATT;
import org.homelinux.client.VideoPlayer.model.TimeMarker;
import org.homelinux.client.VideoPlayer.view.widgets.infopanel.InfoPanelContainer;
import org.homelinux.client.VideoPlayer.view.widgets.highlightspanel.*;

/**
 * A GUI panel added to a {@link VideoPlayer} page to display an embedded flash player and game information
 * @author nelson
 */
public class PlayerPanel extends Composite {

    VerticalPanel mainPanel;
    VideoPlayerController controller;
    HTML videoPlayer;
    int failCount = 0;
    String vid;

    PlayerPanel(VideoPlayerController control, String state)
    {
        mainPanel = new VerticalPanel();
        controller = control;
        loadState(state);
        initWidget(mainPanel);
    }

    public void loadState(String state)
    {
        if (controller == null) {
            System.out.println("controller is null");
        }
        if (state != null && !state.equals("")) {
            getVideo(state);

        }
    }

    public void getVideo(String state)
    {
        if (state != null && !state.equals("")) {
            vid = state;
            controller.getVideos(state);
        }

    }
    final DisclosurePanelImages images =
            (DisclosurePanelImages) GWT.create(DisclosurePanelImages.class);



    class DisclosurePanelHeader extends HorizontalPanel {

        public DisclosurePanelHeader(boolean isOpen, String html)
        {
            add(isOpen ? images.disclosurePanelOpen().createImage()
                    : images.disclosurePanelClosed().createImage());
            add(new HTML(html));
        }
    }

    public void createView(ArrayList<Video> videos)
    {
        for (Video video : videos) {

            if (video != null) {
                String hq = "\"";
                boolean hqEnabled = controller.getHQ();
                if (hqEnabled) {
                    hq = "&ap=%2526fmt%3D18\"";
                }
                videoPlayer =
                        new HTML(" <object width=\"425\" height=\"344\">" +
                        "<param name=\"movie\" value=\"http://www.youtube.com/v/" +
                        video.getAttribute(ATT.VIDEOID) + "&hl=en&fs=1" + hq + "></param><param name=\"wmode\" value=\"opaque\"></param>" +
                        "<param name=\"allowFullScreen\" " +
                        "value=\"true\"></param><param name=\"allowscriptaccess\" value=" +
                        "\"always\"></param><embed src=\"http://www.youtube.com/v/" +
                        video.getAttribute(ATT.VIDEOID) + "&hl=en&fs=1" + hq + " type=\"application/x-shockwave-flash\" " +
                        "allowscriptaccess=\"always\" allowfullscreen=\"true\" width=\"425\" " +
                        "height=\"344\" wmode=\"opaque\"></embed></object>");
                HorizontalPanel hPanel = new HorizontalPanel();
                videoPlayer.setStyleName("below");
                hPanel.add(videoPlayer);
                VerticalPanel vPanel = new VerticalPanel();

                HorizontalPanel temp = new HorizontalPanel();
                temp.add(new RatingWidget(controller, video));
                Controller control = PageFactory.getVideoGalleryController();

                temp.add(new PlayListButton("add", control, video));
                Button spoilerFree = makeSpoilerFreeButton();
                temp.add(spoilerFree);
                vPanel.add(temp);
                HTML title =
                        new HTML("Title: <a href='" + video.getAttribute(ATT.PLAYERURL) + "' >" + video.getAttribute(ATT.TITLE) + "</a>");
                //  title.setWidth("250px");
                vPanel.add(title);
                vPanel.add(new HTML(
                        "Publisher: <a href='http://www.youtube.com/user/" + video.getAttribute(ATT.AUTHOR) + "' >" + video.getAttribute(ATT.AUTHOR) + "</a>"));
                vPanel.add(new Label("Published: " + video.getAttribute(ATT.PUBLISHED)));
                vPanel.add(new HTML("Download: <a href='http://www.pwnyoutube.com/watch?v=" + video.getAttribute(ATT.VIDEOID) + "' >Download </a>"));
                vPanel.add(new InfoPanelContainer(controller, video));

                DisclosurePanel desc = new DisclosurePanel("Description");
                desc.add(new Label(video.getAttribute(ATT.DESCRIPTION)));
                vPanel.add(desc);
                vPanel.add(new HighlightsPanel(controller, video,this, hPanel,videoPlayer));
                hPanel.add(vPanel);
                mainPanel.add(hPanel);
                controller.markAsWatched(video);
            }
        }
    }

    private Button makeSpoilerFreeButton()
    {
        Button button = new Button("Spoiler-Free");

        button.addClickListener(new ClickListener() {

            public void onClick(Widget sender)
            {
                final PopupPanel simplePopup =
                        new PopupPanel(true);

                simplePopup.setWidth("400px");
                simplePopup.setHeight("50px");
                simplePopup.setWidget(new HTML("Click outside box to close"));
                simplePopup.setStyleName("above");

                // Reposition the popup relative to the button

                int left = sender.getAbsoluteLeft() - 530;
                int top = sender.getAbsoluteTop() + 320;
                simplePopup.setPopupPosition(left, top);

                // Show the popup
                simplePopup.show();


            }
        });
        return button;
    }
}
