package com.example.skubiq_kotlin.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.skubiq_kotlin.R
import com.example.skubiq_kotlin.adapters.ExpandbleListAdapter
import com.example.skubiq_kotlin.databinding.ActivityMainBinding
import com.example.skubiq_kotlin.databinding.FragmentDrawerBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DrawerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DrawerFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentDrawerBinding
     lateinit var  listDataParent :List<String>
    lateinit var  listDataChild : HashMap<String, List<String>>
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var mDrawerLayout: DrawerLayout? = null
    private var containerView: View? = null
    private var adapter: ExpandableListAdapter? = null
    var isSupervisor: Boolean = false
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDrawerBinding.inflate(inflater, container, false);
        loadfromcontrollers()

        binding.layout2.setOnClickListener(this)
        return binding.root

    }


     fun loadfromcontrollers(){

         val mainListInbound: List<String> = resources.getStringArray(R.array.mainListInbound).toList()

         val mainListOutbound: List<String> = resources.getStringArray(R.array.mainListOutbound).toList()

         val mainListHouseKeeping: List<String> = resources.getStringArray(R.array.mainListHouseKeeping).toList()

         listDataParent = resources.getStringArray(R.array.nav_headers).toList()
         val titleheaderarray: List<String> = resources.getStringArray(R.array.title_header_array).toList()
         listDataChild   = HashMap<String, List<String>> ()
         listDataChild.put(listDataParent[0],mainListInbound)
         listDataChild.put(listDataParent[1],mainListOutbound)
         listDataChild.put(listDataParent[2],mainListHouseKeeping)

         binding.expandableListView.let {
             adapter = activity?.let { it1 ->
                 ExpandbleListAdapter(it1, titleheaderarray as ArrayList<String>, object : ExpandbleListAdapter.OnItemClick{
                     override fun onItemClick(gpos: Int, cpos: Int, text: String?) {
                         mDrawerLayout!!.closeDrawer(containerView!!)

                         //increasing the child position +1 because to ingore the material transfers when user role is supervisor
                         if (!isSupervisor && gpos == 2) {
                             setNavigationPage(gpos, cpos + 1)
                         } else {
                             setNavigationPage(gpos, cpos)
                         }
                     }

                 },isSupervisor, listDataChild)
             }
             it!!.setAdapter(adapter)
         }


     }


    fun setUp(fragmentId: Int, drawerLayout: DrawerLayout, toolbar: Toolbar) {
        try {
            containerView = requireActivity().findViewById<View>(fragmentId)
            mDrawerLayout = drawerLayout
            mDrawerToggle = object : ActionBarDrawerToggle(
                activity,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
            ) {
                override fun onDrawerOpened(drawerView: View) {
                    super.onDrawerOpened(drawerView)
                    activity!!.invalidateOptionsMenu()
                }

                override fun onDrawerClosed(drawerView: View) {
                    super.onDrawerClosed(drawerView)
                    activity!!.invalidateOptionsMenu()
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


    fun setNavigationPage(groupPosition: Int, childPosition: Int) {
        when (groupPosition) {
            0 -> {
                when (childPosition) {
                    0 -> {
                        //Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_SHORT).show()
                        //findNavController().navigate(DrawerFragmentDirections.actionDrawerFragmentToUnloadingFragment())
                        //navController.navigate(R.id.unloadingFragment)
                    }

                    1 -> {
                        Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_SHORT).show()
                    }


                }
            }

            1 -> {
                when (childPosition) {
                    0 -> {
                        Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_SHORT).show()
                    }

                    1 -> {
                        Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_SHORT).show()
                    }

                    2 -> {
                        Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_SHORT).show()
                    }

                    3 -> {
                        Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_SHORT).show()
                    }

                    4 -> {
                        Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_SHORT).show()
                    }

                    5 -> {
                        Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_SHORT).show()
                    }

                    6 -> {
                        Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_SHORT).show()
                    }

                }
            }

            2 -> {
                when (childPosition) {
                    0 -> {
                        Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_SHORT).show()
                    }

                    1 -> {
                        Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_SHORT).show()
                    }

                    2 -> {
                        Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_SHORT).show()
                    }

                    3 -> {
                        Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_SHORT).show()
                    }

                    4 -> {
                        Toast.makeText(requireActivity(), "Clicked", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            else -> {}
        }
    }

    override fun onClick(v: View) {
        when(v.id) {

            R.id.layout2 -> {
                //findNavController() .navigate(DrawerFragmentDirections.actionDrawerFragmentToHomeFragment())
                //navController.navigate(R.id.homeFragment)
            }
        }
    }

}