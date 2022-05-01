package com.diplom.tattoo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.diplom.tattoo.R
import com.diplom.tattoo.adapter.MasterAdapter
import com.diplom.tattoo.data.TemporarilyDataStorage
import com.diplom.tattoo.databinding.FragmentHomeBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var mapview: MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


//        val tatu = TemporarilyDataStorage.getTatuList()
//        val adapterTatu = TatuAdapter(requireContext(), tatu)
//
//        val RVTatu = root.findViewById<RecyclerView>(R.id.list_works)
//        RVTatu.adapter = adapterTatu

        val master = TemporarilyDataStorage.getMasterList()
        val adapterMaster = MasterAdapter(requireContext(), master)

        val RVMaster = root.findViewById<RecyclerView>(R.id.list_master)
        RVMaster.adapter = adapterMaster

        val locate = Point(55.749138, 49.257200)

        mapview = root.findViewById(R.id.mapview) as MapView
        mapview!!.map.move(
            CameraPosition(locate, 17.0f, 0.0f, 0.0f),
            Animation(Animation.Type.LINEAR, 0.0f), null,
        )
        mapview!!.setNoninteractive(true)
        val viewPointMap = View(requireContext()).apply {
            background = AppCompatResources.getDrawable(requireContext(), R.drawable.where)
        }
        mapview!!.map.mapObjects.addPlacemark(
            locate, ViewProvider(viewPointMap)
        )

        return root
    }

    override fun onStop() {
        // Вызов onStop нужно передавать инстансам MapView и MapKit.
        mapview?.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        // Вызов onStart нужно передавать инстансам MapView и MapKit.
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapview?.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}