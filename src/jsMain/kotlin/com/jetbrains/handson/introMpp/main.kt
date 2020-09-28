package com.jetbrains.handson.introMpp

import kotlinx.browser.document
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.js.*
import org.w3c.dom.HTMLCanvasElement

const val jvmBackend = "http://$jvmHost:$jvmPort"

fun main() {
    document.getElementById("app")
            ?.also { it.innerHTML = "" }
            ?.append {
                h1 { +"Kotlin Fractals" }
                h2 { +"JS Edition" }
                img(src = "$jvmBackend/mandelbrot")
                canvas {
                    id = "canvas"
                    width = "600"
                    height = "600"
                }
            }

    renderToCanvas(document.getElementById("canvas") as HTMLCanvasElement) { image ->
        MandelbrotRender.justRender(300, image, MandelbrotRender.initialArea)
    }
}