public class Main {
    public static void main(String[] args) {
            public ListNode reverseList(ListNode head) {
                if(head.next == null || head == null){
                    return head;
                }
                if(head.next.next == null){
                    head.next.next = head;
                    head.next = null;
                }
                ListNode pre, temp;
                temp = head.next.next;
                pre = head.next;
                head.next = null;
                pre.next = head;
                while(temp != null){
                    head = pre;
                    pre = temp;
                    temp = pre.next;
                    pre.next = head;
                }
                return pre;
            }
    }
}
