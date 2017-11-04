/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.homelinux.client.VideoCatalog.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import org.homelinux.client.Controller;
import org.homelinux.client.VideoCatalog.controller.VideoGalleryController;
import org.homelinux.client.VideoCatalog.model.Video;

/**
 * A button that adds the video to the users playlist.
 * @author nelson
 */
public class PlayListButton extends Button implements ClickListener{

  private  VideoGalleryController controller;
 private   Video video;
    public PlayListButton(String name, Controller control, Video vid)
    {
        super(name);
        if(control instanceof VideoGalleryController){
        controller = (VideoGalleryController) control;
        }else{
            throw new UnsupportedOperationException();
        }
        video = vid;
        this.addClickListener(this);
    }

    public void onClick(Widget sender)
    {
       controller.addPlayListItem(video);
   //    System.out.println("add to playlist button was clicked");
    }







}
