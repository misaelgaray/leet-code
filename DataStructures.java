import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.tree.TreeNode;

import java.util.Arrays;
import java.util.Comparator;

public class DataStructures {

    
    static public class ListNode {
         int val;
         ListNode next;
         ListNode(int x) { val = x; }
    }

    static public class Node {
        String value; 
        Node left;
        Node right;

        public Node(String value) {
            this.value = value;
        }
    }

    static public class NodeInt {
        int value; 
        NodeInt left;
        NodeInt right;

        public NodeInt(int value) {
            this.value = value;
        }
    }
    
    
    public static int maxProfit(int[] prices) {
        // the index and the prince 
        int[] profitStart = {0, prices[0]};
        int[] profitEnd = {0, prices[0]};
        int maxProfit =  0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < profitEnd[1]) {
                if (profitEnd[0] < i) {
                    maxProfit += profitEnd[1] - profitStart[1];
                }
                profitStart[0] = i;
                profitStart[1] = prices[i];
            } else if (prices[i] > profitStart[1]) {
                profitEnd[0] = i;
                profitEnd[1] = prices[i];
            }
        }
        return maxProfit;
    }

    public static String getZone(int row, int column) {
        String[] rowLetter = {"a", "b", "c"};
        int zoneLetterIndex = (int) Math.floor(row/3); 
        int zoneNumber = (int) Math.floor(column/3);
        return rowLetter[zoneLetterIndex] + zoneNumber; 
    }

    public static boolean isValidSudoku(char[][] board) {
        //Row index and numbers(different to .) seen in row
        Map<Integer, Set<String>> seenInRows = new HashMap<>(); 
        //Column index and numbers different to . seen 
        Map<Integer, Set<String>> seenInColumns = new HashMap<>(); 

         Map<String, Set<String>> seenInAreas = new HashMap<>(); 
        
        for (int i = 0; i < board.length; i++) { //Columns
            Set<String> valuesinRow = seenInRows.get(i);
            if (valuesinRow == null) {
                valuesinRow = new HashSet<>();
                seenInRows.put(i, valuesinRow);
            }

            
            for (int j = 0; j < board[i].length; j++) {
                String currentValue = board[i][j] + "";

                if (currentValue.equals(".")) {
                    continue; //Skip empty cells
                }

                String zone = getZone(i, j);
                Set<String> valuesInArea = seenInAreas.get(zone);

                if (valuesInArea == null) {
                    valuesInArea = new HashSet<>();
                    seenInAreas.put(zone, valuesInArea);
                }
                
                if (!valuesInArea.add(currentValue)) {
                    return false;
                }

                // There is no repeated values in the current Row
                if (!valuesinRow.add(currentValue)) {
                    return false;
                }

                Set<String> valuesinColumn = seenInColumns.get(j); // each column
                if (valuesinColumn == null) {
                    valuesinColumn = new HashSet<>();
                    seenInColumns.put(j, valuesinColumn);
                }
                //There is no repeated values in the columns
                if (!valuesinColumn.add(currentValue)) {
                    return false;
                }

            }
        }

        return true;
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        
        Integer top = 0;
        Integer bottom = matrix.length - 1;
        Integer left = 0;
        Integer right = matrix[0].length - 1;

        List<Integer> output = new ArrayList<>();

        while (left <= right && top <= bottom) {
         
            for(int i = left; i <= right; i++) {
               output.add(matrix[top][i]);
            }
            top++;
            
           
            for(int i = top; i <= bottom; i++) {
                output.add(matrix[i][right]);
            }
            right--;
            
            if (top <= bottom) {
                for(int i = right; i >= left; i--) {
                    output.add(matrix[bottom][i]);
                }
                bottom--;
            }
           

            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    output.add(matrix[i][left]);
                }
                left++;
            }
        }

        return output;
    }

    public static int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int num = 1;
        int left = 0, right = matrix[0].length - 1, top = 0, bottom = matrix.length - 1;
        while(num <= n*n) {
            for (int i = left; i <= right; i++) {
                matrix[top][i] = num++;
            }
            top++;

            for (int i = top; i <= bottom; i++) {
                matrix[i][right] = num++;
            }
            right--;
            
            if (top <= bottom) {
                for(int i = right; i >= left; i--) {
                    matrix[bottom][i] = num++;
                }
                bottom--;
            }

            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    matrix[i][left] = num++;
                }
                left++;
            }
        }
        return matrix;
    }

    public static void rotate(int[] nums, int k) {
        int secondIndex = k;
        for (int i = 0; i < nums.length - k; i++) {
            int currentVal = nums[i];
            nums[i] = nums[nums.length - secondIndex];
            nums[nums.length - secondIndex - 1] = currentVal;
            secondIndex--;
        }
        System.out.print("Rotated Array: " + java.util.Arrays.toString(nums));
    }
    //{{1,2,3},{4,5,6},{7,8,9}}
    public static void rotateInPlace(int[][] matrix) {
        int n = matrix.length;
        for(int i = 0; i < matrix.length / 2; i++) {
            for (int j = i; j < (matrix[i].length - 1 - i); j++) {
                int topLeft = matrix[i][j];
                matrix[i][j] =  matrix[(n - 1 - i)-(j - i)][i];
                matrix[(n - 1 - i)-(j - i)][i] =  matrix[n - 1 - i][(n - 1 - i)-(j - i)];
                matrix[n - 1 - i][(n - 1 - i)-(j - i)] = matrix[j][n - 1 - i];
                matrix[j][n -1 - i] = topLeft;
               
            }
        }
        System.out.println("Rotated Matrix" + Arrays.deepToString(matrix));
    }

    public static void rotateV2(int[][] matrix) {
        int left = 0, right = matrix.length - 1;
        int top = 0, bottom = matrix.length - 1;

        while (left < right) {
            for (int i = left; i < (right - left); i++ ) {
                int topLeft = matrix[top][left + i];
                matrix[top][left + i] = matrix[bottom - i][left];
                matrix[bottom - i][left] = matrix[bottom][right - i];
                matrix[bottom][right - i] = matrix[top + i][right];
                matrix[top + i][right] = topLeft; 
            }
            left++;
            right--;
            top++;
            bottom--;
        }
        System.out.println("Rotated Matrix" + Arrays.deepToString(matrix));
    }

    public static void rotateWithSpace(int[][] matrix) {
        int n = matrix.length;
        int [][] rotatedMatrix = new int[n][n];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                rotatedMatrix[j][n - 1 - i] = matrix[i][j];
            }
        }
         System.out.println("Rotated Matrix" + Arrays.deepToString(rotatedMatrix));
    }

    public static List<Integer> findDiagonalOrder(int [][]matrix) {
        List<Integer> rows = new ArrayList<>();
        int y = matrix.length;
        int x = matrix[0].length;

        boolean goingUp = true;

        int row = 0;
        int column = 0;
        while (rows.size() < (y * x)) {
            if (goingUp) {
                while (row >= 0 && column < x) {
                    rows.add(matrix[row][column]);
                    column++;
                    row--;
                }
                if (column == x) {
                    column -= 1;
                    row += 2;
                } else {
                    row += 1;
                }
                goingUp = false;
            } else {
                while (row < y && column >= 0) {
                    rows.add(matrix[row][column]);
                    row++;
                    column--;
                }
                if (row == y) {
                    row -= 1;
                    column += 2;
                } else {
                    column += 1;
                }
                goingUp = true;
            }
        }
        // Convert List<Integer> to int[]
        int[] result = new int[rows.size()];
        for (int i = 0; i < rows.size(); i++) {
            result[i] = rows.get(i);
        }
        return result;
    }

    public int[] findDiagonalOrderV2(int[][] mat) {
        int m = mat.length;
       
        int n = mat[0].length;

        int[] res = new int[m * n];
        int i = 0, j = 0, idx = 0;
        boolean up = true; // moving up-right when true, down-left when false

        while (idx < m * n) {
            res[idx++] = mat[i][j];

            if (up) {
                // Try to go up-right
                if (j == n - 1) {        // hit right boundary, go down and flip
                    i++;
                    up = false;
                } else if (i == 0) {     // hit top boundary, go right and flip
                    j++;
                    up = false;
                } else {                 // normal move
                    i--;
                    j++;
                }
            } else {
                // Try to go down-left
                if (i == m - 1) {        // hit bottom boundary, go right and flip
                    j++;
                    up = true;
                } else if (j == 0) {     // hit left boundary, go down and flip
                    i++;
                    up = true;
                } else {                 // normal move
                    i++;
                    j--;
                }
            }
        }
        return res;
    }


    public static int[][] matrixReshape(int[][] mat, int r, int c) {
        int [][] reshaped = new int[r][c];

        if (r == mat.length && c == mat[0].length ||  r * c < mat.length * mat[0].length) {
            return mat;
        }

        if (r * c > mat.length * mat[0].length) {
            return mat;
        }

        int row = 0, column = 0;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                reshaped[row][column] = mat[i][j];
                column++;
                if (column == c) {
                    column = 0;
                    row++;
                }
            }
        }
        return reshaped;
    }

    //O(m*n)
    public static boolean isToeplitzMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length ;
        for(int i = 0; i < cols; i++ ) {
            int tempI = 0;
            int tempJ = i;
            int val = matrix[tempI][tempJ];
            while (tempI < rows && tempJ < cols ) {
                if (val != matrix[tempI][tempJ]) {
                    return false;
                }
                tempI++;
                tempJ++;
            }
        }

        for(int i = 0; i < rows; i++ ) {
            int tempI = i;
            int tempJ = 0;
            int val = matrix[tempI][tempJ];
            while (tempI < rows && tempJ < cols ) {
                if (val != matrix[tempI][tempJ]) {
                    return false;
                }
                tempI++;
                tempJ++;
            }
        }

        return true;
    }


    public static int largestOverlap(int[][] img1, int[][] img2) {
        //Store the position where the value is 1
        List<Integer[]> overlapsImg1 = new ArrayList<>(); //[{0,1}, {1,0}]
        List<Integer[]> overlapsImg2 = new ArrayList<>();
        for(int i = 0; i < img1.length; i++) {
            for(int j = 0; j < img1[i].length; j++) {
                if (img1[i][j] == 1) {
                    overlapsImg1.add(new int[]{i, j});
                }
                if (img2[i][j] == 1) {
                    overlapsImg2.add(new int[]{i, j});
                }
            }
        }

        Map<String, Integer> overlapTimes = new HashMap<>();
        for (int i = 0; i < overlapsImg1.size(); i++) {
            Integer[] position1 = overlapsImg1.get(i);
            for (int j = 0; j < overlapsImg2.size(); j++) {
                Integer[] position2 = overlapsImg2.get(j);
                String crossPosition = (position2[0] - position1[0]) + "," + (position2[1] - position1[1]);
                Integer timesInOperation = overlapTimes.get(crossPosition);
                if (timesInOperation == null) {
                    overlapTimes.put(crossPosition, 1);
                } else {
                    timesInOperation++;
                    overlapTimes.put(crossPosition, timesInOperation);
                }
            }
        }

        int maxOverlap = 0;
        for (int count : overlapTimes.values()) {
            if (count > maxOverlap) {
                maxOverlap = count;
            }
        }

        return maxOverlap;
    }

    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            char current = s[left];
            s[left] = s[right]; 
            s[right] = current;
            left++;
            right--;
        }
    }

    public static int reverse(int x) {
        if (x < Integer.MIN_VALUE || x > Integer.MAX_VALUE) {
            return 0;
        }

        int power = (int) Math.log10(Math.abs((long) x));
        int digits = power + 1;
        
        long result = 0; //234

        for (int i = 0; i < digits; i++) {
            int lastDigit = x % 10; //4
            result = result * 10 + lastDigit;

            if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE) {
                return 0;
            }
            x = x / 10;
        }

        return (int) result;
    }

    public static int maxProfitV2(int[] arr) {
        int maxProfix = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] < arr[i]) {
                maxProfix += arr[i] - arr[i - 1];
            }
        }
        return maxProfix;
    }

    public static void deleteNode(ListNode node) {
        if (node.next != null) {
            node.val = node.next.val;
            if (node.next.next != null) {
                node.next = node.next.next;
            } else {
                node.next = null;
            }
            
        }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode slow = head;
        ListNode fast = head;
        for (int i = 0; i < n ; i++) {
            fast = fast.next;
        }

        if (fast == null) {
            return  head.next;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        ListNode delete = slow.next;
        slow.next = slow.next.next;
        delete = null;
        return head;
    }


    public static int[] solution(int[] numbers) {
        int[] tripplets = new int[numbers.length - 2];
        //1,2,1,3,4
        int index = 1;
        int trippletsIndex = 0;
        while ((index + 1) < numbers.length) {//2 < 5
            //index = 3, 
            //1 > 3 < 3 = false || 1 < 3 > 4 = false
            boolean zigZag1 =  numbers[index - 1] > numbers[index] && numbers[index] < numbers[index + 1];
            boolean zigZag2 = numbers[index - 1] < numbers[index] && numbers[index] > numbers[index + 1];
            if (zigZag1 || zigZag2) {
                tripplets[trippletsIndex] = 1; //triplet{1, 1, }
            } else {
                tripplets[trippletsIndex] = 0; //triplet {1, 1, 0}
            }
            index++; //index = 4
            trippletsIndex = 0; // trippletsIndex = 3
        }
        return tripplets;
    }

    public static int solution2(int[] A) {
        //1,3,6,4,1,2
        Set<Integer> lookUp = new HashSet<>();
        int smallest = A[0]; // 1
        for(int current : A) {
            if (current < smallest) {
                smallest = current;
            }
            lookUp.add(current);
        }

        int result = smallest; // 1
        for (int i = smallest; i < (A.length + smallest); i++) {
            result = i; //5
            if (!lookUp.contains(result)) {
                return result;
            }
        }

        return result;
    }
    

    // Helper method to reduce a number to a single-digit sum
    private int digitRoot(int n) {
        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        if (sum >= 10) {
            return digitRoot(sum);
        }
        return sum;
    }


    public static int removeDuplicates(int[] nums) {
        //Store the number and its index 
        int nonRepeatedIdx = 0;
        int currentIdx=1;
        while (currentIdx < nums.length) {
            if (nums[nonRepeatedIdx] == nums[currentIdx]) {
                currentIdx++;
            } else {
                nonRepeatedIdx++;
                nums[nonRepeatedIdx] = nums[currentIdx];
                currentIdx++;
            }
        }
        System.out.println("Array without duplicates" + Arrays.toString(nums));
        return nonRepeatedIdx + 1;
    }

    

    public static void rotateV3(int[][] matrix) {
        
        int left = 0; int right = matrix[0].length - 1;
        int top = 0; int bottom = matrix.length - 1;

        while (left <= right) {
            for (int i = 0; i < right - left; i ++) {
                int first = matrix[top][left + i];
                matrix[top][left + i] = matrix[bottom - i][left];
                matrix[bottom - i][left] = matrix[bottom][right - i];
                matrix[bottom][right - i] = matrix[top + i][right];
                matrix[top + i][right] = first;
            }
            top++;
            bottom--;
            left++;
            right--;
        }
       
        System.out.println("Matriz rotada" + Arrays.deepToString(matrix));
    }

    public String getArea(int i, int j) {
        int areaNumber = (int) Math.floor(j / 3);
        int sectorNumber = (int) Math.floor(i / 3);
        char[] sector = new char[]{'a', 'b', 'c'}; 
        return sector[sectorNumber] +"" + areaNumber;
    }
    
    public boolean isValidSudokuV2(char[][] board) {
        //Row index and numbers(different to .) seen in row
        Map<Integer, Set<String>> seenInRow = new HashMap<>(); 
        //Column index and numbers different to . seen 
        Map<Integer, Set<String>> seenInColumn = new HashMap<>();
        
        Map<String, Set<String>> seenInSector = new HashMap<>();
        
        
        for (int i = 0; i < board.length; i++) { //Columns
            Set<String> itemsInRow = seenInRow.get(i);
            if (itemsInRow == null) {
                itemsInRow = new HashSet<>();
                seenInRow.put(i, itemsInRow);
            }
            
            
            for (int j = 0; j < board[i].length; j++) {
                String currentValue = String.valueOf(board[i][j]);
                if (currentValue.equals(".")) {
                    continue;
                }
                String area = getArea(i, j);
                Set<String> itemsInSector = seenInSector.get(area);
                if (itemsInSector == null) {
                    itemsInSector = new HashSet<>();
                    seenInSector.put(area, itemsInSector);
                }
                
                if (!itemsInSector.add(currentValue)) {
                    return false;
                }
                
                if (!itemsInRow.add(currentValue)) {
                    return false; //If any value in current row is repeated then return 0
                }
                
                Set<String> itemsInColumn = seenInColumn.get(j);
                if (itemsInColumn == null) {
                    itemsInColumn = new HashSet<>();
                    seenInColumn.put(j, itemsInColumn);
                }
                
                if (!itemsInColumn.add(currentValue)) {
                    return false;
                }
            }
        }
        
        return true;
    }

    public static void traverse(Node root) {
        System.out.println(root.value);
        if (root.left != null) {
            traverse(root.left);
        }
        if (root.right != null) {
            traverse(root.right);
        }
    }

    public static int sumHeight(Node root) {
        if (root == null) {
            return 0;
        }
        int sumLeft = sumHeight(root.left);
        int sumRight = sumHeight(root.right);

        return Math.max(sumLeft, sumRight) + 1;
    }

    public static boolean validateHelper(NodeInt root, int max, int min) {
        if (root == null) {
            return true;
        }

        if (root.value >= max || root.value <= min) {
            return false;
        }
        return validateHelper(root.left, root.value, min) && validateHelper(root.right, max, root.value);
    }

    public static boolean isValidBST(NodeInt root) {
        return validateHelper(root, Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    public static void rotateV2(int[] nums, int k) {
        
        for (int i = 0; i < k; i++) {
            int lastValue = nums[nums.length - 1];
            int currentValue = nums[0];
            for (int j = 1; j < nums.length; j++) {
                int local = nums[j];
                nums[j] = currentValue;
                currentValue = local;
            }
            nums[0] = lastValue;
        }
        System.out.println("Rotated array" + Arrays.toString(nums));
    }

    //[1,2,3,4,5,6,7]
    //[7,6,5,4,3,2,1]
    //[]
    public static void rotateV3(int[] nums, int k) {
        int n = nums.length;
        int max = k%n;
        basicRotation(nums,0, n - 1);
        basicRotation(nums, 0, max - 1);
        basicRotation(nums, max, n - 1);
        
        System.out.println("Rotated array" + Arrays.toString(nums));
    }

    public static void basicRotation(int[] nums, int start, int end) {
        while (start < end) {
            int endVal = nums[end];
            nums[end] = nums[start];
            nums[start] = endVal;
            start++;
            end--;
        }
    }

    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (!seen.add(nums[i])) {
                return true;                
            }
        }
        return false;
    }

    public static int singleNumber(int[] nums) {
        int a = 0;
        for (int i = 0; i < nums.length; i++) {
            a ^= nums[i];
        }
        return a;
        
    }

    public static int[] intersect(int[] nums1, int[] nums2) {
        List<Integer> output = new ArrayList<>();

        Map<Integer, Long> counter = Arrays.stream(nums1)
            .boxed()
            .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        
        for (int num2 : nums2) {
            Long count = counter.get(num2);
            if (count != null && count > 0) {
                output.add(num2);
                counter.put(num2, count - 1);
            }
        }
        
        int[] results = new int[output.size()];
        int index = 0;
        for (Integer number : output) {
            results[index++] = (int) number;
        }
        
        return results;
    }

    public static int[] intersectV2(int[] nums1, int[] nums2) {
        List<Integer> output = new ArrayList<>();

        int i = 0, j = 0;
        
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] > nums2[j]) {
                j++;
            } else if (nums1[i] < nums2[j]) {
                i++;
            } else {
                output.add(nums1[i]);
                i++;
                j++;
            }
        }
        
        int[] results = new int[output.size()];
        int index = 0;
        for (Integer number : output) {
            results[index++] = (int) number;
        }
        
        return results;
    }

    public static int[] plusOne(int[] digits) {
        int n = digits.length - 1;
        while (n >= 0) {
            if (digits[n] + 1 == 10) {
                digits[n] = 0;
            } else {
                digits[n] += 1;
                return digits;
            }
            n--;
        }
        
        int newLength = digits.length + 1;
        int[] result = new int[newLength];
        result[0] = 1;
        return result;
    }


    public static void wordLadder(String startWord, String endWord, String[] dictionary){
        Set<String> dictionarySet = Arrays.stream(dictionary)
                                        .collect(Collectors.toSet());
        List<String> results = new ArrayList<>();
        while (dictionarySet.size() > 0) {
            for (int i = 0; i < startWord.length(); i++) {
                String newWorld = null;
                for (char letter = 'a'; letter <= 'z'; letter++) {
                    String replacedWord = startWord.substring(0, i) + letter + startWord.substring( i + 1);
                    if (dictionarySet.contains(replacedWord)) {
                        dictionarySet.remove(replacedWord);
                        results.add(replacedWord);
                        newWorld = replacedWord;
                        break;
                    }
                }
                if (newWorld != null) {
                    startWord = newWorld;
                    break;
                }
            }
        }
        System.out.println("Array final: " + results.toString());
    }

    public static splitArrayLargestSum(int [] nums, int m) {
        int totalSum = Arrays.stream(nums)
                        .boxed()
                        .reduce(0, Integer::sum);
        int maxNum = Arrays.stream(nums)
                        .max()
                        .orElse(0);
        
        while (maxNum <= totalSum ) {
            int mid = (totalSum - maxNum) / 2;
        }
        
    }

    public static int searchInsert(int[] nums, int target) {
        int start = 0;
        int end = nums.length-1;

        while (start <= end) {
            int mid = start + (end-start)/2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                end = mid-1;
            } else {
                start = mid+1;
            }
        }

        return start;
    }

    public static boolean searchMatrix(int[][] matrix, int target) {
        int leftI = 0;
        int rightI = matrix.length - 1;
        while (leftI <= rightI) {
            int midI = leftI + (rightI - leftI) / 2;
            if (matrix[midI][0] == target) {
                return true;
            }
            if (target > matrix[midI][0]) {
                int leftJ = 0; 
                int rightJ = matrix[midI].length - 1;
                while (leftJ <= rightJ) {
                    int midJ = leftJ + (rightJ - leftJ) / 2;
                    if (matrix[midI][midJ] == target) {
                        return true;
                    }

                    if (target > matrix[midI][midJ]) {
                        leftJ = midJ + 1;
                    } else {
                        rightJ = midJ - 1;
                    }
                }
                leftI = midI + 1;
            } else {
                rightI = midI - 1;
            }          
        }

        return false;
    }


    public static int mySqrt(int x) {
        int left = 0;
        int right = x; 

        while (left <= right) {
            int mid = left + (right - left) / 2;
            long squared = (long) mid * mid;
            if (squared == x) {
                return mid;
            }

            if (squared > x) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return left - 1;
    }

    public static int[] flat2DArray(int[][] matrix) {
        int[] flattedArray = new int[matrix.length * matrix[0].length];
        int index = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                flattedArray[index] = matrix[i][j]; 
                index++;
            }
        }
        return flattedArray;
    }

    //{1,12,-5,-6,50,3}
    //k = 4
    public static void findBiggestSubarrayAverage(int [] array, int k) {
        
        int left = 0;
        int average = 0;
        while (left <= array.length - k) {
            int n = (left + k);
            int sum = 0;
            for (int i = left; i < n; i++) {
                sum += array[i];
            }
            System.out.println("Promerdio :" + sum);
            average = Math.max(average, sum);
            left++;
        }
        System.out.println("Promedio final: " + average);
    }

    public static void slidingWindow(int [] array, int k) {
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += array[i];
        }
        int average = sum / k;
        System.out.println("Average: " + average);
        for (int i = k; i < array.length; i++) {
            sum = sum + array[i] - array[i - k];
            average = Math.max(average, sum / k);
            System.out.println("Average: " + sum / k);
        }
        System.out.println("Best average: " + average);
    }

    public static void slidingWindow2(String s) {
        String [] letters = s.split();
        int left = 0;
        int maxLength = 0;
        Set<String> lookUp = new HashSet<>();
        for (int i = 0; i < letters.length; i++) {
            while(!lookUp.contains(letters[i])) {
                lookUp.remove(letters[left]);
                left++;
            }

            lookUp.add(letters[i]);
            maxLength = Math.max(maxLength, i - left + 1);

        }
    }


    static class KthLargest {

        private List<Integer> numbers;
        private PriorityQueue<Integer> queue;
        private int k;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            numbers = new ArrayList<>();
            queue = new PriorityQueue<>();
            //queue = new PriorityQueue<>(Comparator.reverseOrder()); MaxHEap
            for (int num : nums) {
                queue.add(num);
                numbers.add(num);
            }

            while (queue.size() > k) {
                queue.poll();
            }
        }
        
        public int add(int val) {
            queue.add(val);
            while (queue.size() > k) {
                queue.poll();
            }
            return queue.peek();
        }
    }

    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        //Will store the words that belogs to a pattern
        Map<String, Set<String>> patternsDictionary = new HashMap<>();   
        Set<String> wordSet = new HashSet<>(wordList);

        if (!wordSet.contains(endWord)) return 0;

        for (String word : wordList) {
            for(int i = 0; i < beginWord.length(); i++) {
                String pattern = word.substring(0, i) + "*" + word.substring(i+1);
                patternsDictionary.computeIfAbsent(pattern, k -> new HashSet<>()).add(word);
            } 
        } 

        Queue<String> dequeue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        dequeue.add(beginWord);
        visited.add(beginWord);
        int pathSize = 1;
        while(!dequeue.isEmpty()) {
            int size = dequeue.size();
            for (int i = 0; i < size; i++) {
                String currentWord = dequeue.poll();
               
                for (int j = 0; j < beginWord.length(); j++) {

                    String pattern = currentWord.substring(0, j) + "*" + currentWord.substring(j+1);
                    Set<String> neighbors = patternsDictionary.getOrDefault(pattern, new HashSet<>());              
                    for (String neighbor : neighbors) {
                        if (neighbor.equals(endWord)) {
                            return pathSize + 1;
                        }
                        if (!visited.contains(neighbor)) {
                            visited.add(neighbor);
                            dequeue.add(neighbor);
                        }
                    }

                }  
            }
            pathSize++;
        }

        return 0;
    }

    public static int ladderLengthV2(String beginWord, String endWord, List<String> wordList) {
        Map<String, Set<String>> patternsDictionary = new HashMap<>();
        Set<String> wordSet = new HashSet<>(wordList);

        if (!wordSet.contains(endWord)) return 0;

        int wordLength = beginWord.length();

        for (String word : wordList) {
            for (int i = 0; i < wordLength; i++) {
                String pattern = word.substring(0, i) + "*" + word.substring(i + 1);
                patternsDictionary.computeIfAbsent(pattern, k -> new HashSet<>()).add(word);
            }
        }

        Queue<String> dequeue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        dequeue.add(beginWord);
        visited.add(beginWord);
        int pathSize = 1;

        while (!dequeue.isEmpty()) {
            int size = dequeue.size();
            for (int i = 0; i < size; i++) {
                String currentWord = dequeue.poll();

                for (int j = 0; j < wordLength; j++) {
                    String pattern = currentWord.substring(0, j) + "*" + currentWord.substring(j + 1);
                    Set<String> neighbors = patternsDictionary.getOrDefault(pattern, new HashSet<>());

                    for (String neighbor : neighbors) {
                        if (neighbor.equals(endWord)) {
                            return pathSize + 1;
                        }
                        if (!visited.contains(neighbor)) {
                            visited.add(neighbor);
                            dequeue.add(neighbor);
                        }
                    }
                }
            }
            pathSize++;
        }

        return 0;
    }

   
    public static void main(String[] args) {
        // int[] prices = { 7,1,5,3,6,4};
        // int result = maxProfit(prices);
        // System.out.println("Maximum Profit: " + result);

        // int[] nums = {1,2,3,4,5,6,7};
        // int k = 3;
        // rotate(nums, k);

        // char[][] board = {
        //     {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
        //     {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
        //     {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
        //     {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
        //     {'4', '.', '6', '8', '.', '3', '.', '.', '1'},
        //     {'7', '.', '.', '2', '.', '.', '.', '.', '.'},
        //     {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
        //     {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
        //     {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        // };
        // boolean isValid = isValidSudoku(board);
        // System.out.println("Is the Sudoku valid? " + isValid);

        // int matrix[][] = {
        //     {1,2,3,4},
        //     {5,6,7,8},
        //     {9,10,11,12}
        // };
        // List<Integer> spiral = spiralOrder(matrix);
        // System.out.println("Spiral Order: " + spiral);

        // int[][] generatedMatrix = generateMatrix(3);
        // System.out.println("Generated Matrix: " + java.util.Arrays.deepToString(generatedMatrix));

        int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        //rotateInPlace(matrix);
        //rotateWithSpace(matrix);
        //rotateV2(matrix);
        // List<Integer> result = findDiagonalOrder(matrix);
        // System.out.println("REsult" + result);

        // int[][] reshapedMatrix = matrixReshape(matrix, 2, 5);
        // System.out.println(Arrays.deepToString(reshapedMatrix));


        // int[][] matrixTapiz = {{1,2,3,4},{5,1,2,3},{9,5,1,2}};

        // System.out.println(isToeplitzMatrix(matrixTapiz));

        // System.out.println("Reversed: " + reverse(-2147483648));

        // System.out.println("Max profit: " + maxProfitV2(new int[]{ 7,1,5,3,6,4 }));

        // System.out.println("TRIPPLETS: " + Arrays.toString(solution(new int[]{1, 2, 1, 3, 4})));

        // System.out.println("Minimum:"+ solution2(new int[] {1, 2, 3}));

        //System.out.println("Total of not duplicated" + removeDuplicates(new int[]{0,0,1,1,1,2,2,3,3,4}));

        // int[][] arr = {
        //     {2, 29, 20, 26, 16, 28},
        //     {12, 27, 9, 25, 13, 21},
        //     {32, 33, 32, 2, 28, 14},
        //     {13, 14, 32, 27, 22, 26},
        //     {33, 1, 20, 7, 21, 7},
        //     {4, 24, 1, 6, 32, 34}
        // };
        // rotateV3(arr);

        // Node a = new Node("A");
        // Node b = new Node("B");
        // Node c = new Node("C");
        // Node d = new Node("D");
        // Node e = new Node("E");
        // Node f = new Node("F");
        
        // a.left = b;
        // a.right = c;
        // b.left = d;
        // b.right = e;
        // c.right = f;
        // traverse(a);

        // NodeInt a = new NodeInt(2);
        // NodeInt b = new NodeInt(2);
        // NodeInt c = new NodeInt(2);
        // a.left = b;
        // a.right = c;

        // System.out.println("Is valid " + isValidBST(a));
        //rotateV3(new int[]{1,2,3,4,5,6,7}, 3);

        // int[] instersection = intersect(
        //     new int[]{4,5,9,9},
        //     new int[]{4,4,8,9,9}
        // );

        // System.out.println("Intersection:" + Arrays.toString(instersection) );

       // System.out.println("Plus one" + Arrays.toString(plusOne(new int[]{1,2,3})));

       //System.out.println("Result of search" + searchInsert(new int[] {1,3,5,6}, 0));

       //System.out.println("Squared root of" + mySqrt(2147395599));
       //wordLadder("hit", "cog", new String[] {"hot","dot","dog","lot","log","cog"});
        // System.out.println("flat2DArray: " + Arrays.toString(flat2DArray(new int[][]{
        //     {1,2,3,4,5,6},
        //     {7,8,9,10,11,12}
        // })));

        //
        //findBiggestSubarrayAverage(new int[] {8, 3, -2, 4, 5, -1, 0, 5, 3, 9, -6}, 5);
       // slidingWindow(new int[] {8, 3, -2, 4, 5, -1, 0, 5, 3, 9, -6}, 5);

       searchMatrix(new int[][] {
            {1,2,4,8},
            {10,11,12,13},
            {14,20,30,40}
       }, 10);
    }
}