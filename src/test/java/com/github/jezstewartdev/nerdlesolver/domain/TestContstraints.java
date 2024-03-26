package com.github.jezstewartdev.nerdlesolver.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.jezstewartdev.nerdlesolver.exception.Constraints;
import com.github.jezstewartdev.nerdlesolver.exception.Guess;
import com.github.jezstewartdev.nerdlesolver.util.CombinationGenerator;

class TestContstraints {

	Constraints constraints = new Constraints();
	CombinationGenerator generator = new CombinationGenerator();

	@Test
	void testValidGuessesCreatesValidConstraints() {

		System.out.println();
		List<Guess> guesses = new ArrayList<>(Arrays.asList(new Guess("2+4+6=12", "bbrbggbb"),new Guess("75-36=39", "brgrggbr"),
				new Guess("84-46=38", "bbggggrb")));
		constraints.generateConstraints(guesses);
		System.out.println();

	}
	
	@Test
	void testMisMatchingLengthsCausesException() {

		System.out.println();
		List<Guess> guesses = new ArrayList<>(Arrays.asList(new Guess("2+4+6=12", "bbrbggbb"),new Guess("75-36=39", "brgrggbrg"),
				new Guess("84-46=38", "bbggggrb")));
		assertThrows(IllegalArgumentException.class, () -> constraints.generateConstraints(guesses));
		System.out.println();

	}
	
}
