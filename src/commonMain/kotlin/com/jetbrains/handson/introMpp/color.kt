@file:Suppress("SameParameterValue")
package com.jetbrains.handson.introMpp

import kotlin.math.ln

object Colors

expect class Color
expect fun Colors.newColor(r: Int, g: Int, b: Int): Color
expect val Colors.BLACK : Color

fun pickColor(z: Complex, iterations: Int): Color {
  //we use the number of iterations and the log log
  // absolute value of the point to compute the color
  val s = iterations + 1 - ln(ln(z.mod2) / 2.0) / ln(2.0)
  return hslToRGB(30f + 10 * s.toFloat(), 90f, 50f)
}

/**
 * The function to convert an HSL color coordinate into RGB.
 * Check out https://en.wikipedia.org/wiki/HSL_and_HSV for more details.
 *
 * The HSL uses cylindrical coordinate system, where the
 * [hh] parameter denotes an angle, so that one does `% 360f`.
 * We use that fact to generate smooth color colors from
 * the number of iterations
 */
private fun hslToRGB(hh: Float, ss: Float, ll: Float): Color {
  //inspired by http://www.camick.com/java/source/HSLColor.java
  val h = (hh % 360f) / 360f
  val s = ss / 100f
  val l = ll / 100f

  val q = when {
    l < 0.5 -> l * (1 + s)
    else -> l + s - s * l
  }

  val p = 2 * l - q
  return Colors.newColor(
    hueToRGB(p, q, h + 1.0f / 3.0f).normalizedByte,
    hueToRGB(p, q, h).normalizedByte,
    hueToRGB(p, q, h - 1.0f / 3.0f).normalizedByte)
}

private fun hueToRGB(p: Float, q: Float, hh: Float): Float {
  val h = hh.normalized
  return when {
    6 * h < 1 -> p + (q - p) * 6f * h
    2 * h < 1 -> q
    3 * h < 2 -> p + (q - p) * 6f * (2f / 3f - h)
    else -> p
  }
}

private val Float.normalized
  get() = when {
    this <= 0f -> 0f
    this >= 1f -> 1f
    else -> this
  }

private val Float.normalizedByte
  get() = (this.normalized * 255 + 0.5).toInt()
