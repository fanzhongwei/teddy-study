package com.teddy.acm.a1健步走活动;

import java.util.*;

/**
 * 健步走活动
 *
 * @author fanzhongwei
 * @date 2023/06/03 09:05
 **/
public class FindNumber {

    /**
     * 找到补发的手环编号
     *
     * @param nums  全部手环编号（数组）
     * @returns 补发的手环编号
     */
    public static int find(int[] nums) {
        int missingNumber = 0;
        for (int num : nums) {
            missingNumber ^= num;
        }
        return missingNumber;
    }

    public static int findBaseCourse(int n, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] inDegree = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            graph.put(i, new ArrayList<>());
        }
        for (int[] prerequisite : prerequisites) {
            int course = prerequisite[0];
            int preCourse = prerequisite[1];
            graph.get(preCourse).add(course);
            inDegree[course]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        int count = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            count++;
            for (int nextCourse : graph.get(course)) {
                inDegree[nextCourse]--;
                if (inDegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }
        return count == n ? queue.poll() : -1;
    }


/**
 * 找到基础课程
 * @param total 课程总数
 * @param courseArray 课程关系数组
 * @return 存在基础课程则返回基础课程编号；不存在基础课程返回-1
 */
    public static int findCourse(int total, int[][] courseArray) {
        if (total == 1) {
            return -1;
        }
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] prerequisite : courseArray) {
            int course = prerequisite[0];
            int preCourse = prerequisite[1];
            graph.computeIfAbsent(preCourse, k -> new ArrayList<>()).add(course);
        }
        if (graph.size() > 1) {
            return -1;
        }
        Integer baseCourse = graph.keySet()
                               .stream()
                               .findFirst()
                               .orElse(-1);
        if (graph.get(baseCourse).size() != total - 1) {
            return -1;
        }
        return baseCourse;
//        if (total == 1) {
//            return -1;
//        }
//        int[] commonCourseArr = new int[total+1];
//        for (int i = 0; i < courseArray.length; i++) {
//            int commonCourse = courseArray[i][1];
//            commonCourseArr[commonCourse] = commonCourseArr[commonCourse] + 1;
//        }
//
//        // 这里要排除自己
//        int num = total - 1;
//        for (int i = 1; i < commonCourseArr.length; i++) {
//            if (commonCourseArr[i] == num) {
//                return i;
//            }
//        }
//        return -1;
    }

    /**
     * 找出电脑被病毒感染的员工
     *
     * @param n 该部门员工总数
     * @param logs 公司文件传输记录
     * @param firstUser 第一个被员工0传染的员工编号
     * @returns 员工编号列表
     */
    public static List<Integer> findAllUser1(int n, int[][] logs, int firstUser) {
        int minTime = -1;
        for (int i = 0; i < logs.length; i++) {
            if (logs[i][0] == firstUser || logs[i][1] == firstUser) {
                if (-1 == minTime) {
                    minTime = logs[i][2];
                } else {
                    minTime = Math.min(minTime, logs[i][2]);
                }
            }
        }

        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i <= n; i++) {
            graph.put(i, new ArrayList<>());
        }
        boolean[] infected = new boolean[n + 1];
        infected[firstUser] = true;
        infected[0] = true;
        for (int[] record : logs) {
            int from = record[0];
            int to = record[1];
            if (from == firstUser || to == firstUser) {
                infected[from] = true;
                infected[to] = true;
            }
        }
        for (int[] record : logs) {
            int from = record[0];
            int to = record[1];
            int time = record[2];
            if ((infected[from] && time >= minTime) || from == 0) { // 只考虑被感染后传输的文件
                graph.get(from).add(to);
                graph.get(to).add(from);
                infected[to] = true;
            } else if ((infected[to] && time >= minTime) || to ==0) { // 只考虑被感染后传输的文件
                graph.get(to).add(from);
                graph.get(from).add(to);
                infected[from] = true;
            }
        }
        boolean[] visited = new boolean[n + 1];
        visited[firstUser] = true;
        visited[0] = true;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(firstUser);
        while (!queue.isEmpty()) {
            int employee = queue.poll();
            for (int neighbor : graph.get(employee)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            if (visited[i]) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * 找出电脑被病毒感染的员工
     *
     * @param n 该部门员工总数
     * @param logs 公司文件传输记录
     * @param firstUser 第一个被员工0传染的员工编号
     * @returns 员工编号列表
     */
    public static List<Integer> findAllUser(int n, int[][] logs, int firstUser) {
        int minTime = 0;
        // 初始化每个有文件传输的关系的用户
        Map<Integer, Map<Integer, Integer>> userRel = new HashMap<>(logs.length);
        for (int i = 0; i < logs.length; i++) {
            userRel.computeIfAbsent(logs[i][0], k -> new HashMap<>()).put(logs[i][1], logs[i][2]);
            userRel.computeIfAbsent(logs[i][1], k -> new HashMap<>()).put(logs[i][0], logs[i][2]);
            if (logs[i][0] == firstUser || logs[i][1] == firstUser) {
                minTime = Math.min(minTime, logs[i][2]);
            }
        }

        Set<Integer> result = new TreeSet<>();
        result.add(0);
        result.add(firstUser);
        // 直接文件传输
        Map<Integer, Integer> hasVirus = userRel.get(firstUser);
        if (null == hasVirus) {
            return new ArrayList<>(result);
        }
        result.addAll(hasVirus.keySet());
        // 间接文件传输
        hasVirus.forEach((user, time) -> {
            if (user != firstUser && userRel.containsKey(user)) {
                find(userRel, userRel.get(user), result, firstUser, time);
            }
        });

        return new ArrayList<>(result);
    }

    public static void find(Map<Integer, Map<Integer, Integer>> userRel, Map<Integer, Integer> hasVirus, Set<Integer> result, int firstUser, int time) {
        Iterator<Map.Entry<Integer, Integer>> iterator = hasVirus.entrySet()
                                                                 .iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            int user = entry.getKey();
            int ctime = entry.getValue();
            if (user != firstUser && ctime >= time && !result.contains(user)) {
                result.add(user);
                if (user != firstUser && userRel.containsKey(user)) {
                    find(userRel, userRel.get(user), result, firstUser, ctime);
                }
            }
        }
    }


    /**
     * 计算可消灭的最多敌人数
     *
     * @param grid  据点网格（二维数组）
     * @returns {number} 可消灭最多敌人数
     */
    public static int maxDestorySum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];

        // 初始化第一列的路径权重
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }

        // 初始化第一行的路径权重
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j-1] + grid[0][j];
        }

        // 计算其他位置的路径权重
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        }

        return dp[m-1][n-1];
    }


    /**
     * 计算参赛人员的最大总得分
     * @param scoreArray 表示每个靶子分数的数组
     * @return 最大总得分
     */
    public static int maxScores(int[] scoreArray) {
        int[] count = new int[11]; // 记录每个分数的靶子的数量
        for (int target : scoreArray) {
            count[target]++;
        }
        int score = 0;
        int i = 10; // 从最高分的靶子开始射击
        while (i >= 0) {
            if (count[i] == 0) { // 如果当前分数的靶子数量为0，跳过这个分数
                i--;
                continue;
            }
            score += i; // 将当前分数加入总分中
            count[i]--; // 将当前分数的靶子数量减1
            if (i >= 2 && count[i - 1] >= count[i] && count[i - 2] >= count[i]) { // 如果存在相邻的两个分数，且这两个分数的靶子数量都不小于当前分数的靶子数量，那么可以继续射击这两个分数的靶子
                score += (i - 1 + i - 2) * count[i]; // 将这两个分数的靶子加入总分中
                count[i - 1] -= count[i]; // 将这两个分数的靶子数量减去当前分数的靶子数量
                count[i - 2] -= count[i];
            }
            i--; // 更新当前射击的分数
        }
        return score;
    }

    /**
     * 计算参赛人员的最大总得分
     * @param scoreArray 表示每个靶子分数的数组
     * @return 最大总得分
     */
    public static int maxScores1(int[] scoreArray) {
        int n = scoreArray.length;
        return 0;
    }

}
