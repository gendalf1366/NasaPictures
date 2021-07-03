package ru.gendalf13666.nasapictures.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.gendalf13666.nasapictures.R
import ru.gendalf13666.nasapictures.ui.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}
