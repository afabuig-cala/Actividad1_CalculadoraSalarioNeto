package com.example.actividad1calculadoradesalarioneto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.actividad1calculadoradesalarioneto.ui.theme.Actividad1CalculadoraDeSalarioNetoTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var resultadoCalculo by remember {
                mutableStateOf<ResultadoCalculadoraSalario?>(null)
            }

            Actividad1CalculadoraDeSalarioNetoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val datosResultado = resultadoCalculo

                    if (datosResultado == null) {
                        PantallaFormulario(
                            modifier = Modifier.padding(innerPadding),
                            enviarResultado = { resultadosRecibidos ->
                                resultadoCalculo = resultadosRecibidos
                            }
                        )
                    } else {
                        PantallaResultados(
                            calculosRecibidos = datosResultado,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PantallaFormulario(
        modifier: Modifier = Modifier,
        enviarResultado: (ResultadoCalculadoraSalario) -> Unit
    ) {
//las variables que vamos a usar en el programa y sus estados iniciales
        val salarioBruto = remember { mutableStateOf("") }
        val salarioNumero = salarioBruto.value.replace(",", ".").toDoubleOrNull()

        val numeroPagas = remember { mutableStateOf("") }
        val numeroPagasNumero = numeroPagas.value.toIntOrNull()

        val edad = remember { mutableStateOf("") }
        val edadNumero = edad.value.toIntOrNull()

        val gruposProfesionales = listOf(
            "1 - Ingenieros y licenciados",
            "2 - Ingenieros técnicos, peritos y ayudantes titulados",
            "3 - Jefes administrativos y de taller",
            "4 - Ayudantes no titulados",
            "5 - Oficiales administrativos",
            "6 - Subalternos",
            "7 - Auxiliares administrativos",
            "8 - Oficiales de primera y segunda",
            "9 - Oficiales de tercera y especialistas",
            "10 - Peones",
            "11 - Trabajadores menores de 18 años"
        )
        val grupoProfesional = remember { mutableStateOf("") }
        val visibleMenu = remember { mutableStateOf(false) }

        val gradosDiscapacidad = listOf(
            "Sin discapacidad", "Menor del 65% (sin asistencia)",
            "Menor del 65% (con asistencia)", "Mayor o igual al 65%"
        )
        val gradoDiscapacidad = remember { mutableStateOf("") }
        val visibleMenuDis = remember { mutableStateOf(false) }

        val estadoCivil = remember { mutableStateOf("") }

        val numeroHijos = remember { mutableStateOf("") }
        val numeroHijosNumero = numeroHijos.value.toIntOrNull()

        val intentoCalcular = remember { mutableStateOf(false) }
        val datosCalcularValidos =
            salarioNumero != null && salarioNumero > 0 &&
                    numeroPagasNumero != null && numeroPagasNumero > 0 &&
                    edadNumero != null && edadNumero >= 16 && edadNumero <= 100 &&
                    grupoProfesional.value.isNotBlank() &&
                    gradoDiscapacidad.value.isNotBlank() &&
                    estadoCivil.value.isNotBlank() &&
                    numeroHijosNumero != null && numeroHijosNumero >= 0

        Column(modifier = modifier.verticalScroll(rememberScrollState()).padding(16.dp)) {
            Text(
                text = "CALCULADORA DE SALARIO NETO",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Introduce tus datos para calcular tu salario neto",
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = salarioBruto.value,
                onValueChange = { salarioBruto.value = it },
                label = { Text("Salario bruto anual") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
            )

            if (salarioBruto.value.isNotBlank() || intentoCalcular.value) {
                if (salarioNumero == null || salarioNumero <= 0) {
                    Text("Salario inválido, debe ser un número y positivo")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = numeroPagas.value,
                onValueChange = { numeroPagas.value = it },
                label = { Text("Número de pagas al año") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            if (numeroPagas.value.isNotBlank() || intentoCalcular.value) {
                if (numeroPagasNumero == null || numeroPagasNumero <= 0) {
                    Text("Número de pagas inválido, debe ser un número y positivo")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = edad.value,
                onValueChange = { edad.value = it },
                label = { Text("Edad") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            if (edad.value.isNotBlank() || intentoCalcular.value) {
                if (edadNumero == null || edadNumero < 16 || edadNumero > 100) {
                    Text("Edad inválida, edad minima 16")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            ExposedDropdownMenuBox(
                expanded = visibleMenu.value,
                onExpandedChange = {
                    visibleMenu.value = !visibleMenu.value
                })
            {
                OutlinedTextField(
                    value = grupoProfesional.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Grupo profesional") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = visibleMenu.value
                        )
                    },
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = visibleMenu.value,
                    onDismissRequest = {
                        visibleMenu.value = false
                    }
                )
                {
                    gruposProfesionales.forEach { grupo ->
                        DropdownMenuItem(
                            text = { Text(grupo) },
                            onClick = {
                                grupoProfesional.value = grupo
                                visibleMenu.value = false
                            }
                        )
                    }
                }
                if (intentoCalcular.value && grupoProfesional.value.isBlank()) {
                    Text("Selecciona un grupo profesional")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = visibleMenuDis.value,
                onExpandedChange = {
                    visibleMenuDis.value = !visibleMenuDis.value
                }
            )
            {
                OutlinedTextField(
                    value = gradoDiscapacidad.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Grado de discapacidad") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = visibleMenuDis.value
                        )
                    },
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = visibleMenuDis.value,
                    onDismissRequest = {
                        visibleMenuDis.value = false
                    }
                ) {
                    gradosDiscapacidad.forEach { grado ->
                        DropdownMenuItem(
                            text = { Text(grado) },
                            onClick = {
                                gradoDiscapacidad.value = grado
                                visibleMenuDis.value = false
                            }
                        )
                    }
                }
                if (intentoCalcular.value && gradoDiscapacidad.value.isBlank()) {
                    Text("Selecciona un grado de discapacidad")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Estado civil")

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = CenterVertically) {
                RadioButton(
                    selected = estadoCivil.value == "1",
                    onClick = { estadoCivil.value = "1" }
                )
                Text("1: soltero, viudo, divorciado o separado legalmente con hijos que conviven exclusivamente con él.")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = CenterVertically) {
                RadioButton(
                    selected = estadoCivil.value == "2",
                    onClick = { estadoCivil.value = "2" }
                )
                Text("2: casado cuyo cónyuge tiene rentas anuales no superiores a 1.500 €.")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = CenterVertically
            ) {
                RadioButton(
                    selected = estadoCivil.value == "3",
                    onClick = { estadoCivil.value = "3" }
                )
                Text("3: las demás situaciones, incluidos solteros sin hijos y casados cuyo cónyuge supera el límite de 1500 €.")
            }
            if (intentoCalcular.value && estadoCivil.value.isBlank()) {
                Text("Selecciona una situación familiar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = numeroHijos.value,
                onValueChange = { numeroHijos.value = it },
                label = { Text("Número de hijos") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            if (numeroHijos.value.isNotBlank() || intentoCalcular.value) {
                if (numeroHijosNumero == null || numeroHijosNumero < 0) {
                    Text("Formato inválido")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    intentoCalcular.value = true
                    if (datosCalcularValidos) {
                        val calculadora = CalculadoraSalario()
                        var calculo = calculadora.calcularSalario(
                            salarioBruto = salarioNumero,
                            numeroPagas = numeroPagasNumero,
                            edad = edadNumero,
                            grupoProfesional = grupoProfesional.value,
                            gradoDiscapacidad = gradoDiscapacidad.value,
                            estadoCivil = estadoCivil.value,
                            numeroHijos = numeroHijosNumero
                        )
                        enviarResultado(calculo)


                    }
                }
            ) {
                Text("Calcular")
            }


        }
    }

    @Composable
    fun PantallaResultados(
        calculosRecibidos: ResultadoCalculadoraSalario,
        modifier: Modifier = Modifier
    ) {

        Column(
            modifier = modifier
                .padding(16.dp)) {
            Text("RESULTADOS")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Salario bruto: ${"%.2f".format(calculosRecibidos.salarioBruto)}")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Salario neto: ${"%.2f".format(calculosRecibidos.salarioNetoAnual)}")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Salario neto por paga: ${"%.2f".format(calculosRecibidos.salarioNetoPorPaga)}")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Retenciones de IRPF: ${"%.2f".format(calculosRecibidos.retencionIrpf)}")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Posibles deducciones: ${"%.2f".format(calculosRecibidos.ahorroPorReducciones)}")

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PantallaFormularioPreview() {
        Actividad1CalculadoraDeSalarioNetoTheme {
            PantallaFormulario(enviarResultado = {})
        }
    }
}