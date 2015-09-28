package net.ravendb.test

import net.ravendb.pages.DocumentsPage
import net.ravendb.pages.IndexesPage
import net.ravendb.pages.NewTransformerPage
import net.ravendb.pages.TransformersPage

import org.testng.annotations.Test


class TransformersTest extends DatabaseWithSampleDataTestBase {

    /**
     * User can create, edit and delete simple transformer.
     * @Step Navigate to Indexes page.
     * @Step Navigate to Transformers page.
     * @Step Create, edit and delete.
     * @verification Transformer created, edited and deleted.
     */
    @Test(groups="Smoke")
    void canCreateEditDeleteSimpleTransformer() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        topNavigation.switchToTransformers()
        waitFor { at TransformersPage }

        newTransformerButton.click()
        waitFor { at NewTransformerPage }

        // create transformer
        String transformerName = "i" + rand.nextInt()
        def transform = [
            """
            from result in results
            let category = LoadDocument(result.Category)
            select new {result.Name,result.PricePerUnit,Category = category.Name,CategoryDescription = category.Description}
            """.toString()
        ]
        createAndSaveTransformer(transformerName, transform)

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        topNavigation.switchToTransformers()
        waitFor { at TransformersPage }
        waitFor { getTransformerLink(transformerName) }

        // edit transformer
        getTransformerLink(transformerName).click()
        waitFor { at NewTransformerPage }

        formatTransformer(transformerName)

        // delete transformer
        deleteTransformerButton.click()
        waitFor { deleteTransformerModalDialog.confirmButton.displayed }

        deleteTransformerModalDialog.confirmButton.click()
        messagesContainer.waitForMessage(NewTransformerPage.TRANSFORMER_DELETE_SUCCESS + transformerName)

        waitFor { at TransformersPage }
        waitFor { !getTransformerLink(transformerName) }
    }
}
