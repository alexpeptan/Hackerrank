package algorithms.GraphTheory.RoadsAndLibraries;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by apeptan on 4/2/2017.
 */
public class Solution {
    private UnionFind algo;

    public Solution(int n){
        algo = new UnionFind(n);
    }

    class UnionFind{
        class Node{
            int x;
            Node parent;
            public Node(int x){
                this.x = x;
                this.parent = this;
            }
        }

        private Node[] uf;
        public UnionFind(int n){
            uf = new Node[n+1];
            for(int i=1; i<=n; i++){
                uf[i] = new Node(i);
            }
        }

        public void union(int x, int y){
            Node rootx = find(x);
            Node rooty = find(y);
            rootx.parent = rooty;
        }

        public Node find(int x){
            if(uf[x].parent.x == x){
                return uf[x];
            } else {
                Node leader = find(uf[x].parent.x);
                uf[x].parent = leader; // path compression - union by rank optimizes even more
                return leader;
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            int n = in.nextInt();
            int m = in.nextInt();
            long libCost = in.nextInt();
            long roadCost = in.nextInt();

            if(roadCost >= libCost){
                System.out.println(n*libCost);
                for(int a1 = 0; a1 < m; a1++){
                    int city_1 = in.nextInt(); // just consume input
                    int city_2 = in.nextInt();
                }

                continue;
            }

            Solution sol = new Solution(n);

            for(int a1 = 0; a1 < m; a1++){
                int city_1 = in.nextInt();
                int city_2 = in.nextInt();
                sol.algo.union(city_1, city_2);
            }

            Map<Integer, Long> freqMap = new HashMap<>();
            for(int i=1; i<=n; i++){
                UnionFind.Node leader = sol.algo.find(i);
                Long leaderFreq = freqMap.get(leader.x);
                if(leaderFreq == null){
                    freqMap.put(leader.x, 1L);
                } else {
                    freqMap.put(leader.x, leaderFreq + 1);
                }
            }

            long cost = 0;
            for(Long freq : freqMap.values()){
                cost +=  libCost + (freq - 1)*roadCost;
            }

            System.out.println(cost);
        }
    }
}
