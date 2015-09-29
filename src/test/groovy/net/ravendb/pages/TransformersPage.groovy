package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.TopNavigationBar


class TransformersPage extends Page {

    final static String TRANSFORMER_NAME_ORDERS_COMPANY = "Orders/Company"
    final static String TRANSFORMER_SAVE_SUCCESS = "Saved "

    static at = {
        newTransformerButton
    }

    static content = {
		// modules
        topNavigation { module TopNavigationBar }
        messagesContainer { module AlertTextModule }

        // tool bar
        newTransformerButton { $("a[title='Add a new transformer (Alt+N)']") }

        transformerLinks(required:false) { $("a[href^='#databases/transformers/edit/']") }

        // transformer panel
        transformerRowContainer { $('.transformers-group.panel.panel-default') }
        lockUnlockTransformerButtonSelector { "a[title='Lock/Un-Lock Transformer']" }
        lockTransformerIconSelector { "i.fa.fa-lock" }
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

    def getTransformerContainer(CharSequence name) {
        def container
        transformerRowContainer.each {
            if(it.getAttribute("innerHTML").contains(name)) {
                container = it
            }
        }
        container
    }

    def clickLockUnlockButton(CharSequence transformerName) {
        def container = getTransformerContainer(transformerName)
        container.find(lockUnlockTransformerButtonSelector).click()
    }

    def getLockIcon(CharSequence transformerName) {
        def container = getTransformerContainer(transformerName)
        container.find(lockTransformerIconSelector)
    }
}
