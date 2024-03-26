package com.github.jezstewartdev.nerdlesolver.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Equation {

	private static final String validationRegex = "((0|[1-9][0-9]*)[\\+\\-\\*\\/])+(0|[1-9][0-9]*)\\=(0|[1-9][0-9]*)";
	private static Pattern validationPattern = Pattern.compile(validationRegex);

	private List<Integer> operands = new ArrayList<>();
	private List<String> operators = new ArrayList<>();
	private int result;
	private boolean parsed;

	public Equation() {
		super();
	}
	
	public Equation(String equationString) {
		super();
		parse(equationString);
	}

	public boolean validate(String equationString) {

		if (equationString == null)
			return false;

		if (equationString.length() != 8)
			return false;

		Matcher matcher = validationPattern.matcher(equationString);
		return matcher.matches();

	}

	public boolean parse(String equationString) {

		if (equationString.equals("6+7+1=13")) {
			System.out.println(equationString);
		}
		if (!validate(equationString)) {
			parsed = false;
			return parsed;
		}

		String[] allNumbers = equationString.split("[\\+\\-\\*\\/\\=]");

		for (int i = 0; i < allNumbers.length; i++) {
			if (i < allNumbers.length - 1)
				operands.add(Integer.valueOf(allNumbers[i]));
			else
				result = Integer.valueOf(allNumbers[i]);
		}

		String[] operatorsAndEquals = equationString.split("[0-9]+");
		for (int i = 1; i < operatorsAndEquals.length - 1; i++) {
			operators.add(operatorsAndEquals[i]);
		}

		parsed = true;
		return parsed;

	}

	public boolean solveAndValidate() {

		if (!parsed)
			return false;

		try {
			performOperationsAndReduce(1);
			performOperationsAndReduce(2);
		} catch (NonIntegerResultException | ArithmeticException e) {
			return false;
		}
		return operands.get(0) == result;

	}

	private void performOperationsAndReduce(int operationOrder) throws NonIntegerResultException {
		for (int x = 0; x < operators.size(); x++) {
			for (int i = x; i < operators.size(); i++) {
				if (operatorOrder(operators.get(i)) == operationOrder) {
					int operationResult = performOperation(operands.get(i), operands.get(i + 1), operators.get(i));
					reduceOperatorAndOperandLists(i, operationResult);
					x = -1;
					break;
				}
			}
		}
	}

	private int operatorOrder(String operator) {
		if (operator.equals("*") || operator.equals("/"))
			return 1;
		if (operator.equals("+") || operator.equals("-"))
			return 2;
		return 2;
	}

	private void reduceOperatorAndOperandLists(int i, int operationResult) {
		operands.set(i, operationResult);
		operands.remove(i + 1);
		operators.remove(i);
	}

	private int performOperation(int a, int b, String operator) throws NonIntegerResultException {

		switch (operator) {
		case "*":
			return a * b;
		case "/":
			if (a % b != 0)
				throw new NonIntegerResultException();
			return a / b;
		case "+":
			return a + b;
		case "-":
			return a - b;
		default:
			throw new IllegalArgumentException();
		}

	}

	public List<Integer> getOperands() {
		return operands;
	}

	public List<String> getOperators() {
		return operators;
	}

	public int getResult() {
		return result;
	}

}
