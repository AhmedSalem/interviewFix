package com.interview.dynamic;

import java.util.List;

/**
 * Date 07/07/2014
 * @author Tushar Roy
 *
 * Given two strings how many minimum edits(update, delete or add) is needed to convert one string to another
 *
 * Time complexity is O(m*n)
 * Space complexity is O(m*n)
 *
 * References:
 * http://www.geeksforgeeks.org/dynamic-programming-set-5-edit-distance/
 * https://en.wikipedia.org/wiki/Edit_distance
 */
public class EditDistance {

    /**
     * Uses recursion to find minimum edits
     */
    public int recEditDistance(char[]  str1, char str2[], int len1,int len2){
        
        if(len1 == str1.length){
            return str2.length - len2;
        }
        if(len2 == str2.length){
            return str1.length - len1;
        }
        return min(recEditDistance(str1, str2, len1 + 1, len2 + 1) + str1[len1] == str2[len2] ? 0 : 1, recEditDistance(str1, str2, len1, len2 + 1) + 1, recEditDistance(str1, str2, len1 + 1, len2) + 1);
    }
    
    /**
     * Uses bottom up DP to find the edit distance
     */
    public int dynamicEditDistance(char[] str1, char[] str2){
        int temp[][] = new int[str1.length+1][str2.length+1];
        
        for(int i=0; i < temp[0].length; i++){
            temp[0][i] = i;
        }
        
        for(int i=0; i < temp.length; i++){
            temp[i][0] = i;
        }
        
        for(int i=1;i <=str1.length; i++){
            for(int j=1; j <= str2.length; j++){
                if(str1[i-1] == str2[j-1]){
                    temp[i][j] = temp[i-1][j-1];
                }else{
                    temp[i][j] = 1 + min(temp[i-1][j-1], temp[i-1][j], temp[i][j-1]);
                }
            }
        }
        printActualEdits(temp, str1, str2);
        return temp[str1.length][str2.length];
        
    }
	
		static int editDistDP(String str1, String str2, int m, int n) {
		// Create a table to store results of subproblems
		int dp[][] = new int[m + 1][n + 1];

		// Fill d[][] in bottom up manner
		for(int i = 0; i <= m; i++){
			for(int j = 0; j <= n; j++){
				// If first string is empty, only option is to
				// isnert all characters of second string
				if (i == 0)
					dp[i][j] = j; // Min. operations = j

				// If second string is empty, only option is to
				// remove all characters of second string
				else if (j == 0)
					dp[i][j] = i; // Min. operations = i

				// If last characters are same, ignore last char
				// and recur for remaining string
				else if (str1.charAt(i - 1) == str2.charAt(j - 1))
					dp[i][j] = dp[i - 1][j - 1];

				// If last character are different, consider all
				// possibilities and find minimum
				else
					dp[i][j] = 1 + min(dp[i][j - 1], // Insert
						dp[i - 1][j], // Remove
						dp[i - 1][j - 1]); // Replace

				System.out.printf("%5d ", dp[i][j]);

			}
			System.out.println();
		}

		printActualEdits(dp, str1.toCharArray(), str2.toCharArray());
		return dp[m][n];
	}

	/**
	 * Prints the actual edits which needs to be done.
	 */
	public static DistanceResult printActualEdits(int T[][], char[] str1, char[] str2) {
		DistanceResult result = new DistanceResult();
		Map<Integer, String> s1_transitionMap = new HashMap<Integer, String>();

		System.out.println("\n ************** Actual Edits ****************** \n");
		int i = T.length - 1;
		int j = T[0].length - 1;

		while (true){
			//System.out.println(" i : " + i + " , j : " + j);
//			System.out.println(" str1[i - 1] : " + str1[i - 1] + " , str2[j - 1] : " + str2[j - 1]);
			if (i == 0 && j == 0){
				break;
			}

			if ((i - 1 >= 0 && j - 1 >= 0) && str1[i - 1] == str2[j - 1]){
				i = i - 1;
				j = j - 1;
			}
			else if ((i - 1 >= 0 && j - 1 >= 0) && T[i][j] == T[i - 1][j - 1] + 1){

				System.out.println("Edit Index -> " + (j - 1) + " , Value : " + str2[j - 1] + " in target to be Index -> " + (i - 1) + " , Value : " + str1[i - 1] + " in source");

				s1_transitionMap.put((i - 1), str1[i - 1] + "");
				i = i - 1;
				j = j - 1;
			}
			else if (T[i][j] == T[i - 1][j] + 1){
				System.out.println("Delete in , Index -> " + (i - 1) + " , Value : " + str1[i - 1]);
				s1_transitionMap.put((i - 1), str1[i - 1] + "");
				i = i - 1;
			}
			else if (T[i][j] == T[i][j - 1] + 1){
				System.out.println("Delete in , Index -> " + (j - 1) + " , Value :  " + str2[j - 1]);
				s1_transitionMap.put((j - 1), str2[j - 1] + "");
				j = j - 1;
			}
			else{
//				throw new IllegalArgumentException("Some wrong with given data");
				continue;
			}

		}

		System.out.println("\n ------------------ Source Changes Original -----------------\n");
		for(Entry<Integer, String> item : s1_transitionMap.entrySet()){

			System.out.println("Index -> " + item.getKey() + " , Value : " + item.getValue());

		}

		System.out.println("\n ------------------ Source Changes ignore Special -----------------\n");
		for(Entry<Integer, String> item : s1_transitionMap.entrySet()){

			if (Pattern.matches("[a-zA-Z 0-9]+", item.getValue())){
				System.out.println("Index -> " + item.getKey() + " , Value : " + item.getValue());
			}

		}

		result.setS1TransitionMap(s1_transitionMap);

		return result;
	}

    private int min(int a,int b, int c){
        int l = Math.min(a, b);
        return Math.min(l, c);
    }

    public static void main(String args[]){
        String str1 = "azced";
        String str2 = "abcdef";
        EditDistance editDistance = new EditDistance();
        int result = editDistance.dynamicEditDistance(str1.toCharArray(), str2.toCharArray());
        System.out.print(result);
    }

}
