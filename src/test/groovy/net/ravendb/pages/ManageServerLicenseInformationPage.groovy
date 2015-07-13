package net.ravendb.pages

import net.ravendb.modules.ManageServerMenu;
import geb.Page


class ManageServerLicenseInformationPage extends Page {

    static at = {
        header
        menu
    }

    static content = {
        menu { module ManageServerMenu }

        header { $("div", text:"License information") }
    }
}
