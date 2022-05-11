package tn.esprit.lolretrofit

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.search.*
import com.mapbox.search.result.SearchResult

class mapbox : AppCompatActivity() ,OnMapClickListener{



    private lateinit var mapView: MapView

    lateinit var mapboxMap: MapboxMap

    lateinit var originPoint: Point
    lateinit var destinationPoint: Point

    private var pointAnnotation: PointAnnotation?=null
    private var pointAnnotation1: PointAnnotation?=null

    lateinit var textView: TextView


    private lateinit var reverseGeocoding11: ReverseGeocodingSearchEngine
    private lateinit var searchRequestTask: SearchRequestTask

    private val searchCallback = object : SearchCallback,OnMapClickListener {

        override fun onResults(results: List<SearchResult>, responseInfo: ResponseInfo) {
            if (results.isEmpty()) {
                Log.i("SearchApiExample", "No reverse geocoding results")
            } else {
                Log.i("SearchApiExample", "Reverse geocoding results: $results")


                val place = results[0].address!!.place

                Log.i("SearchApiExample", "Reverse geocoding results: $place")


                textView.text = place


            }
        }

        override fun onError(e: Exception) {
            Log.i("SearchApiExample", "Reverse geocoding error", e)
        }

        override fun onMapClick(point: Point): Boolean {
            TODO("Not yet implemented")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapbox)



        textView = findViewById(com.mapbox.search.R.id.text)

        mapView = findViewById(R.id.map_view)


        MapboxSearchSdk.initialize(
            application = application,
            accessToken = getString(R.string.mapbox_access_token),
            locationEngine = LocationEngineProvider.getBestLocationEngine(this)
        )


        reverseGeocoding11 = MapboxSearchSdk.getReverseGeocodingSearchEngine()
        val options = ReverseGeoOptions(
            center = Point.fromLngLat(2.294434, 48.858349),
            limit = 1
        )
        reverseGeocoding11.search(options,searchCallback)





        mapView?.getMapboxMap()?.loadStyleUri(
            Style.MAPBOX_STREETS,
            object : Style.OnStyleLoaded {
                override fun onStyleLoaded(style: Style) {


                }
            }
        )

        mapboxMap = mapView.getMapboxMap().apply {

            loadStyleUri(Style.MAPBOX_STREETS) {


                addOnMapClickListener(this@mapbox)



            }

        }








    }



    private fun addAnnotationToMap(point: Point) {
// Create an instance of the Annotation API and get the PointAnnotationManager.


        bitmapFromDrawableRes(
            this@mapbox, R.drawable.a
        )?.let {

            val annotationApi = mapView?.annotations
            val pointAnnotationManager = annotationApi?.createPointAnnotationManager(mapView!!)

// Set options for the resulting symbol layer.
            val    pointAnnotationOptions : PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(point)
// Define a geographic coordinate.
                //  .withPoint(Point.fromLngLat(-74.0060, 40.7128))
// Specify the bitmap you assigned to the point annotation
// The bitmap will be added to map style automatically.
                .withIconImage(it)

            pointAnnotationManager?.create(pointAnnotationOptions)


// Add the resulting pointAnnotation to the map.

        }


    }

    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
// copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }


    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()



       
    }

    override fun onMapClick(point: Point): Boolean {

        Log.d("latitude", point.latitude().toString())
        Log.d("longitude", point.longitude().toString())



        reverseGeocoding11 = MapboxSearchSdk.getReverseGeocodingSearchEngine()
        val options = ReverseGeoOptions(
            center = Point.fromLngLat(point.longitude(), point.latitude()),
            limit = 1
        )
        reverseGeocoding11.search(options,searchCallback)

        addAnnotationToMap(point)



        return true
    }


}