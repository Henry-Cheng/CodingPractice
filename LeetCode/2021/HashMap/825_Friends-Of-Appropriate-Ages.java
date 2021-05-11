// https://leetcode.com/problems/friends-of-appropriate-ages/
class Solution {
    public int numFriendRequests(int[] ages) {
        //numFriendRequests_LC(ages);
        //System.out.println("===============");
        HashMap<Integer, Integer> countMap = new HashMap<>();
        for (int age: ages) {
            countMap.put(age, countMap.getOrDefault(age, 0) + 1);
        }
        int result = 0;
        for(Integer age1 : countMap.keySet()) {
            int count1 = countMap.get(age1);
            // for ppl with same age
            // NOTE: need to check eligibility even for the ppl with same age!
            if (count1 > 1 && canArequestB(age1, age1)) { 
                result += count1 * (count1 - 1); // An2 = n*(n-1)
                //System.out.println(age1 + " has " + count1 + " ppl, add up " + count1 * (count1 - 1));
            }
            // for ppl with different age --> age1 to age2 only
            for (Integer age2 : countMap.keySet()) {
                if (age2 != age1) {
                    int count2 = countMap.get(age2);
                    // check conditions
                    if (canArequestB(age1, age2)) {
                        result += count1 * count2;
                        //System.out.println(age1 + " to " + age2 + ", add up " + count1 * count2);
                    }
                }
            }
        }
        return result;
    }
    
    private boolean canArequestB(int age1, int age2) {
        return !(age2 <= 0.5 * age1 + 7 || age2 > age1 || (age2 > 100 && age1 < 100));
    }
    // LC solution
    public int numFriendRequests_LC(int[] ages) {
        int[] count = new int[121];
        for (int age: ages) count[age]++;

        int ans = 0;
        for (int ageA = 0; ageA <= 120; ageA++) {
            int countA = count[ageA];
            for (int ageB = 0; ageB <= 120; ageB++) {
                int countB = count[ageB];
                if (ageA * 0.5 + 7 >= ageB) continue;
                if (ageA < ageB) continue;
                if (ageA < 100 && 100 < ageB) continue;
                ans += countA * countB;
                if (countA > 0 && countB > 0 && ageA != ageB) {
                    System.out.println(ageA + " to " + ageB + ", add up " + countA * countB);
                }
                
                if (ageA == ageB) {
                    ans -= countA;
                    if (countA > 0) {
                        System.out.println(ageA + " equal to " + ageB + ", add once " + countA);
                    }
                    
                }
            }
        }

        return ans;
    }
}