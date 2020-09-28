package com.jetbrains.handson.introMpp

import com.jetbrains.handson.introMpp.Colors

actual typealias Color = java.awt.Color

actual fun Colors.newColor(r: Int, g: Int, b: Int) = Color(r, g, b)
actual val Colors.BLACK: Color get() = Color.BLACK
