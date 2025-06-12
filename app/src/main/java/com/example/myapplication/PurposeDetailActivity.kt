package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class PurposeDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private var lat: Double? = null
    private var lon: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purpose_detail)

        val titleText = findViewById<TextView>(R.id.detailTitle)
        val timeText = findViewById<TextView>(R.id.detailTime)
        val daysText = findViewById<TextView>(R.id.detailDays)

        titleText.text = intent.getStringExtra("title")
        timeText.text = intent.getStringExtra("time")
        daysText.text = intent.getStringExtra("days")

        lat = intent.getDoubleExtra("lat", 0.0)
        lon = intent.getDoubleExtra("lon", 0.0)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        lat?.let { latitude ->
            lon?.let { longitude ->
                val location = LatLng(latitude, longitude)
                googleMap.addMarker(MarkerOptions().position(location).title("Posizione"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))

                googleMap.setOnMapClickListener {
                    val uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude(Proposito)")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage("com.google.android.apps.maps")
                    startActivity(intent)
                }
            }
        } ?: run {
            Toast.makeText(this, "Posizione non disponibile", Toast.LENGTH_SHORT).show()
        }
    }
}
