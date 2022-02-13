package com.test.periodicwork.utility.extentions

fun Number.roundOff(value: Int = 2) = String.format("%.${value}f".format(this))