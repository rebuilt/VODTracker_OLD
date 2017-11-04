
package org.homelinux.client.VideoCatalog.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.homelinux.client.Controller;
import org.homelinux.client.FilterElement;
import org.homelinux.client.Model;
import org.homelinux.client.VTPanel;
import org.homelinux.client.VideoCatalog.controller.VideoGalleryController;

/**
 * A GUI used by a {@link SmartList} to construct custom queries.
 * @author nelson
 */
class ItemSelector extends Composite implements VTPanel {

    String[] type = {"Author", "Title", "Rating", "Description"};
    String[] operator = {"Like", "Not Like", "Equals", ">", "<"};
    String[] ordering = {"Author", "Published", "Title", "Rating"};
    String[] ascDesc = {"Ascending", "Descending"};
    String[][] listBoxInitStrings = {type, operator};
    VideoGalleryController controller;
    String initState;
    HorizontalPanel hPanel;
    ListBox[] listBoxes = new ListBox[4];
    TextBox selectorBox;
    Button add, remove;
    SmartList smartList;

    public ItemSelector(Controller control, String initializationState, SmartList sm)
    {
        controller = (VideoGalleryController) control;
        initState = initializationState;
        hPanel = new HorizontalPanel();
        smartList = sm;
        loadState(initState);

        initWidget(hPanel);
    }

    public void loadState(String state)
    {


        for (int j = 0; j < listBoxInitStrings.length; j++) {
            listBoxes[j] = new ListBox();
            for (int i = 0; i < listBoxInitStrings[j].length; i++) {
                listBoxes[j].addItem(listBoxInitStrings[j][i]);
            }
        }
        FlexTable flex = new FlexTable();
        listBoxes[0].setWidth("90px");
        flex.setWidget(0, 0, listBoxes[0]);
        flex.setWidget(0, 1, listBoxes[1]);

        selectorBox = new TextBox();
        selectorBox.setText(state);
        selectorBox.setWidth("90px");
        selectorBox.setStyleName("red");
        flex.setWidget(1, 0, selectorBox);
        HorizontalPanel tmp = new HorizontalPanel();
        add = new Button("+");
        remove = new Button("-");

        tmp.add(remove);
        tmp.add(add);
        flex.setWidget(1, 1, tmp);

        addListeners();
        DecoratorPanel decorator = new DecoratorPanel();
        decorator.add(flex);
        hPanel.add(decorator);
    }

    public void update(Model m)
    {
        loadState(initState);
    }
    public static final int AUTHOR = 0;
    public static final int TITLE = 1;
    public static final int RATING = 2;
    public static final int DESCRIPTION = 3;

    public void setFilterType(int type)
    {
        listBoxes[0].setSelectedIndex(type);
    }

    FilterElement getFilter(boolean matchAny)
    {
        int type = FilterElement.AUTHOR;
        FilterElement.Operator op = FilterElement.Operator.LIKE;

        //   String[] type = {"Author", "Title", "Rating", "Description"};
        switch (listBoxes[0].getSelectedIndex()) {
            case 0:
                type = FilterElement.AUTHOR;
                break;
            case 1:
                type = FilterElement.TITLE;
                break;
            case 2:
                type = FilterElement.RATING;
                break;
            case 3:
                type = FilterElement.DESCRIPTION;
                break;

        }
        //    String[] operator = {"Like", "Not Like", "Equals", ">", "<"};
        switch (listBoxes[1].getSelectedIndex()) {
            case 0:
                op = FilterElement.Operator.LIKE;
                break;
            case 1:
                op = FilterElement.Operator.NOT;
                break;
            case 2:
                op = FilterElement.Operator.EQUALS;
                break;
            case 3:
                op = FilterElement.Operator.GREATERTHAN;
                break;
            case 4:
                op = FilterElement.Operator.LESSTHAN;
                break;


        }
        FilterElement.Combiner co = FilterElement.Combiner.AND;
        if (matchAny) {
            co = FilterElement.Combiner.OR;
        }

        FilterElement filt =new FilterElement(selectorBox.getText(), type, op, co);
        return filt;
    }

    
    public String getTextBoxValue()
    {
        return selectorBox.getText();
    }

    private void addListeners()
    {
        add.addClickListener(new ClickListener() {

            public void onClick(Widget sender)
            {
                smartList.addItem();
            }
        });
        remove.addClickListener(new ClickListener() {

            public void onClick(Widget sender)
            {
                removeItem();
            }
        });

    }

    private void removeItem()
    {
        smartList.removeItem(this);
    }
}
