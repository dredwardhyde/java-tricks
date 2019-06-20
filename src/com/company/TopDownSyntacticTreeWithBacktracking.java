package com.company;

import java.util.*;

class GrammarElement {
    private String token;
    private boolean isTerminal;

    GrammarElement(String token, boolean isTerminal) {
        this.token = token;
        this.isTerminal = isTerminal;
    }
    String getToken() { return token; }
    boolean isTerminal() { return isTerminal; }
    boolean isEmpty() { return token.equals("~");}
    @Override
    public String toString() { return "GrammarElement{token='" + token + "\', isTerminal=" + isTerminal + '}'; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrammarElement that = (GrammarElement) o;
        return isTerminal == that.isTerminal &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() { return Objects.hash(token, isTerminal); }
}

class ParseTreeNode {
    private GrammarElement token;
    private List<ParseTreeNode> children;
    private ParseTreeNode parent;
    private int finalGrowGroup;

    ParseTreeNode(GrammarElement token, ParseTreeNode parent) {
        this.token = token;
        this.parent = parent;
    }

    GrammarElement getToken() { return token; }
    void setChildren(List<ParseTreeNode> children) { this.children = children; }
    List<ParseTreeNode> getChildren(){ return children; }
    ParseTreeNode getParent() { return parent; }
    int getFinalGrowGroup() { return finalGrowGroup; }
    void setFinalGrowGroup(int finalGrowGroup) { this.finalGrowGroup = finalGrowGroup; }

    @Override
    public String toString() {
        return "ParseTreeNode{" + "token=" + token + ", children=" + children + '}';
    }
}

class CrownElement {
    private ParseTreeNode parent;
    private Character element;

    CrownElement(ParseTreeNode parent, Character element) {
        this.parent = parent;
        this.element = element;
    }

    ParseTreeNode getParent() { return parent; }
    Character getElement() { return element; }
    @Override
    public String toString() {
        return "CrownElement{" + "parent=" + parent.getToken().getToken() + ", element=" + element + '}';
    }
}

public class TopDownSyntacticTreeWithBacktracking {
    public static void main(String... args) {
        Map<String, String> grammar = new LinkedHashMap<>();
        String startSymbol = "S";
        grammar.put("S", "cAdBf");
        grammar.put("A", "ab|a");
        grammar.put("B", "z|~");

        Map<GrammarElement, List<List<GrammarElement>>> processedGrammar = new LinkedHashMap<>();
        GrammarElement startGrammarElement = null;

        for (Map.Entry<String, String> entry : grammar.entrySet()) {
            GrammarElement keyElement = new GrammarElement(entry.getKey(), false);
            if (entry.getKey().equals(startSymbol)) startGrammarElement = keyElement;
            List<List<GrammarElement>> groups = new ArrayList<>();
            List<GrammarElement> currentGroup = new ArrayList<>();
            groups.add(currentGroup);
            for (char c : entry.getValue().toCharArray()) {
                if (c == '|') {
                    currentGroup = new ArrayList<>();
                    groups.add(currentGroup);
                    continue;
                }
                currentGroup.add(new GrammarElement(Character.toString(c), Character.isLowerCase(c)));
            }
            processedGrammar.put(keyElement, groups);
        }

        System.out.println(processedGrammar);

        try{
            System.out.println("========= cag: " + makeParseTree(processedGrammar, startGrammarElement, "cag"));
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        try{
            System.out.println("========= cadd: " + makeParseTree(processedGrammar, startGrammarElement, "cadd"));
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        try{
            System.out.println("========= cab: " + makeParseTree(processedGrammar, startGrammarElement, "cab"));
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        try{
            System.out.println("========= cabd: " + makeParseTree(processedGrammar, startGrammarElement, "cabd"));
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        try{
            System.out.println("========= cad: " + makeParseTree(processedGrammar, startGrammarElement, "cad"));
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        try{
            System.out.println("========= cadz: " + makeParseTree(processedGrammar, startGrammarElement, "cadz"));
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        try{
            System.out.println("========= cabdz: " + makeParseTree(processedGrammar, startGrammarElement, "cabdz"));
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        try{
            System.out.println("========= cabdzf: " + makeParseTree(processedGrammar, startGrammarElement, "cabdzf"));
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        try{
            System.out.println("========= cabdf: " + makeParseTree(processedGrammar, startGrammarElement, "cabdf"));
        }catch (RuntimeException e){
            e.printStackTrace();
        }

    }

    private static ParseTreeNode makeParseTree(Map<GrammarElement, List<List<GrammarElement>>> processedGrammar, GrammarElement startGrammarElement, String input){
        ParseTreeNode root = new ParseTreeNode(startGrammarElement, null);
        List<CrownElement> crown = new ArrayList<>();

        ParseTreeNode latestGrowNode = root;
        int latestGrowIndex = 0;
        int latestGrowGroup = 0;

        ParseTreeNode nextGrowNode = addChildren(root, crown, processedGrammar, latestGrowGroup);

        System.out.println(crown);
        int currentChar = 0;

        do {

            // вышли за пределы короны и потенциал для роста есть - следующий узел для роста - новый
            if (currentChar > (crown.size() - 1) && nextGrowNode != null && (latestGrowNode != nextGrowNode)) {
                latestGrowNode = nextGrowNode;
                latestGrowIndex = currentChar;
                latestGrowGroup = 0;
                nextGrowNode = addChildren(nextGrowNode, crown, processedGrammar, latestGrowGroup);
                System.out.println("A " + crown);
            }

            // вышли за пределы короны и потенциала для ее роста нет - следующий узел для роста уже был и его группы закончились
            // сюда попадаем в том случае если последний символ в короне == последнему символу во входной строке
            if ((currentChar > (crown.size() - 1) && (nextGrowNode == null || (latestGrowGroup == processedGrammar.get(latestGrowNode.getToken()).size() - 1)))) {
                throw new RuntimeException("Invalid input ");
            }

            // если терминалы равны друг другу - уходим на следующий цикл и сравниваем следующий терминал из входной строки и короны
            if (Character.valueOf(input.charAt(currentChar)).equals(crown.get(currentChar).getElement())) {
                currentChar++;
            } else {
                // если символы не равны друг другу и это последний символ в короне и потенциала для роста нет, потому что следующий нетерминал либо отсутствует или он равен текущему и все группы уже перебраны
                if ((currentChar == (crown.size() - 1) && nextGrowNode == null) || (latestGrowNode == nextGrowNode && latestGrowGroup == processedGrammar.get(latestGrowNode.getToken()).size() - 1)) {
                    throw new RuntimeException("Invalid input");
                }
                // если символы не равны друг другу и все группы в текущем нетерминале уже были перебраны
                if (latestGrowGroup == processedGrammar.get(latestGrowNode.getToken()).size() - 1) {
                    throw new RuntimeException("Invalid input");
                } else {
                    // а если не все группы еще были перебраны - убираем все добавленные терминалы из короны добавленные на текущем проваленном узле и начинаем рост снова
                    // на следующей доступной для него группе
                    removeChildren(latestGrowNode, crown);
                    latestGrowGroup++;
                    // откатываем инпут до последнего корректного роста
                    currentChar = latestGrowIndex;
                    nextGrowNode = addChildren(latestGrowNode, crown, processedGrammar, latestGrowGroup);
                    System.out.println("B: " + crown);
                }
            }
        } while (currentChar < input.length());

        return root;
    }

    private static ParseTreeNode addChildren(ParseTreeNode node, List<CrownElement> crown, Map<GrammarElement, List<List<GrammarElement>>> processedGrammar, int group) {
        ParseTreeNode nextGrowNode = null;
        List<ParseTreeNode> children = new ArrayList<>();
        boolean foundNonTerminal = false;
        node.setChildren(children);
        node.setFinalGrowGroup(group);
        // в крону добавляем только терминальные символы слева направо и то только до появления первого нетерминала, который сохраняем и по нему в следующий раз будем делать рост кроны
        for (GrammarElement x : processedGrammar.get(node.getToken()).get(group)) {
            ParseTreeNode parseTreeNode = new ParseTreeNode(x, node);
            if (x.isTerminal() && !foundNonTerminal) crown.add(new CrownElement(node, x.getToken().charAt(0)));
            if (!x.isTerminal() && !x.isEmpty() && nextGrowNode == null) {
                nextGrowNode = parseTreeNode;
                foundNonTerminal = true;
            }
            // а вот дочерние узлы добавляем безусловно
            children.add(parseTreeNode);
        }
        // а если при добавлении дочерних узлов слева направо мы не встретили нетерминальный символ, то нужно продолжить наращивать крону
        // вопрос - при наращивании кроны нужно обновить следующий узел для роста если я встречу нетерминал или нет?
        if (!foundNonTerminal) {
            // находим родительский узел у текущего узла-нетерминала (по которому и делали рост кроны)
            List<GrammarElement> lastLeafs = processedGrammar.get(node.getParent().getToken()).get(node.getParent().getFinalGrowGroup());
            System.out.println("Last leafs: " + lastLeafs);

            // находим его индекс среди дочерних узлов родительского
            int idxOfCurrentNode = lastLeafs.indexOf(node.getToken());
            // и начинаем добавлять в корону все терминалы правее данного узла
            for (int i = idxOfCurrentNode + 1; i < lastLeafs.size(); i++) {
                GrammarElement x = lastLeafs.get(i);
                if (x.isTerminal() && !foundNonTerminal)
                    crown.add(new CrownElement(node.getParent(), x.getToken().charAt(0)));
                if (!x.isTerminal() && !x.isEmpty()) {
                    foundNonTerminal = true;
                    // тут я добавляю как следующий узел для роста нетерминал который встретился при добавлении родительских терминалов
                    // возможно это правильно, нужно проверить с грамматикой в которой это возможно

                    List<ParseTreeNode> parentChildren = node.getParent().getChildren();
                    if(parentChildren != null && !parentChildren.isEmpty()){
                        for(ParseTreeNode parseTreeNode : parentChildren){
                            if(parseTreeNode.getToken().equals(x)) nextGrowNode = parseTreeNode;
                        }
                    }
                }
            }
        }
        return nextGrowNode;
    }

    private static void removeChildren(ParseTreeNode node, List<CrownElement> crown) {
        int startIdx = -1;
        // ищем слева направо в короне первый элемент который был добавлен при росте по узлу node
        for (int i = 0; i < crown.size(); i++) {
            if (crown.get(i).getParent().getToken().equals(node.getToken())) {startIdx = i;break;}
        }
        // и потом удаляем начиная с него всю корону
        for (int i = startIdx; i < crown.size(); ) {
            crown.remove(i);
        }
        node.setChildren(null);
    }
}
