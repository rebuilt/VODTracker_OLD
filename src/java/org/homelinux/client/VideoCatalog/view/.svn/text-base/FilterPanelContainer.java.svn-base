
package org.homelinux.client.VideoCatalog.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.homelinux.client.Controller;
import org.homelinux.client.Defaults;
import org.homelinux.client.FilterElement;
import org.homelinux.client.Model;
import org.homelinux.client.dateUtils.SimpleDateFormat;
import org.homelinux.client.VTPanel;
import org.homelinux.client.VideoCatalog.controller.VideoGalleryController;
import org.homelinux.client.VideoCatalog.model.NewestVideoModel;

/**
 * A GUI that provides controls for filtering the video catalog.
 * @author nelson
 */
public class FilterPanelContainer extends Composite implements VTPanel {

    VideoGalleryController controller;
    TabPanel tabPanel;
    CheckBox[] commentatorCheckBoxes, leagueCheckBoxes, vodCheckBoxes;
    ArrayList checkBoxes;
    CheckBox[] activeCheckBoxes;
    int activeTab = 0;
    ArrayList names;
    NewestVideoModel newest;
    private String[][] commentators;
    private String[][] leagues;
    private String[][] vods;

    String[][] activeNameList;
    VerticalPanel vodPanel;
    VerticalPanel leaguePanel;
    VerticalPanel commentatorPanel;


    public FilterPanelContainer(Controller control)
    {
        fillArrays();

        controller = (VideoGalleryController) control;

        tabPanel = new TabPanel();

        createView();
        initWidget(tabPanel);

    }
/**
 * Add a listener to the tabPanel to set the active list of checkboxes and names of commentators
 */
    private void addTabListener()
    {
        tabPanel.addTabListener(new TabListener() {

            public boolean onBeforeTabSelected(SourcesTabEvents arg0, int arg1)
            {
                return true;
            }

            public void onTabSelected(SourcesTabEvents arg0, int arg1)
            {
                activeTab = arg1;
                switch (activeTab) {
                    case 0:
                        activeCheckBoxes = commentatorCheckBoxes;
                        activeNameList = commentators;

                        break;
                    case 1:
                        activeCheckBoxes = leagueCheckBoxes;
                        activeNameList = leagues;
                        break;
                    case 2:
                        activeCheckBoxes = vodCheckBoxes;
                        activeNameList = vods;
                        break;
                 
                }
                controller.filterChanged();
                setCheckBoxText(newest.getNewest());
            }
        });
    }
    int count = 1;

    /**
     * Set the text that appears on each checkbox.  The text includes a count of the number of new videos a user has not watched
     * @param map a map of commentator names and the number of new unwatched videos
     */
    public void setCheckBoxText(HashMap<String, Integer> map)
    {
        String name;
        int newCount;
        String cookie = "";
        for (int j = 0; j < activeCheckBoxes.length; j++) {
            name = activeNameList[j][0];
            cookie = Cookies.getCookie("watched" + activeNameList[j][1]);

            if (cookie != null) {
                count = countNewest(cookie);
            } else {
                count = 0;
            }

            try {
                newCount = map.get(activeNameList[j][1]);
                
            } catch (Exception e) {
                //this person has not published new vids this week
                newCount = 0;
            }
            if ((newCount- count) > 0) {
                name = name + " (" + (newCount- count) + ")";
            }
            //  System.out.println(name);
            activeCheckBoxes[j].setText(name);


        }

    }
    /**
     * Counts the number of new unwatched videos
     * @param cookie the name of the cookie that contains the videos the user has already watched
     * @return the number of new unwatched videos
     */

    private int countNewest(String cookie)
    {

        String[] watched = cookie.split("@");
        Date now = Defaults.getNow();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String today = formatter.format(now);
        int day = Defaults.dateCode(today);
     //     System.out.println(cookie);
        for (String d : watched) {

            String[] dd = d.split("/");
            if (dd.length == 2) {
                //     System.out.println("cookie split: " + d + " : " + dd[1]);
                int date = Integer.parseInt(dd[1]);
                //           System.out.println("today: " + day + " and date of video: " + date);

              //  System.out.println("today: " +day +"\tcompared to: "+date + " = " + (day-date));
                if (day - date <= 8) {
                    count++;
                }
            }else{
                System.out.println("something is wrong with this cookie.  could not parse it in the form [videoid]/[datecode]@");
            }

        }
     //   System.out.println("********************************************");

        return count;
    }
/**
 * construct the GUI elements
 */
    private void createView()
    {
        Label filterText = new Label("Filter Videos By: ");
        filterText.setStyleName("filterPanel");

        commentatorPanel = new VerticalPanel();
        commentatorPanel.setStyleName("whiteOnBlack");
        commentatorPanel.add(filterText);
        leaguePanel = new VerticalPanel();
        leaguePanel.setStyleName("whiteOnBlack");
        leaguePanel.add(new Label("Leagues"));
        vodPanel = new VerticalPanel();
        vodPanel.setStyleName("whiteOnBlack");
        vodPanel.add(new Label("Korean Vods"));


        VerticalPanel sideBar = new VerticalPanel();
        Label viewSave = new Label("Saving view");
        viewSave.setVisible(false);
        viewSave.setStyleName("yellow");
        sideBar.add(viewSave);
        commentatorPanel.add(sideBar);

        tabPanel.setAnimationEnabled(true);
        tabPanel.add(commentatorPanel, "SC2GG");
        tabPanel.add(leaguePanel, "1");
        tabPanel.add(vodPanel, "2");

        tabPanel.selectTab(0);
        tabPanel.setWidth("200px");
        addTabListener();

        commentatorCheckBoxes =
                initializeCheckBoxes(commentatorPanel, commentators);
        leagueCheckBoxes = initializeCheckBoxes(leaguePanel, leagues);
        vodCheckBoxes = initializeCheckBoxes(vodPanel, vods);

        this.activeCheckBoxes = commentatorCheckBoxes;
        this.activeNameList = commentators;


        final Label settings = new Label("Saving Settings");
        settings.setStyleName("yellow");
        settings.setVisible(false);
        commentatorPanel.add(settings);
        Button commentatorSelectAll =
                new SelectButton("Select All", commentatorCheckBoxes, controller, true);
        Button commentatorSelectNone =
                new SelectButton("Select None", commentatorCheckBoxes, controller, false);
        Button leaguesSelectAll =
                new SelectButton("Select All", leagueCheckBoxes, controller, true);
        Button leaguesSelectNone =
                new SelectButton("Select None", leagueCheckBoxes, controller, false);
        Button vodsSelectAll =
                new SelectButton("Select All", vodCheckBoxes, controller, true);
        Button vodsSelectNone =
                new SelectButton("Select None", vodCheckBoxes, controller, false);


        commentatorPanel.add(commentatorSelectAll);
        commentatorPanel.add(commentatorSelectNone);
        leaguePanel.add(leaguesSelectAll);
        leaguePanel.add(leaguesSelectNone);
        vodPanel.add(vodsSelectAll);
        vodPanel.add(vodsSelectNone);


        checkBoxes = new ArrayList();
        checkBoxes.add(commentatorCheckBoxes);
        checkBoxes.add(leagueCheckBoxes);
        checkBoxes.add(vodCheckBoxes);

        names = new ArrayList();
        names.add(commentators);
        names.add(leagues);
        names.add(vods);

     //   announceCOTW();


    }
    public void announceCOTW(){
                PopupPanel simplePopup =
                        new PopupPanel(true);


                simplePopup.setWidth("300px");
                simplePopup.setHeight("50px");
                simplePopup.setWidget(new HTML("Commentator of the Week is: DejaVu119"));
                simplePopup.setStyleName("announcement");
                      int left = tabPanel.getAbsoluteLeft()+170;
                int top = tabPanel.getAbsoluteTop() + 200 ;
                simplePopup.setPopupPosition(left, top);
  simplePopup.setAnimationEnabled(true);
                // Show the popup
                simplePopup.show();

    }

    public void loadState(String state)
    {
        
    }

    /**
     * Handle updates to the model.
     * @param m a model that contains a list of new videos
     */
    public void update(Model m)
    {
        if (m instanceof NewestVideoModel) {
            //     System.out.println("signal sent to filterPanelContainer");
            if (newest == null) {
                newest = (NewestVideoModel) m;
            }
            setCheckBoxText(newest.getNewest());
        }
    }
    int[] counts = null;


    private String[][] fill(String[] a, String[] b)
    {
        String[][] output = new String[a.length][2];
        for (int i = 0; i < a.length; i++) {

            output[i][0] = a[i];
            output[i][1] = b[i];
        }
        return output;
    }

    private void fillArrays()
    {
        Publishers constants = GWT.create(Publishers.class);

        String[] a = constants.sc2ggCommentators();
        String[] b = constants.sc2ggAccounts();
        commentators = fill(a, b);
        a = constants.leagues();
        b = constants.leagues();
        leagues = fill(a, b);
        a = constants.koreanVODs();
        b = constants.koreanAccounts();
        vods = fill(a, b);


    }

    private CheckBox[] initializeCheckBoxes(VerticalPanel panel, String[][] filterNames)
    {

        CheckBox[] checkBoxes = new CheckBox[filterNames.length];


        for (int i = 0; i < filterNames.length; i++) {
            final String checkBoxName = filterNames[i][0];
            final CheckBox checkB = new CheckBox(checkBoxName);

            checkB.setChecked(true);
            checkBoxes[i] = checkB;
            String checkBoxState = Cookies.getCookie("checkBoxState");
            if (checkBoxState != null) {
                if (checkBoxState.contains(checkBoxName)) {
                    checkB.setChecked(false);
                }
            }
            checkB.addClickListener(new ClickListener() {

                public void onClick(Widget arg0)
                {
                    if (checkB.isChecked()) {
                        String checkBoxState =
                                Cookies.getCookie("checkBoxState");
                        if (checkBoxState != null) {
                            checkBoxState =
                                    checkBoxState.replaceAll(checkBoxName, "");
                            Cookies.setCookie("checkBoxState", checkBoxState, Defaults.getExpiration());
                        }
                    //                   System.out.println("checkbox is now checked. removing "+ checkBoxName + " from "+ checkBoxState);

                    } else {
                        String checkBoxState =
                                Cookies.getCookie("checkBoxState");
                        if (checkBoxState == null) {
                            checkBoxState += "";
                        }
                        Cookies.setCookie("checkBoxState", checkBoxState + checkBoxName, Defaults.getExpiration());
                    //                System.out.println("checkbox is now unchecked. adding "+ checkBoxName + " to "+ checkBoxState);
                    }

                    controller.filterChanged();
                }
            });
            panel.add(checkB);

        }
        return checkBoxes;
    }
/**
 * Iterate over all the checkboxes to see which are active and construct a {@link FilterElement} that represents a query.
 * @return a FilterElment that represents a query.
 */
    public FilterElement getFilter()
    {
        CheckBox checkB;
        //"this is a dummy filter used to start off the set"
        FilterElement filt = new FilterElement("xxxx", FilterElement.AUTHOR);
        filt.openParens();
        for (int i = 0; i < activeCheckBoxes.length; i++) {
            checkB = activeCheckBoxes[i];
            String name = activeNameList[i][1];
            if (checkB.isChecked()) {
                switch (activeTab) {


                    case 1:
                        filt =
                                new FilterElement(name, FilterElement.TITLE, filt);
                        break;

                    case 0:
                    case 2:// fall through
                    case 3:
                        filt =
                                new FilterElement(name, FilterElement.AUTHOR, FilterElement.Operator.EQUALS, filt);
                        break;
                }

            }
        }
        filt.closeParens();

        return filt;
    }
}
