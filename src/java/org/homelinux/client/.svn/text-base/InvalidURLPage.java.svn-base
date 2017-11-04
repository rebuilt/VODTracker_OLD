
package org.homelinux.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * This page will be used as the default for invalid requests.
 * @author nelson
 */
class InvalidURLPage extends Composite {
private HorizontalPanel panel ;
    public InvalidURLPage() {
        panel= new HorizontalPanel();
        loadState(" null ");
        initWidget(panel);
    }

    public void loadState(String state) {
        Label msg = new Label("The page you are trying to load does not exist.  Please check the url and try again.  ");
        panel.add(msg);
    }


    public Controller getController() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public boolean equals(Object obj){
        return obj instanceof InvalidURLPage;
    }

}
