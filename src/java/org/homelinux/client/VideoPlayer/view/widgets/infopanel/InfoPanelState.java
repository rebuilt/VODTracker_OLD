/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoPlayer.view.widgets.infopanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosureEvent;
import com.google.gwt.user.client.ui.DisclosureHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DisclosurePanelImages;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import java.util.HashMap;
import org.homelinux.client.Observer;
import org.homelinux.client.VideoCatalog.model.Video;

/**
 *
 * @author nelson
 */
public abstract class InfoPanelState extends Composite  implements Observer {


    INFOTYPE type;
    HashMap<String,String> info;
    Video video;
    DisclosurePanel disc;


    DisclosurePanel makeDisclosurePanel(String name, boolean open)
    {
        DisclosurePanel playerPanel;
        if (!info.isEmpty()) {
            playerPanel =
                    new DisclosurePanel(name + " (" + info.size() + ")", open);
       //     HorizontalPanel hPanel = new HorizontalPanel();
        //    hPanel.add(new Label(name + " (" + info.size() + ")"));
       //     hPanel.add(new Button("Edit"));
       //     playerPanel.setHeader(hPanel);
        } else {
            playerPanel = new DisclosurePanel(name, open);
           // HorizontalPanel hPanel = new HorizontalPanel( );
         //   hPanel.add(new Label(name));
          //  hPanel.add(new Button("Edit"));
         //   playerPanel.setHeader(hPanel);
        }
        return playerPanel;
    }

public void buildPage()
{
    final DisclosurePanel panel = new DisclosurePanel(
    		"Click Here To Open");
    panel.addEventHandler(new DisclosureHandler()
    {

        public void onClose(DisclosureEvent event)
        {
            panel.setHeader(new DisclosurePanelHeader(false,
            	 "Click Here To Open"));
        }

        public void onOpen(DisclosureEvent event)
        {
            panel.setHeader(new DisclosurePanelHeader(true,
            	"Click Here To Close"));
        }


    });
    panel.add(new Image("images/voteBackground.png"));
    panel.setWidth("300px");
    panel.addStyleName("table-center");
   disc = panel;
}

final DisclosurePanelImages images = (DisclosurePanelImages)
		GWT.create(DisclosurePanelImages.class);
class DisclosurePanelHeader extends HorizontalPanel
{
    public DisclosurePanelHeader(boolean isOpen, String html)
    {
        add(isOpen ? images.disclosurePanelOpen().createImage()
              : images.disclosurePanelClosed().createImage());
        add(new Hyperlink(html,""));
    }
}


    abstract void draw();
}
