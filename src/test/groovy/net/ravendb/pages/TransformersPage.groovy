package net.ravendb.pages

import geb.Page
import net.ravendb.modules.TopNavigationBar


class TransformersPage extends Page {

    static at = {
        newTransformerButton
    }

    static content = {
		// modules
        topNavigation { module TopNavigationBar }

        // tool bar
        newTransformerButton { $("a[title='Add a new transformer (Alt+N)']") }

        transformerLinks(required:false) { $("a[href^='#databases/transformers/edit/']") }
    }

    def getTransformerLink(CharSequence name) {
        def link
        transformerLinks.each {
            if(it.@href.contains(name)) {
                link = it
            }
        }
        link
    }
}
