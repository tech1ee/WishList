package com.example.wishlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wishlist.view.list.WishesFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, WishesFragment())
            .commit()
    }
}