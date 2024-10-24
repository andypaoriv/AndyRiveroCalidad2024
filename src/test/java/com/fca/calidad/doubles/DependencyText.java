package com.fca.calidad.doubles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DependencyText {
	private Dependency dependency;
	private SubDependency sub;
	
	@BeforeEach
	void setup () {
		sub = mock (SubDependency.class);
		dependency = new Dependency (sub);
		
	}

	@Test
	void test() {
		System.out.println(sub.getClassName());

	}
	void testSubDependencyClassName () {
		
		when (sub.getClassName()).thenReturn("hola");
		String resultadoEsperado = "hola";
		String resultadoReal = sub.getClassName();
		
		//assetThat(resultadoReal, is (resultadoEsperado));
}
}
