package com.amirreza.osmiumproject

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telephony.TelephonyManager
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.amirreza.osmiumproject.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val cellInfoViewModel: CellInfoViewModel by viewModels {
        CellInfoViewModelFactory((application as OsmiumApplication).repository)
    }
    private lateinit var locationManager: LocationManager
    private var currentLocation: Location? = null
    private val handler = Handler(Looper.getMainLooper())
    private val interval: Long = 10000 // 10 seconds

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            currentLocation = location
            Log.d("MainActivity", "Location updated: $location")
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private val getCellInfoRunnable = object : Runnable {
        override fun run() {
            getCellInfo()
            handler.postDelayed(this, interval)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Cell Info"
                1 -> tab.text = "Cell Location"
            }
        }.attach()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            Log.d("MainActivity", "Permissions granted")
            requestLocationUpdates()
            handler.post(getCellInfoRunnable) // Start the repeated task
        }

        cellInfoViewModel.allCellInfo.observe(this, Observer { cellInfo ->
            // Update UI if needed
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                (grantResults.isNotEmpty() && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                requestLocationUpdates()
                handler.post(getCellInfoRunnable) // Start the repeated task
            } else {
                // Handle the case where permissions were not granted
            }
        }
    }

    private fun requestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        }
    }

    private fun getCellInfo() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            val cellInfoList = telephonyManager.allCellInfo

            for (cellInfo in cellInfoList) {
                var cellInfoEntity: CellInfo? = null
                when (cellInfo) {
                    is android.telephony.CellInfoGsm -> {
                        val cellSignalStrengthGsm = cellInfo.cellSignalStrength
                        val cellIdentityGsm = cellInfo.cellIdentity

                        val signalStrength = cellSignalStrengthGsm.dbm
                        val cellId = cellIdentityGsm.cid
                        val locationAreaCode = cellIdentityGsm.lac

                        cellInfoEntity = CellInfo(
                            cellId = cellId,
                            lac = locationAreaCode,
                            signalStrength = signalStrength,
                            latitude = currentLocation?.latitude ?: 0.0,
                            longitude = currentLocation?.longitude ?: 0.0
                        )
                        Log.d("MainActivity", "CellInfoGsm entity: ${cellInfoEntity.cellId}")
                    }
                    is android.telephony.CellInfoLte -> {
                        val cellSignalStrengthLte = cellInfo.cellSignalStrength
                        val cellIdentityLte = cellInfo.cellIdentity

                        val signalStrength = cellSignalStrengthLte.dbm
                        val cellId = cellIdentityLte.ci
                        val trackingAreaCode = cellIdentityLte.tac

                        cellInfoEntity = CellInfo(
                            cellId = cellId,
                            lac = trackingAreaCode,
                            signalStrength = signalStrength,
                            latitude = currentLocation?.latitude ?: 0.0,
                            longitude = currentLocation?.longitude ?: 0.0
                        )
                        Log.d("MainActivity", "CellInfoLte entity: ${cellInfoEntity.cellId}")
                    }
                    is android.telephony.CellInfoCdma -> {
                        val cellSignalStrengthCdma = cellInfo.cellSignalStrength
                        val cellIdentityCdma = cellInfo.cellIdentity

                        val signalStrength = cellSignalStrengthCdma.dbm
                        val networkId = cellIdentityCdma.networkId
                        val systemId = cellIdentityCdma.systemId
                        val basestationId = cellIdentityCdma.basestationId

                        cellInfoEntity = CellInfo(
                            cellId = basestationId,
                            lac = networkId,
                            signalStrength = signalStrength,
                            latitude = currentLocation?.latitude ?: 0.0,
                            longitude = currentLocation?.longitude ?: 0.0
                        )
                        Log.d("MainActivity", "CellInfoCdma entity: ${cellInfoEntity.cellId}")
                    }
                    is android.telephony.CellInfoWcdma -> {
                        val cellSignalStrengthWcdma = cellInfo.cellSignalStrength
                        val cellIdentityWcdma = cellInfo.cellIdentity

                        val signalStrength = cellSignalStrengthWcdma.dbm
                        val cellId = cellIdentityWcdma.cid
                        val locationAreaCode = cellIdentityWcdma.lac

                        cellInfoEntity = CellInfo(
                            cellId = cellId,
                            lac = locationAreaCode,
                            signalStrength = signalStrength,
                            latitude = currentLocation?.latitude ?: 0.0,
                            longitude = currentLocation?.longitude ?: 0.0
                        )
                        Log.d("MainActivity", "CellInfoWcdma entity: ${cellInfoEntity.cellId}")
                    }
                    else -> {
                        Log.d("MainActivity", "Unknown cell info type: ${cellInfo.javaClass.simpleName}")
                    }
                }

                cellInfoEntity?.let { entity ->
                    GlobalScope.launch {
                        val existingCellInfo = cellInfoViewModel.getCellInfo(entity.cellId, entity.lac, entity.signalStrength, entity.latitude, entity.longitude)
                        if (existingCellInfo == null) {
                            cellInfoViewModel.insert(entity)
                        } else {
                            Log.d("MainActivity", "Cell info already exists: $entity")
                        }
                    }
                }
            }
        } else {
            Log.d("MainActivity", "READ_PHONE_STATE permission not granted")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(getCellInfoRunnable) // Stop the repeated task when activity is destroyed
    }
}
