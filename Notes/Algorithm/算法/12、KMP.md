# KMP

## 语法

```java
class Solution { 
    public int strStr(String haystack, String needle) {
        char[] charLong = haystack.toCharArray();
        char[] charShort = needle.toCharArray();
        int n = charLong.length, m = charShort.length;

        // 特殊情况处理
        if (m == 0) return 0;  // 如果 needle 为空，返回 0
        if (n < m) return -1;  // 如果 haystack 短于 needle，返回 -1

        // 生成 next 数组
        int[] next = new int[m];
        next[0] = -1;
        int i = 0, j = -1;
        
        while (i < m - 1) {
            if (j == -1 || charShort[i] == charShort[j]) {
                i++;
                j++;
                next[i] = j; // 修正为 charShort[i] 和 charShort[j] 的比较
            } else {
                j = next[j];
            }
        }

        // KMP 搜索
        int x = 0, y = 0;
        while (x < n && y < m) {
            if (y == -1 || charLong[x] == charShort[y]) {
                x++;
                y++;
            } else {
                y = next[y];
            }
        }

        return y == m ? x - y : -1;
    }
}
```

## 原理

s1字符串是否包含s2字符串，如果包含返回s1中包含s2的最左开头位置，不包含返回-1

KMP算法可以做到时间复杂度O(n + m)

1)理解next数组的定义，定义是一切的关键，不包括当前位置字符，前面的字符串**前缀和后缀的最大匹配长度**(不包括整体，因为整体的前缀后缀就是整体本身)

2)假设已经有了next数组，详解匹配过程是如何得到加速的，加速过程有2个理解核心

3)理解了匹配主流程之后，详解next数组如何快速生成，不停跳跃的过程有1个理解核心

4)KMP算法代码详解，主流程 + next数组生成

## 题目

