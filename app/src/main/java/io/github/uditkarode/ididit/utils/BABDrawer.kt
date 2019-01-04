package io.github.uditkarode.ididit.utils

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.uditkarode.ididit.History
import io.github.uditkarode.ididit.R
import kotlinx.android.synthetic.main.util_bottomdrawer.*

class BABDrawer: BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.util_bottomdrawer, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.history -> startActivity(Intent(navigation_view.context, History::class.java))
            }
            true
        }
    }
}