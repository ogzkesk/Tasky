package com.ogzkesk.domain.ext

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.util.Base64

@OptIn(InternalSerializationApi::class)
inline fun <reified T : Any> T.toBase64(): String {
    val byteArray = Json.encodeToString(T::class.serializer(), this).encodeToByteArray()
    return Base64.getEncoder().encodeToString(byteArray)
}

@OptIn(InternalSerializationApi::class)
inline fun <reified T : Any> String.fromBase64(): T {
    val string = Base64.getDecoder().decode(this)
    return Json.decodeFromString(T::class.serializer(), string.decodeToString())
}
