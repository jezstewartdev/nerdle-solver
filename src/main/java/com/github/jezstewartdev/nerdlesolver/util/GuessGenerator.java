package com.github.jezstewartdev.nerdlesolver.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.jezstewartdev.nerdlesolver.exception.Constraints;
import com.github.jezstewartdev.nerdlesolver.exception.Equation;

public class GuessGenerator {

	private static final char[] possibleChars = { '*', '-', '+', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'=' };

	public List<String> generateGuesses(Constraints constraints) {

		CombinationGenerator generator = new CombinationGenerator();

		List<String> combinations = generator.combinationsFor2dArray(constraints.getValidCharacters());

		return combinations.stream()
				.filter(possibleGuess -> isValidEquation(possibleGuess))
				.filter(possibleGuess -> containsRequiredMinimumOfEachCharacter(possibleGuess,constraints.getMinCharacterOccurrences()))
				.collect(Collectors.toList());

	}

	private boolean isValidEquation(String possibleGuess) {
		Equation equation = new Equation(possibleGuess);
		return equation.solveAndValidate();
	}

	public boolean containsRequiredMinimumOfEachCharacter(String possibleGuess, Map<Character, Integer> minCharacterOccurrences) {
		for (char checkChar : possibleChars) {
			if (minCharacterOccurrences.containsKey(checkChar)) {
				if (possibleGuess.chars().filter(c -> c == checkChar).count() < minCharacterOccurrences.get(checkChar)) {
					return false;
				}
			}
		}
		return true;
	}

}