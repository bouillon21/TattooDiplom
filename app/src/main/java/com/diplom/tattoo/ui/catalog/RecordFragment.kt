package com.diplom.tattoo.ui.catalog

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.diplom.tattoo.data.Record
import com.diplom.tattoo.data.Tattoo
import com.diplom.tattoo.databinding.FragmentRecordBinding
import com.diplom.tattoo.model.SharedDatabaseViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class RecordFragment : Fragment() {

    private var _binding: FragmentRecordBinding? = null
    private val binding get() = _binding!!

    private lateinit var model: SharedDatabaseViewModel

    private val masters = arrayListOf<String>()
    private val defaultTime = arrayListOf<String>(
        "12:00",
        "13:00",
        "14:00",
        "15:00"
    )

    private var cMasterRecords: MutableMap<String, List<String>> = mutableMapOf()
    private var cMaster: String? = null
    private var cTime: String? = null
    private var unavailableData: ArrayList<String> = arrayListOf<String>()
    private var imageTattooUrl: String = ""
    private var nameTattoo:String = "Свой скетч"
    private var imageTattoo: Uri? = null

    private val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.US)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecordBinding.inflate(inflater, container, false)
        model = ViewModelProvider(requireActivity())[SharedDatabaseViewModel::class.java]
        btnListeners()
        initInfo()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.master.observe(viewLifecycleOwner) {
            for (master in it!!)
                masters.add(master.firstName)
            changeMaster(masters)
        }
    }

    private fun initInfo() {
        val tattoo = arguments?.getParcelable<Tattoo>("current_tattoo")
        if (tattoo != null){
            imageTattooUrl = tattoo.photoUrl[0]
            nameTattoo = tattoo.title
        }
        else
            imageTattoo = arguments?.getParcelable<Uri>("image_sketch")
        binding.nameTattoo.text = nameTattoo
        changeMaster(listOf())
    }

    //region dataPicker
    private fun showDatePicker() {
        var calendar = Calendar.getInstance()

        val dateStart: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                binding.data.setText(updateCalendar(calendar))
                setFreeTime(updateCalendar(calendar))
            }

        val dpd = DatePickerDialog.newInstance(dateStart)
        dpd.minDate = calendar
        dpd.show(requireActivity().supportFragmentManager, "DatePickerDialog")
        getUnavailableData()

        var date: Date? = null

        for (i in unavailableData.indices) {
            try {
                date = sdf.parse(unavailableData[i])
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            calendar = dateToCalendar(date)
            val dates: ArrayList<Calendar> = ArrayList()
            dates.add(calendar)
            dpd.disabledDays = dates.toArray(arrayOfNulls<Calendar>(dates.size))
        }
    }

    private fun updateCalendar(calendar: Calendar) = sdf.format(calendar.time)

    private fun dateToCalendar(date: Date?): Calendar {
        val calendar = Calendar.getInstance()
        if (date != null)
            calendar.time = date
        return calendar
    }

    //endregion

    private fun getUnavailableData() {
        unavailableData.clear()
        for (data in cMasterRecords) {
            if (data.value.size == 4) {
                unavailableData.add(data.key)
                continue
            }
        }
    }

    private fun setFreeTime(cData: String) {
        val unavailableTime = ArrayList(defaultTime)

        for (data in cMasterRecords) {
            if (data.key != cData)
                continue
            for (time in data.value)
                unavailableTime.remove(time)
        }
        changeTime(unavailableTime.toList())
    }

    private fun changeTime(time: List<String>) {
        val adapterTime = ArrayAdapter<String?>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            time
        )
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTime.adapter = adapterTime
    }

    private fun changeMaster(master: List<String>) {
        val adapter = ArrayAdapter<String?>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            master
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
    }

    private fun getDataTimeMaster(master: List<Record>?): MutableMap<String, List<String>> {
        val map: MutableMap<String, List<String>> = mutableMapOf()
        var tmp = ""
        for (i in 0 until master!!.size) {
            val data = master[i].data
            if (data == tmp)
                continue
            val data2: ArrayList<String> = arrayListOf<String>()
            master.filter { it.data == data }.forEach { data2.add(it.time) }
            map[data] = data2
            tmp = data
        }
        return map
    }

    private fun cMasterRecords(name: String): List<Record>? =
        model.records.value?.filter { it.master == name }

    private fun btnListeners() {
        binding.data.setOnClickListener {
            showDatePicker()
        }

        binding.buttonRecord.setOnClickListener {
            if (!TextUtils.isEmpty(cMaster) &&
                !TextUtils.isEmpty(cTime) &&
                !TextUtils.isEmpty(binding.data.text.toString())
            )
                model.addRecordDB(
                    Record(
                        "",
                        cMaster!!,
                        nameTattoo,
                        binding.data.text.toString(),
                        cTime!!,
                        imageTattooUrl,
                    ),
                    imageTattoo
                )
            findNavController().popBackStack()
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                cMaster = masters[selectedItemPosition]
                cMasterRecords = getDataTimeMaster(cMasterRecords(cMaster!!))
                binding.data.setText("")
                changeTime(listOf())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spinnerTime.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    itemSelected: View, selectedItemPosition: Int, selectedId: Long
                ) {
                    cTime = defaultTime[selectedItemPosition]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }
}