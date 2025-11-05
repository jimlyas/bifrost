package io.github.jimlyas.sample.a

import io.github.jimlyas.sample.b.SimpleMessage.getMessage
import io.github.jimlyas.sample.c.SimpleLogging

fun main() {
    val logger = SimpleLogging()
    logger.logThis("hehe this is logging".getMessage())
}