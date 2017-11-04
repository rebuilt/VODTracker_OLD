/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoPlayer.view.widgets.infopanel;

import com.google.gwt.user.client.ui.VerticalPanel;

import org.homelinux.client.VideoCatalog.model.Video;
import org.homelinux.client.VideoPlayer.controller.VideoPlayerController;
import org.homelinux.client.VideoPlayer.model.*;
import org.homelinux.client.*;

/**
 *
 * @author nelson
 */
public class InfoPanelFactory {

    static VerticalPanel editablePanel = null;
    static VerticalPanel nonEditablePanel = null;

    public static VerticalPanel makeEditableSection(VideoPlayerController control, Video video)
    {


        editablePanel = new VerticalPanel();
        EditableInfoSection playerInfo =
                new EditableInfoSection(control, video, INFOTYPE.PLAYERS);
        EditableInfoSection teamInfo =
                new EditableInfoSection(control, video, INFOTYPE.TEAMS);
        EditableInfoSection mapInfo =
                new EditableInfoSection(control, video, INFOTYPE.MAPS);


        editablePanel.add(playerInfo);
        editablePanel.add(teamInfo);
        editablePanel.add(mapInfo);
        MapList mapList = PageFactory.getMapList();
        TeamList teamList = PageFactory.getTeamList();
        PlayerList playerList = PageFactory.getPlayerList();
        if (mapList.getList().isEmpty()) {
            control.loadMapList();
        }
        if (teamList.getList().isEmpty()) {
            control.loadTeamList();
        }
        if (playerList.getList().isEmpty()) {
            control.loadPlayerList();
        }
        mapList.addObserver(mapInfo);
        teamList.addObserver(teamInfo);
        playerList.addObserver(playerInfo);

        return editablePanel;
    }

    public static VerticalPanel makeNonEditableSection(VideoPlayerController control, Video video)
    {

        nonEditablePanel = new VerticalPanel();
        NonEditableInfoSection playerInfo =
                new NonEditableInfoSection(video, INFOTYPE.PLAYERS);
        NonEditableInfoSection teamInfo =
                new NonEditableInfoSection(video, INFOTYPE.TEAMS);
        NonEditableInfoSection mapInfo =
                new NonEditableInfoSection(video, INFOTYPE.MAPS);

        nonEditablePanel.add(playerInfo);
        nonEditablePanel.add(teamInfo);
        nonEditablePanel.add(mapInfo);
        MapList mapList = PageFactory.getMapList();
        TeamList teamList = PageFactory.getTeamList();
        PlayerList playerList = PageFactory.getPlayerList();
        if (mapList.getList().isEmpty()) {
            control.loadMapList();
        }
        if (teamList.getList().isEmpty()) {
            control.loadTeamList();
        }
        if (playerList.getList().isEmpty()) {
            control.loadPlayerList();
        }
        mapList.addObserver(mapInfo);
        teamList.addObserver(teamInfo);
        playerList.addObserver(playerInfo);
        return nonEditablePanel;
    }
}
