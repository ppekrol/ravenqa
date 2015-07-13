package net.ravendb.pages

import net.ravendb.modules.ManageServerMenu;
import geb.Page


class ManageServerApiKeysPage extends Page {

    static at = {
        createNewAPiKeyButton
        menu
    }

    static content = {
        menu { module ManageServerMenu }

        createNewAPiKeyButton { $("button[title='Create a new API Key (Alt+N)']") }
    }
}
