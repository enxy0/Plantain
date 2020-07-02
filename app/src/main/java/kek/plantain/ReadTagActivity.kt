package kek.plantain

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import kek.plantain.ui.info.InfoScreen
import kek.plantain.ui.theme.PlantainTheme

class ReadTagActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ReadTagActivity"
    }

    private val viewModel: TagViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.readNfcTag(intent)
        setContent {
            PlantainTheme {
                InfoScreen(dumpLiveData = viewModel.dump)
            }
        }
    }
}

