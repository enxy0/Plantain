package kek.plantain.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import kek.plantain.ui.info.InfoScreen
import kek.plantain.ui.theme.PlantainTheme

class ReadTagActivity : AppCompatActivity() {
    private val tagViewModel: TagViewModel by viewModels()
    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            tagViewModel.readNfcTag(intent)
        }
        setContent {
            PlantainTheme {
                InfoScreen(
                    navigation = navigationViewModel,
                    tagViewModel = tagViewModel,
                    intent = intent
                )
            }
        }
    }
}

