import java.util.HashMap;
import java.util.Map;

public class FinancialForecasting {

    // ---- Approach 1: Plain Recursion ----
    // FV(n) = FV(n-1) * (1 + r), FV(0) = presentValue
    // Time: O(n), Space: O(n) recursion stack
    public static double futureValueRecursive(double presentValue, double growthRate, int years) {
        if (years == 0) return presentValue;           // base case
        return futureValueRecursive(presentValue, growthRate, years - 1) * (1 + growthRate);
    }

    // ---- Approach 2: Recursion with Memoization ----
    // Caches computed values to avoid recomputation in branched recursion.
    // Useful when the recursion branches (e.g., variable growth rates, Fibonacci-style).
    public static double futureValueMemoized(double presentValue, double growthRate,
                                             int years, Map<Integer, Double> memo) {
        if (years == 0) return presentValue;
        if (memo.containsKey(years)) return memo.get(years);

        double value = futureValueMemoized(presentValue, growthRate, years - 1, memo) * (1 + growthRate);
        memo.put(years, value);
        return value;
    }

    // ---- Approach 3: Iterative (Optimal) ----
    // Time: O(n), Space: O(1)
    public static double futureValueIterative(double presentValue, double growthRate, int years) {
        double value = presentValue;
        for (int i = 0; i < years; i++) {
            value *= (1 + growthRate);
        }
        return value;
    }

    // ---- Approach 4: Closed-form (Best) ----
    // FV = PV * (1 + r)^n. Time: O(log n) via Math.pow.
    public static double futureValueFormula(double presentValue, double growthRate, int years) {
        return presentValue * Math.pow(1 + growthRate, years);
    }

    // ---- Variable Growth Rates (year-wise) ----
    // Real-world scenario: growth rate differs each year.
    // Recursive with memoization.
    public static double futureValueVariableRate(double presentValue, double[] annualRates,
                                                 int year, Map<Integer, Double> memo) {
        if (year == 0) return presentValue;
        if (memo.containsKey(year)) return memo.get(year);

        double value = futureValueVariableRate(presentValue, annualRates, year - 1, memo)
                        * (1 + annualRates[year - 1]);
        memo.put(year, value);
        return value;
    }

    // ---- Main / Test ----
    public static void main(String[] args) {
        double presentValue = 100000;   // Rs. 1,00,000 invested today
        double growthRate = 0.08;       // 8% p.a.
        int years = 10;

        System.out.println("Present Value : Rs." + presentValue);
        System.out.println("Growth Rate   : " + (growthRate * 100) + "% p.a.");
        System.out.println("Years         : " + years);
        System.out.println();

        // 1. Plain recursion
        double fv1 = futureValueRecursive(presentValue, growthRate, years);
        System.out.printf("Recursive         : Rs.%.2f%n", fv1);

        // 2. Recursion with memoization
        double fv2 = futureValueMemoized(presentValue, growthRate, years, new HashMap<>());
        System.out.printf("Memoized          : Rs.%.2f%n", fv2);

        // 3. Iterative
        double fv3 = futureValueIterative(presentValue, growthRate, years);
        System.out.printf("Iterative         : Rs.%.2f%n", fv3);

        // 4. Closed-form formula
        double fv4 = futureValueFormula(presentValue, growthRate, years);
        System.out.printf("Closed-form       : Rs.%.2f%n", fv4);

        // 5. Variable growth rates (Y1..Y5)
        System.out.println("\n---- Variable Growth Rate Scenario ----");
        double[] rates = { 0.10, 0.08, 0.06, 0.12, 0.09 }; // Y1..Y5
        double fv5 = futureValueVariableRate(presentValue, rates, rates.length, new HashMap<>());
        System.out.printf("Variable-rate FV (5 yrs): Rs.%.2f%n", fv5);

        // Year-wise projection
        System.out.println("\n---- Year-wise Projection (Fixed 8%) ----");
        for (int y = 0; y <= years; y++) {
            System.out.printf("Year %2d : Rs.%.2f%n", y,
                    futureValueIterative(presentValue, growthRate, y));
        }
    }
}