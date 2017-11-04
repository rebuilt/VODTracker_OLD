package org.homelinux.client.VideoCatalog.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import org.homelinux.client.FilterElement;
import org.homelinux.client.Model;
import org.homelinux.client.Ordering;
import org.homelinux.client.Page;
import org.homelinux.client.VideoCatalog.controller.VideoGalleryController;
import org.homelinux.client.VideoCatalog.model.Video;
import org.homelinux.client.VideoCatalog.model.VideoModel;

/**
 * GUI container for a {@link VideoPane}, ordering controls, and date filtering controls
 * @author nelson
 */
public class VideoCatalog extends Composite implements Page {

    HorizontalPanel hPanel;
    VideoGalleryController controller;
    VideoCatalogOrderingControls galleryOrderingControls;
    VideoCatalogControls galleryControls[] = new VideoCatalogControls[2];
    VideoPane videoPane;

    ArrayList<Video> videos = new ArrayList<Video>();

    public VideoCatalog(VideoGalleryController control, String initializationString)
    {
        // b = new Label("Hello world i am the stub for a videogallery and this is my initial data:  "+ initializationString);
        controller = control;
        hPanel = new HorizontalPanel();
        hPanel.setStyleName("blackOnWhite");
        createView();
        initWidget(hPanel);
    }

    public void loadState(String state)
    {
        //  b.setText("Hello world i am the stub for a videogallery and this is my loaded data:  "+ state);
    }




    public void update(Model m)
    {
        if (m instanceof VideoModel) {
            VideoModel videoModel = (VideoModel) m;
            ArrayList tmp = videoModel.getVideos();
            if (!tmp.equals(videos)) {
                videos = tmp;
                videoPane.setVideos(videos);
            }
        }

    }

    private void createView()
    {

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(galleryOrderingControls =
                new VideoCatalogOrderingControls(controller));
        vPanel.add(galleryControls[0] = new VideoCatalogControls(controller));
        vPanel.add(videoPane = new VideoPane(controller));
        vPanel.add(galleryControls[1] = new VideoCatalogControls(controller));
        hPanel.add(vPanel);


    }

    public void setItemsPerRow(int num)
    {
        videoPane.setItemsPerRow(num);
    }

    public FilterElement getFilter()
    {


        return galleryControls[0].getFilter();


    }

    public Ordering getOrdering()
    {
        return galleryOrderingControls.getOrdering();
    }

    public void setNewest(String ID)
    {
        videoPane.setNewest(ID);
    }
}
