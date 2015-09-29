package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.TopNavigationBar


class TransformersPage extends Page {

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
}
