//https://leetcode.com/problems/design-browser-history/
class BrowserHistory {

    ArrayList<String> history;
    int current = 0;
    // could have duplicate
    //HashMap<String, List<Integer>> urlToIndexList = new HashMap<>();
    
    public BrowserHistory(String homepage) {
        history = new ArrayList<>();
        history.add(homepage);
        current++;
    }
    
    // even for existing pages, append to current+1
    public void visit(String url) {
        if (current < history.size() - 1) { // not at the end
            history.subList(current+1, history.size()).clear();
        } 
        history.add(url);
        current = history.size() - 1;
    }
    
    public String back(int steps) {
        current = Math.max(current - steps, 0);
        return history.get(current);
    }
    
    public String forward(int steps) {
        current = Math.min(current + steps, history.size() - 1);
        return history.get(current);
    }
}

/**
 * Your BrowserHistory object will be instantiated and called as such:
 * BrowserHistory obj = new BrowserHistory(homepage);
 * obj.visit(url);
 * String param_2 = obj.back(steps);
 * String param_3 = obj.forward(steps);
 */