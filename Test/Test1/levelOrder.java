import java.util.ArrayList;
import java.util.List;

public class levelOrder {
    public static void main(String[] args) {
        levelOrder();
    }
    public List<List<Integer>> levelOrder(TreeNode root) {
        TreeNode [] queue = new TreeNode [2001];
        int l = 0, r = 0, count = 1;
        queue[r++] = root;
        ArrayList<ArrayList> ans;
        TreeNode temp;
        while (l != r){
            count = r-l;
            int[]ansCen = new int[count];
            for(int i = 0; i < count; i++){
                temp = queue[l++];
                List[i] = temp;
                if(temp.left != null){
                    queue[r++] = temp.left;
                }
                if(temp.right != null){
                    queue[r++] = temp.right;
                }
            }
        }

    }
}