package ex1.android.scu.edu.loancalculator;
import java.lang.Math;

/**
 * Created by Wenyi Fei on 4/14/15.
 */

public class LoanFormular {
    // To-Do try using BigDecimal
    public static double calculateLoan(double borrowedAmount, double interestRate, int term, boolean isTaxIncluded) {
        double monthlyInterest = interestRate / 1200;
        //System.out.println("monthlyInterest = " + monthlyInterest);
        double monthlyTerm = term * 12;
        double result = 0.0;
        if (interestRate != 0) {
            result = borrowedAmount * monthlyInterest / (1 - Math.pow( 1 + monthlyInterest, - monthlyTerm));
            if (isTaxIncluded) {
                result += borrowedAmount / 1000;
            }
            return result;
        } else {
              result = borrowedAmount / monthlyTerm;
            if (isTaxIncluded) {
                result += borrowedAmount / 1000;
            }
        }
        return result;
    }
}
