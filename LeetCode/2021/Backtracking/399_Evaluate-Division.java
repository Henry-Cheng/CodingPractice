// https://leetcode.com/problems/evaluate-division/
class Solution {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // given two nodes, we are asked to check if there exists a path between them. If so, we should return the cumulative products along the path as the result
        /**
             s1   s2  s3
          s1 1    2   3
          s2 1/2  1   x
          s3 1/3  x   1
        **/
        //s2/s1=1/2
        //s1/s3=3
        //s2/s3=1/2*3= 3/2
        // present the graph by nested hashmap
        HashMap<String,HashMap<String,Double>> map = new HashMap<>();
        for (int i = 0; i < equations.size(); i++) {
            List<String> pair = equations.get(i);
            String dividenA = pair.get(0);
            String dividenB = pair.get(1);
            double quotient = values[i];
            // a/b
            HashMap<String,Double> divisorMapA = map.get(dividenA);
            if (divisorMapA == null) {
                divisorMapA = new HashMap<>();
            }
            divisorMapA.put(dividenB, quotient);
            divisorMapA.put(dividenA, 1.0);
            map.put(dividenA, divisorMapA);
            // b/a
            HashMap<String,Double> divisorMapB = map.get(dividenB);
            if (divisorMapB == null) {
                divisorMapB = new HashMap<>();
            }
            divisorMapB.put(dividenA, 1/quotient);
            divisorMapB.put(dividenB, 1.0);
            map.put(dividenB, divisorMapB);
        }
        // for each query, check if we can find a path in graph
        double[] result = new double[queries.size()];
        for (int j = 0; j < queries.size(); j++) {
            String from = queries.get(j).get(0);
            String to = queries.get(j).get(1);
            if (!map.containsKey(from) || !map.containsKey(to)) {
                //System.out.println("invalid query");
                result[j] = -1;
            } else {
                result[j] = dfs(map, from, to, 1.0, new HashSet<>()) ;
            }
        }
        return result;
    }
    
    protected double dfs(HashMap<String,HashMap<String,Double>> map, String from, String to, double product, HashSet<String> visited) {
        if (map.get(from).get(to) != null) {
            return product * map.get(from).get(to);
        }
        visited.add(from);
        for (String key : map.keySet()) {
            if (!visited.contains(key)) {
                //System.out.println("from " + from + " key " + key);
                if (map.get(from).get(key) != null) {
                    visited.add(key);
                    double tmpResult =  product * map.get(from).get(key) * dfs(map, key, to, product,visited);
                    if (tmpResult > 0) {
                        return tmpResult;
                    } else { // backtrack
                        visited.remove(key);
                    }
                }
            }
        }
        return -1;
    }
}