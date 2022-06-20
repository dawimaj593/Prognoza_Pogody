package com.example.prognozapogody.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.prognozapogody.Data.ApiInterface
import com.example.prognozapogody.Data.Weather
import com.example.prognozapogody.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SimpleFragment : Fragment() {
//    Link do OpenWeather
    val httpLink = "https://api.openweathermap.org/"


//    Deklaracja Elementów
    lateinit var temperature : TextView
    lateinit var discription : TextView
    lateinit var cityName : TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.simple_fragment, container, false)
        temperature = view.findViewById(R.id.simple_temperature)
        discription = view.findViewById(R.id.simple_discription)
        cityName = view.findViewById(R.id.simple_city_name)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getMyData()
    }


//    Import danych do elementów
    private fun getMyData() {
        val retrofitBilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(httpLink)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBilder.getData("Warszawa")

        retrofitData.enqueue(object : Callback<Weather?> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Weather?>, response: Response<Weather?>) {
                val responseBody = response.body()

                cityName.text = responseBody?.name.toString()
                temperature.text = (responseBody?.main?.temp?.minus(273))?.toInt().toString() + " C"
                discription.text = responseBody?.weather?.get(0)?.description.toString()
            }

            override fun onFailure(call: Call<Weather?>, t: Throwable) {
                Log.d("MainActivity", "Failure " + t.message)
            }
        })
    }
}