package org.ex1;

import java.math.BigInteger;


public class PrimeFactorization {

    public Tuple<BigInteger, BigInteger> getPrimeFactors(BigInteger n){
        BigInteger[] sqrtAndRemainder = n.sqrtAndRemainder();
        BigInteger a = sqrtAndRemainder[0];
        if(!sqrtAndRemainder[1].equals(BigInteger.ZERO)){
            a = a.add(BigInteger.ONE);
        }
        BigInteger b2 = a.multiply(a).subtract(n);
        while(!isSquare(b2)){
            a = a.add(BigInteger.ONE);
            b2 = a.pow(2).subtract(n);
        }
        BigInteger b = b2.sqrt();
        BigInteger f1 = a.subtract(b);
        BigInteger f2 = a.add(b);
        Tuple<BigInteger, BigInteger> result = new Tuple<BigInteger, BigInteger>(f1, f2);
        return result;
    }

    private boolean isSquare(BigInteger n){

        BigInteger squareRoot = n.sqrt();
        return (squareRoot.pow(2).equals(n));

    }
}
