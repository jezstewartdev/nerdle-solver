package com.github.jezstewartdev.nerdlesolver.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.github.jezstewartdev.nerdlesolver.exception.Equation;

class TestEquation {
	
	Equation equation = new Equation();
	
	@Test
	void testNullStringReturnsFalse() {

		assertFalse(equation.validate(null));

	}
	
	@Test
	void testStringLengthNot8ReturnsFalse() {

		assertFalse(equation.validate("12345"));

	}
	
	@Test
	void testValidPatternReturnsTrue() {

		assertTrue(equation.validate("1+2-3=45"));

	}
	
	@Test
	void testValidPatternButInvalidLengthReturnsFalse() {

		assertFalse(equation.validate("1+2+3=4"));

	}
	
	@Test
	void testOtherValidOperatorsReturnsTrue() {

		assertTrue(equation.validate("1*2/3=45"));

	}

	@Test
	void testValidFormatButInvalidResultReturnsFalse() {
		equation.parse("1-2*3/2=123");
		assertFalse(equation.solveAndValidate());
		
	}
	
	@Test
	void testValidFormatAndResultReturnsTrue() {
		equation.parse("10-2*3=4");
		assertTrue(equation.solveAndValidate());
		
	}
	
	@Test
	void testParseInvalidFormatReturnsFalse() {
		assertFalse(equation.parse("10--2*3=4"));
	}
	
	@Test
	void testSolveAndValidateReturnsFalseIfEquationNotParsed() {
		equation.parse("10--2*3=4");
		assertFalse(equation.solveAndValidate());
	}
	
	@Test
	void leadingZeroesShouldReturnFalse() {
		equation.parse("01+02=03");
		assertFalse(equation.solveAndValidate());
	}

}
