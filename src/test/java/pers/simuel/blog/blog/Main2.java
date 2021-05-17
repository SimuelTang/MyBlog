package pers.simuel.blog.blog;

/**
 * @Author simuel_tang
 * @Date 2021/5/12
 * @Time 19:48
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 刘邦的士兵截获了项羽的密文，据内探得知，项羽的密文是将一串信息以回字形，从左上角延顺时针方向填充进方阵中，并在末尾补上0。（可参考样例）
 * <p>
 * 先给定一n×n的方阵，请你解密。（保证方阵内容为大小写英文字符，或0）
 * <p>
 * <p>
 * 样例输入
 * 4
 * qwef
 * awad
 * s00s
 * asdf
 * 样例输出
 * qwefdsfdsasawa
 */

public class Main2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String str = sc.nextLine();
            list.add(str);
        }
        char[][] matrix = new char[n][n];
        for (int i = 0; i < list.size(); i++) {
            matrix[i] = list.get(i).toCharArray();
        }
        int up = 0, bottom = matrix.length - 1, left = 0, right = matrix.length - 1;
        StringBuilder sb = new StringBuilder();
        while (up <= bottom && left <= right) {
            int l = left;
            while (l <= right) {
                sb.append(matrix[up][l]);
                l++;
            }
            up++;
            int u = up;
            while (u <= bottom && left <= right) {
                sb.append(matrix[u][right]);
                u++;
            }
            right--;
            int r = right;
            while (r >= left && bottom >= up) {
                sb.append(matrix[bottom][r]);
                r--;
            }
            bottom--;
            int b = bottom;
            while (b >= up) {
                sb.append(matrix[b][left]);
                b--;
            }
            left++;
        }
        System.out.println(sb.substring(0, sb.indexOf("0")));
    }
}
