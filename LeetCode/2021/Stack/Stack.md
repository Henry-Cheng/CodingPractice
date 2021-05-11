## Stack
### Default
#### [LC] 20. Valid Parentheses

https://leetcode.com/problems/valid-parentheses/

#### [LC] 224. Basic Calculator
https://leetcode.com/problems/basic-calculator/

A good blog about it: http://www.noteanddata.com/leetcode-224-Basic-Calculator-java-solution-note.html

1st, how to to collect continuous digits in one run?

```java
// e.g. 125
while(Character.isDigit(c)) {
  currentNum = currentNum * 10 + c - '0';
}
```

2nd, how to track the sign? Sign would be `1` by default, and would change to be `-1` when seeing `-`

```java
sign = (ch == '-') ? -1 : 1; // set sign 
```

3rd, if without parenthesis, how can we calculate it? -- when it's digits, collect them as num, while it's not digits, add up the num to result with sign

```java
// e.g. 1 + 2 - 3
int currentResult = 0;
int currentNum = 0;
int currentSign = 1; // by default is positive
for (char c : s.toCharArray()) {
    if (Character.isDigit(c)) { // always start with digits!!!
        currentNum = currentNum*10 + c - '0'; 
    } else { // if not digits, then we can addup currentResult
        currentResult = currentResult + currentNum * currentSign;
        currentNum = 0; // reset currentNum!!!
        if (c == '+' || c == '-') {
          currentSign = (c == '+' ? 1 : -1);
        }
    }
}

```

4th, how to handle parenthesis? -- we only push/pop when seeing parenthses 
  - when seeing `(`, we push the currentResult and sign (the sign for next parenthesis) to stack, and reset currentResult and sign 
  - when seeing `)`, we pop the previous sign and previous num



#### [LC] 227 Basic Calculator II  
https://leetcode.com/problems/basic-calculator-ii/


Similar like `224. Basic Calculator`, we need to recrod `sign` and `currentNum`.  
The difference is that, instead of pushing numbers when seeing left bracket, we need to push stack when seeing `*` or `/`, and record the current operation as `*` or `/`, then next time we collect enough `currentNum`, we can check the operation to decide what to do
- if operation is `+`, simply push the `currentNum` to stack
- if operation is `*`, pop the stack and times with `currentNum`, then push the result to stack
- if operation is `/`, pop the stack and dividended by the `currentNum`, then push result to stack
- finally we add up every num in stack

Some trick we can play:   
1. how to ignore space? --> do not include space in the if conditions at all  

```java
if (Character.isDigit(c)) { 
    currentNum = 10 * currentNum + c - '0';
} 
if (i == arr.length - 1 || c == '+' || c == '-' || c == '*' || c =='/') {
   // do something
}
```

2. how to cover the final `currentNum` when reaching end of the sting?
See the above code snippet, we include the `i == arr.length - 1` as condition together with other symbols, it helps to handle the last `currentNum`

3. how to save space complexity?  
Actaully we don't need a stack, we just need a `prevNum` variable to record previous number for `*` and `+`, and another variable `result` to add up all `+` and `+`, then finally return `result` 

#### [LC] 772. Basic Calculator III
https://leetcode.com/problems/basic-calculator-iii/

This is a mix of `224. Basic Calculator` and `227 Basic Calculator II`, since we have both `*` `/` and `(``)`, we cannot use a simple one round to solve it since brackets and `*/` have the same priority.

One idea is that, if not considering brackets, we can use the method in `227 Basic Calculator II`, and whenever seeing bracket, we put whole brackets into recursive function and treat the result at `currentNum`.

Be careful to find the bracketed substring, since it may have two scenatios like `1+(2+(3+4))` or `(1+2)+(3+4)`.  

Here is the code with `O(n^2)` since we need to search for `)` for every `(` with at most `n-1` steps, so `n-1 + n-2 + n-3 + ... is n^2` 

```java
  if (c == '(') {
      int j = i + 1;
      int leftCount = 1; // count "("
      int rightCount = 0; // count ")"
      while(rightCount != leftCount) {
          if (s.charAt(j) == '(') {
              leftCount++;
          } else if (s.charAt(j) == ')') {
              rightCount++;
          }
          j++;
      }
      // j-1 is the ")"
      currentNum = calculate(s.substring(i+1,j-1));
      i = j-1; // move i to ")"
  }
```

#### [LC] 71. Simplify Path
https://leetcode.com/problems/simplify-path/

Similar like `224. Basic Calculator`.

Using StringBuilder currentStr (like the currentNum in `224. Basic Calculator`) to store file/dir name at each level, whenever seeing "/", check if we need to push to stack or pop from stack:

e.g.

```java
        e.g. /a/./b/../../c//
        -->
        a       .           b         ..    ..      c      <empty>
        push   do nothing   push      pop   pop     push    do nothing
        -->
        finall append reversed string in stack to stringbuilder and reverse all of them as output
```

Be careful:  
1. string cannot do `reverse()`, only StringBuilder can do `reverse()`
2. `stringbuilder.insert(0, str)` is `O(N)` since it needs to shift array, but `append()` is `O(1)`
3. to make the sequence of final string, we can store `reverse()` dir name in stack, and finally `reverse()` whole string as output
4. `StringBuilder` is more efficient than `StringBuffer`, but in multi-thread scenario only `StringBuffer` is thread-safe

#### [LC] 844. Backspace String Compare
https://leetcode.com/problems/backspace-string-compare/

- Option1: Stack
  - Similar like `71. Simplify Path`, using two stack to store final string of A and B, whenever seeing "#" we pop from stack, otherwise push.
- Option2: Two pointers
  - Traversing from end to start of both string, if seeing "#" then we jump to the previous character

#### [LC] 636. Exclusive Time of Functions
https://leetcode.com/problems/exclusive-time-of-functions/

Treat the problem like `224. Basic Calculator`, and build the stack element by `List<Integer>` like this:  

- list.get(0) --> process id --> actually it's not required, since process ends and pop() must be a pair
- list.get(1) --> start timestamp
- list.get(2) --> durationPendingRemove --> whenever an element being pop(), need to increment the `duratiothe nPendingRemove` of next top element in stack

```
        /** 
             ________________
            |   ___________  |
            |  |   _    _  | |
            |  |  | |  | | | |
            0  1  3 4  5 6 7 8
        **/
```

#### [LC] 921. Minimum Add to Make Parentheses Valid
https://leetcode.com/problems/minimum-add-to-make-parentheses-valid/

Similar idea like to use a stack to validate parenthesis, whenever see right-bracket we pop-up.  

But actually we don't need stack, we just need a leftCounter to do `leftCounter++` when seeing `(`, and do `leftCounter--` when seeing `)`.  
Also before doing the `leftCounter--`, we need to check if `leftCounter==0`, if yes then we can do `result++` which means we need to add a bracket here.


#### [LC] 1249. Minimum Remove to Make Valid Parentheses
https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/

Using stack to check valid parenthese pairs, and using a hashset to record valid parentheses index, then use another for loop and StringBuilder to build the new string.


#### [LC] 1047. Remove All Adjacent Duplicates In String
https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/

Using stack to check `stack.peek()` and `currentChar`, if equals then `stack.pop()`, otherwise `stack.push()`.  Finally pop all character in stack into stringbuilder and reverse it.


