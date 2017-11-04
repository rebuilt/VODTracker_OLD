/*
 * VideoUpdateServiceImpl.java
 *
 * Created on March 7, 2009, 7:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.homelinux.server;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gdata.client.youtube.*;
import com.google.gdata.data.media.mediarss.MediaContent;
import com.google.gdata.data.media.mediarss.MediaKeywords;
import com.google.gdata.data.youtube.*;
import com.google.gdata.util.ServiceException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import org.homelinux.client.networkServices.VideoUpdateService;

/**
The VideoUpdateService neither listens for, nor returns data to the client.  It only needs to
 * be pinged so that the servlet container can deploy it.
 * @author nelson
 */
public class VideoUpdateServiceImpl extends RemoteServiceServlet implements
        VideoUpdateService {

    ResourceBundle constants = ResourceBundle.getBundle("DB");
    YouTubeQuery query = null;
    Statement stmt = null;
    ResultSet rs = null;
    HashMap<String, String> players = new HashMap<String, String>();
    HashMap<String, String> teams = new HashMap<String, String>();
    HashMap<String, String> maps = new HashMap<String, String>();
    boolean initialized = false;
    ArrayList<Connection> connections = new ArrayList<Connection>();
    int connectionDelta = 1;

    /**
     * Creates a new task to run every ten minutes.  The task will iterate over all the youtube accounts in the <code> commentators</code> array
     * and query youtube for the newest videos of each author.  If the task finds new videos, it loads
     * the corresponding video information into the local database.
     */
    public void init() {
        setupDB();
        generateConnections(connectionDelta);
        this.loadMapList();
        this.loadPlayerList();
        this.loadTeamList();
        Timer YTchecker = new Timer("mytask");
        YTchecker.schedule(new QueryTask(), 6000, 600000);
    }

    class QueryTask extends TimerTask {

        public void run() {
            int maxResults = 10;
            int startIndex = 1;
            int iterations = 1;
            System.out.println("timer task running");

            String[][] commentators = {
                {"ImageSC1", "ImageSC1"},
                {"straTos", "straTosSC"},
                {"BladeOfAiur", "BladeOfAiur"},
                {"WeaponsLeft", "TheWeaponsLeft"},
                {"Diggity", "diggitySC"},
                {"Moletrap", "moletrap"},
                {"Klazart", "KlazartSC"},
                {"Cholera", "CholeraSC"},
                {"PsyonicReaver", "PsyonicReaver"},
                {"Rise", "SC2GGRise"},
                {"Vaul", "VaulSC"},
                {"KnightofNi", "knightswhosayniuc"},
                {"Greth", "GrethSC"},
                {"Ranshin", "ranshinda"},
                {"NukeTheStars", "NukeTheStars"},
                {"Sonde", "scTeamRune"},
                {"Violetak", "VioleTAK"},
                {"sc2gg", "sc2gg"},
                {"Hazel", "Hazelynut"},
                {"Morph1081", "Morph1081"},
                {"Hamilcar", "HamilcarSC"},
                {"KennyCasting", "DropMadBombs"},
                {"Parcx", "ParcxSC"},
                {"Smiley", "MrSmi1ey"},
                {"Stevethemacguy", "MattandSteveSC"},
                {"HDstarcraft", "HDstarcraft"},
                {"Phrank", "Phrentoes"},
                {"DejaVu119", "DejaVu119"},
                {"OverSky", "PerfectionistEmblem"},
                {"Vorosh", "Sc2ggVorosh"},
                {"HuskyStarcraft", "HuskyStarcraft"},
                {"Jon747", "Jon747"},
                {"nevake", "nevake"}
            };


            for (int j = 0; j < iterations; j++) {
                for (int i = 0; i < commentators.length; i++) {

                    queryYouTube(commentators[i][1], "", maxResults, startIndex);
                }
                startIndex = maxResults * (j + 1);
            }
            String[][] commentators2 = {
                {"ImageSC1", "ImageSC1"}
            };
            maxResults = 15;
            startIndex = 1;
            iterations = 10;
            if (!initialized) {
                for (int j = 0; j < iterations; j++) {
                    for (int i = 0; i < commentators2.length; i++) {

                        queryYouTube(commentators2[i][1], commentators2[i][0], maxResults, startIndex);

                    }
                    startIndex = maxResults * (j + 1);
                }
            }
            initialized = true;
         
            System.out.println("task complete");
        }
    }

    /**
     * Queries youtube for the newest videos from the provided account names.  Loads the newest videos in the database.
     * @param videoQuery the youtube account to search
     * @param numOfResults the number of results to receive
     * @param startIndex the index at which to page the first result
     */
    private void queryYouTube(String ytAccount, String videoQuery, int numOfResults,
            int startIndex) {

        YouTubeService service =
                new YouTubeService(
                "ytapi-nelsonjovel-starcraftviewer-8b6s9ob1-0",
                "AI39si6DBpkKgzSHAyipqNaLLYDr69U87HGOjqmWUx8eWnkM1GiwbjydJkOjNkWlAMddLvkbPNI53PULraPaEd0FWhtLVZNbxA");

        try {
            query = new YouTubeQuery(new URL(
                    "http://gdata.youtube.com/feeds/api/videos"));

            // do not exclude restricted content from the search results
            query.setMaxResults(numOfResults);
            query.setOrderBy(YouTubeQuery.OrderBy.PUBLISHED);
            query.setStartIndex(startIndex);
            // search for the text we set up in the variable videoQuery
            query.setAuthor(ytAccount);

            if (!videoQuery.equals("")) {
                query.setFullTextQuery(videoQuery);
            }


            VideoFeed videoFeed = service.query(query, VideoFeed.class);
            if (videoFeed != null || !videoFeed.getEntries().isEmpty()) {
                updateDB(videoFeed);
            }
        } catch (IOException e) {
            System.out.println("IO exception");
            e.printStackTrace();
        } catch (ServiceException e) {
            System.out.println("Service exception");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("General exception");
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());

        } finally {
            service = null;
            query = null;

        }
    }

    /**
     * Loads video information into the database.  Only loads videos if they do not already exist.
     * Accepts videoFeed information in the format provided by YouTube.
     * @param videoFeed the list of video information.
     */
    private void updateDB(VideoFeed videoFeed) {


        /*
         * videoID published title content author uri description
         * playerURL thumbnailURL mediaContentURL viewCount
         * favoriteCount numRaters avgYTRating duration gosuRating
         * datePromoted
         */
Connection conn = null;
        try {
            conn = openConnection();
        } catch (SQLException ex) {
            Logger.getLogger(VideoUpdateServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (VideoEntry entry : videoFeed.getEntries()) {
            stmt = null;
            rs = null;
            
            YouTubeMediaGroup media = entry.getMediaGroup();
            List<MediaContent> mediaContent = media.getContents();
            String mediaContentURL = "";

            if (!mediaContent.isEmpty()) {
                mediaContentURL = mediaContent.get(0).getUrl();
            }


            // System.out.println("Title: " +
            // entry.getTitle().getPlainText());
            // published +=entry.getPublished().toS
            String vidID = entry.getId().split(":")[3];
            boolean isStarcraft = false;
            MediaKeywords keywords = media.getKeywords();
            for (String keyword : keywords.getKeywords()) {
                keyword = keyword.toLowerCase();
                if (keyword.equals("starcraft")) {
                    isStarcraft = true;
                }
            }
            if (!isStarcraft) {
                //if the video does not have starcraft as one of its keyswords, skip it
                continue;
            }


            try {
                /*
                 * System.out.println("video id" + vidID);
                 * System.out.println(vidID+"\n"+
                 * entry.getPublished().toStringRfc822()+"\n"+
                 * entry.getPublished().toString()+"\n"+
                 * entry.getPublished().toUiString()+"\n"+
                 * entry.getTitle().getPlainText()+"\n"+
                 * entry.getContent().getLang()+"\n"+
                 * entry.getAuthors().get(0).getName()+"\n"+
                 * entry.getAuthors().get(0).getUri()+"\n"+
                 * media.getDescription().getPlainTextContent()+"\n"+
                 * media.getPlayer().getUrl()+"\n"+
                 * media.getThumbnails().get(0).getUrl()+"\n"+
                 * mediaContent.getUrl()+"\n"+
                 * //stats.getFavoriteCount()+"\n"+ //
                 * stats.getViewCount()+"\n"+ //
                 * entry.getRating().getNumRaters()+"\n"+ //
                 * entry.getRating().getAverage()+"\n"+
                 * mediaContent.getDuration() );
                 */

                



                stmt = conn.createStatement(
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                rs =
                        stmt.executeQuery("SELECT videoID FROM videos where videoID=\"" + vidID + "\"");





                if (rs.next()) {
                    // if there is at least one record in sql db with
                    // matching
                    // videoID, skip this record
                    }
                else {
                    // If it does not yet exist in the db, add it
						/*
                     * videoID published title content author uri
                     * description playerURL thumbnailURL
                     * mediaContentURL viewCount favoriteCount numRaters
                     * avgYTRating duration gosuRating datePromoted
                     */
                    System.out.println("*************************************************" +
                            "*******************************************************************************");
                    System.out.println("Adding: video id" + vidID);
                    String title = entry.getTitle().getPlainText();
                    String description =
                            media.getDescription().getPlainTextContent();
                    System.out.println(vidID + "\n" +
                            entry.getPublished().toStringRfc822() + "\n" +
                            entry.getPublished().toString() + "\n" +
                            entry.getPublished().toUiString() + "\n" +
                            title + "\n" +
                            entry.getContent().getLang() + "\n" +
                            entry.getAuthors().get(0).getName() + "\n" +
                            entry.getAuthors().get(0).getUri() + "\n" +
                            description + "\n" +
                            media.getPlayer().getUrl() + "\n" +
                            media.getThumbnails().get(0).getUrl() + "\n");

                    System.out.println("====================================" +
                            "====================================================================");
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    //					  mediaContent.getUrl()+"\n");
//						  stats.getFavoriteCount()+"\n"+ //
//						  stats.getViewCount()+"\n"+ //
//						  entry.getRating().getNumRaters()+"\n"+ //
//						  entry.getRating().getAverage()+"\n"+
//						  mediaContent.getDuration() );

                    stmt.execute("insert into videos values(\"" + vidID +
                            "\",\"" + entry.getPublished().toUiString() + "\",\"" +
                            entry.getTitle().getPlainText().replaceAll("\"", "'") +
                            "\"," + "null,\"" + entry.getAuthors().get(0).getName().replaceAll("\"", "'") +
                            "\",\"" + entry.getAuthors().get(0).getUri() + "\",\"" +
                            media.getDescription().getPlainTextContent().replaceAll(
                            "\"", "'") + "\",\"" + media.getPlayer().getUrl() + "\",\"" +
                            media.getThumbnails().get(0).getUrl() + "\",\"" + mediaContentURL +
                            "\"," + "0," + "0," + "0," + "0," + media.getDuration() + ",0,null)");


                    //insert into keywords (keyword,videoID) values("greth","8eU3Bjn55gQ")
                    for (String keyword : keywords.getKeywords()) {
                        keyword = keyword.replaceAll("'", "");
                        stmt.execute("insert into keywords (keyword,videoID) values('" + keyword + "','" + vidID + "')");

                    }

                    scanForPlayerData(vidID, title, description, conn, keywords);
                    scanForTeamData(vidID, title, description, conn,  keywords);
                    scanForMapData(vidID, title, description, conn, keywords);

                }
            } catch (SQLException ex) {
                System.out.println(ex);
                Logger.getLogger(VideoDataServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
       closeConnection(stmt, rs, conn);

    }

    private boolean scanForData(String videoID, String title, String description, MediaKeywords keywords, Connection conn, LISTTYPE type) throws SQLException {
        title = title.toLowerCase();
        description = description.toLowerCase();
        //    conn = openConnection();
        rs = null;
        stmt = null;
        stmt = conn.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        HashMap<String, String> list = new HashMap<String, String>();
        String table = "";
        String idColumn = "";
        switch (type) {
            case PLAYERS:
                list = players;
                table = "player2video";
                idColumn = "playerID";
                break;
            case TEAMS:
                list = teams;
                table = "team2video";
                idColumn = "teamID";
                break;
            case MAPS:
                list = maps;
                table = "map2video";
                idColumn = "mapID";
                break;
        }
        int playersFound = 0;

        if (playersFound < 2) {
            for (String tlpd_id : list.keySet()) {
                String value = list.get(tlpd_id).trim().toLowerCase();
                if (title.contains(" " + value)) {

                    rs =
                            stmt.executeQuery("select * from " + table + " where videoID='" + videoID + "' and " + idColumn + "='" + tlpd_id + "'");
                    if (!rs.next()) {
                        stmt.execute("insert into " + table + " (videoID," + idColumn + ") values('" + videoID + "','" + tlpd_id + "')");
                        playersFound++;
                    }

                }
            }

        }
        if (playersFound < 2) {
            for (String tlpd_id : list.keySet()) {
                String value = list.get(tlpd_id).trim().toLowerCase();
                if (title.contains(value + " ")) {
                    rs =
                            stmt.executeQuery("select * from " + table + " where videoID='" + videoID + "' and " + idColumn + "='" + tlpd_id + "'");
                    if (!rs.next()) {
                        stmt.execute("insert into " + table + " (videoID," + idColumn + ") values('" + videoID + "','" + tlpd_id + "')");
                        playersFound++;
                    }

                }
            }

        }

        if (playersFound < 2) {
            for (String tlpd_id : list.keySet()) {
                String value = list.get(tlpd_id).trim().toLowerCase();
                if (title.contains(value)) {
                    rs =
                            stmt.executeQuery("select * from " + table + " where videoID='" + videoID + "' and " + idColumn + "='" + tlpd_id + "'");
                    if (!rs.next()) {
                        stmt.execute("insert into " + table + " (videoID," + idColumn + ") values('" + videoID + "','" + tlpd_id + "')");
                        playersFound++;
                    }

                }
            }

        }
        if (playersFound < 2) {
            for (String tlpd_id : list.keySet()) {
                String value = list.get(tlpd_id).trim().toLowerCase();
                if (description.contains(" " + value)) {
                    rs =
                            stmt.executeQuery("select * from " + table + " where videoID='" + videoID + "' and " + idColumn + "='" + tlpd_id + "'");
                    if (!rs.next()) {
                        stmt.execute("insert into " + table + " (videoID," + idColumn + ") values('" + videoID + "','" + tlpd_id + "')");
                        playersFound++;
                    }

                }
            }
        }

        if (playersFound < 2) {
            for (String tlpd_id : list.keySet()) {
                String value = list.get(tlpd_id).trim().toLowerCase();
                if (description.contains(value)) {
                    rs =
                            stmt.executeQuery("select * from " + table + " where videoID='" + videoID + "' and " + idColumn + "='" + tlpd_id + "'");
                    if (!rs.next()) {
                        stmt.execute("insert into " + table + " (videoID," + idColumn + ") values('" + videoID + "','" + tlpd_id + "')");
                        playersFound++;
                    }

                }
            }
        }

        //   closeConnection(stmt, rs, conn);
        if (playersFound >= 2) {
            return true;
        }

        return false;
    }

    private boolean scanForPlayerData(String videoID, String title, String description, Connection conn, MediaKeywords keywords) throws SQLException {
        return scanForData(videoID, title, description, keywords, conn, LISTTYPE.PLAYERS);

    }

    private boolean scanForTeamData(String videoID, String title, String description,Connection conn,  MediaKeywords keywords) throws SQLException {
        return scanForData(videoID, title, description, keywords, conn, LISTTYPE.TEAMS);

    }

    private boolean scanForMapData(String videoID, String title, String description, Connection conn, MediaKeywords keywords) throws SQLException {
        return scanForData(videoID, title, description, keywords, conn, LISTTYPE.MAPS);

    }

    enum LISTTYPE {

        PLAYERS, TEAMS, MAPS
    }

    private HashMap loadList(LISTTYPE type) {
        HashMap list = new HashMap();
        String table = "";
        String colName = "";
        String listID = "tlpd_id";
        String whereClause = "";
        Connection conn = null;
        switch (type) {
            case PLAYERS:
                table = "players, playerhandle";
                colName = "playerhandle.handle";

                whereClause =
                        " where players.canon_handleID=playerhandle.id";
                break;
            case TEAMS:
                table = "teams";
                colName = "name";
                break;
            case MAPS:
                table = "maps";
                colName = "name";
                break;
        }
        try {
           conn = openConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery("SELECT * from " + table + whereClause);
            System.out.println("Player list: ");
            while (rs.next()) {
                list.put(rs.getString(listID), rs.getString(colName));
                System.out.println("| " + rs.getString(listID) + " | " + rs.getString(colName) + " |");
            }

        } catch (SQLException ex) {
            Logger.getLogger(VideoUpdateServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    private void loadPlayerList() {
        players = loadList(LISTTYPE.PLAYERS);
    }

    private void loadTeamList() {
        teams = loadList(LISTTYPE.TEAMS);
    }

    private void loadMapList() {
        maps = loadList(LISTTYPE.MAPS);
    }

    /**
     * open a new connection to the database
     * @return a new connection
     * @throws java.sql.SQLException
     */
    private synchronized Connection openConnection() throws SQLException {
        Connection poolConnection = null;
       
        if (connections.isEmpty()) {
            generateConnections(connectionDelta);
        }
         poolConnection = connections.remove(0);

        return poolConnection;
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
                System.out.println("Number of connections: "+ connections.size());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * prepares the database for connections.  Initialized when this object is created for faster subsequent connections to the database.
     */
    private void setupDB() {

        try {

            String driver = constants.getString("driver");
            Class.forName(driver).newInstance();


        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println("DB setup successfull");
    }

    /**
     * close the connection to the database
     * @param stmt an SQL Statement
     * @param rs a resultset
     * @param conn a connection
     */
    private void closeConnection(Statement stmt, ResultSet rs, Connection conn) {
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

    /**
     * Pings the server in order to start the VideoUpdateService.  The VideoUpdateService will be called once.  Callback
     * implementation is irrelevant.  The VideoUpdateService neither listens for, nor returns data to the client.  It only needs to
     * be pinged so that the servlet container can deploy it.
     * @param callback
     */
    public boolean ping() {
        return true;
    }

    private void printErrors(SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }
}
