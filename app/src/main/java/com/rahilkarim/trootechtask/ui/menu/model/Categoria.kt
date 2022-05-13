package com.rahilkarim.trootechtask.ui.menu.model

data class Categoria(
    val codigo: String,
    val idcategoriamenu: String,
    val impuesto: String,
    val nombremenu: String,
    val orden: String,
    val porcentaje: String,
    val printers: List<Printer>
)