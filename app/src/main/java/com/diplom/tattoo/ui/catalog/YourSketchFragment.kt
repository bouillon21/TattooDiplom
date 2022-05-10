package com.diplom.tattoo.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.diplom.tattoo.data.Tattoo
import com.diplom.tattoo.databinding.FragmentYourSketchBinding
import com.diplom.tattoo.model.SharedDatabaseViewModel

class YourSketchFragment : Fragment() {

    private var _binding: FragmentYourSketchBinding? = null
    private val binding get() = _binding!!

    private lateinit var model: SharedDatabaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYourSketchBinding.inflate(inflater, container, false)
        model = ViewModelProvider(requireActivity())[SharedDatabaseViewModel::class.java]
        btnListeners()
        return binding.root
    }

    private fun btnListeners() {
        binding.buttonRecord.setOnClickListener {

//            model.uploadTattoo(
//                Tattoo(
//                    "Инь-Янь",
//                    "Популярный сегодня и часто используемый в тату символ «Инь-Янь» начинает свою историю с древней китайской мифологии. Сущность этого символа заключается в объединении во едино двух противоположностей, он был известнейшим символом равновесия и борьбы. Инь и янь — это всегда противоположные силы:\n" +
//                            "\n" +
//                            "ИНЬ — символ женского начала, смерти, земли, луны и четных чисел, тьмы и т.д.\n" +
//                            "\n" +
//                            "ЯН — символ мужского начала, жизни, неба, солнца и нечетных чисел, света и т. д.",
//                    listOf(".", ".", "."),
//                    listOf("Черный", "Белый"),
//                    listOf("Спина", "Парное", "Предплечье")
//                )
//            )
//            model.uploadTattoo(
//                Tattoo(
//                    "Перо",
//                    "Перо в татуировке встречается довольно часто и имеет много различных значений и символических оттенков. Все зависит от стиля и формы наносимого рисунка. Татуировка с изображением пера впервые появилась у древних индейских племен. У коренных жителей американского материка татуировка перо символизировала возрождение жизни (всего живого на земле) или бессмертие.",
//                    listOf(".", ".", "."),
//                    listOf("Черный", "Красный-Синий","Синий","Оранжевый"),
//                    listOf("Рука", "Нога", "Спина")
//                )
//            )
//            findNavController().navigate(R.id.action_nav_your_sketch_to_nav_record)

        }
    }
}