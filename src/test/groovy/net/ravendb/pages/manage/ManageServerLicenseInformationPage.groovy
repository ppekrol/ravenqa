package net.ravendb.pages.manage

import net.ravendb.modules.manage.ManageServerMenu;
import geb.Page


class ManageServerLicenseInformationPage extends Page {

    static at = {
        forceLicenseUpdateButton.displayed
        menu.toSystemDatabaseLink.displayed
    }

    static content = {
        menu { module ManageServerMenu }

        forceLicenseUpdateButton { $("button[data-bind='click: forceUpdate']") }
        licensingStatusHeader(required:false) { $("h4", text:"Licensing Status") }
    }
}
