package br.com.rafaeldornelles.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Entity
@Parcelize
class Nota(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val titulo: String,
    val data: LocalDate,
    val horario: LocalTime
) : Parcelable