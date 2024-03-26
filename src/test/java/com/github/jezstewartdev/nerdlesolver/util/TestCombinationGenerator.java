package com.github.jezstewartdev.nerdlesolver.util;

import org.junit.jupiter.api.Test;

import com.github.jezstewartdev.nerdlesolver.util.CombinationGenerator;

public class TestCombinationGenerator {
	
	@Test
	public void testCombinationGenerator() {
		
		Character[][] my2dArray = { { '1', '2', '3', '4' }, { '5', '6', '7' }, { '7', '8', '9' } };
		CombinationGenerator generator = new CombinationGenerator();
		generator.combinationsFor2dArray(my2dArray);
		
	}

}
