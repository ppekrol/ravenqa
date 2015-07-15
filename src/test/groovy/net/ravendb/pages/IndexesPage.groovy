package net.ravendb.pages

import net.ravendb.modules.TopNavigationBar;
import geb.Page


class IndexesPage extends Page {

    static at = {
        newIndexButton
    }

    static content = {
        topNavigation { module TopNavigationBar }

        newIndexButton { $("a[title='Add a new index (Alt+N)']") }

        indexesLinks(required:false) { $("a[href^='#databases/query/index/']") }
    }

    def getIndexLink(CharSequence name) {
        def link
        indexesLinks.each {
            if(it.@href.contains(name)) {
                link = it
            }
        }
        link
    }
}
