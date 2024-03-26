package com.github.jezstewartdev.nerdlesolver.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Constraints {

	private static final char[] possibleChars = { '*', '-', '+', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'=' };
	private List<Position> positions;
	private Character[][] validCharacters;
	private Map<Character, Integer> maxCharacterOccurrences;
	private Map<Character, Integer> minCharacterOccurrences;

	public void generateConstraints(List<Guess> guesses) {

		validate(guesses);

		setInitialValidCharacters(guesses.get(0).getEquation().length());

		removeInvalidCharacters(guesses);

		calculateMaxAndMinCharacterOccurs(guesses);

		removeCharactersOccurring0Times();

		validCharacters = new Character[positions.size()][];
		for (int i1 = 0; i1 < positions.size(); i1++) {
			validCharacters[i1] = positions.get(i1).getValidCharacters().toArray(new Character[0]);
		}
	}

	private void validate(List<Guess> guesses) {

		int length = guesses.get(0).getEquation().length();
		if (!guesses.stream()
				.allMatch(guess -> guess.getEquation().length() == length && guess.getResult().length() == length))
			throw new IllegalArgumentException("All equation and result strings must be same length");
	}

	public Character[][] getValidCharacters() {
		return this.validCharacters;
	}

	public Map<Character, Integer> getMaxCharacterOccurrences() {
		return this.maxCharacterOccurrences;
	}

	public Map<Character, Integer> getMinCharacterOccurrences() {
		return this.minCharacterOccurrences;
	}

	private void removeCharactersOccurring0Times() {

		for (Position position : positions) {
			for (char c : possibleChars) {
				if (maxCharacterOccurrences.containsKey(c) && maxCharacterOccurrences.get(c) == 0) {
					position.getValidCharacters().remove(c);
				}
			}
		}
	}

	private void calculateMaxAndMinCharacterOccurs(List<Guess> guesses) {

		maxCharacterOccurrences = new HashMap<>();
		minCharacterOccurrences = new HashMap<>();
		minCharacterOccurrences.put('=', 1);
		maxCharacterOccurrences.put('=', 1);

		for (Guess guess : guesses) {

			char[] equationChars = guess.getEquation().toCharArray();
			char[] resultChars = guess.getResult().toCharArray();

			Map<Character, Integer> blackCounts = new HashMap<>();
			Map<Character, Integer> greenAndRedCounts = new HashMap<>();
			for (int i = 0; i < equationChars.length; i++) {
				if (resultChars[i] == 'b')
					blackCounts.merge(equationChars[i], 1, Integer::sum);
				else
					greenAndRedCounts.merge(equationChars[i], 1, Integer::sum);
			}

			for (char c : possibleChars) {
				if (blackCounts.containsKey(c)) {
					int maxOccurencesThisGuess = greenAndRedCounts.containsKey(c) ? greenAndRedCounts.get(c) : 0;
					if (!maxCharacterOccurrences.containsKey(c)
							|| maxOccurencesThisGuess < maxCharacterOccurrences.get(c)) {
						maxCharacterOccurrences.put(c, maxOccurencesThisGuess);
					}
				}
				if (greenAndRedCounts.containsKey(c)) {
					int minOccurencesThisGuess = greenAndRedCounts.get(c);
					if (!minCharacterOccurrences.containsKey(c)
							|| minOccurencesThisGuess > minCharacterOccurrences.get(c)) {
						minCharacterOccurrences.put(c, minOccurencesThisGuess);
					}
				}
			}

		}
	}

	private void removeInvalidCharacters(List<Guess> guesses) {

		for (Guess guess : guesses) {

			char[] equationChars = guess.getEquation().toCharArray();
			char[] resultChars = guess.getResult().toCharArray();

			for (int i = 0; i < equationChars.length; i++) {

				if (resultChars[i] == 'g')
					positions.get(i).setValidCharacters(new HashSet<>(Arrays.asList(equationChars[i])));
				else
					positions.get(i).getValidCharacters().remove(equationChars[i]);
			}
		}
	}

	private void setInitialValidCharacters(int guessLength) {

		positions = new ArrayList<>();

		for (int i = 0; i < guessLength; i++) {
			positions.add(new Position());
		}

		for (Position position : positions) {
			position.setValidCharacters(new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')));
		}

		for (int i = 3; i < positions.size() - 1; i++) {
			positions.get(i).getValidCharacters().add('=');
		}

		for (int i = 1; i < positions.size() - 3; i++) {
			positions.get(i).getValidCharacters().add('+');
			positions.get(i).getValidCharacters().add('-');
			positions.get(i).getValidCharacters().add('*');
			positions.get(i).getValidCharacters().add('/');
		}

	}

}
