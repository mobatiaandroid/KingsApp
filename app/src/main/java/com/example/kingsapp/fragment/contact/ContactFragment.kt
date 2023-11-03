package com.example.kingsapp.fragment.contact

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.activities.login.SigninyourAccountActivity
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.constants.InternetCheckClass
import com.example.kingsapp.constants.ProgressBarDialog
import com.example.kingsapp.constants.api.ApiClient
import com.example.kingsapp.fragment.contact.adapter.ContactusAdapter
import com.example.kingsapp.fragment.contact.model.ContactsListDetailModel
import com.example.kingsapp.fragment.contact.model.ContactusModel
import com.example.kingsapp.fragment.contact.model.ContactusResponseArray
import com.example.kingsapp.manager.PreferenceManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Response

@Suppress(
    "DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "ControlFlowWithEmptyBody",
    "DEPRECATED_IDENTITY_EQUALS"
)
class ContactFragment: Fragment() ,LocationListener,
GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener,
GoogleMap.OnMarkerDragListener, GoogleMap.OnMapLongClickListener   {
    lateinit var titleTextView: TextView
    lateinit var textView:TextView
    lateinit var mContext: Context
     lateinit var menu: ImageView
  //  lateinit var progressDialog: RelativeLayout
    lateinit var contact_usrecyclerview: RecyclerView
    lateinit var description: String
    var aboutusdescription = ArrayList<ContactsListDetailModel>()
    var contactusdescription = ArrayList<ContactusResponseArray>()
    lateinit var progressBarDialog: ProgressBarDialog

    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var descriptiontext: TextView
    lateinit var latitude: String
    lateinit var longitude: String
    lateinit var c_latitude: String
    lateinit var c_longitude: String
    lateinit var locationManager: LocationManager
    lateinit var schoolNameTextView: TextView
    var isGPSEnabled: Boolean? = null
    var isNetworkEnabled: Boolean? = null
    var lat: Double = 0.0
    var long: Double = 0.0
    lateinit var mapFragment: SupportMapFragment
    lateinit var map: GoogleMap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.contact_us_activity, container, false)
        //rootView= inflater.inflate(R.layout.activity_about_us, container, false)



    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        /*val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                } else -> {
                // No location access granted.
            }
            }
        }*/

// ...

// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
       /* locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))*/
        initFn()
        var internetCheck = InternetCheckClass.isInternetAvailable(mContext)
        if (internetCheck)
        {
            fetchlatitudelongitude()
            getcontactdetails()
        }


    }

    private fun getcontactdetails() {
        progressBarDialog.show()
        val call: Call<ContactusModel> = ApiClient.getApiService().contactus("Bearer "+PreferenceManager().getAccessToken(
            mContext).toString(),PreferenceManager().getStudent_ID(mContext).toString(),PreferenceManager().getLanguagetype(mContext).toString())
        call.enqueue(object : retrofit2.Callback<ContactusModel> {
            override fun onResponse(
                call: Call<ContactusModel>,
                response: Response<ContactusModel>
            ) {
                progressBarDialog.hide()

                Log.e("respon",response.body().toString())
                if (response.body() != null) {
                if(response.body()!!.status.equals(100))
                {

              //  contactusdescription.addAll(response.body()!!.contactus)
               //     for (i in 0..contactusdescription.size-1)
                //    {
                        aboutusdescription.addAll(response.body()!!.contactus.contacts)
               //     }
                    contact_usrecyclerview.itemAnimator = DefaultItemAnimator()
                    val contactusAdapter = ContactusAdapter(aboutusdescription)
                    latitude = response.body()!!.contactus.latitude
                    longitude = response.body()!!.contactus.longitude
                    descriptiontext.text=response.body()!!.contactus.description
                    contact_usrecyclerview.adapter = contactusAdapter
                /*val username= response.body()!!.home.user_details.name
                    PreferenceManager().setuser_id(com.example.kingsapp.fragment.mContext,username)
                    Log.e("Username", PreferenceManager().getuser_id(com.example.kingsapp.fragment.mContext).toString())
*/
                }
                else
                {
                    CommonClass.checkApiStatusError(response.body()!!.status, mContext)
                }
                }
                else{
                    val intent = Intent(mContext, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
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
                            .title("KINGS")
                    )
                    map.moveCamera(CameraUpdateFactory.newLatLng(latLng))


                    map.moveCamera(CameraUpdateFactory.newLatLng(latLng))

                    map.animateCamera(CameraUpdateFactory.zoomTo(10f))
                    map.setOnInfoWindowClickListener {

                        if (!isGPSEnabled!!) {
                            val callGPSSettingIntent = Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS
                            )
                            startActivity(callGPSSettingIntent)
                        } else {
                            //val url = "http://maps.google.com/maps?saddr=$c_latitude,$c_longitude&daddr=The British International School,Abudhabi"
                            val url = "http://maps.google.com/maps?saddr=Your Location&daddr=Kings School Al Barsha"

                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(url)
                            startActivity(i)
                        }


                    }
                }
            }

            override fun onFailure(call: Call<ContactusModel?>, t: Throwable) {
                progressBarDialog.hide()
                Toast.makeText(
                    com.example.kingsapp.fragment.mContext,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("succ", t.message.toString())
            }
        })







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

//                    location =
//                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
//                    if (location != null) {
//                        lat = location.latitude
//                        long = location.longitude
//                    }
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
    private fun initFn() {
        mContext = requireContext()

        aboutusdescription = ArrayList()
        contactusdescription = ArrayList()
        //progressDialog = view!!.findViewById(R.id.progressDialog) as RelativeLayout
        menu = view?.findViewById(R.id.menu) as ImageView
        contact_usrecyclerview = view?.findViewById(R.id.contact_usrecyclerview) as RecyclerView
        descriptiontext = view?.findViewById(R.id.descriptiontext) as TextView
        mapFragment = childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment
        textView = view?.findViewById(R.id.textView) as TextView
        schoolNameTextView = view?.findViewById(R.id.schoolNameTextView)!!
        progressBarDialog = ProgressBarDialog(mContext)
        schoolNameTextView.text = PreferenceManager().getSchoolName(requireContext())
        linearLayoutManager = LinearLayoutManager(mContext)
        contact_usrecyclerview.layoutManager = linearLayoutManager
        if (PreferenceManager().getLanguage(mContext).equals("ar")) {
            val face: Typeface =
                Typeface.createFromAsset(mContext.assets, "font/times_new_roman.ttf")
            textView.typeface = face
        }

        menu.setOnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onLocationChanged(location: Location) {

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