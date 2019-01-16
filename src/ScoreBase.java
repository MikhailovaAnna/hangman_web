import java.io.*;
import java.util.*;
import java.util.logging.Logger;

class ScoreBase {
    private final String path="/home/ann/git/hangman_web/src/resource/score.txt";
    private static final TreeMap<String, Integer> scoreList = new TreeMap<>();
    private final HashMap<Integer, ArrayList<String>> records= new HashMap<>();
    private final Logger logger= Logger.getLogger(ScoreBase.class.getName());


    public ScoreBase(){
        try {
            readFile();
        }catch (IOException e){
            logger.info(e.getMessage());
        }
    }

    public void changeScore(int score, String name){
        Integer val;
        if(score==10){
            val=scoreList.get(name)+score;
        }
        else {
            val=scoreList.get(name)-score;
        }
        scoreList.put(name,val);
        try {
            writeFile();
        }catch (IOException e){
            logger.info(e.getMessage());
        }
    }

    public void addNewUser(String name){
        scoreList.put(name,0);
        ArrayList<String> arr=new ArrayList<>();
        arr.add(name);
        records.put(0,arr);
        try {
            writeFile();
        }catch (IOException e){
            logger.info(e.getMessage());
        }
    }

    public Integer findScoreByName(String name){

        return scoreList.get(name);
    }

    public int getPlace(String name){
        int score = findScoreByName(name);
        int place=1;
        for (Map.Entry< Integer,ArrayList<String>> it: records.entrySet()){
            if(score==it.getKey()){break;}
            place++;
        }
        return records.size()-place+1;
    }

    public String getRecords(){
        StringBuilder sb= new StringBuilder();
        int count =records.size();
        sb.append("Place:            User:            Scores:\n");
        for (Map.Entry< Integer,ArrayList<String>> it1: records.entrySet()){
            for(String it2:it1.getValue()){
                sb.append(count).append("                 ").append(it1.getKey()).append("                 ").append(it2).append("\n");
            }
            sb.append("\n");
            count--;
        }
        return sb.toString();
    }

    private synchronized void readFile() throws FileNotFoundException {
        try (Scanner sc = new Scanner(new File(path))){
            while (sc.hasNextLine()) {
                String[] tmp = sc.nextLine().split(" ");
                if(tmp.length==2) {
                    String name=tmp[0];
                    Integer score =Integer.parseInt(tmp[1]);
                    scoreList.put(name,score);
                    if(records.containsKey(score)){
                        records.get(score).add(name);
                    }
                    else{
                        ArrayList<String> arr=new ArrayList<>();
                        arr.add(name);
                        records.put(score,arr);
                    }
                }
            }
        }
    }


    synchronized void writeFile() throws IOException {
        try (FileWriter fstream = new FileWriter(path); BufferedWriter out = new BufferedWriter(fstream)) {
            for (Map.Entry<String, Integer> it : scoreList.entrySet()) {
                out.write(it.getKey() + " ");
                out.write(it.getValue().toString());
                out.write("\n");
            }
            out.close();
        } catch (IOException e) {
            logger.info(e.toString());
        }
    }
}