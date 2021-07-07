package ru.gendalf13666.nasapictures.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.gendalf13666.nasapictures.R
import ru.gendalf13666.nasapictures.ui.picture.PictureOfTheDayFragment
import ru.gendalf13666.nasapictures.ui.settings.SETTINGS_SHARED_PREFERENCES
import ru.gendalf13666.nasapictures.ui.settings.THEME_RES_ID

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(
            getSharedPreferences(SETTINGS_SHARED_PREFERENCES, MODE_PRIVATE)
                .getInt(THEME_RES_ID, R.style.DefaultTheme)
        )
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNowAllowingStateLoss()
        }
    }
}
