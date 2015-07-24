package net.ravendb.pages.manage

import net.ravendb.modules.manage.ManageServerMenu;
import geb.Page


class ManageServerServerSmugglingPage extends Page {

    static at = {
        $("form[data-view='views/manage/serverSmuggling']")
    }

    static content = {
        menu { module ManageServerMenu }
    }
}
