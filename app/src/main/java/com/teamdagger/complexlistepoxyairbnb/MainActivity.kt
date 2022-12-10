package com.teamdagger.complexlistepoxyairbnb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.teamdagger.complexlistepoxyairbnb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val controller = HomeController(this)
        binding.homeRecyclerView.setController(controller)

        viewModel.viewState.observe(this) {
            controller.setData(it)
        }

        viewModel.loadData(1)
    }
}