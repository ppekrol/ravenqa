package net.ravendb.pages

import net.ravendb.modules.ManageServerMenu;
import geb.Page


class ManageServerServerSmugglingPage extends Page {

    static at = {
        $("form[data-view='views/manage/serverSmuggling']")
    }

    static content = {
        menu { module ManageServerMenu }
    }
}
