package com.liuxing.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Arrays;

@MapperScan("com.liuxing.user.mapper")
@SpringBootApplication
@EnableFeignClients
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
//public static void main(String[] args) {
//    int[] array = {170, 45, 75, 90, 802,  2, 247, 68, 29, 56, 37,845, 38,376};
//    radixSort(array);
////    System.out.println(Arrays.toString(array));
//}
//
//    public static void radixSort(int[] input) {
//        // 获取数组中最大值的位数
//        int max = getMax(input);
//        for (int exp = 1; max / exp > 0; exp *= 10) {
////            System.out.println(Arrays.toString(input));
//            countSort(input, exp);
//        }
//    }
//
//    private static void countSort(int[] input, int exp) {
//        int[] output = new int[input.length];
//        int[] count = new int[10];
//
//        // 计算每个数字出现的次数{170, 45, 75, 90, 802,  2}
//        for (int i = 0; i < input.length; i++) {
//            int index = (input[i] / exp) % 10;
//            int s = input[i] / exp;
//            int e = s % 10;
//            count[index]++;
//        }
//
//        // 修改计数数组，使其包含实际位置信息{170, 45, 75, 90, 802,  2}
//        for (int i = 1; i < 10; i++) {
//            count[i] += count[i - 1];
//        }
//
//        // 构建输出数组
//        for (int i = input.length - 1; i >= 0; i--) {
//            int index = (input[i] / exp) % 10;
//            output[count[index] - 1] = input[i];
//            count[index] = count[index] - 1;
//        }
//
//        // 复制输出数组到输入数组，这样输入数组现在包含排序后的数字
//        System.arraycopy(output, 0, input, 0, input.length);
//        System.out.println(Arrays.toString(output));
//    }
//
//    private static int getMax(int[] input) {
//        int max = input[0];
//        for (int i = 1; i < input.length; i++) {
//            if (input[i] > max) {
//                max = input[i];
//            }
//        }
//        return max;
//    }
}
