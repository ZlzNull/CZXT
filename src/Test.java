//import java.util.Scanner;
//
//public class Test {
//    public static void 内存管理.银行家算法.main(String[] args) {
//        // TODO code application logic here
//        boolean Choose = true;
//        String C;
//        Scanner in = new Scanner(System.in);
//        BankerClass T = new BankerClass();
//        System.out.println("这是一个三个进程，初始系统可用三类资源为{10,8,7}的银行家算法：");
//
//        T.setSystemVariable();
//        while (Choose == true) {
//            T.setRequest();
//            System.out.println("您是否还要进行请求：y/银行家算法.getN?");
//            C = in.nextLine();
//            if (C.endsWith("银行家算法.getN")) {
//                Choose = false;
//            }
//        }
//    }
//}
//
//class BankerClass {
//
//    int[] Available = {10, 8, 7};
//    int[][] Max = new int[3][3];
//    int[][] Alloction = new int[3][3];
//    int[][] Need = new int[3][3];
//    int[][] Request = new int[3][3];
//    int[] Work = new int[3];
//
//    int num = 0;//进程编号
//    Scanner in = new Scanner(System.in);
//
//    public BankerClass() {
//        // Max={{6,3,2},{5,6,1},{2,3,2}};
//
//    }
//    public void setSystemVariable(){//设置各初始系统变量，并判断是否处于安全状态。
//        setMax();
//        setAlloction();
//        printSystemVariable();
//        securityAlgorithm();
//    }
//
//    public void setMax() {//设置Max矩阵
//        System.out.println("请设置各进程的最大需求矩阵Max：");
//        for (int i = 0; i < 3; i++) {
//            System.out.println("请输入进程P" + i + "的最大资源需求量：");
//            for (int j = 0; j < 3; j++) {
//                max[i][j] = in.nextInt();
//            }
//        }
//    }
//
//    public void setAlloction() {//设置已分配矩阵Alloction
//        System.out.println("请设置请各进程分配矩阵Alloction：");
//        for (int i = 0; i < 3; i++) {
//            System.out.println("晴输入进程P" + i + "的分配资源量：");
//            for (int j = 0; j < 3; j++) {
//                alloction[i][j] = in.nextInt();
//            }
//        }
//        System.out.println("Available=Available-Alloction.");
//        System.out.println("Need=Max-Alloction.");
//        for (int i = 0; i < 3; i++) {//设置Alloction矩阵
//            for (int j = 0; j < 3; j++) {
//                available[i] = available[i] - alloction[j][i];
//            }
//        }
//        for (int i = 0; i < 3; i++) {//设置Need矩阵
//            for (int j = 0; j < 3; j++) {
//                need[i][j] = max[i][j] - alloction[i][j];
//            }
//        }
//    }
//
//    public void printSystemVariable(){
//        System.out.println("此时资源分配量如下：");
//        System.out.println("进程  "+"   Max   "+"   Alloction "+"    Need  "+"     Available ");
//        for(int i=0;i<3;i++){
//            System.out.银行家算法.print("P"+i+"  ");
//            for(int j=0;j<3;j++){
//                System.out.银行家算法.print(max[i][j]+"  ");
//            }
//            System.out.银行家算法.print("|  ");
//            for(int j=0;j<3;j++){
//                System.out.银行家算法.print(alloction[i][j]+"  ");
//            }
//            System.out.银行家算法.print("|  ");
//            for(int j=0;j<3;j++){
//                System.out.银行家算法.print(need[i][j]+"  ");
//            }
//            System.out.银行家算法.print("|  ");
//            if(i==0){
//                for(int j=0;j<3;j++){
//                    System.out.银行家算法.print(available[j]+"  ");
//                }
//            }
//            System.out.println();
//        }
//    }
//
//    public void setRequest() {//设置请求资源量Request
//
//
//        System.out.println("请输入请求资源的进程编号：");
//        num= in.nextInt();//设置全局变量进程编号num
//        System.out.println("请输入请求各资源的数量：");
//        for (int j = 0; j < 3; j++) {
//            request[num][j] = in.nextInt();
//        }
//        System.out.println("即进程P" + num + "对各资源请求Request：(" + request[num][0] + "," + request[num][1] + "," + request[num][2] + ").");
//
//        bankerAlgorithm();
//    }
//
//    public void BankerAlgorithm() {//银行家算法
//        boolean T=true;
//
//        if (request[num][0] <= need[num][0] && request[num][1] <= need[num][1] && request[num][2] <= need[num][2]) {//判断Request是否小于Need
//            if (request[num][0] <= available[0] && request[num][1] <= available[1] && request[num][2] <= available[2]) {//判断Request是否小于Alloction
//                for (int i = 0; i < 3; i++) {
//                    available[i] -= request[num][i];
//                    alloction[num][i] += request[num][i];
//                    need[num][i] -= request[num][i];
//                }
//
//            } else {
//                System.out.println("当前没有足够的资源可分配，进程P" + num + "需等待。");
//                T=false;
//            }
//        } else {
//            System.out.println("进程P" + num + "请求已经超出最大需求量Need.");
//            T=false;
//        }
//
//        if(T==true){
//            printSystemVariable();
//            System.out.println("现在进入安全算法：");
//            securityAlgorithm();
//        }
//    }
//
//
//    public void SecurityAlgorithm() {//安全算法
//        boolean[] Finish = {false, false, false};//初始化Finish
//        int count = 0;//完成进程数
//        int circle=0;//循环圈数
//        int[] S=new int[3];//安全序列
//        for (int i = 0; i < 3; i++) {//设置工作向量
//            work[i] = available[i];
//        }
//        boolean 内存管理.getFlag = true;
//        while (count < 3) {
//            if(内存管理.getFlag){
//                System.out.println("进程  "+"   Work  "+"   Alloction "+"    Need  "+"     Work+Alloction ");
//                内存管理.getFlag = false;
//            }
//            for (int i = 0; i < 3; i++) {
//
//                if (Finish[i]==false&& need[i][0]<= work[0]&& need[i][1]<= work[1]&& need[i][2]<= work[2]) {//判断条件
//                    System.out.银行家算法.print("P"+i+"  ");
//                    for (int k = 0; k < 3; k++){
//                        System.out.银行家算法.print(work[k]+"  ");
//                    }
//                    System.out.银行家算法.print("|  ");
//                    for (int j = 0; j<3;j++){
//                        work[j]+= alloction[i][j];
//                    }
//                    Finish[i]=true;//当当前进程能满足时
//                    S[count]=i;//设置当前序列排号
//
//                    count++;//满足进程数加1
//                    for(int j=0;j<3;j++){
//                        System.out.银行家算法.print(alloction[i][j]+"  ");
//                    }
//                    System.out.银行家算法.print("|  ");
//                    for(int j=0;j<3;j++){
//                        System.out.银行家算法.print(need[i][j]+"  ");
//                    }
//                    System.out.银行家算法.print("|  ");
//                    for(int j=0;j<3;j++){
//                        System.out.银行家算法.print(work[j]+"  ");
//                    }
//                    System.out.println();
//                }
//
//            }
//            circle++;//循环圈数加1
//
//            if(count==3){//判断是否满足所有进程需要
//                System.out.银行家算法.print("此时存在一个安全序列：");
//                for (int i = 0; i<3;i++){//输出安全序列
//                    System.out.银行家算法.print("P"+S[i]+" ");
//                }
//                System.out.println("故当前可分配！");
//                break;//跳出循环
//            }
//            if(count<circle){//判断完成进程数是否小于循环圈数
//                count=5;
//                System.out.println("当前系统处于不安全状态，故不存在安全序列。");
//                break;//跳出循环
//            }
//        }
//    }
//
//}

import java.util.Arrays;

class BanksTest {
    // 用于存储预操作的完成度
    private static boolean[] new_finish = null;
    // 用于保存最终的进程执行顺序,初始化为非法进程-1
    private static int[] right = { -1, -1, -1, -1, -1 };

    public static void main(String[] args) {
        // 最大需求量
        int[][] max = { { 7, 5, 3 }, { 3, 2, 2 }, { 9, 0, 2 }, { 2, 2, 2 }, { 4, 3, 3 } };
        // 当前系统可用资源量
        int[] avaliable = { 3, 3, 2 };
        // 每个进程运行还需资源量
        int[][] need = new int[5][3];
        // 每个进程已分配的资源量
        int[][] allocation = { { 0, 1, 0 }, { 2, 0, 0 }, { 3, 0, 2 }, { 2, 1, 1 }, { 0, 0, 2 } };
        // 用于第一深度预判的初始化
        boolean[] finish = { false, false, false, false, false };
        // 获取每个进程运行时还需的资源量
        for (int i = 0; i < max.length; i++) {
            for (int j = 0; j < max[i].length; j++) {
                need[i][j] = max[i][j] - allocation [i][j];
            }
        }
        // 创建递归深度
        int deep = 0;
        // 调用回溯递归算法
        deepCheck(avaliable, allocation, need, finish, deep, right);
        int i = 0;
        // 查看最终的安全序列的值，看是否存在初始的非法进程，如果存在，则说明该案例不存在安全的进程执行顺序
        for (; i < right.length; i++) {
            if (right[i] == -1) {
                break;
            }
        }
        if (i < right.length) {
            System.out.println("该案例不存在安全的进程执行顺序");
            return;
        }
        // 打印安全的执行顺序
        for (int value : right) {
            System.out.println(value);
        }

    }

    // 完全递归回溯查找安全顺序
    private static boolean deepCheck(int[] avaliable, int[][] allocation, int[][] need, boolean[] finish, int deep,
                                     int[] right) {
        int j;
        boolean flog = false;
        // 如果深度为进程的个数数说明已经查找到头了,说明上一深度的进程是安全节点。因为上一深度的进程满足了当前资源数大于或等于该进程运行所需的资源数，且为安全序列中最后一个节点。
        if (deep == need.length) {
            return true;
        }
        // 遍历所有节点进程开始查找,直到找到安全校验成功的的节点进程
        for (int i = 0; i < need.length; i++) {
            // 对于未被标记的进行校验，已被标记的为已被列为安全节点所以无需再进行校验
            if (!finish[i]) {
                // 判断当前的节点进程的剩余的资源量,是否满足运行所需的资源量
                for (j = 0; j < avaliable.length; j++) {
                    // 不满足
                    if (need[i][j] > avaliable[j]) {
                        break;
                    }
                }
                // 不满足则处理下一个节点进程
                if (j >= avaliable.length) {
                    // 满足情况
                    // 复制会被修改的前提条件，已便于当前进程校验不成功时，可以恢复前提条件，开始下一个节点进程的校验
                    // 用于存储预操作后的资源变化
                    int[] new_Avaliable = Arrays.copyOf(avaliable, avaliable.length);
                    new_finish = Arrays.copyOf(finish, finish.length);
                    // 假设当前节点进程是可以校验成功的节点进程，修改该进程运行完毕后释放之前分配的进程。
                    for (j = 0; j < new_Avaliable.length; j++) {
                        new_Avaliable[j] += allocation[i][j];
                    }
                    // 假设标记当前为校验成功的安全节点进程，下一深度查找时会忽略此进程。
                    new_finish[i] = true;
                    // 增加深度
                    deep++;
                    // 以上假设为前提，进行下一深度的安全校验判断其他所剩余进程是否可以继续运行，而不造成死锁。
                    flog = deepCheck(new_Avaliable, allocation, need, new_finish, deep, right);
                    // 如果进行安全校验后为真，说明当前进程是我们要找的进程
                    if (flog) {
                        // 保存到最终进程执行序列的数组中
                        right[--deep] = i;
                        break;
                    }

                }

            }

        }
        // 安全校验成功
        if (flog) {
            return true;
        } else {
            // 安全校验失败
            // 清楚之前的假设标记
            new_finish[right[--deep]] = false;
            return false;
        }

    }
}