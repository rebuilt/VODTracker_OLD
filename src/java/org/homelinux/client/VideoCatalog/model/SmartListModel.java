/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoCatalog.model;

import com.google.gwt.user.client.Cookies;
import java.util.ArrayList;
import org.homelinux.client.Defaults;
import org.homelinux.client.Model;
import org.homelinux.client.QueryObject;

/**
 * Provides a collection for holding smartlists
 * @author nelson
 */
public class SmartListModel extends Model {

    private ArrayList<QueryObject> queryList = new ArrayList<QueryObject>();

    public void setQueries(ArrayList query)
    {
        queryList = query;
        notifyObservers();
    }

    public ArrayList getQueries()
    {
        return queryList;
    }

    public void addQuery(QueryObject query)
    {
        if (!queryList.contains(query)) {
            queryList.add(query);
            addToCookie(query.getFilter().getDigest());
            notifyObservers();
        }
    }

    public void removeQuery(QueryObject query)
    {
        queryList.remove(query);
        removeFromCookie(query.getFilter().getDigest());
        notifyObservers();
    }

    public String getSmartListCookie()
    {
        String playLists = Cookies.getCookie("smartLists");
        if (playLists == null) {
            playLists = "";
        }
        return playLists;
    }

    private void addToCookie(String query)
    {
        if (query.length() < 1500) {
            String playLists = getSmartListCookie();
            if(!playLists.contains(query+".")){
            playLists += query + ".";
            Cookies.setCookie("smartLists", playLists,Defaults.getExpiration());
         //   System.out.println("adding to cookie: "+ playLists);
            }
        } else {
            System.out.println("cookie too long to be set");
        }
    }

    private void removeFromCookie(String query)
    {
        String playLists = getSmartListCookie();
        playLists =
                playLists.replaceAll(query + ".", "");
        Cookies.setCookie("smartLists", playLists,Defaults.getExpiration());
    }
}
