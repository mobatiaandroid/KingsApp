package com.mobatia.bisad.fragment.contact_us

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.kingseducation.app.R
import com.kingseducation.app.fragment.contact.adapter.ContactusAdapter
import com.kingseducation.app.fragment.contact.model.ContactsListDetailModel


@Suppress(
    "DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "ControlFlowWithEmptyBody",
    "DEPRECATED_IDENTITY_EQUALS"
)
class ContactUsFragment : Fragment(), LocationListener,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    GoogleMap.OnMarkerDragListener, GoogleMap.OnMapLongClickListener {
    lateinit var titleTextView: TextView
    lateinit var mContext: Context
    lateinit var progressDialog: RelativeLayout
    lateinit var contact_usrecyclerview: RecyclerView
    lateinit var description: String
    var aboutusdescription = ArrayList<ContactsListDetailModel>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var descriptiontext: TextView
    lateinit var latitude: String
    lateinit var longitude: String
    lateinit var c_latitude: String
    lateinit var c_longitude: String
    lateinit var locationManager: LocationManager
    var isGPSEnabled: Boolean? = null
    var isNetworkEnabled: Boolean? = null
    var lat: Double = 0.0
    var long: Double = 0.0
    lateinit var mapFragment: SupportMapFragment
    lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.contact_us_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        if (Build.VERSION.SDK_INT >= 23 &&
            ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        }

        initializeUI()
       // permissioncheck()

            fetchlatitudelongitude()
            getcontactdetails()



    }
    private fun fetchlatitudelongitude() {
        var location: Location
        locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGPSEnabled!! && !isNetworkEnabled!!) {

        } else {
            if (isNetworkEnabled as Boolean) {
                if (ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                } else {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        0L,
                        0.0F,
                        this
                    )

                    location =
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
                    if (location != null) {
                        lat = location.latitude
                        long = location.longitude
                    }
                }
            }
            if (isGPSEnabled as Boolean) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0L,
                    0.0F,
                    this
                )
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
                if (location != null) {
                    lat = location.latitude
                    long = location.longitude
                    println("lat---$lat")
                    println("lat---$long")
                    Log.e("CONTACTLATITUDE:", (lat + long).toString())
                }
            }
        }
        c_latitude = lat.toString()
        c_longitude = long.toString()

    }

    private fun getcontactdetails() {

                mapFragment.getMapAsync { googleMap ->
                    Log.d("Map Working", "good")
                    map = googleMap
                    map.uiSettings.isMapToolbarEnabled = false
                    map.uiSettings.isZoomControlsEnabled = false
                    val latLng = LatLng(latitude.toDouble(), longitude.toDouble())
                    map.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .draggable(true)
                            .title("BISAD")
                    )


                    map.moveCamera(CameraUpdateFactory.newLatLng(latLng))

                    map.animateCamera(CameraUpdateFactory.zoomTo(13f))
                    map.setOnInfoWindowClickListener {

                        if (!isGPSEnabled!!) {
                            val callGPSSettingIntent = Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS
                            )
                            startActivity(callGPSSettingIntent)
                        } else {
                            //val url = "http://maps.google.com/maps?saddr=$c_latitude,$c_longitude&daddr=The British International School,Abudhabi"
                            val url = "http://maps.google.com/maps?saddr=Your Location&daddr=The British International School,Abudhabi"

                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(url)
                            startActivity(i)
                        }


                    }
                }


    }

    private fun initializeUI() {

        mContext =requireContext()
        latitude="3.136246"
        longitude="101.606852"
        aboutusdescription = ArrayList()
        var model= ContactsListDetailModel("Reception","+971987864556","reception@kings.ae")
        aboutusdescription.add(model)
        var xmodel= ContactsListDetailModel("Admissions Enquries","+971987664556","admission@kings.ae")
        aboutusdescription.add(xmodel)
        var nmodel= ContactsListDetailModel("H R Enquries","+971987668556","hr@kings.ae")
        aboutusdescription.add(nmodel)
        var emodel= ContactsListDetailModel("Finance Enquries","+971907668556","finance@kings.ae")
        aboutusdescription.add(emodel)

       // progressDialog = view!!.findViewById<RelativeLayout>(R.id.progressDialog)
        contact_usrecyclerview = view?.findViewById(R.id.contact_usrecyclerview) as RecyclerView
        descriptiontext = view?.findViewById(R.id.descriptiontext) as TextView
        linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager = LinearLayoutManager(mContext)
        contact_usrecyclerview.layoutManager = linearLayoutManager
        contact_usrecyclerview.itemAnimator = DefaultItemAnimator()
        val contactusAdapter = ContactusAdapter(aboutusdescription)
        contact_usrecyclerview.adapter = contactusAdapter


        mapFragment = childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment
    }



    override fun onLocationChanged(p0: Location) {
        TODO("Not yet implemented")
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }



    override fun onConnected(p0: Bundle?) {
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onMarkerDrag(p0: Marker) {
        TODO("Not yet implemented")
    }


    override fun onMarkerDragEnd(p0: Marker) {
        TODO("Not yet implemented")
    }

    override fun onMarkerDragStart(p0: Marker) {
        TODO("Not yet implemented")
    }

    override fun onMapLongClick(p0: LatLng) {
        TODO("Not yet implemented")
    }


}