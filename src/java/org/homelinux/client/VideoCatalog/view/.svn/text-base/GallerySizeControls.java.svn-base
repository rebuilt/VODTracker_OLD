
package org.homelinux.client.VideoCatalog.view;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import org.homelinux.client.Controller;
import org.homelinux.client.QueryObject;
import org.homelinux.client.VideoCatalog.controller.VideoGalleryController;

/**
 * A GUI that contains controls to change the size of the list.
 * @author nelson
 */
public class GallerySizeControls extends Composite {

    VideoGalleryController controller;
    HorizontalPanel hPanel;
    ListBox itemsPerRowLB;
    ListBox totalItems;
    int total;
    int numItemsPerRow =4;
    int numRows=3;

    public GallerySizeControls(Controller cont) {
        controller = (VideoGalleryController) cont;
        hPanel = new HorizontalPanel();
        initWidget(hPanel);
        initialize();
    }

    private void createListBox() {
        hPanel.clear();
        totalItems = new ListBox();
        for (int j = 3; j < 8; j++) {

     //       System.out.println("Rows : " + total);
            totalItems.addItem(j + "");
        }
        totalItems.setSelectedIndex(numRows-3);
        totalItems.addChangeListener(changeListener);
        

        Label itemsPerRow = new Label("Items per row: ");
        hPanel.add(itemsPerRow);
        hPanel.add(itemsPerRowLB);
        Label totalIT = new Label("Number of rows: ");
        hPanel.add(totalIT);
        hPanel.add(totalItems);

    }

    private void initialize() {

        itemsPerRowLB = new ListBox();
        for (int i = 3; i < 8; i++) {
            itemsPerRowLB.addItem(i + "");

        }
        itemsPerRowLB.setSelectedIndex(1);
        createListBox();
        itemsPerRowLB.addChangeListener(changeListener);
    }
    ChangeListener changeListener = new ChangeListener() {

        public void onChange(Widget arg0) {
            if(arg0==itemsPerRowLB){
            createListBox();
            }
           numItemsPerRow =
                    Integer.parseInt(itemsPerRowLB.getItemText(itemsPerRowLB.getSelectedIndex()));
            numRows =
                    Integer.parseInt(totalItems.getItemText(totalItems.getSelectedIndex()));
            total = numItemsPerRow * numRows;
            QueryObject query = controller.getQueryObject();
            query.setNumOfResults(total);
            
            VideoCatalog gallery = controller.getVideoGallery();
            gallery.setItemsPerRow(numItemsPerRow);
            controller.query(query);

        }
    };
    
}
