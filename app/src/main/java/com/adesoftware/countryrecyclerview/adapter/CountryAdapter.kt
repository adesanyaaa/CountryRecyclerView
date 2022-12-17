package com.adesoftware.countryrecyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adesoftware.countryrecyclerview.databinding.ItemCountryBinding
import com.adesoftware.countryrecyclerview.model.Country
import com.adesoftware.countryrecyclerview.util.loadImage

class CountryAdapter(
    var countries: ArrayList<Country>
): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(ItemCountryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int = countries.size

    class CountryViewHolder(
        binding: ItemCountryBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val imageView = binding.imageView
        private val countryName = binding.name
        private val countryCapital = binding.capital

        fun bind(country: Country) {
            countryName.text = country.countryName
            countryCapital.text = country.capital
            imageView.loadImage(country.flag)
        }
    }

}