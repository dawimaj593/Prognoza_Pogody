package com.example.prognozapogody.View

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.prognozapogody.Data.ApiInterface
import com.example.prognozapogody.Data.Weather
import com.example.prognozapogody.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.Date as Date1

class DetailedFragment : Fragment() {
    val httpLink = "https://api.openweathermap.org/"

    //    Deklaracja Elementów
    lateinit var temp : TextView
    lateinit var press : TextView
    lateinit var hum : TextView
    lateinit var sunrise : TextView
    lateinit var sunset : TextView
    lateinit var cityName : EditText
    lateinit var search : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.detailed_fragment, container, false)
        temp = view.findViewById(R.id.temp)
        press = view.findViewById(R.id.press)
        hum = view.findViewById(R.id.hum)
        sunrise = view.findViewById(R.id.sunrise)
        sunset = view.findViewById(R.id.sunset)
        cityName = view.findViewById(R.id.city_name)
        search = view.findViewById(R.id.search_button)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getMyData("Warszawa")

        search.setOnClickListener {
            getMyData(cityName.text.toString())
        }
    }

    private fun getMyData(city : String) {
        val retrofitBilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(httpLink)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBilder.getData(city)

        retrofitData.enqueue(object : Callback<Weather?> {
            @RequiresApi(Build.VERSION_CODES.O)
            @SuppressLint("SetTextI18n", "SimpleDateFormat")
            override fun onResponse(call: Call<Weather?>, response: Response<Weather?>) {
                val responseBody = response.body()

                if (responseBody?.name != null){
                    val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val time1 : Long? = responseBody?.sys?.sunset?.toLong()?.times(1000)
                    val time2 : Long? = responseBody?.sys?.sunrise?.toLong()?.times(1000)

                    temp.text = (responseBody?.main?.temp?.minus(273))?.toInt().toString() + " C"
                    press.text = responseBody?.main?.pressure.toString() + "hPa"
                    hum.text = responseBody?.main?.humidity.toString() + " %"
                    sunset.text = sdf.format(time2)
                    sunrise.text = sdf.format(time1)
                } else {
                    Toast.makeText(context, "Nie znaleziono takiego miasta!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Weather?>, t: Throwable) {
                Log.d("MainActivity", "Failure " + t.message)
            }
        })
    }
}