package com.example.alpha.barcodescanner

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.alpha.R
import com.example.alpha.databinding.ActivityMainBinding
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView


open class CaptureActivity  : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var capture: CaptureManager? = null
    private var barcodeScannerView: DecoratedBarcodeView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        barcodeScannerView = initializeContent()
        capture = CaptureManager(this, barcodeScannerView)
        capture!!.initializeFromIntent(intent, savedInstanceState)
        capture!!.decode()
    }

    /**
     * Override to use a different layout.
     *
     * @return the DecoratedBarcodeView
     */
    private fun initializeContent(): DecoratedBarcodeView {
        setContentView(R.layout.zxing_capture)
        return findViewById<View>(R.id.zxing_barcode_scanner) as DecoratedBarcodeView
    }

    override fun onResume() {
        super.onResume()
        capture!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture!!.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture!!.onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        capture!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return barcodeScannerView!!.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

}