package com.rahilkarim.trootechtask.ui.menu.model

data class Data(
    val categoria: Categoria,
    val codigo: String,
    val codigoBarra: String,
    val comision: String,
    val descTipoComision: String,
    val descripcion: Any,
    val idmenu: String,
    val imagen: String,
    val impuesto: Int,
    val impuestoAplicado: Boolean,
    val nombre: String,
    val permite_descuentos: Boolean,
    val precioSugerido: String,
    val precio_abierto: Boolean,
    val tipo: String,
    val tipo_comision: String,
    val tipo_desc: String
)