package com.github.jezstewartdev.nerdlesolver.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.jezstewartdev.nerdlesolver.exception.Constraints;
import com.github.jezstewartdev.nerdlesolver.exception.Guess;
import com.github.jezstewartdev.nerdlesolver.util.GuessGenerator;

class TestGuessGenerator {
	
	GuessGenerator generator = new GuessGenerator();
	Constraints constraints = new Constraints();

	@Test
	void testValidGuessesAndResults() {
		
		List<Guess> guesses = new ArrayList<>(Arrays.asList(new Guess("23+45=68", "brbrrggb"),new Guess("95-31=64", "grggbggr")));
		constraints.generateConstraints(guesses);
		List<String> expected = new ArrayList<>(Arrays.asList("99-34=65"));
		assertEquals(expected, generator.generateGuesses(constraints));
		
	}
	
	@Test
	void testMoreGuessesAndResults() {
		
		List<Guess> guesses = new ArrayList<>(Arrays.asList(new Guess("23+45=68", "brrbbgrb"),new Guess("6-3+7=10", "gbrgbggb")));
		constraints.generateConstraints(guesses);
		List<String> expected = new ArrayList<>(Arrays.asList("6+1+6=13","6+6+1=13"));
		assertEquals(expected, generator.generateGuesses(constraints));
		
	}
	
	@Test
	void tesEventMoreGuessesAndResults() {
		
		List<Guess> guesses = new ArrayList<>(Arrays.asList(new Guess("23+45=68", "bbbrrgbr"),new Guess("8*5/1=40", "rggbrggg")));
		constraints.generateConstraints(guesses);
		List<String> expected = new ArrayList<>(Arrays.asList("1*5*8=40"));
		List<String> validGuesses = generator.generateGuesses(constraints);
		validGuesses.stream().forEach(s -> System.out.println(s));
		assertEquals(expected, generator.generateGuesses(constraints));
		
	}

}
