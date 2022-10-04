import java.util.*;
import java.io.*;

public class Polynomial {
	double [] coefficients;
	int [] exponents;
	public Polynomial() {
		coefficients = null;
		exponents = null;
	}
	public Polynomial(double[] c1, int[] powers) {
		coefficients = c1.clone();
		exponents = powers.clone();
	}
	public Polynomial(File f) {
		try {
			Scanner reader = new Scanner(f);
			String poly = reader.nextLine();
			//Split the terms around the sign of the number
			String [] terms = poly.split("(?=-|\\+)");
			exponents = new int[terms.length];
			coefficients = new double[terms.length];
			//Populate the new coefficients and exponents array
			for(int i=0; i<terms.length; i++) {
				if(terms[i].contains("x")) {
					exponents[i] = Integer.parseInt(terms[i].split("x")[1]);
					coefficients[i] = Double.parseDouble(terms[i].split("x")[0]);
				}
				else {
					exponents[i] = 0;
					coefficients[i] = Double.parseDouble(terms[i]);
				}
			}
			reader.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public int index(int [] arr, int key) {
		// Return index of key in arr. If not found, return -1
		for(int i=0; i<arr.length; i++) {
			if(arr[i] == key)
				return i;
		}
		return -1;
	}
	public int getLength(double [] coef, int [] exp) {
		int len = 1;
		for(int i=0; i<exp.length; i++) {
			if(i > 0 && coef[i] != 0)
				len++;
		}
		return len;
	}
	public Polynomial add(Polynomial p) {
		if(exponents == null && p.exponents == null)
			return new Polynomial();
		else if(exponents == null)
			return new Polynomial(p.coefficients, p.exponents);
		else if(p.exponents == null)
			return new Polynomial(coefficients, exponents);
		// Find length of new exponents array
		int length = 0;
		int l1 = exponents.length;
		int l2 = p.exponents.length;
		for(int j=0; j<l1; j++) {
			if(exponents[j] > length)
				length = exponents[j]; //length becomes the highest degree
		}
		for(int j=0; j<l2; j++) {
			if(p.exponents[j] > length)
				length = p.exponents[j]; // length becomes the highets degree
		}
		length += 1; // To account for the constant term
		int [] newExp = new int[length];
		double [] newCoef = new double[length];
		// Populate the new exponents array
		for(int i=0; i<length; i++) {
			newExp[i] = i; // Index represents the degree
		}
		// Populate the new coefficients array
		for(int i=0; i<l1; i++) {
			newCoef[exponents[i]] += coefficients[i];
		}
		for(int i=0; i<l2; i++) {
			newCoef[p.exponents[i]] += p.coefficients[i];
		}
		// Delete zero terms
		int actualLength = getLength(newCoef, newExp); // Number of terms
		double [] finalCoef = new double[actualLength];
		int [] finalExp = new int[actualLength];
		int idx = 1; // Index tracker for the new arrays
		finalCoef[0] = newCoef[0]; // First term will always be the constant term
		for(int i=0; i<length; i++) {
			// If not zero term, copy terms
			if(i > 0 && newCoef[i] != 0) {
				finalCoef[idx] = newCoef[i];
				finalExp[idx] = newExp[i];
				idx++;
			}
		}
		return new Polynomial(finalCoef, finalExp);
	}
	public Polynomial multiply(Polynomial p) {
		Polynomial results = new Polynomial();
		for(int i=0; i<exponents.length; i++) {
			double [] tempCoef = new double[p.exponents.length];
			int [] tempExp = new int[p.exponents.length];
			for(int j=0; j<p.exponents.length; j++) {
				tempCoef[j] = coefficients[i] * p.coefficients[j];
				tempExp[j] = exponents[i] + p.exponents[j];
			}
			results = results.add(new Polynomial(tempCoef, tempExp));
		}
		return results;
	}
	public double evaluate(double x) {
		double result = 0;
		for(int i=0; i<exponents.length; i++)
			result += coefficients[i] * Math.pow(x, exponents[i]);
		return result;
	}
	public boolean hasRoot(double x) {
		return (this.evaluate(x) == 0);
	}
	public void saveToFile(String fileName) {
		try {
			File f = new File(fileName);
			if(f.createNewFile()) {
				System.out.println("File " + fileName + " was created as it did not exist.");
			}
			FileWriter myWriter = new FileWriter(fileName);
			String str = "";
			for(int i=0; i<exponents.length; i++) {
				if(i == 0)
					str += coefficients[0];
				if(i > 0 && coefficients[i] != 0) {
					if(i != 0 && coefficients[i] > 0)
						str += "+";
					if(exponents[i] == 0)
						str += Double.toString(coefficients[i]);
					else {
						str += Double.toString(coefficients[i]) + "x" + Integer.toString(exponents[i]);
					}
				}
			}
			myWriter.write(str);
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}