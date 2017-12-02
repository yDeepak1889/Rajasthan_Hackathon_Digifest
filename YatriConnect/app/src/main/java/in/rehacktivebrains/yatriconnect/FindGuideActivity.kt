package `in`.rehacktivebrains.yatriconnect

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_find_guide.*
import org.altbeacon.beacon.BeaconConsumer
import org.altbeacon.beacon.BeaconManager


class FindGuideActivity : AppCompatActivity(), BeaconConsumer, OnMapReadyCallback {

    private lateinit var beaconManager: BeaconManager
    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_guide)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.bind(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapView)
        (mapFragment as SupportMapFragment).getMapAsync(this)
    }

    override fun onBeaconServiceConnect() {
        beaconManager.addRangeNotifier { beacons, region ->
            beacons.forEach {

            }
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        val sydney = LatLng(-33.852, 151.211)
        googleMap?.addMarker(MarkerOptions().position(sydney)
                .title("Marker in Sydney"))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }

}
