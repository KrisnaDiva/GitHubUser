package com.krisna.diva.githubuser.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.krisna.diva.githubuser.databinding.ActivitySplashScreenBinding
import com.krisna.diva.githubuser.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        lifecycleScope.launch {
            delay(3000)
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }
    }
}