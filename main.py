def add(num1, num2, base):
    carry = 0
    result = ''
    max_len = max(len(num1), len(num2))
    num1 = num1.zfill(max_len)
    num2 = num2.zfill(max_len)
    
    for i in range(max_len):
        digit_sum = carry
        digit_sum += int(num1[-(i+1)], base) + int(num2[-(i+1)], base)
        carry, digit = divmod(digit_sum, base)
        result = str(digit) + result
    
    if carry > 0:
        result = str(carry) + result
    
    return result.lstrip('0') or '0'

def multiply(num1, num2, base):
    product = [0] * (len(num1) + len(num2))
    
    for i in range(len(num1)):
        for j in range(len(num2)):
            product[i+j+1] += int(num1[i], base) * int(num2[j], base)
    
    carry = 0
    for i in range(len(product)-1, -1, -1):
        total = product[i] + carry
        carry, product[i] = divmod(total, base)
    
    result = ''.join([str(digit) for digit in product]).lstrip('0')
    return result if result else '0'

def divide(num1, num2, base):
    dividend = int(num1, base)
    divisor = int(num2, base)
    quotient = dividend // divisor
    
    result = ''
    while quotient > 0:
        quotient, remainder = divmod(quotient, base)
        result = str(remainder) + result
    
    return result.lstrip('0') or '0'

def main():
    num1, num2, base = input().split()
    
    sum_result = add(num1, num2, int(base))
    product_result = multiply(num1, num2, int(base))
    division_result = divide(num1, num2, int(base))
    
    print(sum_result, product_result, division_result)

if __name__ == "__main__":
    main()
