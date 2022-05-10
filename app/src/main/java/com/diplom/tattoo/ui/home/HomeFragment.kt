package com.diplom.tattoo.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.diplom.tattoo.R
import com.diplom.tattoo.adapter.MasterAdapter
import com.diplom.tattoo.adapter.TatuAdapter
import com.diplom.tattoo.data.Master
import com.diplom.tattoo.data.TemporarilyDataStorage
import com.diplom.tattoo.databinding.FragmentHomeBinding
import com.diplom.tattoo.model.SharedDatabaseViewModel
import com.squareup.picasso.Picasso
import java.util.*


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var model: SharedDatabaseViewModel

    private var adapterMaster: MasterAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        model = ViewModelProvider(requireActivity())[SharedDatabaseViewModel::class.java]
        btnListeners()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.master.observe(viewLifecycleOwner){
            adapterMaster = MasterAdapter(requireContext(), it!!){ position ->
                val cMaster = it[position]
                val bundle = Bundle()
                bundle.putParcelable("current_master", cMaster)
                findNavController().navigate(R.id.action_nav_home_to_masterFragment, bundle)
            }
            val RVMaster = binding.listMaster
            RVMaster.adapter = adapterMaster
        }
        model.tattoo.observe(viewLifecycleOwner) {
            val adapterMaster = TatuAdapter(requireContext(), it!!) { position ->
                val cTattoo = it[position]
                val bundle = Bundle()
                bundle.putParcelable("current_tattoo", cTattoo)
                findNavController().navigate(R.id.action_nav_home_to_tattooFragment, bundle)
            }
            val RVMaster = binding.listWorks
            RVMaster.adapter = adapterMaster
        }
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