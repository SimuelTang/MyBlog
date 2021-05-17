package pers.simuel.blog.blog;

/**
 * @Author simuel_tang
 * @Date 2021/5/12
 * @Time 19:48
 */

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 小明得到一个只包含大小写英文字母的字符串s，下标从1开始计算。现在他希望得到这个字符串下标的一个奇怪的集合。这个奇怪的集合需要满足的条件是：
 * <p>
 * 1. 集合中的任意下标i对应的字母s[i]必须是小写字母
 * <p>
 * 2. 对于集合中的任意两个下标i、j，对于任意数字k，i<=k<=j，有s[k]是小写字母
 * <p>
 * 3. 集合中的下标对应的字母是两两不同的
 * <p>
 * 4. 集合中的数字尽可能的多
 * <p>
 * 帮助小明计算这个集合最多可以有多少下标。
 * <p>
 * <p>
 * aaBBBabBaAb
 * 2
 * 样例2：
 * 样例2输入：aabbcc
 * 样例2输出：3
 * 样例2解释：可以是下标1，3，5组成的集合。满足所有条件。
 */
public class Main {
    public static void main(String[] args) {
        String str = new Scanner(System.in).nextLine();
        char[] cs = str.toCharArray();
        int max_len = 0;
        int i = 0, j = 0;
        Set<Character> set = new HashSet<>();
        for (; j < cs.length; j++) {
            if (cs[j] == cs[i]) {
                continue;
            }
            if (!isLow(cs[j])) {
                max_len = Math.max(max_len, j - i);
                do {
                    j++;
                } while (!isLow(cs[j]));
                i = j;
                set = new HashSet<>();
            }
            set.add(cs[j]);
        }
        System.out.println(max_len);
    }

    private static boolean isLow(char ch) {
        return ch >= 'a' && ch <= 'z';
    }
}


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
 * 输入描述
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
 */
