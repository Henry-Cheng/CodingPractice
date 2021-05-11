// https://leetcode.com/problems/stickers-to-spell-word/
class Solution {

    private int result = Integer.MAX_VALUE;
    public int minStickers(String[] stickers, String target) {
        // 1. build dit from stickers
        int[][] dicts = new int[stickers.length][26];
        for (int i = 0; i < stickers.length; i++) {
            for (char c : stickers[i].toCharArray()) {
                dicts[i][c-'a']++;
            }
        }
        // 2. recursive to find min stickers 
        HashMap<String, Integer> memory = new HashMap<>();
        return recursive(dicts, target, memory);
    }
    
    private int recursive(int[][] dicts, String target, HashMap<String, Integer> memory) {
        if (memory.containsKey(target)) {
            return memory.get(target);
        }
        int result = Integer.MAX_VALUE;
        // try all available stickers
        for (int i = 0; i < dicts.length; i++) {
            int[] sticker = dicts[i];
            if (sticker[target.charAt(0)-'a'] > 0) { // if the sticker can cover first character of target, then we can use it
                // deep copy the sticker to stickerBackup, so that dict can be unmodified
                int[] stickerBackup = new int[26];
                for (int j = 0; j < 26; j++) {
                    stickerBackup[j] = sticker[j];
                }
                StringBuilder restSb = new StringBuilder();
                // use all usable characters in this sticker
                for (char c : target.toCharArray()) {
                    if (stickerBackup[c - 'a'] > 0) {
                        stickerBackup[c - 'a']--; 
                    } else {
                        restSb.append(c);
                    }
                }
                String restStr = restSb.toString();
                if (restStr.length() == 0) {
                    memory.put(target,  1); // at least use 1 sticker
                    return 1;
                } else {
                    int minCount = recursive(dicts, restStr.toString(), memory);
                    if (minCount == -1) {
                        memory.put(target, -1);
                        return -1;
                    }
                    result = Math.min(result, minCount + 1);
                }
            }
        }
        int finalResult = result == Integer.MAX_VALUE ? -1 : result;
        memory.put(target, finalResult);
        return finalResult;
    }
    
    private void printArray(int[] dicts) {
        for (int num : dicts) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
    
    public int minStickers_LC(String[] stickers, String target) {

        int n = stickers.length;
		
		int[][] map = new int[n][26];// stickers -> [26] [26] [26]
		for (int i = 0; i < n; i++) {
			char[] str = stickers[i].toCharArray();
			for (char c : str) {
				map[i][c - 'a']++;
			}
		}
		HashMap<String, Integer> dp = new HashMap<>();
		dp.put("", 0);
		return process(dp, map, target);
    }

	// dp 傻缓存，如果t已经算过了，直接返回dp中的值
	// t 剩余的目标
	// 0..N每一个字符串所含字符的词频统计
	// 返回值是-1，map 中的贴纸  怎么都无法rest
	public  int process(
			HashMap<String, Integer> dp,
			int[][] map, 
			String rest) {
		if (dp.containsKey(rest)) {
			return dp.get(rest);
		}
		// 以下就是正式的递归调用过程
		int ans = Integer.MAX_VALUE; // ans -> 搞定rest，使用的最少的贴纸数量 
		int n = map.length; // N种贴纸
		int[] tmap = new int[26]; // tmap 去替代 rest
		char[] target = rest.toCharArray();
		for (char c : target) {
			tmap[c - 'a']++;
		}
		for (int i = 0; i < n; i++) {
			// 枚举当前第一张贴纸是谁？
			if (map[i][target[0] - 'a'] == 0) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			// i 贴纸， j 枚举a~z字符
			for (int j = 0; j < 26; j++) { // 
				if (tmap[j] > 0) { // j这个字符是target需要的
					for (int k = 0; k < Math.max(0, tmap[j] - map[i][j]); k++) {
						sb.append((char) ('a' + j));
					}
				}
			}
			// sb ->  i
			String s = sb.toString();
			int tmp = process(dp, map, s);
			if (tmp != -1) {
				ans = Math.min(ans, 1 + tmp);
			}
		}
		// ans 系统最大  rest
		dp.put(rest, ans == Integer.MAX_VALUE ? -1 : ans);
		return dp.get(rest);
	}

}