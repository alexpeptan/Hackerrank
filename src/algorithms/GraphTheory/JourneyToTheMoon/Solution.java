package algorithms.GraphTheory.JourneyToTheMoon;

import java.util.*;

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
            int name;
            Node parent;
            public Node(int x){
                this.name = x;
                this.parent = this;
            }
        }

        private Node[] uf;
        public UnionFind(int n){
            uf = new Node[n];
            for(int i=0; i<n; i++){
                uf[i] = new Node(i);
            }
        }

        public void union(int x, int y){
            Node rootx = find(x);
            Node rooty = find(y);
            rootx.parent = rooty;
        }

        public Node find(int x){
            if(uf[x].parent.name == x){
                return uf[x];
            } else {
                Node leader = find(uf[x].parent.name);
                uf[x].parent = leader; // path compression - union by rank optimizes even more
                return leader;
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int p = in.nextInt();
        Solution sol = new Solution(n);

        for(int k = 0; k < p; k++){
            int astronaut1 = in.nextInt();
            int astronaut2 = in.nextInt();

            sol.algo.union(astronaut1, astronaut2);
        }

        Map<Integer, Long> populationMap = new HashMap<>();
        for(int astronaut=0; astronaut<n; astronaut++){
            UnionFind.Node country = sol.algo.find(astronaut);
            Long population = populationMap.get(country.name);
            if(population == null){
                populationMap.put(country.name, 1L);
            } else {
                populationMap.put(country.name, population + 1);
            }
        }

        long crewOptions = getCrewOptions(n, populationMap);

        System.out.println(crewOptions);
    }

    private static long getCrewOptions(int n, Map<Integer, Long> populationMap) {
        long crewOptions = 0;
        Long[] parr = populationMap.values().toArray(new Long[populationMap.values().size()]);

        Arrays.sort(parr);
        int lastSinglePopulationIdx = -1;
        for(int i=0; i<parr.length; i++){
            if(parr[i] == 1){
                lastSinglePopulationIdx = i;
            } else{
                break;
            }
        }

        long oneManShowCountries = lastSinglePopulationIdx + 1;

        long astronautsThatAreNotAlone = 0;

        // adding collaboration between 2+ people teams
        for(int i=lastSinglePopulationIdx + 1; i<parr.length; i++){
            astronautsThatAreNotAlone += parr[i];
            for(int j=i+1; j<parr.length; j++){
                crewOptions += 1L*parr[i]*parr[j];
            }
        }

        // adding collaboration possible only between one man show countries
        crewOptions += oneManShowCountries*(oneManShowCountries - 1)/2;

        // adding inter-collaboration
        crewOptions += oneManShowCountries * astronautsThatAreNotAlone;

        return crewOptions;
    }
}
