package net.ravendb.pages.manage

import net.ravendb.modules.AreYouSureModalDialog;
import net.ravendb.modules.manage.ManageServerMenu;
import geb.Page


class ManageServerPage extends Page {

    static at = {
        header.displayed
        menu.toSystemDatabaseLink.displayed
    }

    static content = {
        header { $("h3", text:"Manage Your Server") }

        menu { module ManageServerMenu }
    }
}
