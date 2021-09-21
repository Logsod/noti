package com.losman.noti.view.fragment.printer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.losman.noti.R
import com.losman.noti.StatesFragment
import com.losman.noti.ViewPagerAdapter
import com.losman.noti.databinding.FragmentPrintersBinding


class PrintersFragment : Fragment() {


    lateinit var binding: FragmentPrintersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_printers, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_printers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PrintersViewPagerAdapter(childFragmentManager)
        binding.tabs.setupWithViewPager(binding.viewPager)
        adapter.addFragment(PrinterListFragment(), "Список")
        adapter.addFragment(PrinterModelFragment(), "Модели")
        binding.viewPager.adapter = adapter
    }


}