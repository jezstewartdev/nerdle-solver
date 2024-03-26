package com.github.jezstewartdev.nerdlesolver.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guess {

	private String equation;
	private String result;
}
