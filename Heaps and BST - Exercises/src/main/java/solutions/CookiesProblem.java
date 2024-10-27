package solutions;

import java.util.*;

public class CookiesProblem {
    public Integer solve(int requiredSweetness, int[] cookies) {
        Queue<Integer> cookiesQueue = new PriorityQueue<>();
        for (int cookie : cookies) {
            cookiesQueue.add(cookie);
        }
        int currentMinSweetness = cookiesQueue.peek();
        int steps = 0;
        while (currentMinSweetness < requiredSweetness && cookiesQueue.size() > 1) {
            steps++;
            int leastSweetCookie = cookiesQueue.poll();
            int secondLeastSweetCookie = cookiesQueue.poll();

            int combinedSweetCookie = leastSweetCookie + 2 * secondLeastSweetCookie;

            cookiesQueue.add(combinedSweetCookie);

            currentMinSweetness = cookiesQueue.peek();
        }
        return currentMinSweetness > requiredSweetness ? steps : -1;
    }
}
