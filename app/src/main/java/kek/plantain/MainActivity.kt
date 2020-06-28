package kek.plantain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import kek.plantain.ui.home.PlantainApp
import kek.plantain.ui.theme.PlantainTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantainTheme {
                PlantainApp()
            }
        }
    }
}