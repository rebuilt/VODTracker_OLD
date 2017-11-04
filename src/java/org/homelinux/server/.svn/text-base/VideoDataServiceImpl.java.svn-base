/*
 * VideoDataServiceImpl.java
 *
 * Created on February 10, 2009, 7:13 PM
 */
package org.homelinux.server;

import java.util.ArrayList;
import com.google.gdata.client.youtube.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import org.homelinux.client.QueryObject;
import org.homelinux.client.networkServices.VideoDataService;
import org.homelinux.client.VideoCatalog.model.Video;
import org.homelinux.client.VideoCatalog.model.Video.ATT;
import org.homelinux.client.VideoPlayer.model.TimeMarker;
import org.homelinux.client.VideoPlayer.view.widgets.infopanel.INFOTYPE;

/**
 *
 * @author nelson
 */
public class VideoDataServiceImpl extends RemoteServiceServlet implements
        VideoDataService {

    ResourceBundle constants = ResourceBundle.getBundle("DB", Locale.ENGLISH);
    YouTubeService service;
    ArrayList<Connection> connections = new ArrayList<Connection>();
    int numOfColumns = 10;
    YouTubeQuery query = null;
    int connectionDelta = 1;
    HashSet<String> currentHour = new HashSet<String>();
    HashSet<String> lastHour = new HashSet<String>();
    Date hour = new Date();

    public void init() {
        setupDB();
        generateConnections(connectionDelta);
    }

    public ArrayList getVideos(String state) {
        Statement stmt = null;
        ResultSet rs = null;

        Connection conn = null;
        ArrayList<Video> videos = new ArrayList<Video>();
        if (state != null && !state.equals("")) {
            try {

                conn = openConnection("requesting video data for video ids: " + state);

                stmt = conn.createStatement(
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                if (!state.startsWith("Select")) {
                    String[] ids = state.split("@");
                    for (String id : ids) {

                        String query =
                                "select VIDEOID, PUBLISHED, TITLE, AUTHOR, URI, DESCRIPTION, PLAYERURL, THUMBNAILURL, DURATION, GOSURATING from videos where videoID='" + id + "' order by title";
                        rs = stmt.executeQuery(query);
                        //        System.out.println(query);

                        videos = this.fillArray(videos, rs, 20, 0, conn);


                    }
                }
                else {
                    rs = stmt.executeQuery(state);
                    videos = this.fillArray(videos, rs, 20, 0, conn);
                }
            } catch (SQLException ex) {
                printErrors(ex);
            } finally {

                releaseConnection(stmt, rs, conn);
            }
        }
        //    System.out.println("Returning video: "+ video.toString());
        return videos;
    }

    public ArrayList<Video> getVideos(QueryObject queryObject) {
       

        // System.out.println("The query object contains the following query: \n" + q.toString());
        ArrayList<Video> videos = new ArrayList<Video>();



        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

       // HttpServletRequest sr = this.getThreadLocalRequest();
        //     System.out.println("Address of user is: " + sr.getRemoteAddr());
        /**
        System.out.println("remote host is: " + sr.getRemoteHost());
        System.out.println("local address is: " + sr.getLocalAddr());
        System.out.println("remote host is: " + sr.getRemoteUser());
        System.out.println("remote port is: " + sr.getRemotePort());
        System.out.println("context path is: " + sr.getContextPath());
        System.out.println("request uri is: " + sr.getRequestURI());
        System.out.println("request URL is: " + sr.getRequestURL());
        System.out.println("********************************************************************");
        System.out.println("returning videos:");
         **/
        try {
           
            conn = openConnection(queryObject.toString());

            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);


            rs = stmt.executeQuery(queryObject.toString());

            // do something with the results
					/*
             * videoID published title content author uri description
             * playerURL thumbnailURL mediaContentURL viewCount
             * favoriteCount numRaters avgYTRating duration gosuRating
             * datePromoted
             */
            //  System.out.println("got results, filling array now");
            videos =
                    fillArray(videos, rs, queryObject.getNumofResults(), queryObject.getStartIndex(), conn);


        } catch (SQLException ex) {

            // handle any errors
            printErrors(ex);

        } finally {
            queryObject = null;
            releaseConnection(stmt, rs, conn);
        }
        //   System.out.println("********************************************************************");
        return videos;
    }

    public HashMap countNewest() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {

            conn = openConnection("getting count of newestvids");

            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            String query =
                    "SELECT author, Count(*) as 'numVids' FROM videos where published > DATE_SUB(CURDATE(), interval 8 DAY)  group by author order by author";
            rs = stmt.executeQuery(query);
            //      System.out.println(query);
            while (rs.next()) {
                map.put(rs.getString(1), rs.getInt(2));

            }
        } catch (SQLException ex) {
            printErrors(ex);
        } finally {
            releaseConnection(stmt, rs, conn);
        }

        return map;
    }

    private synchronized Connection openConnection(String in) throws SQLException {
        Connection poolConnection = null;
        HttpServletRequest sr = this.getThreadLocalRequest();

        if (connections.isEmpty()) {
            generateConnections(connectionDelta);
        }

        poolConnection = connections.remove(0);


        Date now = new Date();
        System.out.println(now.toString() + " : " + sr.getRemoteAddr() + " : " + in);
        this.currentHour.add(sr.getRemoteAddr());
        System.out.println("Number of users this hour: " + currentHour.size());
        System.out.println("Number of users last hour: " + lastHour.size());
        System.out.println("Size of connection pool: " + connections.size());
        if (now.getTime() - hour.getTime() > 1000 * 60 * 60 ) { //modified from once per day to once per hour
            Statement stmt = poolConnection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            String insert =
                    "insert into statistics(numUsers,day)  values(" + currentHour.size() + ",'" + formatDate(hour) + "')";
            stmt.execute(insert);
            hour.setTime(now.getTime());
            lastHour.clear();
            lastHour.addAll(currentHour);
            currentHour.clear();

        }

        return poolConnection;
    }

    private void setupDB() {
        try {
            String driver = constants.getString("driver");
            Class.forName(driver).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatDate(Date date) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        return formatter.format(date);
    }

    private void generateConnections(int numConnections) {

        try {

            for (int i = 0; i < numConnections; i++) {


                String url = constants.getString("url");
                String dbName = constants.getString("dbName");
                String userName = constants.getString("userName");
                String password = constants.getString("password");
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);
                connections.add(conn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void releaseConnection(Statement stmt, ResultSet rs, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            } // ignore

            rs = null;
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            } // ignore

            stmt = null;
        }

        connections.add(conn);

    }

    private void printErrors(SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }

    private ArrayList fillArray(ArrayList<Video> videos, ResultSet rs, int numOfResults, int startIndex, Connection conn) throws SQLException {

      
        int recNum = 0;
        //   System.out.println("entering: recNum: " + recNum + "    startIndex:" + startIndex + "    numResults" + numOfResults);
        //  System.out.println("RS:  "+ rs.)
ResultSet  rs2 = null;
        while (rs.next()) {
            String[] data = new String[numOfColumns];
            if (recNum < startIndex) {
                //skip anything below the range we want
                //      System.out.println("skipping: recNum: " + recNum + "    startIndex:" + startIndex);
                recNum++;
                continue;
            }
            else if (recNum >= (numOfResults + startIndex)) {
                //we have already collected the number of results requested, no need to check for more
                //         System.out.println("over: recNum: " + recNum + "    startIndex:" + startIndex + "    numResults" + numOfResults);
                break;
            }

            /*
             * videoID published title content author uri description
             * playerURL thumbnailURL mediaContentURL viewCount
             * favoriteCount numRaters avgYTRating duration gosuRating
             * datePromoted
             */
            //        System.out.println(" putting rs into data[]");
            for (int j = 0; j < numOfColumns; j++) {

                data[j] = rs.getString(j + 1);


            }

            Video video = new Video(data);
            // System.out.println("loading game info");
            HashMap<String, String> players =
                    new HashMap<String, String>();
            HashMap<String, String> teams =
                    new HashMap<String, String>();
            HashMap<String, String> maps =
                    new HashMap<String, String>();
            String[] types = {"player", "team", "map"};
            HashMap[] lists = {players, teams, maps};
            String[] queries = {
                "SELECT  players.tlpd_id , playerhandle.handle FROM player2video, players, playerhandle where player2video.videoID='" + video.getAttribute(ATT.VIDEOID) + "' and player2video.playerID=players.tlpd_id and players.canon_handleID=playerhandle.id",
                "select * from team2video , teams where videoID='" + video.getAttribute(ATT.VIDEOID) + "' and teamid=tlpd_id",
                "select maps.name, maps.tlpd_id from map2video , maps where map2video.videoID='" + video.getAttribute(ATT.VIDEOID) + "' and map2video.mapID=maps.tlpd_id"
            };
            String[] cols = {
                "handle", "name", "name"
            };
            Statement stmt = null;
            rs2 = null;


            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            for (int i = 0; i < types.length; i++) {
                rs2 = stmt.executeQuery(queries[i]);
                while (rs2.next()) {
                    lists[i].put(rs2.getString("tlpd_id"), rs2.getString(cols[i]));
                }

            }
		if (rs2 != null) {
            try {
                rs2.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            } // ignore

            rs2 = null;

                if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            } // ignore

            stmt = null;
        }
            video.setPlayers(players);
            video.setMaps(maps);
            video.setTeams(teams);
            videos.add(video);
            recNum++;
        }  
		
        }

        return videos;
    }

    public void removeInfo(String videoID, String info, INFOTYPE type) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        String query = "";
        switch (type) {
            case PLAYERS:
                query =
                        "delete from player2video where videoID='" + videoID + "' and playerID=" + info;
                break;
            case TEAMS:
                query =
                        "delete from team2video where videoID='" + videoID + "' and teamID=" + info;
                break;
            case MAPS:
                query =
                        "delete from map2video where videoID='" + videoID + "' and mapID=" + info;
                break;


        }
        try {
            conn = openConnection("Removing from " + videoID + " , " + info);
            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.execute(query);

        } catch (SQLException ex) {
            printErrors(ex);
        } finally {
            releaseConnection(stmt, rs, conn);
        }

    }

    public String getSimilar(String query) {

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        StringBuilder output = new StringBuilder();
        try {

            conn = openConnection("Getting videos similar to: " +query);

            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                output.append(rs.getString("videoID"));
                output.append("@");
            }
            output.deleteCharAt(output.length() - 1);

        } catch (SQLException ex) {

            printErrors(ex);
        } finally {

            releaseConnection(stmt, rs, conn);
        }
        return output.toString();
    }

    public boolean upVote(String id) {

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = openConnection("upvoting video: " + id);
            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs =
                    stmt.executeQuery("SELECT videoID,gosuRating FROM videos where videoID=\"" + id + "\"");
            int rating = 0;
            if (rs.next()) {
                rating = rs.getInt("gosuRating") + 1;
                rs.updateInt("gosuRating", rating);
                rs.updateRow();
            }
        } catch (SQLException ex) {
            printErrors(ex);
            return false;
        } finally {
            releaseConnection(stmt, rs, conn);
        }
        return true;
    }

    private HashMap getList(String query, String name) {
        HashMap list = new HashMap();
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = openConnection("getting List of "+ name);
            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs =
                    stmt.executeQuery(query);

            while (rs.next()) {
                list.put(rs.getString("tlpd_id"), rs.getString(name));
            }
        } catch (SQLException ex) {
            printErrors(ex);

        } finally {
            releaseConnection(stmt, rs, conn);
        }
        return list;
    }

    public HashMap getPlayerList() {
        return getList("SELECT * FROM players, playerhandle where players.canon_handleID=playerhandle.id order by handle", "handle");
    }

    public HashMap getTeamList() {
        return getList("SELECT * FROM teams order by name", "name");
    }

    public HashMap getMapList() {
        return getList("SELECT * FROM maps order by name", "name");
    }

    public void addInfo(String videoID, String itemText, INFOTYPE type) {


        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        int id = 0;
        try {


            String query = "";

            conn = openConnection("Adding info" + itemText+ " to video: " + videoID);
            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            switch (type) {
                case PLAYERS:
                    query =
                            "SELECT tlpd_id FROM players, playerhandle where players.canon_handleID=playerhandle.id and handle like '" + itemText + "'";
                    break;
                case TEAMS:
                    query =
                            "select tlpd_id from teams where name ='" + itemText + "'";
                    break;
                case MAPS:
                    query =
                            "select tlpd_id from maps where name ='" + itemText + "'";
                    break;


            }

            rs = stmt.executeQuery(query);
            if (rs.next()) {
                id = rs.getInt("tlpd_id");
            }


            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            switch (type) {
                case PLAYERS:
                    query =
                            "insert into player2video (videoID,playerID) values ('" + videoID + "'," + id + ")";
                    break;
                case TEAMS:
                    query =
                            "insert into team2video (videoID,teamID) values ('" + videoID + "'," + id + ")";
                    break;
                case MAPS:
                    query =
                            "insert into map2video (videoID,mapID) values ('" + videoID + "'," + id + ")";
                    break;


            }
            stmt.execute(query);

        } catch (SQLException ex) {
            printErrors(ex);

        } finally {
            releaseConnection(stmt, rs, conn);
        }
    }

    public ArrayList<TimeMarker> getTimeMarkers(String videoID) {
        ArrayList<TimeMarker> list = new ArrayList<TimeMarker>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        String query =
                "select * from pimpestplays where videoID='" + videoID + "'";
        try {
            conn = openConnection("getting timemarkers for video: " + videoID);
            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs =
                    stmt.executeQuery(query);

            while (rs.next()) {
                String id = rs.getString("videoID");
                int time = rs.getInt("startTime");
                int rating = rs.getInt("rating");
                TimeMarker timeM = new TimeMarker(id, time, rating);
                list.add(timeM);
            }
        } catch (SQLException ex) {
            printErrors(ex);

        } finally {
            releaseConnection(stmt, rs, conn);
        }
        return list;
    }

    public void setTimeMarker(TimeMarker tm) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn =
                    openConnection("Setting time marker for video " + tm.getVideoID() + " at time " + tm.getTime());
            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs =
                    stmt.executeQuery("select * from pimpestplays where videoID='" + tm.getVideoID() + "' and startTime=" + tm.getTime());
            if (!rs.next()) {
                stmt = conn.createStatement(
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);

                stmt.execute("insert into pimpestplays (videoID,startTime,rating) values ('" + tm.getVideoID() + "'," + tm.getTime() + "," + tm.getRating() + ")");
            }
        } catch (SQLException ex) {
            printErrors(ex);
        } finally {
            releaseConnection(stmt, rs, conn);
        }
    }

    public void changeTimeMarkerRating(TimeMarker tm, int delta) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn =
                    openConnection("Adding rating of time marker for video " + tm.getVideoID() + " to " + delta);
            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs =
                    stmt.executeQuery("select * from pimpestplays where videoID='" + tm.getVideoID() + "' and startTime=" + tm.getTime());

            if (rs.next()) {
                String query = "";
                int rating = rs.getInt("rating");
                rating = delta + rating;
                if (rating < -2) {
                    query =
                            "delete from pimpestplays where videoID='" + tm.getVideoID() + "' and startTime=" + tm.getTime();
                }
                else {
                    query =
                            "update pimpestplays set rating = " + rating + " where videoID='" + tm.getVideoID() + "' and startTime = " + tm.getTime();
                }
                stmt = conn.createStatement(
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);

                stmt.execute(query);
            }
        } catch (SQLException ex) {
            printErrors(ex);
        } finally {
            releaseConnection(stmt, rs, conn);
        }
    }
}
