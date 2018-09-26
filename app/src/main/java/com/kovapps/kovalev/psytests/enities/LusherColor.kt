package com.kovapps.kovalev.psytests.enities

import android.graphics.Color


enum class LusherColor(val number : Int, val color : Int) {
    BlUE(1, Color.parseColor("#004983")),
    GREEN(2, Color.parseColor("#1D9772")),
    RED(3, Color.parseColor("#F12F23")),
    YELLOW(4, Color.parseColor("#F2DD00")),
    PURPLE(5, Color.parseColor("#D42481")),
    BROWN(6, Color.parseColor("#C55223")),
    BLACK(7, Color.parseColor("#231F20")),
    GRAY(8, Color.parseColor("#98938D"))
}