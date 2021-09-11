package br.com.rafaeldornelles.util

import com.google.android.material.textfield.TextInputLayout

object NotasAppExtensions {
    fun TextInputLayout.setText(text: String){
        this.editText?.setText(text)
    }
}