package io.klineapps.fya.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.klineapps.fya.R
import io.klineapps.fya.adapter.SportsAdapter
import io.klineapps.fya.model.SportResponse

class ListSportsFragment: Fragment(){

    private var sportList: RecyclerView? = null
    private var searchView:SearchView? = null
    private var fabSearch:FloatingActionButton? = null

    companion object {

        fun newInstance(): ListSportsFragment {
            return ListSportsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_sports_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpElements(view)
        setupAdapters()

        fabSearch?.setOnClickListener {
            onFabSearchButtonClicked()
        }
        searchView?.setOnCloseListener { onSearchViewClosed() }
    }

    private fun onFabSearchButtonClicked(){
        searchView?.visibility = View.VISIBLE
        searchView?.isIconified = false
        fabSearch?.hide()
    }

    private fun onSearchViewClosed():Boolean{
        searchView?.visibility = View.INVISIBLE
        fabSearch?.show()
        return true
    }

    private fun setUpElements(view:View){
        sportList = view.findViewById(R.id.sport_list) as RecyclerView
        fabSearch = view.findViewById(R.id.fab_search)
        searchView = view.findViewById(R.id.searchView)
    }

    private fun setupAdapters(){
        if (SportResponse.getInstance().sports != null) {
            val sportadapter = SportsAdapter(SportResponse.getInstance().sports)
            sportList?.apply {
                //layoutManager = GridLayoutManager(context, 2)
                adapter = sportadapter
            }
        }
    }



}