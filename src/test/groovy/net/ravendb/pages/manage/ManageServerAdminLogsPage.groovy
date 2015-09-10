package net.ravendb.pages.manage

import net.ravendb.modules.manage.ManageServerMenu;
import geb.Page


class ManageServerAdminLogsPage extends Page {

    static at = {
        configureConnectionButton.displayed
        menu.toSystemDatabaseLink.displayed
    }

    static content = {
        menu { module ManageServerMenu }

        configureConnectionButton { $("button", text:"Configure Connection") }
        reconnectButton { $("button", text:"Reconnect") }
        disconnectButton { $("button", text:"Disconnect") }
        exportButton { $("button", text:"Export") }

        statusConnectedLabel { $("label", text:"Connected") }
        statusDisconnectedLabel { $("label", text:"Disconnected") }
        logsContainer { $("pre#adminLogsPre") }

        // modal dialog
        maxLogEntriesInput(required:false) { $("input#maxEntries") }
        categoryInputSelector { "input[data-bind='value: category']" }
        levelSelectSelector { "select[data-bind='value: level']" }
        removeButtonSelector { "button[data-bind='click: \$root.deleteRow.bind(\$root, \$data)']" }
        addNewCategoryButton(required:false) { $("button[data-bind='click: addCategory']") }
        okButton(required:false) { $("button", text:"Ok") }
    }

    public final static String LEVEL_DEBUG = "Debug"
    public final static String LEVEL_INFO = "Info"
    public final static String LEVEL_WARN = "Warn"
    public final static String LEVEL_ERROR = "Error"
    public final static String LEVEL_FATAL= "Fatal"
}
