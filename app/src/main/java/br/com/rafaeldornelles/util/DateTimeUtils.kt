package br.com.rafaeldornelles.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object DateTimeUtils {
    fun localDateFromEpochMIlli(milli: Long) = LocalDateTime
        .ofInstant(Instant.ofEpochMilli(milli), ZoneId.of("UTC")).toLocalDate()
}