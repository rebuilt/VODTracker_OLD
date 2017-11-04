package org.homelinux.client.VideoCatalog.view;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import org.homelinux.client.Controller;
import org.homelinux.client.Defaults;
import org.homelinux.client.VideoCatalog.model.Video;
import org.homelinux.client.VideoCatalog.model.Video.ATT;

/**
 * GUI for incrementing the rating for a video
 * @author nelson
 */
public class RatingWidget extends Composite {

    HorizontalPanel gosuPanel;
    Controller controller;
    Video video;
    Label gosuRating;

    public RatingWidget(Controller control, Video vid) {
        video = vid;
        controller = control;
        final Button dgs;

        gosuPanel = new HorizontalPanel();
        gosuRating = new Label(video.getAttribute(ATT.GOSURATING));
        gosuRating.setStyleName("rating");
        dgs = new Button("Vote");
        String rated = Cookies.getCookie("rated");
        if (rated != null) {
            if (rated.contains(video.getAttribute(ATT.VIDEOID))) {
                dgs.setEnabled(false);
                dgs.setText("Voted");
            }
        }
        dgs.addClickListener(new ClickListener() {

            public void onClick(Widget arg0) {
                String id = video.getAttribute(ATT.VIDEOID);
                controller.upVote(id);
                Button but = (Button) arg0;
                but.setEnabled(false);
                String rated = Cookies.getCookie("rated");
                if (rated != null) {
                    if (!rated.contains(video.getAttribute(ATT.VIDEOID))) {
                        rated += video.getAttribute(ATT.VIDEOID);
                    }
                } else {
                    rated += video.getAttribute(ATT.VIDEOID);
                }
                Cookies.setCookie("rated", rated,Defaults.getExpiration());
                dgs.setText("Voted");
                String num = gosuRating.getText();
                int rating = Integer.parseInt(num);
                rating++;
                gosuRating.setText(rating + "");
            }
        });

        gosuPanel.add(gosuRating);
        gosuPanel.add(dgs);
        initWidget(gosuPanel);

    }
}
