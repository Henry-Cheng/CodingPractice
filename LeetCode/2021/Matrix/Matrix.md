# Matrix

These questions are all about traverse matrix diagonal etc.

### Default


#### [LC][Easy] 766. Toeplitz Matrix
https://leetcode.com/problems/toeplitz-matrix/

When `i = matrix.length-1`, move i up until `i==0`, using `check(matrix,i,j)` to validate if all `(i+1, j+1)` are the same.

Then move j to right until `j=matrix[0].length-1`, using `check(matrix,i,j)` to validate if all `(i+1, j+1)` are the same.

#### [LC][Medium] 498. Diagonal Traverse
https://leetcode.com/problems/diagonal-traverse/

This type of question is hard to think about the simulation: 

```java
        00 01 02 03 04
        10 11 12 13 14 
        20 21 22 23 24
        30 31 32 33 34  
```
We can have the following logic:  

```java
        while(true) {
            // first row
            if (pos[0] == 0) {
                // if can move right
                if (canMoveRight) {
                	move("right");
                	move("left-diagonal");
                } else {
                	move("down");
                	move("left-diagonal");
                }
            }
            // last row
            if (pos[0] == matrix.length - 1) {
                move("right");
                move("right-diagnoal");
            }
            // first col
            if (pos[1] == 0) { // try move down first
                // if can move down
                if (canMoveDown()) {
                	move("down");
                	move("right-diagonal");
                } else {
                	move("right");
                	move("right-diagonal"); 
                }
            } 
            // last col
            if (pos[1] == matrix[0].length -1) {
            	move("down");
            	move("left-diagonal"); 
            }

            if(count[0] == matrix.length * matrix[0].length) {
                break;
            }
        }
```
