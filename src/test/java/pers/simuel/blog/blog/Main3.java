package pers.simuel.blog.blog;

/**
 * @Author simuel_tang
 * @Date 2021/5/12
 * @Time 19:48
 */


import java.util.Scanner;

/**
 * 小A的农田里面出现了一种新型植物！这种植物有n株且长成一排，
 * 从左到右其高度依次为a1,a2,...,an。小A发现：当高度不超过w厘米时，
 * 这种植物会对农作物有益；否则，需要花钱请专业人员来将植物剪枝，剪成w厘米。
 * 将高度为h厘米的植物剪成w厘米的花费为h-w元。但是，由于这种植物很敏感，
 * 对第i株进行修剪之后，其右边的所有植物在第二天都会长高k厘米。
 * 小A雇的专业人员认为只能从左到右依次修剪每株植物，每天也最多只能修剪一株植物（
 * 如果这一天对应的植物不需要修剪，那么这一天不会做任何事）并希望小A提前计算好他应付的费用。
 * <p>
 * <p>
 * 输入描述
 * 第一行三个整数n,w,k；
 * <p>
 * 第二行n个整数，表示a1,a2,...,an。
 * <p>
 * 1≤n≤2×105，0≤w,k,ai≤104。
 * <p>
 * 输出描述
 * 输出一个整数，表示小A应付的费用。
 * <p>
 * input:
 * 4 2 1
 * 3 1 0 5
 * output:
 * 5
 * hint:
 * 第一天花1元修剪第一株植物，到了第二天高度分别为2 2 1 6；
 * 第二天对应的植物高度为2，不需要花费，跳过；
 * 第三天对应的植物高度为1，不需要花费，跳过；
 * 第四天对应的高度为6，花4元修剪第四株植物。
 * 综上，花费一共为5元。
 */

public class Main3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String numStr = sc.nextLine();
        String[] num = numStr.split(" ");
        int n = Integer.parseInt(num[0]);
        int w = Integer.parseInt(num[1]);
        int k = Integer.parseInt(num[2]);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        int ans = 0, adding = 0;
        for (int i = 0; i < n; i++) {
            if ((arr[i] + adding) > w) {
                ans += (arr[i] + adding) - w;
                adding += k;
            }
        }
        System.out.println(ans);
    }
}
