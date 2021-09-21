package com.losman.noti

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.losman.noti.databinding.FragmentStatesBinding
import com.losman.noti.model.CartridgeLabelStateModel
import com.losman.noti.view.fragment.state.MainStateFragment
import com.losman.noti.view.fragment.state.StateFragment
import com.losman.noti.view.fragment.state.StatesPresenter
import com.losman.noti.view.fragment.state.StatesView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StatesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class ViewPagerAdapter(manager: FragmentManager) :
    FragmentPagerAdapter(manager) {
    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val titleList: MutableList<String> = ArrayList()
    private val sId: MutableList<Int> = ArrayList()

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        val fragment = fragmentList[position]
        if (position > 0) {
            val arg = Bundle()
            arg.putInt("STATE_ID", sId[position])

            fragment.arguments = arg
        }
        return fragment
    }

    fun addFragment(fragment: Fragment, title: String, stateId: Int = 0) {
        fragmentList.add(fragment)
        titleList.add(title)
        sId.add(stateId)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

}

class StatesFragment : MvpAppCompatFragment(), StatesView {

    @Inject
    lateinit var presenterProvider: Provider<StatesPresenter>
    val presenter by moxyPresenter { presenterProvider.get() }

    var stateLabelList = listOf<CartridgeLabelStateModel>()
    lateinit var binding: FragmentStatesBinding
    lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ViewPagerAdapter(childFragmentManager)
        binding.tabs.setupWithViewPager(binding.viewPager)

        adapter.addFragment(MainStateFragment(), "Склад")
        presenter.getAllStates()
//        adapter.addFragment(StateFragment(), "state two")
//        adapter.addFragment(StateFragment(), "state three")
//        adapter.addFragment(StateFragment(), "state four")
        binding.viewPager.adapter = adapter

        binding.viewPager.addOnPageChangeListener(
            object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    if(position > 0) {
                        val item = adapter.getItem(position) as StateFragment
                        item.onResume()
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                }

            }
        )
        Log.e("ddd", "setup complite")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_states, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_states, container, false)

    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun updateStateTab(list: List<CartridgeLabelStateModel>) {
        stateLabelList = list
        list.forEach {
            adapter.addFragment(StateFragment(), it.state_name, it.id)
        }
        adapter.notifyDataSetChanged()
    }


}