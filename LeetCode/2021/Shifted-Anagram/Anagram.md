## Shifted String & Anagrams
### Default
For this type of problem, we usually use hashmap to group string, and use an `encoding` function to identify `similar` strings.

#### [LC] 49. Group Anagrams
https://leetcode.com/problems/group-anagrams/

The encoding function is "char-count#". Using `int[] dict = new int[26]` since it could have duplicate char.

#### [LC] 249. Group Shifted Strings
https://leetcode.com/problems/group-shifted-strings/

The idea is to use HashMap key to maintain distance of characters in a string. But the tricky part is the the string could be rotated. So we need to `+ 26` is the next char is smaller than previous char:  

```java
    private int getDistance(char c1, char c2) {
        int num1 = c1 - 'a';
        int num2 = c2 - 'a';
        //  w    y    a   --> x    z    b
        // 23   25    1       24   26   2
        //    2    2             2    2
        if (num2 < num1) {
            return num2 - num1 + 26;
        } else {
            return num2 - num1;
        }
    }
```

#### [LC] 438. Find All Anagrams in a String
https://leetcode.com/problems/find-all-anagrams-in-a-string/

We can use the same "encoding funtion" idea to check if they are anagrams, and while moving from left to right, we can using "sliding window" so that we don't need to recalculate every char, then we can use this magic array equals function!!!!

```java
Arrays.equals(pCount, sCount); // time complexity is O(n), when n is 26, it's O(26)=O(1)
```  

#### [LC] 242. Valid Anagram
https://leetcode.com/problems/valid-anagram/

Similar like `438. Find All Anagrams in a String`, using `new int[26]` to build two array, and then using the `Arrays.equals()` to compare.

For the follow-up question about handling Unicode char, we can use HashMap to record char to couner mapping. NOTE that we only need 1 HashMap, and we can increase count by traversing one string and then decrease count when traversing anohter.
