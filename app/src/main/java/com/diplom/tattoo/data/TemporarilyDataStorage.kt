package com.diplom.tattoo.data

import com.diplom.tattoo.R

object TemporarilyDataStorage {
    fun getTatuList(): List<Tatu>{
        return listOf(
            Tatu(
                title = "Дракон",
                R.drawable.dragon
            ),
            Tatu(
                title = "Цветок",
                R.drawable.flower
            ),
            Tatu(
                title = "Майя",
                R.drawable.maya
            ),
            Tatu(
                title = "Цветок",
                R.drawable.flower
            ),
            Tatu(
                title = "Майя",
                R.drawable.maya
            ),
            Tatu(
                title = "Цветок",
                R.drawable.flower
            ),
            Tatu(
                title = "Майя",
                R.drawable.maya
            ),
            Tatu(
                title = "Цветок",
                R.drawable.flower
            ),
            Tatu(
                title = "Майя",
                R.drawable.maya
            )
        )
    }

//    fun getMasterList(): List<Master>{
//        return listOf(
//            Master(
//                title = "Булат",
//                R.drawable.master
//            ),
//            Master(
//                title = "Расим",
//                R.drawable.master
//            ),
//            Master(
//                title = "Рамиль",
//                R.drawable.master
//            )
//        )
//    }

    fun getDescriptionList(): List<DescriptionTatuInfo>{
        return listOf(
            DescriptionTatuInfo(title = "Нога"),
            DescriptionTatuInfo(title = "Рука"),
            DescriptionTatuInfo(title = "Туловище"))
    }

}