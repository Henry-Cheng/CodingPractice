## HashMap
### Default
#### [LC] 1. Two Sum
Why "two sum" is not a two-pointers problem? To use two-pointers, we need to know when should we move the pointer, and for problem like "two sum", we cannot know it without sorting the array. But sorting would result in at least O(nlgn) time complexity. Considering by using hashmap we can already achieve O(n) time complexity, there is no need to use two-pointer here.


#### [LC 13. Roman to Integer
https://leetcode.com/problems/roman-to-integer/

Using LinkedHashMap to record all possible Roman characters (since the order matters, we use LinkedHashMap here). Then for each key we do `s.startsWith(key)`, and then shorten the string by substring.

e.g.

```java
        map.put("III", 3);
        map.put("II", 2);
        map.put("IV", 4);
        map.put("IX", 9);
        map.put("XL", 40);
        map.put("XC", 90);
        map.put("CD", 400);
        map.put("CM", 900);
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);
```

#### [LC] 76. Minimum Window Substring
https://leetcode.com/problems/minimum-window-substring/

This is a smart use of HashMap and sliding window!!

If we do brute force, we will need to maintain two hashMap for dict string and processing string:  
```java
HashMap<Character, Integer> dictCharToCountMap;
HashMap<Character, Integer> currentCharToCountMap;
```
Then every time our sliding window moves, we will need to compare the two hashMap using for loop, which would result in `O(M*N)` time complexity.

But there is a smart way to compare it, by using a counter called `uniqueCharEnough`, and we increment it when one of the unique char is completed (which means we have found enough count for this unique char)
```java
if (currentCharToCountMap.get(c).equals(dictCharToCountMap.get(c))) {
    uniqueCharEnough++;
}
```
If `uniqueCharEnough == dictCharToCountMap.size()`, it means we have found a substring, then we record the substring in a globale variable.  

How do we decrement the `uniqueCharEnough`? 
It also happens when we find `uniqueCharEnough == dictCharToCountMap.size()`, since at this moment we can move left pointer to right.  
We will check whether moving left pointer would impact the `uniqueCharEnough` or not --> looking at the count of the char is less than the count in `dictCharToCountMap`
```java
while(right < s.length()) {
    ...
    ...
    while(uniqueCharEnough == dict.size()) {// all unique char has been matched
        // update minStr and minLength
        ...

        // move left pointer
        if (impacted) {
            //System.out.println("remove " + originalLeft);
            uniqueCharEnough--;
        }
        left++;
    }
    right++;
}

```

NOTE:  
1. when using two pointers, there is an inner while loop to continue moving left pointer to right, it ends until we cannot maintain enough `uniqueCharEnough`
2. when comparing the count of 2 maps, cannot using `==` since it is `Integer`!!!! must use `equals()`  here


#### [LC] 128. Longest Consecutive Sequence
https://leetcode.com/problems/longest-consecutive-sequence/

HashSet.

#### [LC] 138. Copy List with Random Pointer
https://leetcode.com/problems/copy-list-with-random-pointer/

HashMap to store original node to new node mapping.

#### [LC] 266. Palindrome Permutation
https://leetcode.com/problems/palindrome-permutation/

Using hashmap to store number of characters in string, then check if odd occrurences appeals only once. 
Or using set to only store odd occurence number, and finally check how many odd num inside.

#### [LC] 387 First Unique Character in a String 
https://leetcode.com/problems/first-unique-character-in-a-string/

Easy question, 1st round to go through all characters and count each of them, 2nd round to find the 1st one with count 1;

#### [LC] 788. Rotated Digits
https://leetcode.com/problems/rotated-digits/

- option1: brute force to calculate each digit to check if it is `2/5/6/9` or `0/1/8`， the time complexity is `O(nlogn)` since we traverse n numbers and for each num we do `logN` times of checking on each digit.  
The code to check each digit:

```java
for (int j = 0; j < 5; j++) {
    int digit = i % 10;
    i = i / 10;
}
```

- option2: DP

#### [LC] 825. Friends Of Appropriate Ages
https://leetcode.com/problems/friends-of-appropriate-ages/

The idea is very simple, we have to do `An2 = n*(n-1)` checking for any two ppl.  
But considering there could be duplicate ages, we can using hashMap to count the same age, and do the eligibility checking for each distinct age pair, and then addup `count1*count2`.  

Be careful for the ppl with same age, we also need to do eligbility checking!!! Then addup `count*(count-1)` is the same age is eligible to send friend request.

#### [LC] 953. Verifying an Alien Dictionary
https://leetcode.com/problems/verifying-an-alien-dictionary/

Using hashmap to store the dictionary.

#### [LC] 1152. Analyze User Website Visit Pattern
https://leetcode.com/problems/analyze-user-website-visit-pattern/

For each user, brute force find all 3-sequence and using hashmap to record it, the time compleity is `O(N^3)` since we do `CN3 = n * (n-1) * (n-2)`.


#### [LC] 1606. Find Servers That Handled Most Number of Requests

https://leetcode.com/problems/find-servers-that-handled-most-number-of-requests/

per https://leetcode.com/problems/find-servers-that-handled-most-number-of-requests/discuss/1054308/Java-Easy-to-understand-solution-with-explanation, we can use `TreeSet` and `TreeMap`