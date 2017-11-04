
package org.homelinux.client;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.Iterator;
import org.homelinux.client.VideoCatalog.controller.VideoGalleryController;
import org.homelinux.client.VideoCatalog.model.PlayListModel;
import org.homelinux.client.VideoCatalog.model.Video;
import org.homelinux.client.VideoCatalog.model.Video.ATT;
import org.homelinux.client.VideoCatalog.model.VideoModel;
import org.homelinux.client.VideoCatalog.view.PlayListPanel;

/**
 * A GUI panel that displays a top10 panel and a playlist panel.  The top 10 panel holds the top 10 voted videos of the past week and
 * the playlist panel holds the videos a user has manually added to his playlist by clicking the 'add' button.
 * @author nelson
 * @see PlayListPanel
 */
public class VideoList extends Composite implements VTPanel {

    private VideoGalleryController controller;
    private TabPanel tabPanel = new TabPanel();
    private String[] panelNames = {"Top 10", "PlayLists"};
    private FlexTable[] panels;
    private PlayListPanel playListPanel;
    private PlayListModel playListModel =
            (PlayListModel) PageFactory.getModel(PageFactory.PLAYLISTMODEL);
   private  int selectedTab = 0;

    VideoList(Controller videoGalleryController)
    {

        controller = (VideoGalleryController) videoGalleryController;

        load();
        tabPanel.selectTab(0);
        tabPanel.addStyleName("whiteOnBlack");
        tabPanel.setWidth("200px");

        initWidget(tabPanel);
    }

    public VideoList(Controller control, String initializationState)
    {
        this(control);
    }
/**
 * Creates a new flexTable on which to add the top10 panel and the playlist panel
 */
    public void load()
    {

        panels = new FlexTable[panelNames.length];
        for (int i = 0; i < panelNames.length; i++) {
            panels[i] = new FlexTable();
            panels[i].addStyleName("whiteOnBlack");
            tabPanel.add(panels[i], panelNames[i]);
        }
        makePlayLists();
    }

    private void makePlayLists()
    {
        if (playListPanel == null) {
            playListPanel =
                    (PlayListPanel) PageFactory.getPanel(PageFactory.PLAYLISTS);
            panels[1].setWidget(0, 0, playListPanel);
         //   System.out.println("making playlist panel in top10 and selecting the tab");
        }
        playListPanel.update(playListModel);

        tabPanel.selectTab(1);

    }

    private void makeTop10()
    {

        Iterator it = top10.iterator();
        int count = 1;
     //   System.out.println("Making top 10");
        panels[0].setWidget(0, 0, new Label("Weekly Top 10"));
        while (it.hasNext()) {
            //  System.out.println("video #" + count);
            Video video = (Video) it.next();
            Image videoIcon = new Image(video.getAttribute(ATT.THUMBNAILURL));
            PushButton pb = new PushButton(videoIcon);
            pb.addClickListener(new MyClickListener(video));
            HorizontalPanel hPanel = new HorizontalPanel();
            hPanel.add(pb);
            Label rating = new Label(video.getAttribute(ATT.GOSURATING));
            rating.setStyleName("rating");
            hPanel.add(rating);
            hPanel.setStyleName("whiteOnBlack");
            Label videoLink = new Label(video.getAttribute(ATT.TITLE));
            videoLink.addClickListener(new MyClickListener(video));
            videoIcon.setSize("60px", "50px");
            panels[0].setWidget(count, 0, hPanel);
            count++;
            panels[0].setWidget(count, 0, videoLink);
            count++;
        }

    }

    /**
     * Activate the tab and the index.  First tab starts at 0.
     * @param index index of the tab to actuate
     */
    public void selectTab(int index)
    {
        tabPanel.selectTab(index);
    }

    class MyClickListener implements ClickListener {

        Video video;

        public MyClickListener(Video vid)
        {
            video = vid;
        }

        public void onClick(Widget arg0)
        {
            String watched = Cookies.getCookie("watched");
            if (watched != null) {
                if (!watched.contains(video.getAttribute(ATT.VIDEOID))) {
                    watched += video.getAttribute(ATT.VIDEOID);
                }
            } else {
                watched += video.getAttribute(ATT.VIDEOID);
            }
            if (watched.length() > 1000) {
                watched = watched.substring(0, watched.length() - 11);
            }
    //        System.out.println("Setting watched cookie as: " + watched);
            Cookies.setCookie("watched", watched, Defaults.getExpiration());

            controller.watch(video);

        }
    };




    public void update(Model m)
    {
        if (m instanceof VideoModel) {
            VideoModel videoModel = (VideoModel) m;

            ArrayList tmp = videoModel.getTop10();
            if (tmp != null && !tmp.equals(top10) && top10 == null) {
                top10 = tmp;
                makeTop10();
            }
        }
        if (m instanceof PlayListModel) {
            playListModel = (PlayListModel) m;
            makePlayLists();
        }
    }

    public void loadState(String state)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    ArrayList top10;
}
