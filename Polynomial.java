public class Polynomial {
	double [] coefficients;
	public Polynomial() {
		coefficients = new double[1];
		coefficients[0] = 0;
	}
	public Polynomial(double[] c1) {
		coefficients = c1.clone();
	}
	public Polynomial add(Polynomial p) {
		int i;
		boolean pGreater = p.coefficients.length > coefficients.length;
		int maxlength = Math.max(p.coefficients.length, coefficients.length);
		int minlength = Math.min(p.coefficients.length, coefficients.length);
		double [] newPoly = new double[maxlength];
		for(i=0; i<minlength; i++)
			newPoly[i] = coefficients[i] + p.coefficients[i];
		if(pGreater) {
			for(i=minlength; i<maxlength; i++)
				newPoly[i] = p.coefficients[i];
		}
		else {
			for(i=minlength; i<maxlength; i++)
				newPoly[i] = coefficients[i];
		}
		return new Polynomial(newPoly);
	}
	public double evaluate(double x) {
		double result = 0;
		for(int i=0; i<coefficients.length; i++)
			result += coefficients[i] * Math.pow(x, i);
		return result;
	}
	public boolean hasRoot(double x) {
		return (this.evaluate(x) == 0);
	}
}