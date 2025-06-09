package com.app.omnipro_test_rm.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.app.omnipro_test_rm.ui.screens.characters.CharacterScreen
import com.app.omnipro_test_rm.ui.screens.characters.EnhancedCharactersScreen
import com.app.omnipro_test_rm.ui.screens.detail.CharacterDetail
import com.app.omnipro_test_rm.ui.screens.detail.EnhancedCharacterDetailScreen

@Composable
fun Navigation() {
    val backStack = rememberNavBackStack(CharacterScreen)

    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<CharacterScreen> {
                EnhancedCharactersScreen(
                    onCharacterClick = { characterId ->
                        backStack.add(CharacterDetail(characterId))
                    }
                )
            }
            
            entry<CharacterDetail> { navEntry ->
                EnhancedCharacterDetailScreen(
                    characterId = navEntry.characterId,
                    onBackClick = {
                        backStack.removeLastOrNull()
                    }
                )
            }
        }
    )
}
