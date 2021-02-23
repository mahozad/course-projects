import java.io.*;
import java.util.*;

import static spark.Spark.*;

// indexing 5,000 pages (440,000 words) -> 45 seconds
public class Main {

    private static final String REPOSITORY_FILE_NAME = "repo.dat";
    private static final String INDEX_FILE_NAME = "index.dat";
    private static final String ID_MAP_FILE_NAME = "id-map.dat";
    private static final String WORDS_FILE_NAME = "words.txt";
    private static final String MY_NAME = "?EYES?";
    private static String firstWordOfQuery;
    private static LinkedList<Entry> repository;
    private static Map<Integer, Entry> idToEntryMap;
    private static Map<String, Set<Integer>> index;
    private static Set<Integer> resultDocIds;
    private static String userQuery;

    public static void main(String[] args) throws IOException {
        readRepository();
        prepareIndex();
        writeAllKeysToFile();
        /////////////////////
        staticFileLocation("/public/");
        port(8080);
        System.out.println("Server started...");
        get("/search", (req, res) -> {
            userQuery = req.queryParams("input"); // get user query from the input
            /////////////////////
           /* if (!userQuery.matches("((\\s*[^ &|]+\\s*)*[&|])*(\\s*[^ &|]+\\s*)
           +")) {
                return "<html>\n" + "<head>\n" + "    <title>Malformed
                query</title>\n" + "    " +
                        "<link rel=\"shortcut icon\" href=\"favicon.png\">\n" +
                        "</head>\n" +
                        "<body style=\"text-align: center; background-color: rgb
                        (165,27,36)\">\n"
                        + "    <h1 style=\"color: #ffd9c2; font-size: 30pt;
                        font-family: " +
                        "Monofonto,sans-serif; margin-top: 260px\">Your query is
                        not valid (it " +
                        "does not comply with our regex):</h1>\n" + "    <br>\n" +
                         "    <h2 " +
                        "style=\"color: moccasin; font-size: 18pt; font-family:
                        Tahoma," +
                        "sans-serif\">USAGE: (phrase  &| ...) phrase</br></br>
                        spaces are " +
                        "allowed anywhere in the query.</h2>\n" + "</body>\n" +
                        "</html>";
            }*/
            if (userQuery.equalsIgnoreCase(MY_NAME)) {
                return "<html>\n" + "<head>\n" + "    <title>EYES</title>\n" + "  " +
                        "" + "" + "  <link " + "rel=\"shortcut icon\" " +
                        "href=\"favicon" + ".png\">\n" + "    <style>\n" + "    "
                        + "" + "" + "" + "" + "    body {\n" + "            " +
                        "background-color: rgb" + "" + "(25, " + "25, 20)" + ";\n"
                        + "  " + "      }\n" + " " + "   " + "</style>\n" +
                        "</head>\n" + "<body>\n" + "<div " + "style=\"text-align: " +
                        "center; " + "margin-top: " + "40px\">\n" + "    " +
                        "<img" + " " + "src=\"eyes.png\" " + "alt=\"eyes\" " +
                        "width=\"500\">\n" + "    " + "<br><br><br>\n   " + " " +
                        "<h2 style=\"color: " + "#ffd9c2; font-family: " +
                        "Monofonto," + "sans-serif\">T " + "" + "H I S" + " " +
                        "&nbsp&nbsp I S &nbsp&nbsp E Y E " + "S</h2>\n" +
                        "</div>\n" + "</body>\n</html>";
            }
            long startTime = System.currentTimeMillis();
            query(); // do the query
            String retrieveTime = String.valueOf((System.currentTimeMillis() -
                    startTime) / 1000D);
            StringBuilder HTMLResult = new StringBuilder("<!DOCTYPE html>\n<html "
                    + "lang=\"en\">\n<head>\n<meta charset=\"UTF-8\">\n<title>" +
                    userQuery + "</title>\n<link rel=\"shortcut icon\" " +
                    "href=\"favicon.png\"/>\n<style>\np " + "{\nline-height: 0;" +
                    "}\n</style>\n</head>" + "\n<body>\n<div " +
                    "style=\"direction:" + " rtl\">\n<img src=\"logo_static" + ""
                    + ".png\" " + "alt=\"EYES\" width=\"220\" " +
                    "style=\"margin-right: " + "50px; margin-top: " + "20px; " +
                    "margin-bottom: 12px\">\n" + "<hr/><div style=\"margin-right: " +
                    "" + "120px\">");
            String resultPrompt;
            String englishSizeNumber = String.valueOf(resultDocIds.size());
            char[] persoChars = {'۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'};
            String size = "";
            for (int i = 0; i < englishSizeNumber.length(); i++) {
                size += persoChars[englishSizeNumber.charAt(i) - 48];
            }
            if (resultDocIds.size() == 0) {
                resultPrompt = "نتیجه‌ای پیدا نشد!";
            } else {
                String seconds = "";
                for (int i = 0; i < retrieveTime.length(); i++) {
                    if (Character.isDigit(retrieveTime.charAt(i))) {
                        seconds += persoChars[retrieveTime.charAt(i) - 48];
                    } else {
                        seconds += retrieveTime.charAt(i);
                    }
                }
                resultPrompt = size + " نتیجه پیدا شد";
                // resultPrompt += " (" + seconds + " ثانیه)";
            }
            resultPrompt = "<h3 style=\"color: #838383; margin-top: 28px; " +
                    "margin-bottom: 30px\">" + resultPrompt + "</h3>";
            HTMLResult.append(resultPrompt);
            for (Integer resultDocId : resultDocIds) {
                Entry entry = idToEntryMap.get(resultDocId);
                String summary;
                int wordLocation = entry.getBody().toLowerCase().indexOf
                        (firstWordOfQuery);
                if (wordLocation < 0) {
                    summary = entry.getBody().substring(100, entry.getBody()
                            .length() > 260 ? 260 : entry.getBody().length()) +
                            "...";
                } else {
                    if (wordLocation - 50 < 0) {
                        summary = entry.getBody().substring(0, wordLocation) +
                                "<b>" + entry.getBody().substring(wordLocation,
                                wordLocation + firstWordOfQuery.length()) + "</b>"
                                + entry.getBody().substring(wordLocation +
                                firstWordOfQuery.length(), entry.getBody().length
                                () < 160 ? entry.getBody().length() : 160) + "...";
                    } else {
                        summary = entry.getBody().substring(wordLocation - 50,
                                wordLocation) + "<b>" + entry.getBody().substring
                                (wordLocation, wordLocation + firstWordOfQuery
                                        .length()) + "</b>" + entry.getBody()
                                .substring(wordLocation + firstWordOfQuery.length
                                        (), wordLocation + firstWordOfQuery.length
                                        () + 101 > entry.getBody().length() ?
                                        entry.getBody().length() : wordLocation +
                                        firstWordOfQuery.length() + 100) + "...";
                    }
                }
                String URL = entry.getUrl().length() > 70 ? entry.getUrl()
                        .substring(0, 67) + "..." : entry.getUrl();
                //HTMLResult = new String(Files.readAllBytes(Paths.get("duke
                // .java")));
                HTMLResult.append("<div style=\"color: #3e03bc;  margin-bottom: "
                        + "0px;\">\n <b>\n " + "<a " + "" + "href=\"" + entry
                        .getUrl() + "\"\n style=\"font-family: " + "Roboto," +
                        "sans-serif; " + "font-size: " + "13pt; " + "" +
                        "margin-bottom: 0; " + "" + "" + "text-decoration: " + ""
                        + "none\">\n" + " " + entry.getTitle() + " </a>\n </b>\n"
                        + "</div>\n" + "\n" + "\n" + "<div " + "style=\"color:" +
                        " " + "teal;" + "" + "" + " " + "font-family: Roboto," +
                        "" + "sans-serif;" + "" + "" + " font-size: " + "" +
                        "11pt\">\n" + " " + "" + "  " + "" + "" + "" + "" + "" +
                        "" + " " + "<h5 " + "" + "style=\"margin-top: " + "" +
                        "6px\">" + URL + "</h5>\n" + "</div>\n\n" + "<p " +
                        "style=\"font-family:" + " " + "" + "Roboto,sans-serif; "
                        + "" + "" + " " + "font-size: " + "11pt\">" + summary +
                        "</p>\n" + "\n" + "<div " + "style=\"font-family: Roboto,"
                        + "sans-serif;" + "" + " " + "font-size: " + "9pt;" + "" +
                        " color: #bc6654; " + "margin-bottom: " + "30px\">\n" +
                        entry.getTime() + "</div>");
            }
            HTMLResult.append("</div></div></body>\n</html>");
            return HTMLResult; /* the result page*/
        });
    }

    private static void writeAllKeysToFile() {
        try (PrintWriter printWriter = new PrintWriter(WORDS_FILE_NAME)) {
            for (String word : index.keySet()) {
                printWriter.println(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static void readRepository() {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream
                (REPOSITORY_FILE_NAME))) {
            repository = (LinkedList<Entry>) stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Cannot read data from repository.");
            System.exit(1);
        }
    }

    @SuppressWarnings("unchecked")
    private static void prepareIndex() {
        try {
            ObjectInputStream indexStream = new ObjectInputStream(new
                    FileInputStream(INDEX_FILE_NAME));
            ObjectInputStream mapStream = new ObjectInputStream(new
                    FileInputStream(ID_MAP_FILE_NAME));
            System.out.println("Loading the indexes...");
            index = (Map<String, Set<Integer>>) indexStream.readObject();
            idToEntryMap = (Map<Integer, Entry>) mapStream.readObject();
            indexStream.close();
            mapStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Building the index...");
            makeAndStoreMaps();
        }
    }

    private static void makeAndStoreMaps() {
        index = new HashMap<>();
        idToEntryMap = new HashMap<>();
        for (int docNumber = 0; docNumber < repository.size(); docNumber++) {
            Entry nextDoc = repository.get(docNumber);
            idToEntryMap.put(docNumber, nextDoc);
            String[] docTokens = nextDoc.getBody().split("\\s+");
            for (String token : docTokens) { // token == word
                token = token.replaceAll("[.,;:?!|&~@#$%^_\\-+*<>\\[\\]()" +
                        "{}\"'/\\\\؟،؛«»]", "");
                token = token.toLowerCase(); // normalize the token
                if (index.containsKey(token)) {
                    index.get(token).add(docNumber);
                } else {
                    Set<Integer> setOfDocIds = new HashSet<>();
                    setOfDocIds.add(docNumber);
                    index.put(token, setOfDocIds);
                }
            }
        }
        try {
            ObjectOutputStream indexStream = new ObjectOutputStream(new
                    FileOutputStream(INDEX_FILE_NAME));
            ObjectOutputStream mapStream = new ObjectOutputStream(new
                    FileOutputStream(ID_MAP_FILE_NAME));
            System.out.println("Storing the index...");
            indexStream.writeObject(index);
            mapStream.writeObject(idToEntryMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void query() {
        List<String> tokens = parseQuery();
        String operator = "FIRST_OPERATOR";
        resultDocIds = new HashSet<>();
        for (String token : tokens) {
            if (token.equals("&") || token.equals("|")) {
                operator = token;
            } else {
                Set<Integer> setOfDocIds = index.get(token);
                if (setOfDocIds == null) {
                    setOfDocIds = new HashSet<>();
                }
                if (operator.equals("&")) {
                    resultDocIds.retainAll(setOfDocIds); // intersection
                } else {
                    resultDocIds.addAll(setOfDocIds); // union
                }
            }
        }
        /*
        for (int i = 0; i < tokens.size(); i++) {
            String word = tokens.get(i);
            List<Integer> listOfDocIds = index.get(new Word(word));
            if (listOfDocIds != null) {
                if (operator.equals("&")) {
                    if (!resultDocIds.isEmpty()) {
                        List<Integer> tempList = new ArrayList<>();
                        for (int wordDocId : listOfDocIds) {
                            for (int resultDocId : resultDocIds) {
                                if (wordDocId == resultDocId) {
                                    if (!tempList.contains(wordDocId)) {
                                        tempList.add(wordDocId);
                                    }
                                } else if (wordDocId < resultDocId) {
                                    break;
                                }
                            }
                        }
                        resultDocIds = tempList;
                    } else {
                        i++;
                    }
                } else if (operator.equals("|")) {
                    if (resultDocIds.isEmpty()) {
                        resultDocIds.addAll(listOfDocIds);
                    } else {
                        List<Integer> tempList = new ArrayList<>();
                        for (int wordDocId : listOfDocIds) {
                            for (int resultDocId : resultDocIds) {
                                if (wordDocId < resultDocId) {
                                    if (!tempList.contains(wordDocId)) {
                                        tempList.add(wordDocId);
                                    }
                                    break;
                                } else if (wordDocId > resultDocId) {
                                    if (!tempList.contains(resultDocId)) {
                                        tempList.add(resultDocId);
                                    }
                                } else if (wordDocId == resultDocId) {
                                    if (!tempList.contains(wordDocId)) {
                                        tempList.add(wordDocId);
                                    }
                                }
                            }
                        }
                        resultDocIds = tempList;
                    }
                } else {
                    resultDocIds.addAll(listOfDocIds);
                }
            }
            if (++i < tokens.size()) {
                operator = tokens.get(i);
            }
        }
        */
    }

    private static List<String> parseQuery() {
        // @formatter:off
        userQuery = userQuery.trim();
        List<String> tokens = new LinkedList<>();
        int tokenBegin = 0;
        String operator = "NOT_SET";
        for (int i = 0; i < userQuery.length(); i++) {
            if (userQuery.charAt(i) == '&' || userQuery.charAt(i) == '|' || Character.isWhitespace(userQuery.charAt(i))) {
                tokens.add(userQuery.substring(tokenBegin, i).toLowerCase());
                while (Character.isWhitespace(userQuery.charAt(i)) || userQuery.charAt(i) == '&' || userQuery.charAt(i) == '|') {
                    if (userQuery.charAt(i) == '&' || Character.isWhitespace(userQuery.charAt(i))) {
                        operator = "&";
                    } else {
                        operator = "|";
                        i++;
                        while (Character.isWhitespace(userQuery.charAt(i))) {
                            i++;
                        }
                        break;
                    }
                    i++;
                }
                tokens.add(operator);
                tokenBegin = i;
                i--;
            }
        }
        tokens.add(userQuery.substring(tokenBegin, userQuery.length()).toLowerCase()); // last token
        firstWordOfQuery = tokens.get(0).toLowerCase();
        return tokens;
        // @formatter:on
    }

    //    //private static List<String> parseQuery() {
    //    // @formatter:off
    //    userQuery = userQuery.trim();
    //    List<String> tokens = new LinkedList<>();
    //    int tokenBegin = 0;
    //    String operator = "NOT_SET";
    //        for (int i = 0; i < userQuery.length(); i++) {
    //        if (userQuery.charAt(i) == '&' || userQuery.charAt(i) == '|' || Character.isWhitespace(userQuery.charAt(i))) {
    //            tokens.add(userQuery.substring(tokenBegin, i));
    //            while (Character.isWhitespace(userQuery.charAt(i)) || userQuery.charAt(i) == '&' || userQuery.charAt(i) == '|') {
    //                if (userQuery.charAt(i) == '&' || Character.isWhitespace(userQuery.charAt(i))) {
    //                    operator = "&";
    //                } else {
    //                    operator = "|";
    //                    i++;
    //                    while (Character.isWhitespace(userQuery.charAt(i))) {
    //                        i++;
    //                    }
    //                    break;
    //                }
    //                i++;
    //            }
    //            tokens.add(operator);
    //            tokenBegin = i;
    //            i--;
    //        }
    //    }
    //        tokens.add(userQuery.substring(tokenBegin, userQuery.length())); // the last word
    //    firstWordOfQuery = tokens.get(0);
    //        return tokens;
    //    // @formatter:on
    //}
}
