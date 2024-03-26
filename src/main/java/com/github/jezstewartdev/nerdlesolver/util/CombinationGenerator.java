package com.github.jezstewartdev.nerdlesolver.util;

import java.util.ArrayList;
import java.util.List;

public class CombinationGenerator {
	
	private Character[][] array;
	private char[] combination;
	private List<String> combinationList;
	
	public List<String> combinationsFor2dArray(Character[][] array) {
		this.array = array;
		combination = new char[array.length];
		combinationList = new ArrayList<>();
		getCombinations(0);
		return combinationList;
	}
	
	private void getCombinations(int i1) {
		for (int i2 = 0; i2 < array[i1].length; i2++) {
			combination[i1] = array[i1][i2];
			if (i1 < array.length - 1)
				getCombinations(i1 + 1);
			else
				combinationList.add(String.valueOf(combination));
		}
	}

}
