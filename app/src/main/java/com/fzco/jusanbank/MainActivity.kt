package com.fzco.jusanbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fzco.jusanbank.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.seekPercentage.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                viewModel.updatePercentage(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })

        lifecycleScope.launch {
            viewModel.container.stateFlow.collectLatest(::handleState)
        }
    }

    private fun handleState(state: MainState) = with(state) {
        setPercentage(state.percentage)
    }

    private fun setPercentage(percentage: Int) = with(binding) {
        if (seekPercentage.progress != percentage) {
            seekPercentage.progress = percentage
        }
        txtPercentage.text = "$percentage%"
        when(percentage) {
            in 0..9 -> {
                txtLevel.setTextColor(resources.getColor(R.color.dep_pens))
                txtLevel.text = resources.getString(R.string.txt_level_pens)
            }
            in 10..14 -> {
                txtLevel.setTextColor(resources.getColor(R.color.dep_optimum))
                txtLevel.text = resources.getString(R.string.txt_level_optimum)
            }
            in 15..19 -> {
                txtLevel.setTextColor(resources.getColor(R.color.dep_comfort))
                txtLevel.text = resources.getString(R.string.txt_level_comfort)
            }
            in 20..24 -> {
                txtLevel.setTextColor(resources.getColor(R.color.dep_business))
                txtLevel.text = resources.getString(R.string.txt_level_business)
            }
            else -> {
                txtLevel.setTextColor(resources.getColor(R.color.dep_max))
                txtLevel.text = resources.getString(R.string.txt_level_max)
            }
        }
    }
}