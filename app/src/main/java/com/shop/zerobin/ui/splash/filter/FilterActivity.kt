package com.shop.zerobin.ui.splash.filter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.chip.Chip
import com.shop.zerobin.R
import com.shop.zerobin.databinding.ActivityFilterBinding
import com.shop.zerobin.ui.MainActivity
import com.shop.zerobin.ui.common.BaseBindingActivity
import org.koin.android.viewmodel.ext.android.viewModel

class FilterActivity : BaseBindingActivity<ActivityFilterBinding>(R.layout.activity_filter) {

    private val filterViewModel: FilterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = filterViewModel

        if (!filterViewModel.isFirstEnter()) {
            startMainActivity()
            return
        }

        requestFilterList()
        observeLiveData()
        setListeners()
    }

    private fun requestFilterList() {
        filterViewModel.requestFilterList()
    }

    private fun observeLiveData() {
        filterViewModel.isLoading.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }
        filterViewModel.hashtagList.observe(this) { hashTagList ->
            for (i in 0 until binding.hashTagContainer.chipGroup.childCount) {
                binding.root.findViewWithTag<Chip>("${hashTagList[i].hashtagIndex}")?.text =
                    getString(R.string.hash_tag_format, hashTagList[i].name)
            }
        }

        filterViewModel.isError.observe(this) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setListeners() {
        binding.btnNext.setOnClickListener {
            val selectedHashTagList = mutableListOf<Int>()
            binding.hashTagContainer.chipGroup.checkedChipIds.forEach { checkedId ->
                val chip = findViewById<Chip>(checkedId)
                val index = chip.tag.toString().toInt()
                selectedHashTagList.add(index)
            }
            filterViewModel.saveHashTagList(selectedHashTagList)
            filterViewModel.saveFirstEnter()
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(0, R.anim.fade_out)
    }
}