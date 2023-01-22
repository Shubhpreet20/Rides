package com.mobiledevelopment.rides

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mobiledevelopment.rides.model.Response
import com.mobiledevelopment.rides.model.Vehicle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.URL

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _response: MutableLiveData<Response<List<Vehicle>>> = MutableLiveData()
    val response: LiveData<Response<List<Vehicle>>> = _response


    fun getVehiclesList(size: Int?) {
        size?.apply {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    makeSynchronousNetworkRequest(size, object : ResponseCallback<List<Vehicle>> {
                        override fun onComplete(result: Response<List<Vehicle>>) {
                            _response.postValue(result)
                        }
                    })
                }
            }
        }
    }


    private fun makeSynchronousNetworkRequest(
        size: Int, callback: ResponseCallback<List<Vehicle>>
    ) {
        try {
            val url = URL("https://random-data-api.com/api/vehicle/random_vehicle?size=$size")
            val httpConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            httpConnection.requestMethod = "GET"
            Log.d("TAG", httpConnection.responseCode.toString())
            val respCode = httpConnection.responseCode
            if (respCode == HTTP_OK) {
                val `in`: InputStream = BufferedInputStream(httpConnection.inputStream)
                val sb = StringBuilder()
                val br = BufferedReader(InputStreamReader(`in`))
                var read: String?
                while (br.readLine().also { read = it } != null) {
                    //System.out.println(read);
                    sb.append(read)
                }
                br.close()
                val response = sb.toString()
                Log.d("TAG", response)
                val vehicleList = Gson().fromJson(response, Array<Vehicle>::class.java).toList()
                val result: Response<List<Vehicle>> = Response.Success(vehicleList)
                callback.onComplete(result)
            } else {
                callback.onComplete(Response.Error(Exception("Something went wrong, Please try again!")))
            }
        } catch (e: Exception) {
            callback.onComplete(Response.Error(e))
        }
    }

    interface ResponseCallback<T> {
        fun onComplete(result: Response<T>)
    }

}