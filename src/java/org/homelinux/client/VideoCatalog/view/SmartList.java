/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.homelinux.client.VideoCatalog.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import org.homelinux.client.Controller;
import org.homelinux.client.FilterElement;
import org.homelinux.client.Model;
import org.homelinux.client.Ordering;
import org.homelinux.client.QueryObject;
import org.homelinux.client.VTPanel;
import org.homelinux.client.VideoCatalog.controller.VideoGalleryController;
import org.homelinux.client.VideoCatalog.model.SmartListModel;

/**
 * GUI that provides a way for the user to construct smartLists.  A smart list is a custom-made query that can be saved and executed
 * on subsequent visits.
 * @author nelson
 */
public class SmartList extends Composite implements VTPanel {

    VideoGalleryController controller;
    TabPanel tabPanel;
    Button submit;
    VerticalPanel vPanel;
    ArrayList<ItemSelector> selectors = new ArrayList<ItemSelector>();
    ListBox matchThis;
    ListBox orderBy;
    String[] type = {"Date desc", "Date asc", "Author desc", "Author asc", "Title desc", "Title asc", "Rating desc", "Rating asc"};
    VerticalPanel savedPanel;

    public SmartList(Controller control)
    {
        controller = (VideoGalleryController) control;
        tabPanel = new TabPanel();
        tabPanel.setWidth("200px");
        orderBy = new ListBox();

        for (int i = 0; i < type.length; i++) {
            orderBy.addItem(type[i]);
        }
        loadState("");
        initWidget(tabPanel);
    }

    public void loadState(String state)
    {


        vPanel = new VerticalPanel();
        Label match = new Label("Match ");
        Label match2 = new Label(" of the following:");
        match.setStyleName("whiteOnBlack");
        match2.setStyleName("whiteOnBlack");
        matchThis = new ListBox();
        matchThis.addItem("all");
        matchThis.addItem("any");
        HorizontalPanel tmp0 = new HorizontalPanel();
        tmp0.add(match);
        tmp0.add(matchThis);
        tmp0.add(match2);

        HorizontalPanel tmp2 = new HorizontalPanel();
        tmp2.add(new Label("Order by:"));
        tmp2.add(orderBy);
        DecoratorPanel dec = new DecoratorPanel();
        dec.add(tmp2);


        ItemSelector selector = new ItemSelector(controller, "", this);
        selectors.add(selector);
        HorizontalPanel tmp1 = new HorizontalPanel();
        tmp1.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);



        submit = new Button("Submit");
        submit.setStyleName("red");
        tmp1.add(submit);

        vPanel.add(tmp0);
        vPanel.add(selector);
        vPanel.add(dec);
        vPanel.add(tmp1);
        addButtonListeners();

        tabPanel.add(vPanel, "SmartList");

        savedPanel = new VerticalPanel();
        savedPanel.add(new Label("Saved SmartLists"));
        savedPanel.setStyleName("whiteOnBlack");

        tabPanel.add(savedPanel, "Saved");
        tabPanel.selectTab(0);

    }



    private void makeButton(final QueryObject query)
    {
     //   System.out.println("smartlistpanel got signal to make button");
        final HorizontalPanel buttonPanel = new HorizontalPanel();

        FilterElement filt = query.getFilter();

        String name = filt.getSqlFilterText().replaceAll("[0-9]_", "");
        name = name.replaceAll("~", " : ");
        Button q = new Button(name);

        q.addClickListener(new MyClickListener(query));
        buttonPanel.add(q);
        Button remove = new Button("X");
        remove.addClickListener(new ClickListener() {

            public void onClick(Widget sender)
            {
                savedPanel.remove(buttonPanel);
                controller.removeSmartListItem(query);
            }
        });
        buttonPanel.add(remove);
        savedPanel.add(buttonPanel);
    }

    class MyClickListener implements ClickListener {

        QueryObject query;

        public MyClickListener(QueryObject q)
        {
            query = q;
        }

        public void onClick(Widget sender)
        {
            controller.query(query);

        }
    }

    public void update(Model m)
    {
        if (m instanceof SmartListModel) {
     //       System.out.println("smartlistpanel got signal to update");
            SmartListModel model = (SmartListModel) m;
            savedPanel.clear();
            savedPanel.add(new Label("Saved SmartLists"));
            ArrayList<QueryObject> queries = model.getQueries();

            for (QueryObject query : queries) {
                makeButton(query);
            }
            
            if(!queries.isEmpty())
            tabPanel.selectTab(1);

        }
    }

    void addItem()
    {
        int count = vPanel.getWidgetCount();
  //      System.out.println("adding itemselector at index: " + count);
        ItemSelector select = new ItemSelector(controller, "", this);
        vPanel.insert(select, count - 2);
        selectors.add(select);
    }

    void removeItem(ItemSelector item)
    {
        int count = vPanel.getWidgetCount();
        vPanel.remove(item);
        selectors.remove(item);
        if (selectors.size() == 0) {
            ItemSelector select = new ItemSelector(controller, "", this);
            vPanel.insert(select, count - 3);
            selectors.add(select);
        }


    }

    private void addButtonListeners()
    {

        submit.addClickListener(new ClickListener() {

            public void onClick(Widget sender)
            {

                boolean matchAny = true;
                int index = matchThis.getSelectedIndex();
                switch (index) {
                    case 0:
                        matchAny = false;
                        break;
                    case 1:
                        matchAny = true;
                        break;
                }
                FilterElement filt = null;
                for (ItemSelector selector : selectors) {
                    if (filt == null) {
                        filt = selector.getFilter(matchAny);

                        filt.openParens();
                    } else {
                        filt.add(selector.getFilter(matchAny));
                    }
                }

                filt.closeParens();

                Ordering od = getOrdering();
            
                QueryObject query = new QueryObject();
                query.setFilter(filt);
                query.setOrdering(od);
                controller.query(query);
                controller.addSmartListQuery(query);
                tabPanel.selectTab(1);

            }

// String[] type = {"Author ascending", "Author descending", "Title ascending","Title descending", "Rating ascending","Rating descending"};
            private Ordering getOrdering()
            {
                int index = orderBy.getSelectedIndex();
               Ordering od = Ordering.AUTHOR_ASC;
                switch (index) {
                    case 0:
                        od = Ordering.PUBLISHED_DESC;
                        break;
                    case 1:
                        od = Ordering.PUBLISHED_ASC;
                        break;
                    case 2:
                        od = Ordering.AUTHOR_DESC;
                        break;
                    case 3:
                        od = Ordering.AUTHOR_ASC;
                        break;
                    case 4:
                        od = Ordering.TITLE_DESC;
                        break;
                    case 5:
                        od = Ordering.TITLE_ASC;
                        break;
                    case 6:
                        od = Ordering.GOSURATING_DESC;
                        break;
                    case 7:
                        od = Ordering.GOSURATING_ASC;
                        break;

                }
                return od;
            }
        });
    }
}
