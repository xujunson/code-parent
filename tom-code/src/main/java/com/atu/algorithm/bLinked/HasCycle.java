package com.atu.algorithm.bLinked;

import com.atu.algorithm.bLinked.vo.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Tom
 * @Date: 2021/7/9 1:58 下午
 * @Description: 判断链表中是否有环
 */
public class HasCycle {
    public boolean hasCycle(ListNode head) {
        // 解法1：快慢指针也叫相向指针
        // 慢指针针每次走一步，快指针每次走两步，如果相遇就说明有环，如果有一个为空说明没有环
        /*if (head == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) return true;
        }

        return false;*/
        /**
         * 解法1复杂度分析：
         * 时间复杂度：O(N)，其中 N 是链表中的节点数
         * 空间复杂度：O(1)，我们只使用了两个指针的额外空间
         */
        //------------------------------------------------------------------------------------------------------

        // 解法2：哈希表 遍历所有节点，每次遍历到一个节点时，判断该节点此前是否被访问过
        /*Set<ListNode> set = new HashSet<>();
        while(head != null) {
            if(!set.add(head)) return true;

            head = head.next;
        }
        return false;*/

        /**
         * 解法2复杂度分析：
         * 时间复杂度：O(N)
         * 空间复杂度：O(N)
         */

        //------------------------------------------------------------------------------------------------------

        //解法3：逐个删除 让他的next指针指向他自己。如果没有环，从头结点一个个删除，最后肯定会删完
        //如果head为空，或者他的next指向为空，直接返回false
        if (head == null || head.next == null)
            return false;
        //如果出现head.next = head表示有环
        if (head.next == head)
            return true;
        ListNode nextNode = head.next;
        //当前节点的next指向他自己，相当于把它删除了
        head.next = head;
        //然后递归，查看下一个节点
        return hasCycle(nextNode);
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(6);
        ListNode node4 = new ListNode(3);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2;

        System.out.println(new HasCycle().hasCycle(node1));
    }
}
