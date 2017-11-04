/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoPlayer.view.widgets.infopanel;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.HashMap;
import org.homelinux.client.Model;
import org.homelinux.client.VideoCatalog.model.Video;

/**
 *
 * @author nelson
 */
public class NonEditableInfoSection extends InfoPanelState {

    public NonEditableInfoSection(Video vid, INFOTYPE typ)
    {
        video = vid;
        type = typ;
            loadState("");
      //  buildPage();
        initWidget(disc);
    }

    @Override
    void draw()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    void loadState(String state)
    {
        VerticalPanel vPanel = new VerticalPanel();


        switch (type) {
            case PLAYERS:
             info = video.getPlayers();
                disc = makeDisclosurePanel("Players", false);
                HashMap<String, String> players = video.getPlayers();

                for (String key : players.keySet()) {
                    vPanel.add(new HTML("<a href='http://www.teamliquid.net/tlpd/players/" + key + "_" + players.get(key) + "'>" + players.get(key) + "</a>"));
                }

                break;
            case TEAMS:
                 info = video.getTeams();
                disc = makeDisclosurePanel("Teams", false);
                HashMap<String, String> teams = video.getTeams();

                for (String key : teams.keySet()) {
                    vPanel.add(new HTML("<a href='http://www.teamliquid.net/tlpd/teams/" + key + "_" + teams.get(key) + "'>" + teams.get(key) + "</a>"));
                }
                break;
            case MAPS:
                 info = video.getMaps();
                disc = makeDisclosurePanel("Maps", false);
                HashMap<String, String> maps = video.getMaps();

                for (String key : maps.keySet()) {
                    vPanel.add(new HTML("<a href='http://www.teamliquid.net/tlpd/maps/" + key + "_" + maps.get(key) + "'>" + maps.get(key) + "</a>"));
                }
                break;


        }
        disc.add(vPanel);
    }


    public void update(Model m)
    {
// Do nothing
    }
}
