package com.example.skubiq_kotlin.view.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.skubiq_kotlin.R
import com.example.skubiq_kotlin.databinding.FragmentAboutBinding
import java.util.Calendar

class AboutFragment : Fragment() {

    private lateinit var fragmentAboutBinding: FragmentAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentAboutBinding = FragmentAboutBinding.inflate(layoutInflater)
        val view = fragmentAboutBinding.root
        //return inflater.inflate(R.layout.fragment_about, container, false)

        // txtVersion.setText(AndroidUtils.getVersionName().toString());
        try {
            val pInfo = requireActivity().packageManager.getPackageInfo(
                requireActivity().packageName, 0
            )
            val version = pInfo.versionName
            fragmentAboutBinding.tvVersionName.text = version
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        fragmentAboutBinding.tvReleaseDateValue.text = resources.getString(R.string.fragment_about_txt_ReleaseDate_value)

        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val copyright = "Copyright © $year SKUBIQ, All rights reserved."
        fragmentAboutBinding.tvBottom.setText(copyright)

        return view
    }

    override fun onResume() {
        super.onResume()

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            getString(R.string.title_about)
    }

}