package net.ravendb.pages.manage

import net.ravendb.modules.manage.ManageServerMenu;
import geb.Page


class ManageServerTrafficWatchPage extends Page {

    public final static String RESOURCE_SYSTEM_DATABASE = "<system>"

    static at = {
        configureConnectionButton.displayed
        menu.toSystemDatabaseLink.displayed
    }

    static content = {
        menu { module ManageServerMenu }

        configureConnectionButton { $("button", text:"Configure Connection") }
        reconnectConnectionButton { $("button", text:"Reconnect") }
        disconnectConnectionButton { $("button", text:"Disconnect") }

        statusConnectedLabel { $("label", text:"Connected") }
        statusDisconnectedLabel { $("label", text:"Disconnected") }
        logsContainer { $("div#logRecordsContainer") }

        // modal dialog
        maxEntriesInput(required:false) { $("input#maxEntries") }
        resourceNameInput(required:false) { $("input#watchedResource") }
        connectButton(required:false) { $("button", text:"Connect") }
    }
}
