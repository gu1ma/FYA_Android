package io.klineapps.fya.activity

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.firstdecision.cowip.util.RestClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tbruyelle.rxpermissions.RxPermissions
import rx.android.schedulers.AndroidSchedulers
import io.klineapps.fya.App
import io.klineapps.fya.fragments.ListSportsFragment
import io.klineapps.fya.R
import io.klineapps.fya.activity.base.BaseActivity
import io.klineapps.fya.fragments.MapFragment
import io.klineapps.fya.fragments.ProfileFragment
import io.klineapps.fya.model.SportResponse

class MainActivity : BaseActivity() {

    private var bottomNavigation:BottomNavigationView? = null
    private var listFragment = ListSportsFragment()
    private var mapFragment = MapFragment()
    private var profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottom_navigation);

        getSports()
        setupPermissions()


        bottomNavigation?.setOnNavigationItemSelectedListener { item ->
            hideAllFragments()
            when (item.itemId) {
                R.id.nav_explore -> {
                    showFragment(listFragment)
                }
                R.id.nav_map -> {
                   showFragment(mapFragment)
                }
                R.id.nav_profile -> {
                    showFragment(profileFragment)
                }
            }
            true
        }

    }

    private fun setupPermissions(){
        RxPermissions(this)
            .request(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it){
                    App.instance?.locationUpdate()
                }
            },  {
                it.printStackTrace()
            })
    }

    private fun getSports(){

        showProgressDialog(this,"Aguarde")
        RestClient.get()!!.getCoworkings()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                dismissProgressDialog()
                if (response.isSuccessful) {
                    response.body()?.let {
                        println(it.sports[0].address)
                        SportResponse.getInstance().sports = it.sports
                        setupFragments()
                    }

                }
            }, { error ->
                dismissProgressDialog()
                error.printStackTrace()
            })
    }

    private fun setupFragments(){
        addFragment(listFragment)
        addFragment(mapFragment)
        hideFragment(mapFragment)
        addFragment(profileFragment)
        hideFragment(profileFragment)
    }

    private fun showFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .show(fragment)
            .commit()
    }

    private fun hideFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .hide(fragment)
            .commit()
    }

    private fun hideAllFragments(){
        hideFragment(listFragment)
        hideFragment(mapFragment)
        hideFragment(profileFragment)
    }

    private fun addFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .add(R.id.content, fragment)
            .commit()
    }
}
