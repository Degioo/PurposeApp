package com.example.myapplication

import java.util.Calendar

object SuggestionUtils {
    private val suggestions = listOf(
        "Fai 10 minuti di meditazione",
        "Bevi 2 litri di acqua",
        "Scrivi un diario per 5 minuti",
        "Leggi 5 pagine di un libro",
        "Fai una camminata di 15 minuti"
    )

    fun getDailySuggestion(): String {
        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        return suggestions[dayOfYear % suggestions.size]
    }
}
