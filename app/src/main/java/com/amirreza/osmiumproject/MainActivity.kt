package com.amirreza.osmiumproject

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amirreza.osmiumproject.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val cellInfoViewModel: CellInfoViewModel by viewModels {
        CellInfoViewModelFactory((application as OsmiumApplication).repository)
    }
    private lateinit var locationManager: LocationManager
    private var currentLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            getCellInfo()
            requestLocationUpdates()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                (grantResults.isNotEmpty() && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                getCellInfo()
                requestLocationUpdates()
            } else {
                // Handle the case where permissions were not granted
            }
        }
    }

    private fun getCellInfo() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            val cellInfoList = telephonyManager.allCellInfo

            for (cellInfo in cellInfoList) {
                if (cellInfo is android.telephony.CellInfoGsm) {
                    val cellSignalStrengthGsm = cellInfo.cellSignalStrength
                    val cellIdentityGsm = cellInfo.cellIdentity

                    val signalStrength = cellSignalStrengthGsm.dbm
                    val cellId = cellIdentityGsm.cid
                    val locationAreaCode = cellIdentityGsm.lac

                    val cellInfoEntity = CellInfo(
                        cellId = cellId,
                        lac = locationAreaCode,
                        signalStrength = signalStrength,
                        latitude = currentLocation?.latitude ?: 0.0,
                        longitude = currentLocation?.longitude ?: 0.0
                    )

                    GlobalScope.launch {
                        cellInfoViewModel.insert(cellInfoEntity)
                    }
                }
                // Similar handling for other cell types (LTE, CDMA, WCDMA, ...)
            }
        } else {
            // Handle the case where the permission is not granted
        }
    }

    private fun requestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        }
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            currentLocation = location
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}
