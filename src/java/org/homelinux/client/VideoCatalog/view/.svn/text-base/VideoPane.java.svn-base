/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoCatalog.view;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import java.util.Iterator;
import org.homelinux.client.FilterElement;
import org.homelinux.client.Ordering;
import org.homelinux.client.PageFactory;
import org.homelinux.client.QueryObject;
import org.homelinux.client.VideoCatalog.controller.VideoGalleryController;
import org.homelinux.client.VideoCatalog.model.Video;

/**
 *
 * @author nelson
 */
public class VideoPane extends Composite {

    VideoGalleryController controller;
    VerticalPanel vPanel;
    ArrayList<Video> videos;
    ArrayList<VideoItem> videoItems;
    int vidItemsPerRow = 4;


    VideoPane(VideoGalleryController control)
    {
        videoItems = new ArrayList<VideoItem>();
        controller = control;


        vPanel = new VerticalPanel();
        vPanel.setWidth("400px");

        // no longer need to call createView explicitly because the videoModel notifies observers on startup and activates the update method
        // This is where the fun starts, initializes first query.  All the listening pages and panels get updated after this is called
        populateWithVideos();
        initWidget(vPanel);

    }

    public void setVideos(ArrayList<Video> videos)
    {
        createView(videos);
    }

    void setNewest(String ID)
    {

        for (VideoItem item : videoItems) {
            item.setNewest(ID);
        }
    }

    private void createView(ArrayList videos)
    {
        if (videos != null) {
            vPanel.clear();

            Iterator it = videos.iterator();
            int currentRow = 0;
            int currentColumn = 0;

            HorizontalPanel row = newRow();
            vPanel.add(row);

            while (it.hasNext()) {

                if (vidItemsPerRow == currentColumn) {
                    currentColumn = 0;
                    currentRow++;
                    row = newRow();
                }

                Video video = (Video) it.next();
                VideoItem item = new VideoItem(controller, video);
                videoItems.add(item);
                row.add(item);
                currentColumn++;
            }

            vPanel.add(row);
        }
    }

    public void setItemsPerRow(int videosPerRow)
    {
        vidItemsPerRow = videosPerRow;
    }

    public HorizontalPanel newRow()
    {
        HorizontalPanel table = new HorizontalPanel();
        vPanel.add(table);
        table.setVerticalAlignment(VerticalPanel.ALIGN_TOP);
        table.setSpacing(15);
        return table;
    }
/**
 * Initializes the Video Pane to display a list of videos
 */
    private void populateWithVideos()
    {
        FilterElement filter =
                new FilterElement("jon747", FilterElement.AUTHOR, FilterElement.Operator.NOT);
        filter =
                new FilterElement("nevake", FilterElement.AUTHOR, FilterElement.Operator.NOT, filter);

        String checkBoxState = Cookies.getCookie("checkBoxState");
        if (checkBoxState != null) {
            FilterPanelContainer filterPanel =
                    (FilterPanelContainer) PageFactory.getPanel(PageFactory.FILTERPANEL);
            filter.add(filterPanel.getFilter());
        }

        QueryObject query = new QueryObject();
        query.setFilter(filter);
        query.setOrdering(Ordering.PUBLISHED_DESC);
        controller.query(query);

    }
}
