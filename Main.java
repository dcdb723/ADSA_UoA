import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static String add(String num1, String num2, int base) {
        StringBuilder result = new StringBuilder();
        int maxLength = Math.max(num1.length(), num2.length());

        num1 = addZeros(num1, maxLength);
        num2 = addZeros(num2, maxLength);

        int carry = 0;
        for (int i = maxLength - 1; i >= 0; i--) {
            int digit1 = num1.charAt(i) - '0';
            int digit2 = num2.charAt(i) - '0';
            int sum = digit1 + digit2 + carry;
            carry = sum / base;
            result.insert(0, sum % base);
        }

        if (carry != 0) {
            result.insert(0, carry);
        }

        return result.toString();
    }

    private static String addZeros(String num, int length) {
        StringBuilder sb = new StringBuilder(num);
        while (sb.length() < length) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    public static String multiply(String num1, String num2, int base) {
        BigInteger bigint1 = new BigInteger(num1, base);
        BigInteger bigint2 = new BigInteger(num2, base);
        BigInteger product = karatsuba(bigint1, bigint2);
        return product.toString(base);
    }

    private static BigInteger karatsuba(BigInteger x, BigInteger y) {
        int n = Math.max(x.bitLength(), y.bitLength());
        if (n <= 2000) {
            return x.multiply(y);
        }
        n = (n / 2) + (n % 2);
        BigInteger xHigh = x.shiftRight(n);
        BigInteger xLow = x.subtract(xHigh.shiftLeft(n));
        BigInteger yHigh = y.shiftRight(n);
        BigInteger yLow = y.subtract(yHigh.shiftLeft(n));

        BigInteger a = karatsuba(xHigh, yHigh);
        BigInteger b = karatsuba(xLow.add(xHigh), yLow.add(yHigh));
        BigInteger c = karatsuba(xLow, yLow);

        return a.shiftLeft(2 * n).add(b.subtract(a).subtract(c)).shiftLeft(n).add(c);
    }

    public static String longDivision(String dividend, String divisor, int base) {
        BigInteger bigDividend = new BigInteger(dividend, base);
        BigInteger bigDivisor = new BigInteger(divisor, base);

        BigInteger quotient = BigInteger.ZERO;
        BigInteger remainder = BigInteger.ZERO;

        for (int i = 0; i < dividend.length(); i++) {
            int digit = Character.getNumericValue(dividend.charAt(i));
            BigInteger currentDigit = BigInteger.valueOf(digit);

            BigInteger temp = remainder.multiply(BigInteger.valueOf(base)).add(currentDigit);
            BigInteger q = temp.divide(bigDivisor);
            quotient = quotient.multiply(BigInteger.valueOf(base)).add(q);
            remainder = temp.mod(bigDivisor);
        }

        return quotient.toString(base);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter two integers and base in single line(separated by spaces):");
        String input = scanner.nextLine();
        String[] inputs = input.split(" ");
        String I1 = inputs[0];
        String I2 = inputs[1];
        int B = Integer.parseInt(inputs[2]);

        String sum = add(I1, I2, B);
        String product = multiply(I1, I2, B);
        String quotient = longDivision(I1, I2, B);

        System.out.println(sum + " " + product + " " + quotient);

        scanner.close();
    }
}
