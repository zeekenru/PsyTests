package com.kovapps.kovalev.psytests.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kovapps.kovalev.psytests.R


class IntroFragment : Fragment() {

    private lateinit var continueBtn: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_intro, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        continueBtn = view.findViewById(R.id.intro_continue_btn)
        continueBtn.setOnClickListener {
            (requireActivity() as MainActivity).onContinueBtnClicked()

        }
    }


}