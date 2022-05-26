package ru.shafran.cards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.shafran.startup.setupShafran


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupShafran()
    }

}

