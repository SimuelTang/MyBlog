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
 * “周游世界”是携程举办的一个大型旅游类节目，节目规则如下：
 *
 * 1. 节目组给出一组有序的全球旅游景点，到达该景点则将会获取不同的分数奖励。
 *
 * 2. 每位参赛者都位于旅游景点的第1站，默认获取该景点的分数奖励。
 *
 * 3. 每位参赛者会得到一组数值一样的卡片，每张卡片上有一个数字，1，2，3或4，不同的数字代表可以从当前站点向后行进的步数。
 *
 * 例如，当前处于第一站，若使用一张数字1的卡片，则到达第二站，若使用一张数字2的卡片，则到达第三站。
 *
 * 很显然，这个游戏获胜的条件和卡片的使用顺序相关。
 *
 * 那么，应该如何合理的安排这些卡片的使用顺序获取比赛的胜利呢？游游陷入了沉思。。。
 * 
 * 
 * 
 * 
 * scores数组代表当前给出的各个景点的分数。
 *
 * cards数组代表当前给出的卡片。
 *
 * 求当前这个输入下，游游可以获得的最高分。
 *
 * 例如样例中所示，共四个景点，分值分别为1，2，3，4。共2张卡片，分值分别为1，2。很显然先使用2再使用1可以使获得的分数最高。1+3+4=8分。
 *
 * 提示：
 *
 * 1. scores.length <= 100
 *
 * 2. cards中每一种卡片的数值不超过40，就是说卡片数值为1的总数不超过40，2/3/4同理。
 *
 * 3. sum(cards[i]) < scores.length
 * 
 * 
 * 
 * 4
 * 1
 * 2
 * 3
 * 4
 * 2
 * 1
 * 2
 * 8
 */

public class Main5 {
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
