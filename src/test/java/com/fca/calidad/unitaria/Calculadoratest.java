package com.fca.calidad.unitaria;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
class Calculadoratest {
	
	private double num1 = 0;
	private double num2 = 0;
	private Calculadora calculadora = null;
	
	@BeforeEach
	void setup() {
		num1 =2;
		num2 = 5;
		calculadora = new Calculadora();
	}
		@Test
		void suma2numerosPositivosTest() {
			//Inicializacion
			
			double resEsperado= 7;
			
			//Ejercicio , llamar al método que queremos probar
			double resEjecucion = calculadora.suma(num1, num2);
			
			//verificar
			assertThat (resEsperado, is(resEjecucion));

}
	
		@Test
		void resta2numerosPositivosTest() {
			//Inicializacion
			
			double resEsperado= -3;
			
			
			//Ejercicio , llamar al método que queremos probar
			double resEjecucion = calculadora.resta(num1, num2);
			
			//verificar
			assertThat (resEsperado, is(resEjecucion));

}

		@Test
		void divide2numerosPositivosTest() {
			//Inicializacion
			
			double divEsperado= 0.4;
			
			//Ejercicio , llamar al método que queremos probar
			double divEjecucion = calculadora.divide(num1, num2);
			
			//verificar
			assertThat (divEsperado, is(divEjecucion));
		}
		
		 void multiplicacion2numerosPositivostest() {
				
				double resEsperado = 10;
				
				//Ejercicio, llamar al metodo que queremos probar
				double resEjecucion = calculadora.multiplica(num1, num2);
				
				//verificar
				assertThat(resEsperado, is(resEjecucion));
			 
			 
			}
			 
}