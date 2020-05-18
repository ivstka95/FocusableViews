package karpiuk.ivan.myvisibilityproject.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import karpiuk.ivan.myvisibilityproject.R
import karpiuk.ivan.myvisibilityproject.ui.list.MyItemRecyclerViewAdapter
import karpiuk.ivan.myvisibilityproject.ui.list.dummy.DummyContent

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {


    private val TAG: String = PlaceholderFragment.javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.content_scrolling, container, false)

        with(root.findViewById(R.id.list1) as RecyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = MyItemRecyclerViewAdapter(DummyContent.ITEMS)
        }
        with(root.findViewById(R.id.list2) as RecyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = MyItemRecyclerViewAdapter(DummyContent.ITEMS)
        }
        with(root.findViewById(R.id.list3) as RecyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = MyItemRecyclerViewAdapter(DummyContent.ITEMS)
        }
        with(root.findViewById(R.id.list4) as RecyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = MyItemRecyclerViewAdapter(DummyContent.ITEMS)
        }
        with(root.findViewById(R.id.list5) as RecyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = MyItemRecyclerViewAdapter(DummyContent.ITEMS)
        }
        with(root.findViewById(R.id.list6) as RecyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = MyItemRecyclerViewAdapter(DummyContent.ITEMS)
        }
        view
        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}