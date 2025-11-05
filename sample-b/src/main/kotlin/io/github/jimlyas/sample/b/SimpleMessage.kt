package io.github.jimlyas.sample.b

object SimpleMessage {

    fun String.getMessage() = "'$this' logged at ${System.currentTimeMillis()}"

}