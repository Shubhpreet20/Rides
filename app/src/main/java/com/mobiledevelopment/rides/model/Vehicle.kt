package com.mobiledevelopment.rides.model

import com.google.gson.annotations.SerializedName


data class Vehicle(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("uid") var uid: String? = null,
    @SerializedName("vin") var vin: String? = null,
    @SerializedName("make_and_model") var makeAndModel: String? = null,
    @SerializedName("color") var color: String? = null,
    @SerializedName("transmission") var transmission: String? = null,
    @SerializedName("drive_type") var driveType: String? = null,
    @SerializedName("fuel_type") var fuelType: String? = null,
    @SerializedName("car_type") var carType: String? = null,
    @SerializedName("car_options") var carOptions: ArrayList<String> = arrayListOf(),
    @SerializedName("specs") var specs: ArrayList<String> = arrayListOf(),
    @SerializedName("doors") var doors: Int? = null,
    @SerializedName("mileage") var mileage: Int? = null,
    @SerializedName("kilometrage") var kilometrage: Int? = null,
    @SerializedName("license_plate") var licensePlate: String? = null
) : java.io.Serializable