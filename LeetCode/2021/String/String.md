## String
### Default

#### [LC] 65. Valid Number
https://leetcode.com/problems/valid-number/

NOTE for `string.split()`
- when spliting special characters like `.`, need to escape it like `string.split(\\.)`
- when only right-hand side has characters, result array is of size 2
  - `String s = ".1"; String[] arr = s.split("\\.");`
  - `arr` length is 2
  - `arr[0] == ""; arr[1] == "1"`
- when only left-hand side has characters, result array is of size 1
  - `String s = "1."; String[] arr = s.split("\\.");`
  - `arr` length is 1
  - `arr[0] == "1";`
- when nothing at left-hand side and right-hand side, result array is of size 0
  - `String s = "."; String[] arr = s.split("\\.");`
  - `arr` length is 0
- we can also force `split()` to return 2-element array by `string.split(delimiter, limit)`
  - `String s = "."; String[] arr = s.split("\\.", 2);`
  - `arr` length is 2 and both elements are empty
  - `arr[0] == ""; arr[1] == ""`

#### [LC] 43. Multiply Strings
https://leetcode.com/problems/multiply-strings/

- use `stringBuilder.insert(index, char)` to append character to the front
- be careful when encounterting 0, we can use shortcut

#### [LC] 67. Add Binary
https://leetcode.com/problems/add-binary/

- For String, StringBuffer, and StringBuilder, charAt() is a constant-time operation O(1).
- For StringBuffer and StringBuilder, deleteCharAt(), insert(index,char) and reverse() are linear-time operation O(n).


#### [LC][Medium] 151. Reverse Words in a String
https://leetcode.com/problems/reverse-words-in-a-string/

This is marked as medium problem, but actually easy.  
Just split the string by space, then collect from right to left.  
Finally return `String.join(" ", list);`.

#### [LC] 408. Valid Word Abbreviation
https://leetcode.com/problems/valid-word-abbreviation/

Using two pointers to point to original word and abbr, then move both pointers in different scenarios: isLetter() or isDigit(). Finally if the two pointers are not at the bottom of corresponding string, it's invalid so that we return false;

Be careful:
- the abbr could have multi-digit count, e.g. `a12b` means there are 12 characters after `a`
- there is a special case that the abbr may have adjacent digit or invalid digits like `a01b` where `01` is invalid.


#### [LC] 415. Add Strings
https://leetcode.com/problems/add-strings/

- Return the result by `stringBuilder.reverse().toString()`

#### [LC] 616. Add Bold Tag in String
https://leetcode.com/problems/add-bold-tag-in-string/

It is the same as `758. Bold Words in String `

#### [LC] 758. Bold Words in String  
https://leetcode.com/problems/bold-words-in-string/


It is a combination of problem "find matched substring" and problem "merge interval".
1. solution 1: find and store all label pairs and do interval merge
  1. define a new object Pos to store index and left-or-right flag
    1. NOTE: cannot use TreeMap since pairs could share index 
  2. find all pairs of labels by traversing the string -- O(MNK) where M is # of characters in string, N is # of dict words and K is average size of dict words
    1. can use `int foundIndex = s.indexOf(key, index);`, where index is the starting position in string
  3. sort the list of Pos object by index
  4. do interval merge by stack
  5. add label to string by using `stringbuilder.insert(offset, "<br>")`
2. solution 2: define a boolean array to record whether character is in dict
  1. use `s.substring(i, i + dictWord.length()).equals(dictWord)`

NOTE:  
1. The complexity of Java's implementation of indexOf is O(m*n) where n and m are the length of the search string and pattern respectively.
2. The insert operation on a StringBuffer is O(n). This is because it must shift up to n characters out of the way to make room for the element to be inserted.
3. A faster way to match substring is to use trie, or using KMP algorithm to reach O(n) (which is too hard to remember during interview).
  1. https://www.tutorialspoint.com/Knuth-Morris-Pratt-Algorithm#:~:text=Knuth%20Morris%20Pratt%20(KMP)%20is,KMP%20is%20O(n).


Way to compare object:
```java
        Collections.sort(positions, new Comparator<Pos>() {
            @Override
            public int compare(Pos p1, Pos p2) {
                if (p1.index > p2.index) {
                    return 1; // p2 before p1
                } else if (p1.index == p2.index) {
                    if (p1.isLeft) {
                        return -1; // p1 before p2
                    } else {
                        return 1; // p2 before p1
                    }
                } else {
                    return -1; // p1 before p2
                }
            }
        });
```

#### [LC][Medium] 1233. Remove Sub-Folders from the Filesystem
https://leetcode.com/problems/remove-sub-folders-from-the-filesystem/

This problem is simple, just sort the string in ascending order, then check if the next one is a substring of previous one.  
If the following 2 conditions satisfies, it is a subfolder:  

1. current.startsWith(prefix)
2. current.charAt(prefix.length()) == '/'

Be careful on condition #2, since it is a corner case like this:  
```
/a/b --> a/bbb
```
