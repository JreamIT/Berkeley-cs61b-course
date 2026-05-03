package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordNet {
    private DirectedGraph graph;
    private Map<Integer, List<String>> numTransWord;

    public WordNet() {
        graph = new DirectedGraph();
        numTransWord = new HashMap<>();
    }

    public WordNet(String synName, String hypName) {
        graph = new DirectedGraph();
        numTransWord = new HashMap<>();
        loadSyn(synName);
        loadHyp(hypName);
    }

    // hlper function to load
    private void loadSyn(String synName) {
        try (BufferedReader temp = new BufferedReader(new FileReader(synName))) {
            String lines;
            while ((lines = temp.readLine()) != null) {
                String[] block = lines.split(",");
                int num = Integer.parseInt(block[0]);
                String[] word = block[1].split(" ");
                ArrayList<String> wordsLists = new ArrayList<>(Arrays.asList(word));
                graph.addNode(num);
                numTransWord.put(num, wordsLists);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void loadHyp(String hypName) {
        try (BufferedReader temp = new BufferedReader(new FileReader(hypName))) {
            String lines;
            while ((lines = temp.readLine()) != null) {
                String[] block = lines.split(",");
                int num1 = Integer.parseInt(block[0]);
                for (int i = 1; i < block.length; i++) {
                    int num2 = Integer.parseInt(block[i]);
                    graph.addEdge(num1, num2);
                }
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public List<String> getHypWord(String words) {
        List<Integer> numList = new ArrayList<Integer>();

        for (Map.Entry<Integer, List<String>> temp : numTransWord.entrySet()) {
            if (temp.getValue().contains(words)) {
                numList.add(temp.getKey());
            }
        }
        List<String> result = new ArrayList<>();

        for (int numLis : numList) {
            List<Integer> hypNums = getHypID(numLis);

            for (int hypNum : hypNums) {
                result.addAll(numTransWord.get(hypNum));
            }
        }
        Set<String> resSet = new HashSet<String>(result);
        List<String> resIsolate = new ArrayList<String>(resSet);

        Collections.sort(resIsolate);
        return resIsolate;
    }

    public List<String> getHypWords(List<String> words) {
        List<List<String>> wordList = new ArrayList<>();
        for (String w : words) {
            wordList.add(getHypWord(w));
        }
        List<String> item = wordList.get(0);
        for (int i = 1; i < wordList.size(); i++) {
            item.retainAll(wordList.get(i));
        }
        return item;
    }


    public List<Integer> getHypID(int num) {
        List<Integer> result = new ArrayList<>();
        List<Integer> childs = graph.getEdges(num);
        int count = 0;

        result.add(num);
        while (childs != null && count < childs.size()) {
            ArrayList<Integer> tempList = new ArrayList<>(childs);
            List<Integer> temp = getHypID(tempList.get(count));
            if (temp == null) {
                break;
            }
            result.addAll(temp);
            count++;
        }
        return result;
    }
}
