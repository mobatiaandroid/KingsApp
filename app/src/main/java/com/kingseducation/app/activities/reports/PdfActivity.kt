package com.kingseducation.app.activities.reports

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import com.kingseducation.app.R

class PdfActivity : AppCompatActivity() {
    lateinit var back: ImageView
    lateinit var downloadpdf: ImageView
    lateinit var context: Context
    lateinit var pdfviewer: PDFView
    lateinit var logoClickImgView:TextView
    private val STORAGE_PERMISSION_CODE: Int = 1000
    lateinit var progressBar: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pdfview_layout)
        context = this
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        back = findViewById(R.id.btn_left)
        downloadpdf = findViewById(R.id.downloadpdf)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        progressBar = findViewById(R.id.progressbar)
        logoClickImgView.text = "Reports"
        progressBar.visibility = View.VISIBLE
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(context, R.anim.linear_interpolator)
        progressBar.startAnimation(aniRotate)

        downloadpdf.visibility=View.GONE
        pdfviewer = findViewById(R.id.pdfView)
        pdfviewer.fromAsset("SampleKSD.pdf").load()
        progressBar.visibility = View.GONE
        back.setOnClickListener {
            finish()
        }

    }
//download


}