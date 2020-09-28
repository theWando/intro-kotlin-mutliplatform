package com.jetbrains.handson.introMpp

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement

fun renderToCanvas(canvas: HTMLCanvasElement, action: (FractalImage) -> Unit) {
    val ctx = canvas.getContext("2d") as CanvasRenderingContext2D
    val w = ctx.canvas.width.toDouble()
    val h = ctx.canvas.height.toDouble()
    ctx.clearRect(0.0, 0.0, w, h)

    val imageData = ctx.createImageData(w, h)

    val image = object : FractalImage {
        override val pixelRect: Rect<Int>
            get() = Rect(left = 0, top = 0, right = imageData.width, bottom = imageData.height)

        override fun putPixel(p: Pixel, c: Color) {
            val base = 4 * (p.x + imageData.width * p.y)
            val image: dynamic = imageData.data
            image[base + 0] = c.r
            image[base + 1] = c.g
            image[base + 2] = c.b
            image[base + 3] = 255
        }
    }

    action(image)
    ctx.putImageData(imageData, 0.0, 0.0)
}