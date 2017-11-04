/*


Copyright 2009 nelson <nelson@STUFFHOLDER>

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
MA 02110-1301, USA.
 */
package org.homelinux.client;

import org.homelinux.client.networkServices.Service;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.TextBox;

/**
 * The main entry point of the project.  Composes pages and panels and handles page requests.  Forwards those requests to the correct pages.
 * @author nelson
 */
public class starcraftEntryPoint implements EntryPoint, HistoryListener {

//    PageFactory factory;
    //  String[][] pages = {{"Videos", "v"}, {"Player", "w"}, {"Upload", "u"}, {"DB", "d"}};
    String[][] pages = {
        {
            "Videos", "v"
        },
        {
            "Player", "w"
        }
    };
    private Widget[] p = new Widget[pages.length];
    private DecoratedTabPanel tab;
    private DockPanel centering;
    private HorizontalPanel mainPanel;
    private VerticalPanel rootPanel,  leftPanel;
    String cotw = "ImageSC1";

    /** Creates a new instance of starcraftEntryPoint */
    public starcraftEntryPoint()
    {
        //      factory = new PageFactory();
    }

    /** 
    The entry point method, called automatically by loading a module
    that declares an implementing class as an entry-point
     */
    public void onModuleLoad()
    {
        History.addHistoryListener(this);


        //  MySettings constants = GWT.create(MySettings.class);
        //    System.out.println("welcome message: " + constants.welcomeMessage());

        tab = new DecoratedTabPanel();
        for (int i = 0; i < pages.length; i++) {
            p[i] = PageFactory.getPage(pages[i][1]);
            tab.add(p[i], pages[i][0]);
        }
        leftPanel = new VerticalPanel();
        leftPanel.setStyleName("leftPanel");
        leftPanel.add(PageFactory.getPanel(PageFactory.FILTERPANEL));
        leftPanel.add(PageFactory.getPanel(PageFactory.SMARTLISTS));
        leftPanel.add(PageFactory.getPanel(PageFactory.TOP10));

        tab.setWidth("100%");
        tab.setAnimationEnabled(true);
        tab.selectTab(0);


        addTabListener();

        //     Image sc2 = new Image("images/sc2.jpg");

        rootPanel = new VerticalPanel();
        rootPanel.setSize("100%", "100%");
        //   vPanel.add(sc2);
        mainPanel = new HorizontalPanel();

        rootPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

        rootPanel.add(new HTML(
                "<div id=\"container\">" +
                "<div id=\"header\"></div>" +
                "<div id=\"menu\">" +
                "<ul><li>" +
                "<a href=\"http://www.sc2gg.com/\" class=\"home_2\" id=\"home_1\">" +
                "<img src=\"http://www.sc2gg.com/images/buttons/home_1.gif\" alt=\"home\" width=\"100\" height=\"37\" />" +
                "</a></li>" +
                "<li>" +
                "<a href=\"http://sc2gg.com/forum\" class=\"forums_2\" id=\"forums_1\">" +
                "<img src=\"http://www.sc2gg.com/images/buttons/forums_1.gif\" alt=\"forums\" width=\"85\" height=\"37\" />" +
                "</a></li>" +
                "<li>" +
                "<a href=\"http://sc2gg.com/chat\" class=\"chat_2\" id=\"chat_1\">" +
                "<img src=\"http://www.sc2gg.com/images/buttons/chat_1.gif\" alt=\"forums\" width=\"115\" height=\"37\" />" +
                "</a></li>" +
                "<li>" +
                "<a href=\"http://starcraft.homelinux.org/\" class=\"vods_2\" id=\"vods_1\">" +
                "<img src=\"http://www.sc2gg.com/images/buttons/vods_1.gif\" alt=\"vods\" width=\"80\" height=\"37\" />" +
                "</a></li>" +
                //  "<li>" +
                //  "<a href=\"http://www.sc2gg.com/msl/msl20.php\" class=\"msl_2\" id=\"msl_1\">" +
                // "<img src=\"http://www.sc2gg.com/images/buttons/msl_1.gif\" alt=\"msl\" width=\"80\" height=\"37\" />" +
                //  "</a></li>" +
               "<li><a href=\"http://www.sc2gg.com/livestream\" class=\"livestream_2\" id=\"livestream_1\"><img src=\"http://www.sc2gg.com/images/buttons/livestream_1.gif\" alt=\"livestream\" width=\"115\" height=\"37\" /></a></li>" +
                 "<li><a href=\"http://www.sc2gg.com/blizzcon\" class=\"blizzcon_2\" id=\"blizzcon_1\"><img src=\"http://www.sc2gg.com/images/buttons/blizzcon_1.gif\" alt=\"livestream\" width=\"115\" height=\"37\" /></a></li>" +
                "<li><img src=\"http://www.sc2gg.com/images/layout/menu_right6.jpg\" alt=\"menu_padding\" width=\"200\" height=\"37\" /> </li>" +
                "</ul>" +
                "</div>" +
                "</div>"));
        //   makeIRCPanel();
        HTML forumLink =
                new HTML("Discuss the VODTracker on the <a href=\"http://sc2gg.com/forum/index.php?showtopic=9008\">SC2GG Forums</a>. ");
        forumLink.setStyleName("announcement");
        Hyperlink commentator = new Hyperlink("Commentator of the week is "+ cotw, "s"+cotw);
        commentator.addClickListener(filterByAuthor);
        commentator.setStyleName("announcement");
        rootPanel.add(forumLink);
        rootPanel.add(commentator);
        


        mainPanel.add(leftPanel);
        mainPanel.add(tab);
        mainPanel.setWidth("830px");
        VerticalPanel footer = new VerticalPanel();
        footer.add(new Label("If you have questions or if you want to have your YouTube account added to this page, email me@NelsonJovel.com"));
        footer.setStyleName("whiteOnBlack");
        footer.add(new Image("images/fsf.png"));
        centering = new DockPanel();
        centering.setWidth("100%");
        centering.add(mainPanel, DockPanel.CENTER);
        centering.add(rootPanel, DockPanel.NORTH);
        //  centering.add(spacer, DockPanel.WEST);
        //  centering.add(spacer2, DockPanel.EAST);
        centering.add(footer, DockPanel.SOUTH);
        centering.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
        //    centering.setCellWidth(mainPanel, "70%");

        // centering.setCellWidth(spacer, adjustedSpacerWidth(Window.getClientWidth()));
        // centering.setCellWidth(spacer2, adjustedSpacerWidth(Window.getClientWidth()));

        RootPanel.get().add(centering);
        History.fireCurrentHistoryState();
        //   Window.addWindowResizeListener(this);
        Service.initUpdater().ping(new AsyncCallback() {

            public void onFailure(Throwable caught)
            {
                System.out.println("got no answer from the updater service");
            }

            public void onSuccess(Object result)
            {
                // do nothing
            }
        });
    }

    private String adjustedSpacerWidth(int width)
    {
        String out = "0%";
        int difference = width - 830;
        if (difference > 0) {
            float left = difference / 2f;
            //    System.out.println("left" + left);
            float percent = left / width * 100f;
            out = percent + "%";
        }
        return out;
    }

    /**
     * Called each time a new history token is created with History.newItem()
     * @param historyToken the URI, i.e. everything after the '#' in the url
     */
    public void onHistoryChanged(String historyToken)
    {
        if (historyToken == null || historyToken.length() < 1) {
            historyToken = "v";
        }
        if (historyToken.length() > 0) {
            PageFactory.getPage(historyToken);
        } else {
            PageFactory.getPage(pages[0][1]);
        }
        char page = historyToken.toLowerCase().charAt(0);

        for (int i = 0; i < pages.length; i++) {
            if (page == pages[i][1].charAt(0)) {
                tab.selectTab(i);
            }
            if (page == 'p') {
                VideoList top10 = (VideoList) leftPanel.getWidget(0);
                top10.loadState(historyToken.substring(1, historyToken.length()));
            }
        }
    }

    private void addTabListener()
    {
        tab.addTabListener(new TabListener() {

            public boolean onBeforeTabSelected(SourcesTabEvents arg0, int arg1)
            {
                if (arg1 != 1) {
                    History.newItem(pages[arg1][1]);
                }
                return true;
            }

            public void onTabSelected(SourcesTabEvents arg0, int arg1)
            {
                switch (arg1) {
                    case 0:
                        mainPanel.insert(leftPanel, 0);

                        leftPanel.add(PageFactory.getPanel(PageFactory.FILTERPANEL));
                        leftPanel.add(PageFactory.getPanel(PageFactory.SMARTLISTS));
                        leftPanel.add(PageFactory.getPanel(PageFactory.TOP10));
                        PageFactory.getModel(PageFactory.NEWESTVIDEOMODEL).notifyObservers();
                        break;

                    case 1:
                        leftPanel.remove(PageFactory.getPanel(PageFactory.FILTERPANEL));
                        leftPanel.remove(PageFactory.getPanel(PageFactory.SMARTLISTS));
                        break;
                }
            }
        });
    }

    private void makeIRCPanel()
    {
        DisclosurePanel irc = new DisclosurePanel("IRC chat", true);
        irc.setStyleName("whiteOnBlack");
        final HorizontalPanel hPanel = new HorizontalPanel();

        final Label name = new Label("NickName: ");
        final TextBox userName = new TextBox();
        final Button chat = new Button("Start Chat");
        hPanel.add(name);
        hPanel.add(userName);
        hPanel.add(chat);
        chat.addClickListener(new ClickListener() {

            public void onClick(Widget sender)
            {
                if (userName.getText().equals("")) {
                    userName.setText("Please enter a name to use");
                }
                hPanel.add(new HTML("<applet code=IRCApplet.class archive=\"pjirc/irc.jar,pjirc/pixx.jar\" width=1000 height=400>" +
                        "<param name=\"CABINETS\" value=\"pjirc/irc.cab,pjirc/securedirc.cab,pjirc/pixx.cab\">" +
                        "<param name=\"nick\" value=\"" + userName.getText() + "\">" +
                        "<param name=\"name\" value=\"PJIRC User\">" +
                        "<param name=\"host\" value=\"irc.gamesurge.net\">" +
                        "<param name=\"command1\" value=\"/join #sc2gg\">" +
                        "<param name=\"gui\" value=\"pixx\">" +
                        "</applet>"));
                hPanel.remove(name);
                hPanel.remove(userName);
                hPanel.remove(chat);
            }
        });
        irc.add(hPanel);
        rootPanel.add(irc);
    }
                ClickListener filterByAuthor = new ClickListener() {

        public void onClick(Widget sender)
        {
            QueryObject query = new QueryObject();
            FilterElement filt =
                    new FilterElement(cotw, FilterElement.AUTHOR);

            query.setFilter(filt);
            query.setOrdering(Ordering.PUBLISHED_DESC);
            PageFactory.getVideoGalleryController().query(query);
            History.newItem("s" + cotw);
        }
    };
}
