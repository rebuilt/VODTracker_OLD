/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoPlayer.view.widgets.infopanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.homelinux.client.Model;
import org.homelinux.client.PageFactory;
import org.homelinux.client.VideoCatalog.model.Video;
import org.homelinux.client.VideoCatalog.model.Video.ATT;
import org.homelinux.client.VideoPlayer.controller.VideoPlayerController;
import org.homelinux.client.VideoPlayer.model.MapList;
import org.homelinux.client.VideoPlayer.model.PlayerList;
import org.homelinux.client.VideoPlayer.model.TeamList;

/**
 *
 * @author nelson
 */
public class EditableInfoSection extends InfoPanelState {

    VideoPlayerController controller;
    VerticalPanel iPanel = new VerticalPanel();
    ListBox lb = new ListBox();
    HorizontalPanel lbPanel = new HorizontalPanel();
    PlayerList playerList;
    MapList mapList;
    TeamList teamList;
    String typeAsString;

    public EditableInfoSection(VideoPlayerController control, Video vid, INFOTYPE typ)
    {
        controller = control;
        video = vid;
        type = typ;
        playerList = PageFactory.getPlayerList();
        mapList = PageFactory.getMapList();
        teamList = PageFactory.getTeamList();
        loadState("");

        //   buildPage();
        initWidget(disc);
    }

    void draw()
    {
    }

    private class InfoEntry extends Composite {

        String key;
        String value;

        public InfoEntry(String k)
        {
            key = k;
            HTML link;
            HorizontalPanel hPanel = new HorizontalPanel();
            HashMap<String, String> list = new HashMap<String, String>();
            switch (type) {
                case PLAYERS:
                    list = playerList.getList();

                    break;
                case TEAMS:
                    list = teamList.getList();
                    break;
                case MAPS:
                    list = mapList.getList();
                    break;


            }
            value = list.get(key);
            link =
                    new HTML("<a href='http://www.teamliquid.net/tlpd/" + typeAsString + "/" + key + "_" + value + "'>" + value + "</a>");
            hPanel = new HorizontalPanel();
            hPanel.add(link);
            Button remove = makeRemoveButton(video, key, link, type);
            hPanel.add(remove);

            initWidget(hPanel);
        }
    }

    void loadState(String state)
    {


        switch (type) {
            case PLAYERS:
                typeAsString = "players";
                info = video.getPlayers();

                disc = makeDisclosurePanel("Players", true);


                for (String key : info.keySet()) {

                    iPanel.add(new InfoEntry(key));
                }
                if (playerList != null) {
                    loadList(playerList.getList());
                }

                break;
            case TEAMS:
                typeAsString = "teams";
                info = video.getTeams();
                disc = makeDisclosurePanel("Teams", true);


                for (String key : info.keySet()) {

                    iPanel.add(new InfoEntry(key));
                }
                if (teamList != null) {
                    loadList(teamList.getList());
                }
                break;
            case MAPS:
                typeAsString = "maps";
                info = video.getMaps();
                disc = makeDisclosurePanel("Maps", true);


                for (String key : info.keySet()) {

                    iPanel.add(new InfoEntry(key));
                }
                if (mapList != null) {
                    loadList(mapList.getList());
                }
                break;


        }

        iPanel.add(makeAddOtherButton());
        disc.add(iPanel);
    }

    Button makeRemoveButton(final Video video, final String key, final HTML link, final INFOTYPE type)
    {
        final Button remove = new Button("Delete");
        remove.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event)
            {
                controller.remove(video.getAttribute(ATT.VIDEOID), key, type);
                switch (type) {
                    case PLAYERS:
                        video.getPlayers().remove(key);
                        break;
                    case TEAMS:
                        video.getTeams().remove(key);
                        break;
                    case MAPS:
                        video.getMaps().remove(key);
                        break;
                }
                link.setVisible(false);
                remove.setVisible(false);
            }
        });
        return remove;
    }

    private VerticalPanel makeAddOtherButton()
    {
        VerticalPanel vPanel = new VerticalPanel();


        vPanel.add(lbPanel);


        final Button addInfo = new Button("Add");
        addInfo.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event)
            {

                if (lb.getSelectedIndex() != 0) {
                    String id =
                            getTLPD_ID(lb.getItemText(lb.getSelectedIndex()));
                    controller.addInfo(video.getAttribute(ATT.VIDEOID), lb.getItemText(lb.getSelectedIndex()), type);

                    int count = iPanel.getWidgetCount();
                    iPanel.insert(new InfoEntry(id), count - 1);
                    lb.setSelectedIndex(0);
                    switch (type) {
                        case PLAYERS:
                            video.getPlayers().put(id, (String) playerList.getList().get(id));
                            break;
                        case TEAMS:
                            video.getTeams().put(id, (String) teamList.getList().get(id));
                            break;
                        case MAPS:
                            video.getMaps().put(id, (String) mapList.getList().get(id));
                            break;

                    }
                }
            }
        });
        lbPanel.add(lb);
        lbPanel.add(addInfo);


        return vPanel;
    }

    private String getTLPD_ID(String value)
    {
        HashMap<String, String> list = new HashMap<String, String>();
        switch (type) {
            case PLAYERS:
                list = playerList.getList();

                break;
            case TEAMS:
                list = teamList.getList();
                break;
            case MAPS:
                list = mapList.getList();
                break;


        }
        for (String key : list.keySet()) {

            String v = list.get(key);

            if (v.equals(value)) {
                return key;
            }
        }
        return "0";
    }

    private void loadList(HashMap<String, String> list)
    {


        ArrayList<String> sortedList = new ArrayList(list.values());
        Collections.sort(sortedList);

        lb.addItem(" ");

        for (String entry : sortedList) {
            lb.addItem(entry);

        }


    }

    public void update(Model m)
    {
        if (m instanceof PlayerList) {
            playerList = (PlayerList) m;
            loadList(playerList.getList());
        } else if (m instanceof TeamList) {
            teamList = (TeamList) m;
            loadList(teamList.getList());
        } else if (m instanceof MapList) {
            mapList = (MapList) m;
            loadList(mapList.getList());
        }
    }
}
