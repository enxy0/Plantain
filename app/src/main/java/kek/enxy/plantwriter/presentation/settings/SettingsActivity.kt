package kek.enxy.plantwriter.presentation.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kek.enxy.plantwriter.BuildConfig
import kek.enxy.plantwriter.R
import kek.enxy.plantwriter.databinding.ActivitySettingsBinding
import kek.enxy.plantwriter.presentation.common.Constants
import kek.enxy.plantwriter.presentation.common.IntentUtils

class SettingsActivity : AppCompatActivity() {

    companion object {
        private fun newIntent(context: Context) = Intent(context, SettingsActivity::class.java)

        fun start(context: Context) {
            context.startActivity(newIntent(context))
        }
    }

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        initView()
    }

    private fun initView() = with(binding) {
        textAppVersion.text = getString(
            R.string.settings_version,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE
        )
        textAuthor.setOnClickListener {
            IntentUtils.openLink(this@SettingsActivity, Constants.GITHUB_AUTHOR_URL)
        }
        toolbar.onStartBtnClicked {
            onBackPressed()
        }
    }
}
