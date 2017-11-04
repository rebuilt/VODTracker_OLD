/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client;

import com.google.gwt.user.client.ui.Composite;
import org.homelinux.client.VideoPlayer.controller.VideoPlayerController;
import org.homelinux.client.VideoCatalog.model.VideoModel;


import org.homelinux.client.VideoPlayer.view.VideoPlayer;
import com.google.gwt.user.client.ui.Widget;
import java.util.HashMap;
import org.homelinux.client.VideoPlayer.view.StatsPanel;
import org.homelinux.client.VideoCatalog.controller.VideoGalleryController;
import org.homelinux.client.VideoCatalog.model.NewestVideoModel;
import org.homelinux.client.VideoCatalog.model.PlayListModel;
import org.homelinux.client.VideoCatalog.model.SmartListModel;
import org.homelinux.client.VideoCatalog.view.FilterPanelContainer;
import org.homelinux.client.VideoCatalog.view.PlayListPanel;
import org.homelinux.client.VideoCatalog.view.SmartList;
import org.homelinux.client.VideoCatalog.view.VideoCatalog;
import org.homelinux.client.VideoPlayer.model.MapList;
import org.homelinux.client.VideoPlayer.model.PlayerList;
import org.homelinux.client.VideoPlayer.model.TeamList;

/**
 * Utilility for creation of Pages, Panels, and Models.  {@link Page} , {@link  VTPanel} , {@link Model}
 * @author nelson
 */
public class PageFactory {

    private static Page videoGallery,  videoPlayer;
    private static Controller videoGalleryController,  videoPlayerController;
    private static NewestVideoModel newest = new NewestVideoModel();
    private static Composite filterPanel;
    private static Composite statsPanel;
    private static VideoList top10;
    private static SmartList smartList;
    private static PlayListPanel playList;
    private static VideoModel videoModel = new VideoModel();
    private static PlayListModel playListModel = new PlayListModel();
    private static SmartListModel smartListModel = new SmartListModel();
    private static MapList mapList = new MapList();
    private static PlayerList playerList = new PlayerList();
    private static TeamList teamList = new TeamList();

    /**
     * Creates a page of a given type.  The type is specified by the first character in the initializationString.  Creates a page if it does not already exist.  Creates a model and controller for the created page.  Initializes the page with the initializationString (minus first character).  
     * @param initializationString determines the type of page created and the data with which a page is initialized.
     * @return a page in the form of a widget
     */
    public synchronized static Widget getPage(String initializationString)
    {
        char pageType = initializationString.charAt(0);
        initializationString =
                initializationString.substring(1, initializationString.length());

        //  System.out.println("making page of type: " + pageType);
        switch (pageType) {
            case 's': //search
            case 'v': //videogallery
                if (videoGallery == null) {
                    videoGalleryController =
                            new VideoGalleryController(videoModel, videoGallery);

                    videoGallery =
                            new VideoCatalog((VideoGalleryController) videoGalleryController, initializationString);
                    videoModel.addObserver(videoGallery);
                    ((VideoGalleryController) videoGalleryController).setVideoGallery((VideoCatalog) videoGallery);
                //  newest.addObserver(videoGallery);

                } else {
                    videoGallery.loadState(initializationString);
                }
                return (Widget) videoGallery;

            case 'w':  //watch
                if (videoPlayer == null) {
                    //         System.out.println("making a controller");
                    videoPlayerController =
                            new VideoPlayerController(videoModel, videoPlayer);
                    videoPlayer =
                            new VideoPlayer(videoPlayerController, initializationString);

                } else {
                    videoPlayer.loadState(initializationString);
                }
                return (Widget) videoPlayer;

        }
        return new InvalidURLPage();
    }
    public static final int FILTERPANEL = 0;
    public static final int BESTWEEKLY = 1;
    public static final int SMARTLISTS = 2;
    public static final int TOP10 = 3;
    public static final int PLAYLISTS = 4;

    public synchronized static Widget getPanel(int type)
    {

        switch (type) {
            case 0:
                if (filterPanel == null) {
                    filterPanel =
                            new FilterPanelContainer(videoGalleryController);

                    ((VideoGalleryController) videoGalleryController).setFilterPanel((FilterPanelContainer) filterPanel);

                    newest.addObserver((VTPanel) filterPanel);
                }

                return filterPanel;
            case 1:
                if (statsPanel == null) {
                    statsPanel = new StatsPanel(videoPlayerController);
                }

                return statsPanel;
            case 2:
                if (smartList == null) {
                    smartList = new SmartList(videoGalleryController);
                }
                smartListModel.addObserver(smartList);
                return smartList;
            case 3:
                if (top10 == null) {
                    top10 = new VideoList(videoGalleryController);
                    videoModel.addObserver(top10);
                    playListModel.addObserver(top10);
                }
                return top10;
            case 4:
                if (playList == null) {
                    playList = new PlayListPanel(videoGalleryController);
                //  playListModel.addObserver(playList);
                }
                return playList;
            default:
                return null;
        }
    }
    public static final int VIDEOMODEL = 0;
    public static final int UPLOADMODEL = 1;
    public static final int PLAYLISTMODEL = 2;
    public static final int SMARTLISTMODEL = 3;
    public static final int STATSMODEL = 4;
    public static final int NEWESTVIDEOMODEL = 5;

    public static Model getModel(int type)
    {
        switch (type) {
            case 0:
                return videoModel;

            case 2:
                return playListModel;
            case 3:
                return smartListModel;

            case 5:
                return newest;
        }
        return videoModel;

    }

    public static Controller getVideoGalleryController()
    {
        return videoGalleryController;
    }
    public static TeamList getTeamList(){
        return teamList;
    }
    public static PlayerList getPlayerList(){
        return playerList;
    }
    public static MapList getMapList(){
        return mapList;
    }
}
