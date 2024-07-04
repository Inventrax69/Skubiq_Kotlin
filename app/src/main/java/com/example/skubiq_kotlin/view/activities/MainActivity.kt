package com.example.skubiq_kotlin.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.skubiq_kotlin.R
import com.example.skubiq_kotlin.adapters.NavigationDrawerAdapter
import com.example.skubiq_kotlin.databinding.ActivityMainBinding
import com.example.skubiq_kotlin.databinding.ToolbarBinding
import com.example.skubiq_kotlin.view.fragments.DrawerFragment
import com.google.android.material.navigation.NavigationView
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    lateinit var toolbar: ToolbarBinding
    lateinit var logoView: View
    lateinit var drawerFragment: DrawerFragment
    private var qrScanIntegrator: IntentIntegrator? = null
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var  listDataParent :List<String>
    lateinit var  listDataChild : HashMap<String, List<String>>
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var mDrawerLayout: DrawerLayout? = null
    private var containerView: View? = null
    private var adapter: ExpandableListAdapter? = null
    var isSupervisor: Boolean = false

    private lateinit var expandableListView: ExpandableListView
    private lateinit var expandableListAdapter: ExpandableListAdapter
    private lateinit var titleList: List<String>
    private lateinit var dataList: HashMap<String, List<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        toolbar = binding.toolbar
        setSupportActionBar(toolbar.toolbar)
        init()

        /*try {
            drawerFragment =
                supportFragmentManager.findFragmentById(R.id.drawerFragment) as DrawerFragment
            drawerFragment.setUp(
                R.id.fragment_drawer,
                binding.drawerLayout,
                toolbar.toolbar
            )


        } catch (e: Exception) {
            e.printStackTrace()
        }*/

        navController = Navigation.findNavController(this, R.id.container)
        //NavigationUI.setupActionBarWithNavController(this, navController)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment

        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment), binding.drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)

        expandableListView = findViewById(R.id.expandableListView)
        dataList = getData()
        titleList = ArrayList(dataList.keys)
        expandableListAdapter = NavigationDrawerAdapter(this, titleList, dataList)
        expandableListView.setAdapter(expandableListAdapter)

        binding.navMenu.txtHome.setOnClickListener {
            navController.navigate(R.id.homeFragment)
            (this as AppCompatActivity?)!!.supportActionBar!!.title =
                getString(R.string.home)
        }

        expandableListView.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            when (dataList[titleList[groupPosition]]?.get(childPosition)) {
                "Receiving" -> {
                    navController.navigate(R.id.unloadingFragment)
                    supportActionBar?.title = "Goods In"
                }
                "putaway" -> navController.navigate(R.id.homeFragment)
            }
            binding.drawerLayout.closeDrawers()
            true
        }

        expandableListView.setOnGroupClickListener { _, _, groupPosition, _ ->
            when (titleList[groupPosition]) {
                "Home" -> navController.navigate(R.id.homeFragment)
            }
            //binding.drawerLayout.closeDrawers()
            false
        }

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setupWithNavController(navController)

        /*navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){

                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                }
                R.id.receiving_fragment -> {
                    navController.navigate(R.id.unloadingFragment)
                }

                R.id.putaway_fragment -> {
                    Toast.makeText(this, "Put Away Fragment", Toast.LENGTH_SHORT).show()
                }

                else -> false
            }
            binding.drawerLayout.closeDrawers()
            true
        }*/
    }


    /*private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()

    }
*/
    fun init(){
        qrScanIntegrator = IntentIntegrator(this)
        qrScanIntegrator?.let {
            it.setOrientationLocked(false)
            it.setCaptureActivity(CaptureActivity::class.java)
        }


        /*val homeFragment = HomeFragment()
        val supportFragmentManager = supportFragmentManager
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.container.id, homeFragment)
        transaction.commit()*/
    }

    private fun getData(): HashMap<String, List<String>> {
        val listData = HashMap<String, List<String>>()



        //val home = listOf<String>("Home")
        val expandableItems = listOf(
            "Receiving",
            "putaway",
            "Direct Receiving",
            "Gate Entry",
            "Gate Confirm"
        )
        //val nameList = listOf<String>("Abhishek Mathur")

        val outBoundItems = listOf(
            "OBD Picking",
            "Packing",
            "Packing Info",
            "Load Generation",
            "Loading",
            "Batch Picking",
            "Sorting",
            "Direct Picking"
        )

        val houseKeepingItems = listOf(
            "Material Transfer",
            "Bin To Bin",
            "Live Stock",
            "Cycle Count",
            "Bin Transfer"
        )


        //listData["Home"] = home


        listData["Inbound"] = expandableItems
        listData["Outbound"] = outBoundItems
        listData["House Keeping"] = houseKeepingItems
        //listData[nameList.toString()] = nameList

        return listData
    }

    fun setUp(fragmentId: Int, drawerLayout: DrawerLayout, toolbar: Toolbar) {
        try {
            containerView = this.findViewById<View>(fragmentId)
            mDrawerLayout = drawerLayout
            mDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
            ) {
                override fun onDrawerOpened(drawerView: View) {
                    super.onDrawerOpened(drawerView)
                    this@MainActivity!!.invalidateOptionsMenu()
                }

                override fun onDrawerClosed(drawerView: View) {
                    super.onDrawerClosed(drawerView)
                    this@MainActivity!!.invalidateOptionsMenu()
                }

                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    super.onDrawerSlide(drawerView, slideOffset)
                    toolbar.alpha = 1 - slideOffset / 2
                }
            }
            mDrawerLayout!!.setDrawerListener(mDrawerToggle)
            mDrawerLayout!!.post(Runnable { (mDrawerToggle as ActionBarDrawerToggle).syncState() })
        } catch (ex: Exception) {
            // Logger.Log(DrawerFragment.class.getName(),ex);
            return
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.main_menu,menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.rescan -> {
                qrScanIntegrator?.initiateScan()
            }

            R.id.action_about -> {
                //Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.aboutFragment)
                (this as AppCompatActivity?)!!.supportActionBar!!.title =
                    getString(R.string.title_about)
            }

            R.id.action_logout -> {
                //Toast.makeText(this, "Exit Selected", Toast.LENGTH_SHORT).show()
                val loginIntent = Intent(this, LoginActivity::class.java)
                this.startActivity(loginIntent)
                //sharedPreferencesUtils.removePreferences("url")

                Toast.makeText(this, "You have successfully logged out", Toast.LENGTH_LONG).show();
                this.finish()

            }

            R.id.home -> {

                //FragmentsUtils.replaceFragmentWithBackStack(this,R.id.container,HomeFragment())
                navController.navigate(R.id.homeFragment)
                (this as AppCompatActivity?)!!.supportActionBar!!.title =
                    getString(R.string.home)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(this, "result not found", Toast.LENGTH_LONG).show()
            } else {

                try {

                     //processScan(result.contents)


                } catch (e: Exception) {
                    e.printStackTrace()

                    // Data not in the expected format. So, whole object as toast message.
                    Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    /*private fun processScan(scannedData: String) {

        if (scannedData!=null){

            var  fragmentManager : FragmentManager = supportFragmentManager
            for ( fragment in fragmentManager.fragments ){

                if (fragment!= null && fragment.isVisible){

                    when(fragment){
                        is UnloadingFragment ->  fragment.myScannedData(this, scannedData)
                    }
                }

            }

        }
    }*/

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}