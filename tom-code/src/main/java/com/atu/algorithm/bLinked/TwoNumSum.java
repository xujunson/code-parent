package com.atu.algorithm.bLinked;

import com.atu.algorithm.bLinked.vo.ListNode;

/**
 * @Author: Tom
 * @Date: 2022/7/4 14:46
 * @Description: 「链表」两数相加
 */
public class TwoNumSum {

    private ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        if (l1.val > 10 || l2.val > 10) {
            throw new RuntimeException("格式不规范");
        }

        // 记录进位
        int carry = 0;

        //虚拟头结点（构建新链表时的常用技巧）
        ListNode dummy = new ListNode(-1);

        //指针 p 负责构建新链表
        ListNode p = dummy;

        //开始执行加法，两条链表走完且没有进位时才能结束循环
        while (l1 != null || l2 != null || carry > 0) {
            // 先加上上次的进位
            int val = carry;
            if (l1 != null) {
                val += l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                val += l2.val;
                l2 = l2.next;
            }

            // 处理进位情况
            carry = val / 10;
            val = val % 10;
            p.next = new ListNode(val % 10);
            p = p.next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(0);
        ListNode node2 = new ListNode(8);
        ListNode node3 = new ListNode(8);
        node1.next = node2;
        node2.next = node3;


        ListNode node4 = new ListNode(0);
        ListNode node5 = new ListNode(9);
        ListNode node6 = new ListNode(1);
        node4.next = node5;
        node5.next = node6;

        ListNode listNode = new TwoNumSum().addTwoNumbers(node1, node4);
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }


    }

}
