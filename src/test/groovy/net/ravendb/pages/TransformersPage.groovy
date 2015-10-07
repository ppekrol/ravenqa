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
        pasteTransformerButton { $("button[title='Paste transformer from JSON (Alt+V)']") }

        transformerLinks(required:false) { $("a[href^='#databases/transformers/edit/']") }

        // paste transformer from JSON modal dialog
        pasteTransformerFromJsonModalHeader { $("h4", text:"Paste Transformer") }
        transformerAceEditorPasteFromJson { $("pre.ace_editor.ace-xcode.ui-resizable textarea") }
        saveTransformerButton { $("button[title='Save transformer (Alt+S)']") }

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

    def createAndSaveTransformerFromJson(String name, String json) {
        pasteTransformerButton.click()
        waitFor { pasteTransformerFromJsonModalHeader.displayed }
        transformerAceEditorPasteFromJson = json

        waitFor { !(saveTransformerButton.@disabled == 'true') }
        saveTransformerButton.click()

        messagesContainer.waitForMessage("Saved " + name)
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
