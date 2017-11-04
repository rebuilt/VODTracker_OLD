package org.homelinux.client.VideoCatalog.controller;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import java.util.ArrayList;
import java.util.Date;
import org.homelinux.client.Controller;
import org.homelinux.client.FilterElement;
import org.homelinux.client.networkServices.NetworkQueue;
import org.homelinux.client.networkServices.NetworkRequest;
import org.homelinux.client.Ordering;
import org.homelinux.client.Page;
import org.homelinux.client.PageFactory;
import org.homelinux.client.QueryObject;
import org.homelinux.client.VideoPlayer.controller.UpVoteRequest;
import org.homelinux.client.VideoCatalog.model.PlayListModel;
import org.homelinux.client.VideoCatalog.model.SmartListModel;
import org.homelinux.client.VideoCatalog.model.Video;
import org.homelinux.client.VideoCatalog.model.Video.ATT;
import org.homelinux.client.VideoCatalog.model.VideoModel;
import org.homelinux.client.VideoCatalog.view.FilterPanelContainer;
import org.homelinux.client.VideoCatalog.view.VideoCatalog;

/**
 * Provides classes that process and respond to events initialized by a {@link VideoCatalog} page or related panel
 * @author nelson
 */
public class VideoGalleryController implements Controller {

    VideoModel videoModel;
    VideoCatalog videoGallery;
    FilterPanelContainer filterPanel;


    public VideoGalleryController(VideoModel videoList, Page page)
    {
        videoModel = videoList;
        videoGallery = (VideoCatalog) page;
        this.loadNewVidCount();
        this.loadTop10();
        this.loadPlayList();
        this.loadSmartList();
    }

    public void next()
    {
        QueryObject q = videoModel.getQuery();
        int startIndex = q.getStartIndex();
        int numResults = q.getNumofResults();
        q.setStartIndex(startIndex + numResults);
        query(q);
    }

    public void previous()
    {
        QueryObject q = videoModel.getQuery();
        int startIndex = q.getStartIndex();
        int numResults = q.getNumofResults();
        q.setStartIndex(startIndex - numResults);
        query(q);
    }

    public void loadTop10()
    {
        ArrayList top10 = videoModel.getTop10();
        if (top10 == null) {
            DateTimeFormat formatter = DateTimeFormat.getFormat("yyyy-MM-dd");

            long currentTime = System.currentTimeMillis();
            long week = 1000 * 60 * 60 * 24 * 7L;
            Date weekAgo = new Date(currentTime - week);
            String lastWeek = formatter.format(weekAgo);
            Date now = new Date();
            String today = formatter.format(now);
            lastWeek =
                    "'" + lastWeek + " 00:00:00" + "' and '" + today + ":23:59:59' ";
            FilterElement filter = new FilterElement();
            filter =
                    new FilterElement(lastWeek, FilterElement.PUBLISHED, FilterElement.Operator.BETWEEN, filter);

            QueryObject queryObject = new QueryObject(filter);
            queryObject.setNumOfResults(10);
            queryObject.setOrdering(Ordering.GOSURATING_DESC);

            NetworkRequest rq = new LoadTop10Request(queryObject);
            NetworkQueue.addRequest(rq);
        }
    }

    public void addSmartListQuery(QueryObject query)
    {
        SmartListModel smartListModel =
                (SmartListModel) PageFactory.getModel(PageFactory.SMARTLISTMODEL);
        smartListModel.addQuery(query);
    }

    public void removeSmartListItem(QueryObject query)
    {
        SmartListModel smartListModel =
                (SmartListModel) PageFactory.getModel(PageFactory.SMARTLISTMODEL);
        smartListModel.removeQuery(query);

    }

    private void loadNewVidCount()
    {
        NetworkRequest rq = new NewVidCountRequest();
        NetworkQueue.addRequest(rq);

    }

    private void loadPlayList()
    {

        PlayListModel playListModel =
                (PlayListModel) PageFactory.getModel(PageFactory.PLAYLISTMODEL);
        String videos = playListModel.getPlayListCookie();
        if (videos == null || videos.equals("")) {
            // do nothing
        } else {
    
            NetworkRequest rq = new PlayListRequest(videos);
            NetworkQueue.addRequest(rq);
        }
    }

    private void loadSmartList()
    {
        String cookie = Cookies.getCookie("smartLists");
        //    System.out.println("smartListCookie" + cookie);
        ArrayList<QueryObject> queryList = new ArrayList<QueryObject>();
        if (cookie != null && cookie.length() > 0) {

            String[] smartLists = cookie.split("\\.");
            for (String list : smartLists) {
                String[] filterElements = list.split("~");
                FilterElement filter = null;
                for (String filterElement : filterElements) {
                    if (filter == null) {
                        filter = new FilterElement(filterElement);

                    } else {
                        filter = new FilterElement(filterElement, filter);
                    }
                //              System.out.println("making button of this type: " + filterElement);
                }
                QueryObject query = new QueryObject();
                query.setFilter(filter);
                queryList.add(query);

            }
            SmartListModel smartListModel =
                    (SmartListModel) PageFactory.getModel(PageFactory.SMARTLISTMODEL);
            smartListModel.setQueries(queryList);

        }
    }

    public void query(QueryObject query)
    {

        videoModel.setQuery(query);
        // Make remote call. Control flow will continue immediately and later
        // 'callback' will be invoked when the RPC completes.


        NetworkRequest rq = new QueryRequest(query);
        NetworkQueue.addRequest(rq);

    }

    public void watch(Video video)
    {

        String query = createQueryString(video);

        NetworkRequest rq = new WatchRequest(query);
        NetworkQueue.addRequest(rq);


    }

    public String createQueryString(Video video)
    {
        String author = video.getAttribute(ATT.AUTHOR);
        if (author.equals("Jon747") || author.equals("nevake")) {
            return "Select videoID from videos where videoID like \'%" + video.getAttribute(ATT.VIDEOID) + "' ";
        }
        String temp = video.getAttribute(ATT.TITLE);
        temp = temp.toLowerCase();
        temp = temp.replaceAll("[^a-z ]", "%");
        temp = temp.replaceAll("set", "%");

        temp = temp.replaceAll("part", "%");
        temp = temp.replaceAll("pt", "%");
        temp = temp.replaceAll(" ", "%");
        temp =
                "Select videoID from videos where title like \'%" + temp + "%\' and author='" + video.getAttribute(ATT.AUTHOR) + "' and published between DATE_SUB('" + video.getAttribute(ATT.PUBLISHED) + "', interval 7 DAY) and DATE_ADD('" + video.getAttribute(ATT.PUBLISHED) + "', interval 7 DAY) order by title ";
        //      System.out.println(temp);
        return temp;
    }

    public void filterChanged()
    {
        FilterElement galleryFilter = ((VideoCatalog) videoGallery).getFilter();

        galleryFilter.add(filterPanel.getFilter());
        //   System.out.println(galleryFilter);
        QueryObject query = new QueryObject();
        query.setFilter(galleryFilter);
        query.setStartIndex(0);
        query.setOrdering(((VideoCatalog) videoGallery).getOrdering());
        //      System.out.println("Searching for : " + query.toString());
        videoModel.setQuery(query);
   

        query(query);
    }

    public void upVote(String id)
    {
    
        NetworkRequest rq = new UpVoteRequest(id);
        NetworkQueue.addRequest(rq);
    }


    public void addPlayListItem(Video video)
    {
        PlayListModel playListModel =
                (PlayListModel) PageFactory.getModel(PageFactory.PLAYLISTMODEL);
        playListModel.addVideo(video);
    //    System.out.println("controller got the message to add the playlist item");
    }

    public void clearPlaylist()
    {
        PlayListModel playListModel =
                (PlayListModel) PageFactory.getModel(PageFactory.PLAYLISTMODEL);
        playListModel.clear();
    }

    public boolean getHQ()
    {
        return videoModel.getHQ();
    }

    public void playAll(String playListCookie)
    {
        History.newItem("w" + playListCookie);
    }

    public void removePlayListItem(Video video)
    {
        PlayListModel playListModel =
                (PlayListModel) PageFactory.getModel(PageFactory.PLAYLISTMODEL);
        playListModel.removeVideo(video);
    }

    public int getStartIndex()
    {
        return videoModel.getQuery().getStartIndex();
    }

    public void setFilterPanel(FilterPanelContainer filterPanel)
    {
        this.filterPanel = filterPanel;
    }

    public VideoCatalog getVideoGallery()
    {
        return videoGallery;
    }

    public void setVideoGallery(VideoCatalog videoGallery)
    {
        this.videoGallery = videoGallery;
    }

    public VideoModel getVideoModel()
    {
        return videoModel;
    }

    public void setVideoModel(VideoModel videoModel)
    {
        this.videoModel = videoModel;
    }

    public FilterPanelContainer getFilterPanel()
    {
        return filterPanel;
    }

    public QueryObject getQueryObject()
    {
        return videoModel.getQuery();
    }

    public void setHQ(boolean checked)
    {
        videoModel.setHQ(checked);
    }
}
