package com.example.actividad1calculadoradesalarioneto

class CalculadoraSalario {

    fun calcularBaseIrpf(salarioBruto: Double): Double {
        if (salarioBruto <= 15000) {
            return 10.0
        } else if (salarioBruto <= 30000) {
            return 15.0
        } else {
            return 20.0        }
    }

    fun calcularReduccionHijos(numeroHijos: Int): Double {
        val reduccion = numeroHijos * 0.2
        if (reduccion > 5.0) {
            return 5.0
        } else {
            return reduccion
        }
    }

    fun calcularReduccionEstadoCivil(estadoCivil: String): Double {
        return when (estadoCivil) {
            "1" -> 2.0
            "2" -> 1.0
            "3" -> 0.0
            else -> 0.0
        }
    }

    fun calcularReduccionDiscapacidad(gradoDiscapacidad: String): Double {
        return when (gradoDiscapacidad) {
            "Sin discapacidad" -> 0.0
            "Menor del 65% (sin asistencia)" -> 1.0
            "Menor del 65% (con asistencia)" -> 1.5
            "Mayor o igual al 65%" -> 2.0
            else -> 0.0
        }
    }

    fun calcularReduccionEdad(edad: Int): Double {
        if (edad >= 65) {
            return 1.0
        }
        else  {
            return  0.0
        }
    }

    fun calcularCotizacionGrupoProfesional(grupoProfesional: String): Double {
        val numeroGrupo = grupoProfesional.substringBefore(" - ").toIntOrNull()
        return when (numeroGrupo) {
            1, 2, 3 -> 4.0
            4, 5, 6, 7 -> 5.0
            8, 9, 10, 11 -> 6.0
            else -> 0.0
        }
    }
    fun calcularPorcentajeFinalIrpf(porcentajeIrpf: Double, reduccionTotal: Double): Double{
       var porcentajeFinalIrpf = porcentajeIrpf - reduccionTotal
        if (porcentajeFinalIrpf < 0.0) {
            porcentajeFinalIrpf = 0.0
        }
        return porcentajeFinalIrpf
    }

fun calcularSalario(
    salarioBruto: Double,
    numeroPagas: Int,
    edad: Int,
    grupoProfesional: String,
    gradoDiscapacidad: String,
    estadoCivil: String,
    numeroHijos: Int
    ): ResultadoCalculadoraSalario {
        val porcentajeIrpf = calcularBaseIrpf(salarioBruto)
        val reduccionHijos = calcularReduccionHijos(numeroHijos)
        val reduccionEstadoCivil = calcularReduccionEstadoCivil(estadoCivil)
        val reduccionDiscapacidad = calcularReduccionDiscapacidad(gradoDiscapacidad)
        val reduccionEdad = calcularReduccionEdad(edad)
        val reduccionTotal = reduccionHijos + reduccionEstadoCivil + reduccionDiscapacidad + reduccionEdad
        val porcentajeFinalIrpf = calcularPorcentajeFinalIrpf(porcentajeIrpf, reduccionTotal)
        val retencionIrpf = salarioBruto * porcentajeFinalIrpf / 100
        val porcentajeCotizacion = calcularCotizacionGrupoProfesional(grupoProfesional)
        val cotizacionSeguridadSocial = salarioBruto * porcentajeCotizacion / 100
        val salarioNeto = salarioBruto - retencionIrpf - cotizacionSeguridadSocial
        val salarioNetoPorPaga = salarioNeto / numeroPagas
        val reduccionAplicada = porcentajeIrpf - porcentajeFinalIrpf
        val ahorroPorReducciones = salarioBruto * reduccionAplicada / 100
    return ResultadoCalculadoraSalario(
        salarioBruto = salarioBruto,
        porcentajeIrpf= porcentajeFinalIrpf,
        retencionIrpf = retencionIrpf,
        cotizacionSeguridadSocial = cotizacionSeguridadSocial,
        reduccionIrpf = reduccionAplicada,
        ahorroPorReducciones = ahorroPorReducciones,
        salarioNetoAnual = salarioNeto,
        salarioNetoPorPaga= salarioNetoPorPaga
    )
    }


}