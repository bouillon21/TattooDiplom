package com.diplom.tattoo.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.diplom.tattoo.adapter.MasterAdapter
import com.diplom.tattoo.data.TemporarilyDataStorage
import com.diplom.tattoo.databinding.FragmentHomeBinding
import java.util.*


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        btnListeners()
//        val tatu = TemporarilyDataStorage.getTatuList()
//        val adapterTatu = TatuAdapter(requireContext(), tatu)
//
//        val RVTatu = root.findViewById<RecyclerView>(R.id.list_works)
//        RVTatu.adapter = adapterTatu
        val master = TemporarilyDataStorage.getMasterList()
        val adapterMaster = MasterAdapter(requireContext(), master)
        val RVMaster = binding.listMaster
        RVMaster.adapter = adapterMaster



        return binding.root
    }

    private fun btnListeners() {
        binding.locate.setOnClickListener {
            val uri: String = java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", 55.749138, 49.257200)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            context?.startActivity(intent)
        }
        binding.phone.setOnClickListener {
            val phone = "+79179379235"
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)
        }
        binding.tg.setOnClickListener {
            val Telegram = Intent(Intent.ACTION_VIEW)
            Telegram.data = Uri.parse("https://t.me/fikus_rus")
            startActivity(Telegram)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}