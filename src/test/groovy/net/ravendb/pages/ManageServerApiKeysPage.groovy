package net.ravendb.pages

import net.ravendb.modules.ManageServerMenu;
import geb.Page


class ManageServerApiKeysPage extends Page {

    static at = {
        createNewAPIKeyButton
        menu
    }

    static content = {
        menu { module ManageServerMenu }

        createNewAPIKeyButton { $("button[title='Create a new API Key (Alt+N)']") }
    }
}
