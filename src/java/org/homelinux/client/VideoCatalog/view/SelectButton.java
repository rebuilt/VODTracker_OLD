/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoCatalog.view;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import org.homelinux.client.Controller;
import org.homelinux.client.Defaults;
import org.homelinux.client.VideoCatalog.controller.VideoGalleryController;

/**
 * Custom button for selecting a group of checkboxes as all enabled or disabled
 * @author nelson
 */
public class SelectButton extends Button {

    CheckBox[] checkBoxes;
    VideoGalleryController controller;
    boolean checked;

    public SelectButton(String title, CheckBox[] boxes, Controller control, boolean check)
    {
        super(title);
        checkBoxes = boxes;
        this.checked = check;
        controller = (VideoGalleryController) control;
        addClickListener(new ClickListener() {

            public void onClick(Widget arg0)
            {
                for (CheckBox checkB : checkBoxes) {
                    checkB.setChecked(checked);
                }

                Cookies.setCookie("checkBoxState", "", Defaults.getExpiration());

                controller.filterChanged();
            }
        });
    }
}
