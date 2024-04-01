package com.loginapp.recomendia.utils

import java.text.Normalizer
import java.util.regex.Pattern

class Utils {
    fun quitarTildes(input: String): String {
        val normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        return pattern.matcher(normalized).replaceAll("")
    }
}