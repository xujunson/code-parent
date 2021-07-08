package com.atu.algorithm.aTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Tom
 * @Date: 2021/6/30 0:22
 * @Description: 两数之和
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 * https://leetcode-cn.com/problems/two-sum/
 */
public class TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        //方案一：暴力枚举
       /* int n = nums.length;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];*/

        //方案二：哈希表
        Map<Integer, Integer> hashtable = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; ++i) {
            if (hashtable.containsKey(target - nums[i])) {
                return new int[]{hashtable.get(target - nums[i]), i};
            }
            hashtable.put(nums[i], i);
        }
        return new int[0];
    }
    public static void main(String[] args) {
        int[] nums = new int[]{3,2,4};
        System.out.println(twoSum(nums, 6)[0]);
        System.out.println(twoSum(nums, 6)[1]);
    }
}
