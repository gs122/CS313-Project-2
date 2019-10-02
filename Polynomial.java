import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class Polynomial implements PolynomialInterface {

	private HashMap<Integer, Integer> PolynomialMap = new HashMap<Integer, Integer>();
	String polynomialString = "";

	public Polynomial(String s) {
		polynomialString = s;
		String[] polyTokens = polynomialString.split("(?=\\+)|(?=-)");
		for (String term : polyTokens) {
			String coef = "";
			String exp = "";
			if (!term.contains("X")) {
				coef = term.substring(0, term.length());
				exp = "0";
			} else {
				coef = term.substring(0, term.indexOf("X"));
				if (!term.contains("^")) {
					exp = "1";
				} else {
					exp = term.substring(term.indexOf("^") + 1, term.length());
				}
			}
			if (coef.length() <= 1) {
				if (coef.contains("-"))
					coef = "-1";
				if (coef.contains("+"))
					coef = "1";
				if (coef.length() == 0)
					coef = "1";
			}
			PolynomialMap.put(Integer.parseInt(exp), Integer.parseInt(coef));
		}
	}

	public Polynomial(HashMap<Integer, Integer> insertMap) {
		PolynomialMap = insertMap;
		polynomialString = this.toString();
	}
	
	
	public Polynomial divide(Polynomial p) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}

	public Polynomial remainder(Polynomial p) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}

	public final String toString() {
		String returnString = "";
		for (Integer i : PolynomialMap.keySet()) {
			returnString = returnString + " +" + PolynomialMap.get(i) + "X^" + i;
		}
		returnString = returnString.replaceAll("\\+" + "-", "-");
		
		if ((returnString.charAt(0) == ' ') && (returnString.charAt(1) == '+')) {
			returnString = returnString.substring(2);
		}
		
		returnString = returnString.replaceAll("\\+X\\^0", "+1");
        returnString = returnString.replaceAll("-X\\^0", "-1");
        returnString = returnString.replaceAll("X\\^0", "1");
        
        for (int k = 0; k < 10; k++) {
        	returnString = returnString.replaceAll("\\+0X\\^" + k,"");
        }
        for (int k = 0; k < 10; k++) {
        	returnString = returnString.replaceAll("\\-0X\\^" + k,"");
        }
        for (int k = 0; k < 10; k++) {
        	returnString = returnString.replaceAll("0X\\^" + k,"");
        }
        
        returnString = returnString.replaceAll(" ", "");
        
		return returnString;
	}

	@Override
	public Polynomial add(Polynomial q) {
		
		HashMap<Integer, Integer> answer = new HashMap<>();
		answer = this.PolynomialMap;
		HashMap<Integer, Integer> addMap = q.getPolynomialMap();
		for (Integer i: addMap.keySet()) {
			if (answer.containsKey(i)) {
				answer.put(i, addMap.get(i) + answer.get(i));
			}
			else {
				answer.put(i, addMap.get(i)); 
			}
		}
		this.PolynomialMap = new Polynomial(polynomialString).getPolynomialMap();
		return new Polynomial(answer);
	}

	@Override
	public Polynomial subtract(Polynomial q) {
		
		HashMap<Integer, Integer> answer = new HashMap<>();
		answer = this.PolynomialMap;
		HashMap<Integer, Integer> addMap = q.getPolynomialMap();
		for (Integer i: addMap.keySet()) {
			if (answer.containsKey(i)) {
				answer.put(i, addMap.get(i) - answer.get(i));
			}
			else {
				answer.put(i, -addMap.get(i)); 
			}
		}
		this.PolynomialMap = new Polynomial(polynomialString).getPolynomialMap();
		return new Polynomial(answer);
	}

	@Override
	public Polynomial multiply(Polynomial p) {
		HashMap<Integer,Integer> answer = new HashMap<>();
		for(Integer i: this.PolynomialMap.keySet()) {
			for(Integer j: p.PolynomialMap.keySet()) {
				int exp = j+i;
				int value = this.PolynomialMap.get(i) * p.PolynomialMap.get(j);
				if(answer.containsKey(exp)) {
					int add = value + answer.get(exp);
					answer.replace(exp, add);
				}else {
					answer.put(exp,value);	
				}
			}
		}
		return new Polynomial(answer);
	}

	public HashMap<Integer, Integer> getPolynomialMap() {
		return PolynomialMap;
	}
}
