package com.fca.calidad.doubles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DependencyText {
	private Dependency dependency;
	private SubDependency sub;
	
	@BeforeEach
	void setup () {
		sub = mock (SubDependency.class);
		dependency = mock (Dependency.class);
		
	}
	
	@Test
	void addTwoTest () {
		//Inicializacion
		when (dependency.addTwo(2)).thenReturn(10);
		int resultadoEsperado = 12;
		assertThat(resultadoEsperado, is(dependency.addTwo(2)));
	}



	@Test
	void test() {
		System.out.println(sub.getClassName());

	}
	void testSubDependencyClassName () {
		
		when (sub.getClassName()).thenReturn("hola");
		String resultadoEsperado = "hola";
		String resultadoReal = sub.getClassName();
		
		assertThat(resultadoReal, is (resultadoEsperado));
}
}
