package br.com.rafaeldornelles.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity
class Nota(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val titulo: String,
    val data: LocalDate,
    val horario: LocalTime
)