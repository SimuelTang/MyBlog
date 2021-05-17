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
 * 小A大学毕业后，回家继承了老爸的租车公司。现在小A的店内只剩下一辆汽车可出租，
 * 但是一天内有n个订单，每个订单的用车开始时间为第x小时，用车结束时间为第y小时，订单金额为z，
 * 请你帮小A安排订单，计算小A可以获得的最大收益。
 * 注意：一辆车在同一个时间段内不能同时安排两个订单。开始时间和结束时间是小时维度的整数，可取1-24小时。
 * <p>
 * 输入描述
 * 第一行读入一个整数n，表示有n（1<=n<=10）个订单；
 * <p>
 * 第二行读入用空格分隔的整数，表示订单的开始时间。
 * <p>
 * 第三行读入用空格分隔的整数，表示订单的结束时间。
 * <p>
 * 第四行读入用空格分隔的整数，表示订单金额。
 * <p>
 * 输出描述
 * 输出小A可以获得的最大收益。
 * <p>
 * <p>
 * 4
 * 1 2 3 3
 * 3 4 5 6
 * 200 150 180 210
 * <p>
 * 410
 */

public class Main4 {
    static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        int[][] orders = new int[n][2];
        String[] startTime = sc.nextLine().split(" ");
        String[] endTime = sc.nextLine().split(" ");
        String[] orderPrice = sc.nextLine().split(" ");
        for (int i = 0; i < startTime.length; i++) {
            orders[i][0] = Integer.parseInt(startTime[i]);
        }
        for (int i = 0; i < endTime.length; i++) {
            orders[i][1] = Integer.parseInt(endTime[i]);
        }
        int ans = 0;
        for (int i = 0; i < orders.length; i++) {
            ans = Integer.parseInt(orderPrice[i]);
            getMaxProfit(orders, orderPrice, i, ans);
        }
        for (Integer num : list) {
            ans = Math.max(ans, num);
        }
        System.out.println(ans);
    }

    private static void getMaxProfit(int[][] orders, String[] orderPrice, int level, int ans) {
        for (int i = level + 1; i < orders.length; i++) {
            if (orders[i][0] < orders[level][1] % 24) {
                continue;
            }
            ans += Integer.parseInt(orderPrice[i]);
            getMaxProfit(orders, orderPrice, i, ans);
            ans -= Integer.parseInt(orderPrice[i]);
        }
        list.add(ans);
    }
}
