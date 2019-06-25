package com.company;


import java.util.*;

// https://www.geeksforgeeks.org/compiler-design-first-in-syntax-analysis/
// https://www.geeksforgeeks.org/compiler-design-follow-set-in-syntax-analysis/
public class TopDownSyntacticTreeWithoutBacktracking {
    public static void main(String... args) {
        Map<String, String> grammar = new LinkedHashMap<>();
        grammar.put("S", "ACB|Cbb|Ba");
        grammar.put("A", "da|BC");
        grammar.put("B", "g|~");
        grammar.put("C", "h|~");

        Map<GrammarElement, List<List<GrammarElement>>> processedGrammar = prepareGrammar(grammar);

        System.out.println("PROCESSED GRAMMAR:");
        System.out.println(processedGrammar);
        System.out.println("\nFIRST(X):");

        System.out.println(getFirstsForElement(processedGrammar));
        System.out.println();

        Map<String, String> grammar1 = new LinkedHashMap<>();
        grammar1.put("E", "TM");
        grammar1.put("M", "mTM|~");
        grammar1.put("T", "FK");
        grammar1.put("K", "aFK|~");
        grammar1.put("F", "eEt|d");

        /*
        Expected FIRSTs:

        FIRST(E) = FIRST(T) = { e , d }
        FIRST(M) = { m, ~ }
        FIRST(T) = FIRST(F) = { e , d }
        FIRST(K) = { a, ~ }
        FIRST(F) = { e , d }
         */
        Map<GrammarElement, List<List<GrammarElement>>> processedGrammar1 = prepareGrammar(grammar1);
        System.out.println("PROCESSED GRAMMAR:");
        System.out.println(processedGrammar1);
        System.out.println("\nFIRST(X):");
        System.out.println(getFirstsForElement(processedGrammar1));
        System.out.println();
    }

    private static Map<GrammarElement, List<List<GrammarElement>>> prepareGrammar(Map<String, String> grammar) {
        Map<GrammarElement, List<List<GrammarElement>>> processedGrammar = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry : grammar.entrySet()) {
            GrammarElement keyElement = new GrammarElement(entry.getKey(), false);
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
        return processedGrammar;
    }

    private static Map<GrammarElement, Set<GrammarElement>> getFirstsForElement(Map<GrammarElement, List<List<GrammarElement>>> processedGrammar) {
        Map<GrammarElement, Set<GrammarElement>> elementSetMap = new LinkedHashMap<>();
        for (GrammarElement element : processedGrammar.keySet()) {
            Set<GrammarElement> firsts = new HashSet<>();
            for (List<GrammarElement> value : processedGrammar.get(element)) {
                GrammarElement empty = groupContainsEmpty(firsts);
                List<GrammarElement> currentFirsts = first(element, processedGrammar, value);
                if (empty != null) currentFirsts.remove(empty);
                firsts.addAll(currentFirsts);
            }
            elementSetMap.put(element, firsts);
        }

        return elementSetMap;
    }

    /*
        Rules to compute FIRST set:

        1. If x is a terminal, then FIRST(x) = { ‘x’ }
        2. If x-> ~, is a production rule, then add ~ to FIRST(x).
        3. If X->Y1 Y2 Y3….Yn is a production,
                FIRST(X) = FIRST(Y1)
                If FIRST(Y1) contains ~ then FIRST(X) = { FIRST(Y1) – ~ } U { FIRST(Y2) }
     */
    public static List<GrammarElement> first(GrammarElement element,
                                             Map<GrammarElement, List<List<GrammarElement>>> processedGrammar,
                                             List<GrammarElement> targetGroup) {

        if (element.isTerminal()) {
            List<GrammarElement> list = new ArrayList<>();
            list.add(element);
            return list;
        } else {
            GrammarElement firstFromGroup = targetGroup.get(0);
            if (firstFromGroup.isEmpty()) {
                return new ArrayList<>();
            }

            GrammarElement secondFromGroup = null;
            if (targetGroup.size() > 1) {
                secondFromGroup = targetGroup.get(1);
                if (secondFromGroup.isEmpty()) secondFromGroup = null;
            }

            // Получить FIRST(Y1)
            List<GrammarElement> y1 = new ArrayList<>();
            if (firstFromGroup.isTerminal()) {
                y1.addAll(first(firstFromGroup, processedGrammar, null));
            } else {
                List<List<GrammarElement>> grammarElementList = processedGrammar.get(firstFromGroup);
                for (List<GrammarElement> grammarElements : grammarElementList) {
                    y1.addAll(first(firstFromGroup, processedGrammar, grammarElements));
                }
            }

            // Если FIRST(Y1) содержит ~ тогда FIRST(X) = { FIRST(Y1) – ~ } U { FIRST(Y2) }
            GrammarElement emptyInY1 = groupContainsEmpty(y1);
            if (emptyInY1 != null) {
                // есть следующий элемент группы?
                if (secondFromGroup != null) {

                    // добавить все FIRST(Y2)
                    List<GrammarElement> y2 = new ArrayList<>();
                    if (secondFromGroup.isTerminal()) {
                        y2.addAll(first(secondFromGroup, processedGrammar, null));
                    } else {
                        List<List<GrammarElement>> grammarElementList = processedGrammar.get(secondFromGroup);
                        for (List<GrammarElement> grammarElements : grammarElementList) {
                            y1.addAll(first(secondFromGroup, processedGrammar, grammarElements));
                        }
                    }

                    //Если результирующая группа содержит
                    if (groupContainsEmpty(y2) != null) {
                        y1.remove(emptyInY1);
                    }
                    y1.addAll(y2);
                }
            }


            // если в есть группа в которой есть производная x -> ~
            GrammarElement containsEmptyProduction = anyGroupContainsEmpty(element, processedGrammar);

            // и на данный момент в FIRST(X) не содержится ~ тогда добавить в результат
            if (containsEmptyProduction != null && groupContainsEmpty(y1) == null) y1.add(containsEmptyProduction);

            return y1;
        }
    }

    // проверяет есть ли в любой группе данного элемента производная x -> ~
    private static GrammarElement anyGroupContainsEmpty(GrammarElement element,
                                                        Map<GrammarElement,
                                                        List<List<GrammarElement>>> processedGrammar) {
        List<List<GrammarElement>> allElements = processedGrammar.get(element);
        for (List<GrammarElement> elements : allElements) {
            for (GrammarElement grammarElement : elements) {
                if (grammarElement.isEmpty()) return grammarElement;
            }
        }
        return null;
    }

    // проверяет, есть ли в группе элемент ~
    private static GrammarElement groupContainsEmpty(Collection<GrammarElement> targetGroup) {
        for (GrammarElement grammarElement : targetGroup) {
            if (grammarElement.isEmpty()) {
                return grammarElement;
            }
        }
        return null;
    }

    private static Map<GrammarElement, Set<GrammarElement>> getFollowsForElement(Set<GrammarElement> elements,
                                                                                 Map<GrammarElement, List<List<GrammarElement>>> processedGrammar,
                                                                                 Map<GrammarElement, Set<GrammarElement>> firsts) {


        return null;
    }
}