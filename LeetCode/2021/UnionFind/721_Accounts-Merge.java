// https://leetcode.com/problems/accounts-merge/submissions/
class Solution {
    
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        DJS djs = new DJS(accounts);
        HashMap<String, String> emailToName = new HashMap<>();
        for (List<String> account : accounts) {
            for (int i = 1; i < account.size(); i++) { // for each email
                emailToName.put(account.get(i), account.get(0)); 
                if (i > 1) { // only when account has more than 2 emails
                    djs.union(account.get(i - 1), account.get(i));
                }
            }
        }
        // now djs has a forest, traverse the accounts again to get "parentEmail to email list mapping"
        HashMap<String, Set<String>> resultMap = new HashMap<>();
        for (List<String> account : accounts) {
            for (int i = 1; i < account.size(); i++) { // for each email
                String parent = djs.find(account.get(i));
                Set<String> set = resultMap.getOrDefault(parent, new HashSet<>());
                set.add(account.get(i));
                resultMap.put(parent, set);
            }
        }
        // convert "parentEmail to email list mapping" to final result with name
        return resultMap.entrySet().stream().map(e->{
            LinkedList<String> l = new LinkedList<>(e.getValue());
            Collections.sort(l); // sort email
            l.addFirst(emailToName.get(e.getKey())); // put the name to front of list
            return l;
        }).collect(Collectors.toList());
    }

    // parent could be HashMap<Node, Node>, Node is a node in graph
    private static class DJS {
        public HashMap<String, String> parent = new HashMap<>();
        public HashMap<String, Integer> rank = new HashMap<>();
        public int disjointSize = 0;
        public DJS(List<List<String>> accounts) {
            for (List<String> account : accounts) {
                for (int i = 1; i < account.size(); i++) {
                    String email = account.get(i);
                    parent.put(email, email); // initialize parent
                    rank.put(email, 0); // initialize rank
                }
            }
            disjointSize = parent.size(); // initialize disjointSize
        }
        public String find(String x) {
            if (parent.get(x).equals(x)) {
                return x; // find parent email
            } else {
                parent.put(x, find(parent.get(x))); // compression
                return parent.get(x);
            }
        }
        public boolean union(String x, String y) {
            String parentX = find(x);
            String parentY = find(y);
            //System.out.println("union " + x + "-->" + parentX + ", " + y + "-->" + parentY);
            if (parentX.equals(parentY)) {
                return true; // already unioned
            } else {
                if (rank.get(parentX) < rank.get(parentY)) {
                    parent.put(parentX, parentY);
                    //System.out.println("x<y, parent[" + parentX + "] = " + parentY);
                } else if (rank.get(parentX) > rank.get(parentY)) {
                    parent.put(parentY, parentX);
                    //System.out.println("x>y, parent[" + parentY + "] = " + parentX);
                } else {
                    parent.put(parentY, parentX);
                    //System.out.println("x=y, parent[" + parentY + "] = " + parentX);
                    rank.put(parentX, rank.get(parentX) + 1);
                    //System.out.println("rank[" + parentX + "] = " + rank.get(parentX));
                }
                return false;
            }
        }
    }
    
}