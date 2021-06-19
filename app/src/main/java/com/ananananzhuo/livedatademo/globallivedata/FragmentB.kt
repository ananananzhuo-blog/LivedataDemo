package com.ananananzhuo.livedatademo.globallivedata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.ananananzhuo.livedatademo.R

class FragmentB : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_b, container, false)
        GlobalLivedata.getInstance().observe(viewLifecycleOwner,
            { t -> inflate.findViewById<TextView>(R.id.tv_fragmentB).text = "fragmentb：${t}" })
        return inflate
    }
}