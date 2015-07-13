package net.ravendb.pages

import net.ravendb.modules.AreYouSureModalDialog;
import net.ravendb.modules.ManageServerMenu;
import geb.Page


class ManageServerPage extends Page {

    static at = {
        header
        menu
    }

    static content = {
        header { $("h3", text:"Manage Your Server") }

        menu { module ManageServerMenu }
    }
}
