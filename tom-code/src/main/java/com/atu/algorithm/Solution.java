package com.atu.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Tom
 * @Date: 2021/6/30 0:22
 * @Description: 两数之和
 */
public class Solution {
    public int[] twoSum(int[] nums, int target) {
       /* int n = nums.length;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];*/

        Map<Integer, Integer> hashtable = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; ++i) {
            if (hashtable.containsKey(target - nums[i])) {
                return new int[]{hashtable.get(target - nums[i]), i};
            }
            hashtable.put(nums[i], i);
        }
        return new int[0];
    }

}
