package com.adesoftware.countryrecyclerview.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.adesoftware.countryrecyclerview.adapter.CountryAdapter
import com.adesoftware.countryrecyclerview.databinding.ActivityCountryBinding
import com.adesoftware.countryrecyclerview.viewmodel.ListViewModel

class CountryActivity : AppCompatActivity() {

    lateinit var binding: ActivityCountryBinding
    lateinit var viewModel: ListViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this)
            .get(ListViewModel::class.java)
        viewModel.refresh()

        setUpRecyclerView()

        observeViewModel()
    }

    private fun setUpRecyclerView() {
        binding.countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countryAdapter

            val decoration = DividerItemDecoration(applicationContext, VERTICAL)
            addItemDecoration(decoration)
        }
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, Observer { countries ->
            countries.let {
                binding.countriesList.visibility = View.VISIBLE
                countryAdapter.updateCountries(it)
            }
        })

        viewModel.countryLoadError.observe(this, Observer { isError ->
            binding.listError.visibility =
                if (isError == null) {
                    View.GONE
                } else View.VISIBLE
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.listError.visibility = View.GONE
                    binding.countriesList.visibility = View.GONE
                }
            }
        })
    }
}