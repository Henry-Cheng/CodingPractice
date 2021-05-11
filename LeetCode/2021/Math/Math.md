## Math
### Default

Why many questions requres the result to modulo `10^9 + 7` (aka`1e9+7`) ?

-  `10^9 + 7` can make any num inside `[0,10^9+7)` to do the addition without overflow integer, and doign the multiplication without overflow long.
- since  `10^9 + 7` is a prime number, we can find a unique number within `[0,10^9+7)` after moduloing it


#### [LC] 7. Reverse Integer
https://leetcode.com/problems/reverse-integer/

Similar like `224. Basic Calculator`, we can collect digits into new num by `sum = sum * 10 + digit`.

Option1: using long to store the final sum, since the reversed num may over MAX_VALUE

```java
long sum = 0;
while(x != 0) {
    int digit = x % 10; // sign is auto considered
    sum = sum * 10 + digit; // 
    x = x / 10;
}
```

Option2: using int to store the final sum, and check before doing `sum * 10 + digit` :  
- fistly check if `sum > Integer.MAX_VALUE / 10`, if yes then `sum * 10` must be overflow
- secondly check if `sum == Integer.MAX_VALUE / 10` and `digit > 7`, if yes then overflow, the reason is that the last number of Integer.MAX_VALUE is 7: `MAX=2147483647`
- if the num is negative, check if `digit < -8`, since `MIN=-2147483648`


#### [LC] 172. Factorial Trailing Zeroes
https://leetcode.com/problems/factorial-trailing-zeroes/

```java
    // option1: using BigInteger
    // option2: count factors of 5 and 2, then use min(# of 5, # of 2)
    // option3: only count 5, since 2 is always more than 5
    // option4: only count 5, and not traverse 1 to n, traverse at 5 interval
    // option5: count how many "n /= 5" exists, result in O(logn)
```

#### [LC] 273. Integer to English Words
https://leetcode.com/problems/integer-to-english-words/

Not very hard, just divide by billion/million/thousand, and then process less than 1000 situation.

#### [LC] 311. Sparse Matrix Multiplication
https://leetcode.com/problems/sparse-matrix-multiplication/

Generally time complexity is `O(m*n*k)`, but the followup is to handle super large matrix.
- one improvement is to record all-0 rows and all-0 col so that we can skip them
- another way is to use Strassen algorithm, to use some "plus" to replace "multiple", since multiple is more expensive 
  - https://sites.google.com/a/chaoskey.com/algorithm/02/03


#### [LC] 537. Complex Number Multiplication
https://leetcode.com/problems/complex-number-multiplication/

- split by "+" or "i" using `String[] x = a.split("\\+|i");`
- split by space using `String[] x = a.split("\\s+");`

#### [LC] 781. Rabbits in Forest
https://leetcode.com/problems/rabbits-in-forest/

Brain teasers.