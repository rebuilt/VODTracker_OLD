/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoPlayer.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import org.homelinux.client.Controller;
import org.homelinux.client.Model;
import org.homelinux.client.VTPanel;
import org.homelinux.client.VideoPlayer.controller.VideoPlayerController;

/**
 *
 * @author nelson
 */
public class StatsPanel extends Composite implements VTPanel {

    VideoPlayerController controller;
    TabPanel tabPanel;
    String[] panelNames = {"Players", "Map"};
    FlexTable[] panels;

    public StatsPanel(Controller control) {
        controller = (VideoPlayerController) control;
        tabPanel = new TabPanel();
        loadState("");
        tabPanel.setStyleName("whiteOnBlack");
        initWidget(tabPanel);
    }

    public void loadState(String state) {

        panels = new FlexTable[panelNames.length];
        for (int i = 0; i < panelNames.length; i++) {
            panels[i] = new FlexTable();

            panels[i].addStyleName("whiteOnBlack");
            tabPanel.add(panels[i], panelNames[i]);
        }
        tabPanel.selectTab(0);
        makePlayerTab();
        makeMapTab();

    }

    public void update(Model m) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



    private void makeMapTab() {
        panels[1].setWidget(0, 0, new Label("Map Statistics"));
        panels[1].setWidget(1, 0, new Label("win % vs z: "));
        panels[1].setWidget(2, 0, new Label("win % vs t: "));
        panels[1].setWidget(3, 0, new Label("win % vs p: "));
    }

    private void makePlayerTab() {
        panels[0].setWidget(0, 0, new Label("Player Stats"));
        panels[0].setWidget(1, 0, new Label("win % vs z: "));
        panels[0].setWidget(2, 0, new Label("win % vs t: "));
        panels[0].setWidget(3, 0, new Label("win % vs p: "));

    }
}
