class Solution {
    public boolean canTransform(String start, String end) {
        if(start.equals(end)) return true;
        if(!start.replace("X", "").equals(end.replace("X", "")))
            return false;
        int i = 0, j = 0;
        int m = start.length();
        int n = end.length();
        while (i < m || j < n){
            while (i < m && start.charAt(i) == 'X') i++;
            while (j < n && end.charAt(j) == 'X') j++;
            if(i == m && j == n) return true;
            if(i == m || j == n) return false;
            if(start.charAt(i) != end.charAt(j)) return false;
            if(start.charAt(i) == 'L' && j > i){
                return false;
            }
            if(start.charAt(i) == 'R' && j < i){
                return false;
            }
            i++;
            j++;
        }
        return true;
    }
}