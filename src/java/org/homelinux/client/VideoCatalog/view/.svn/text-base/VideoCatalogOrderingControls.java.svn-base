/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoCatalog.view;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import org.homelinux.client.Controller;
import org.homelinux.client.FilterElement;
import org.homelinux.client.Ordering;
import org.homelinux.client.VideoCatalog.controller.VideoGalleryController;

/**
 * GUI for sorting by Author, Rating, Title, or Publisher
 * @author nelson
 */
public class VideoCatalogOrderingControls extends Composite {

    VideoGalleryController controller;
    HorizontalPanel hPanel;
    ListBox lb;
    ListBox descAsc;
   
    
    VideoCatalogOrderingControls(Controller control) {
        controller = (VideoGalleryController) control;
        hPanel = new HorizontalPanel();
        createView();
        initWidget(hPanel);
    }

    Ordering getOrdering() {
        int d = descAsc.getSelectedIndex();
        switch (lb.getSelectedIndex()) {
            case 0:
                switch (d) {
                    case 0:
                        return Ordering.PUBLISHED_DESC;
                    case 1:
                        return Ordering.PUBLISHED_ASC;
                }
            case 1:
                switch (d) {
                    case 0:
                        return Ordering.GOSURATING_DESC;
                    case 1:
                        return Ordering.GOSURATING_ASC;
                }
            case 2:
                switch (d) {
                    case 0:
                        return Ordering.AUTHOR_DESC;
                    case 1:
                        return Ordering.AUTHOR_ASC;
                }
            case 3:
                switch (d) {
                    case 0:
                        return Ordering.TITLE_DESC;
                    case 1:
                        return Ordering.TITLE_ASC;
                }
        }
        return Ordering.PUBLISHED_DESC;
    }

    private void createView() {
        Label orderBy = new Label("Order By:");
        hPanel.add(orderBy);
        lb = new ListBox();
        lb.addItem("Date");
        lb.addItem("Votes");
        lb.addItem("Author");
        lb.addItem("Title");
        lb.addChangeListener(changed);

        hPanel.add(lb);
        descAsc = new ListBox();
        descAsc.addItem("Descending");
        descAsc.addItem("Ascending");
        hPanel.add(descAsc);

        descAsc.addChangeListener(changed);
        
        
    }
    ChangeListener changed = new ChangeListener() {

        public void onChange(Widget arg0) {
            controller.filterChanged();
        }
    };
}
