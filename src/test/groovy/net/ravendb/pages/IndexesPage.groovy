package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.DeleteResourceModalDialog
import net.ravendb.modules.TopNavigationBar


class IndexesPage extends Page {

	final static String INDEX_NAME_ORDERS_BY_COMPANY = "Orders/ByCompany"

	final static String INDEX_TOGGLE_OPTION_DELETE = "Delete Index"

	final static String INDEX_DELETE_SUCCESS = "Deleted "

    static at = {
        newIndexButton
    }

    static content = {
        topNavigation { module TopNavigationBar }
		deleteIndexModalDialog { module DeleteResourceModalDialog }
		alert { module AlertTextModule }

        newIndexButton { $("a[title='Add a new index (Alt+N)']") }

        indexesLinks(required:false) { $("a[href^='#databases/query/index/']") }

		indexRowContainer {$('.index-panel.panel.panel-default')}
		indexRowButtonSelector { "button" }
		indexRowLinkSelector { "li[role='presentation'] a" }

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

	def getIndexContainer(CharSequence name) {
		def container
		indexRowContainer.each {
			if(it.getAttribute("innerHTML").contains(name)) {
				container = it
			}
		}
		container
	}

	def clickDropdownOption(CharSequence indexName, CharSequence optionName) {
		def container = getIndexContainer(indexName)
		container.find(indexRowButtonSelector).click()
		def link
		container.find(indexRowLinkSelector).each {
			if(it.getAttribute("innerHTML").contains(optionName)) {
				link = it
			}
		}
		assert link
		link.click()
	}

}
