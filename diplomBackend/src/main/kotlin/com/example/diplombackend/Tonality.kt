package com.example.diplombackend

enum class Tonality {
    POSITIVE,
    NEGATIVE,
    NEUTRAL;
    companion object {

        fun String.toTonality(): Tonality {
            return when (this) {
                "positive" -> POSITIVE
                "negative" -> NEGATIVE
                "neutral" -> NEUTRAL
                else -> throw IllegalArgumentException("Unknown tonality: $this")
            }
        }
    }
}