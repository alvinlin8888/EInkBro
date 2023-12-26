package info.plateaukao.einkbro.view.handlers

import androidx.fragment.app.FragmentActivity
import info.plateaukao.einkbro.browser.BrowserController
import info.plateaukao.einkbro.preference.ConfigManager
import info.plateaukao.einkbro.preference.TranslationMode
import info.plateaukao.einkbro.preference.toggle
import info.plateaukao.einkbro.unit.IntentUnit
import info.plateaukao.einkbro.view.dialog.compose.ToolbarConfigDialogFragment
import info.plateaukao.einkbro.view.dialog.compose.TouchAreaDialogFragment
import info.plateaukao.einkbro.view.dialog.compose.TtsSettingDialogFragment
import info.plateaukao.einkbro.view.toolbaricons.ToolbarAction
import info.plateaukao.einkbro.viewmodel.TRANSLATE_API
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ToolbarActionHandler(
    private val activity: FragmentActivity
) : KoinComponent {
    private val config: ConfigManager by inject()
    private val browserController = activity as BrowserController

    fun handleLongClick(toolbarAction: ToolbarAction) = when (toolbarAction) {
        ToolbarAction.Back -> browserController.openHistoryPage(5)
        ToolbarAction.Refresh -> browserController.fullscreen()
        ToolbarAction.Touch -> TouchAreaDialogFragment().show(
            activity.supportFragmentManager,
            "TouchAreaDialog"
        )

        //ToolbarAction.PageUp -> browserController.jumpToTop() //AL-20231003-
        //ToolbarAction.PageDown -> browserController.jumpToBottom() //AL-20231003-
        //ToolbarAction.TabCount -> config::isIncognitoMode.toggle() //AL-20231003-
        ToolbarAction.Settings -> browserController.showFastToggleDialog()
        //ToolbarAction.Bookmark -> browserController.saveBookmark() //AL-20231003-
        //ToolbarAction.Translation -> browserController.showTranslationConfigDialog() //AL-20231003-
        ToolbarAction.NewTab -> IntentUnit.launchNewBrowser(activity, config.favoriteUrl)
        /* //AL-20231003-
        ToolbarAction.Tts ->
            TtsSettingDialogFragment { IntentUnit.gotoSystemTtsSettings(activity) }
                .show(activity.supportFragmentManager, "TtsSettingDialog")
        */
        //ToolbarAction.Font -> browserController.toggleReaderMode() //AL-20231003-
        ToolbarAction.InputUrl -> {
            // toggle papago translate
            if (config.papagoApiSecret.isBlank()) {
                config.papagoApiSecret = "123"
            } else {
                config.papagoApiSecret = ""
            }
        }
        //ToolbarAction.TranslateByParagraph -> browserController.configureTranslationLanguage(TRANSLATE_API.GOOGLE) //AL-20231003-
        ToolbarAction.PapagoByParagraph -> browserController.configureTranslationLanguage(TRANSLATE_API.PAPAGO)
        else -> {}
    }

    fun handleClick(toolbarAction: ToolbarAction) = when (toolbarAction) {
        ToolbarAction.Title -> browserController.focusOnInput()
        ToolbarAction.Back -> browserController.handleBackKey()
        ToolbarAction.Refresh -> browserController.refreshAction()
        ToolbarAction.Touch -> browserController.toggleTouchTurnPageFeature()
        //ToolbarAction.PageUp -> browserController.pageUp() //AL-20231003-
        //ToolbarAction.PageDown -> browserController.pageDown() //AL-20231003-
        //ToolbarAction.TabCount -> browserController.showOverview() //AL-20231003-
        //ToolbarAction.Font -> browserController.showFontSizeChangeDialog() //AL-20231003-
        ToolbarAction.Settings -> browserController.showMenuDialog()
        //ToolbarAction.Bookmark -> browserController.openBookmarkPage() //AL-20231003-
        /* //AL-20231003-
        ToolbarAction.IconSetting -> ToolbarConfigDialogFragment().show(
            activity.supportFragmentManager,
            "toolbar_config"
        )
        */
        //ToolbarAction.VerticalLayout -> browserController.toggleVerticalRead() //AL-20231003-
        //ToolbarAction.ReaderMode -> browserController.toggleReaderMode() //AL-20231003-
        ToolbarAction.BoldFont -> config::boldFontStyle.toggle()
        ToolbarAction.IncreaseFont -> browserController.increaseFontSize()
        ToolbarAction.DecreaseFont -> browserController.decreaseFontSize()
        //ToolbarAction.FullScreen -> browserController.fullscreen() //AL-20231003-
        ToolbarAction.Forward -> browserController.goForward()
        //ToolbarAction.RotateScreen -> browserController.rotateScreen() //AL-20231003-
        //ToolbarAction.Translation -> browserController.showTranslation() //AL-20231003-
        ToolbarAction.CloseTab -> browserController.removeAlbum()
        ToolbarAction.MoveToBackground-> activity.moveTaskToBack(true)
        ToolbarAction.InputUrl -> browserController.focusOnInput()
        ToolbarAction.NewTab -> browserController.newATab()
        ToolbarAction.Desktop -> config::desktop.toggle()
        //ToolbarAction.Search -> browserController.showSearchPanel() //AL-20231003-
        //ToolbarAction.DuplicateTab -> browserController.duplicateTab() //AL-20231003-
        //ToolbarAction.Tts -> browserController.toggleTtsRead() //AL-20231003-
        ToolbarAction.TOC -> browserController.showTocDialog()
        ToolbarAction.PageInfo -> browserController.summarizeContent()
        //ToolbarAction.GoogleInPlace -> browserController.translate(TranslationMode.GOOGLE_IN_PLACE) //AL-20231003-
        //ToolbarAction.TranslateByParagraph -> browserController.translate(TranslationMode.TRANSLATE_BY_PARAGRAPH) //AL-20231003-
        ToolbarAction.PapagoByParagraph -> browserController.translate(TranslationMode.PAPAGO_TRANSLATE_BY_PARAGRAPH)
    }
}