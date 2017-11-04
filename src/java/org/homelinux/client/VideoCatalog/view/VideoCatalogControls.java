/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoCatalog.view;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.Date;
import org.homelinux.client.Defaults;
import org.homelinux.client.FilterElement;
import org.homelinux.client.Model;
import org.homelinux.client.Observer;
import org.homelinux.client.QueryObject;
import org.homelinux.client.VideoCatalog.controller.VideoGalleryController;
import org.homelinux.client.VideoCatalog.model.VideoModel;

/**
 * GUI for filtering by date
 * @author nelson
 */
public class VideoCatalogControls extends Composite implements Observer {

    VideoGalleryController controller;
    VerticalPanel vPanel;
    private Button previous = new Button("Previous");
    private Button next = new Button("Next");
    private SuggestBox searchBox = new SuggestBox();
    private Button searchButton = new Button("search");
    private Label pages = new Label();
    private String[] timePeriodText = {
        "Today", "This Week", "This Month",
        "All Time"
    };
    private int activeTimePeriod = 3;
    private Button[] timePeriodButtons = new Button[timePeriodText.length];

    VideoCatalogControls(VideoGalleryController control)
    {
        controller = control;
        vPanel = new VerticalPanel();
        VideoModel videoModel = controller.getVideoModel();
        videoModel.addObserver((Observer) this);
        createView();
        initWidget(vPanel);
    }

    private void createView()
    {
        HorizontalPanel time = new HorizontalPanel();
        for (int i = 0; i < timePeriodText.length; i++) {
            final Button b = new Button(timePeriodText[i]);
            final int tmp = i;
            b.addClickListener(new ClickListener() {

                public void onClick(Widget arg0)
                {
                    activeTimePeriod = tmp;
                    controller.filterChanged();

                }
            });
            timePeriodButtons[i] = b;
            time.add(b);
        }

        HorizontalPanel searchAndNext = new HorizontalPanel();
        DecoratorPanel dec = new DecoratorPanel();
        dec.add(searchBox);
        searchAndNext.add(dec);

        searchAndNext.add(searchButton);
        HorizontalPanel hPanel2 = new HorizontalPanel();


        hPanel2.add(previous);
        hPanel2.add(next);
        hPanel2.add(pages);
        Label spacer = new Label();
        time.add(spacer);
        time.setCellWidth(spacer, "100px");
        time.add(hPanel2);
        vPanel.add(time);
        vPanel.add(searchAndNext);
        searchButton.addClickListener(new ClickListener() {

            public void onClick(Widget sender)
            {
                String searchText = searchBox.getText();
                FilterElement filt =
                        new FilterElement(searchText, FilterElement.AUTHOR);
                filt = new FilterElement(searchText, FilterElement.TITLE, filt);

                QueryObject obj = new QueryObject(filt);
                controller.query(obj);

            }
        });
        next.addClickListener(new ClickListener() {

            public void onClick(Widget arg0)
            {
                controller.next();
                previous.setEnabled(true);
            }
        });
        previous.addClickListener(new ClickListener() {

            public void onClick(Widget arg0)
            {
                controller.previous();
                if (controller.getStartIndex() == 0) {
                    previous.setEnabled(false);
                }
            }
        });

    }

    public FilterElement getFilter()
    {
        String date = createTimePeriodString(activeTimePeriod);
        return new FilterElement(date, FilterElement.PUBLISHED, FilterElement.Operator.GREATERTHAN);

    }

    private String createTimePeriodString(int period)
    {

        long currentTime = Defaults.getNow().getTime();


        DateTimeFormat formatter =
                DateTimeFormat.getFormat("yyyy-MM-dd");
        String today;//= formatter.format(now);

        switch (period) {
            case 0:
                long ago = 1000 * 60 * 60 * 24;
                Date yesterday = new Date(currentTime - ago);
                today = formatter.format(yesterday);
                return "'" + today + "'";
            case 1:
                long week = 1000 * 60 * 60 * 24 * 7L;
                Date weekAgo = new Date(currentTime - week);
                String lastWeek = formatter.format(weekAgo);
                return "'" + lastWeek + "'";
            case 2:
                long month = 1000 * 60 * 60 * 24 * 30L;
                Date monthAgo = new Date(currentTime - month);
                String lastMonth = formatter.format(monthAgo);
                return "'" + lastMonth + "'";
            default:
                return "0";

        }

    }

    public void update(Model m)
    {
        QueryObject query = controller.getQueryObject();
        if (query.getStartIndex() == 0) {
            previous.setEnabled(false);
        } else {
            previous.setEnabled(true);
        }

        int startIndex = query.getStartIndex();
        int numResults = query.getNumofResults();
        int total = startIndex + numResults;
        startIndex++;

        String out = "(" + startIndex + "-" + total + ")";
        pages.setText(out);
    }
}
