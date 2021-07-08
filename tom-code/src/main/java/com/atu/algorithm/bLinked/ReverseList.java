package com.atu.algorithm.bLinked;

/**
 * @Author: Tom
 * @Date: 2021/7/8 5:44 下午
 * @Description: 反转链表
 * 要求
 * 输入：{1,2,3}
 * 返回值：{3,2,1}
 */
public class ReverseList {
    public ListNode reverse(ListNode head) {
        //初始化：3个指针
        //pre指针指向已经反转好的链表的首个节点，最开始没有反转，所以指向nullptr
        //cur指针指向待反转链表的第一个节点，最开始第一个节点待反转，所以指向head
        //nex指针指向待反转链表的第二个节点，目的是保存链表，因为cur改变指向后，后面的链表则失效了，所以需要保存

        ListNode pre = null;
        ListNode cur = head;
        ListNode nex = null;

        while (null != cur) {
            //保存作用
            nex = cur.next;

            //未反转链表的第一个节点的下个指针指向已反转链表的首个节点
            cur.next = pre;

            //指针后移，操作下一个未反转链表的第一个节点
            pre = cur;
            cur = nex;

        }
        return pre;
    }

    public static void main(String[] args) {
        ReverseList reverseList = new ReverseList();
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        node1.next = node2;
        node2.next = node3;
        ListNode reverse = reverseList.reverse(node1);

        while (null != reverse) {
            System.out.println(reverse.val);
            reverse = reverse.next;
        }
    }
}

class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}
