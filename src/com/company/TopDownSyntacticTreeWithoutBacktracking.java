package com.company;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TopDownSyntacticTreeWithoutBacktracking {
    public static void main(String... args) {
        Map<String, String> grammar = new LinkedHashMap<>();
        grammar.put("S", "ACB|Cbb|Ba");
        grammar.put("A", "da|BC");
        grammar.put("B", "g|~");
        grammar.put("C", "h|~");

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
        //{GrammarElement{token='S', isTerminal=false}=[
        //                              [
        //                                  GrammarElement{token='A', isTerminal=false},
        //                                  GrammarElement{token='C', isTerminal=false},
        //                                  GrammarElement{token='B', isTerminal=false}
        //                              ],
        //                              [
        //                                  GrammarElement{token='C', isTerminal=false},
        //                                  GrammarElement{token='b', isTerminal=true},
        //                                  GrammarElement{token='b', isTerminal=true}
        //                              ],
        //                              [
        //                                  GrammarElement{token='B', isTerminal=false},
        //                                  GrammarElement{token='a', isTerminal=true}
        //                              ]
        //                          ],
        //
        // GrammarElement{token='A', isTerminal=false}=[
        //                              [
        //                                  GrammarElement{token='d', isTerminal=true},
        //                                  GrammarElement{token='a', isTerminal=true}
        //                              ],
        //                              [
        //                                  GrammarElement{token='B', isTerminal=false},
        //                                  GrammarElement{token='C', isTerminal=false}
        //                              ]
        //                          ],
        // GrammarElement{token='B', isTerminal=false}=[
        //                              [
        //                                  GrammarElement{token='g', isTerminal=true}
        //                              ],
        //                              [
        //                                  GrammarElement{token='~', isTerminal=false}
        //                              ]
        //                          ],
        // GrammarElement{token='C', isTerminal=false}=[
        //                              [
        //                                  GrammarElement{token='h', isTerminal=true}
        //                              ],
        //                              [
        //                                  GrammarElement{token='~', isTerminal=false}
        //                              ]
        //                          ]
        // }
        System.out.println(processedGrammar);

        for(GrammarElement element : processedGrammar.keySet()){
            System.out.println(element.getToken() + " " + getFirstsForElement(element, processedGrammar));
        }
    }


    private static List<GrammarElement> getFirstsForElement(GrammarElement element, Map<GrammarElement, List<List<GrammarElement>>> processedGrammar ){
        List<GrammarElement> firsts = new ArrayList<>();
        for (List<GrammarElement> value : processedGrammar.get(element)) {
            GrammarElement empty = groupContainsEmpty(firsts);
            List<GrammarElement> currentFirsts = first(element, processedGrammar, value);
            if(empty != null) currentFirsts.remove(empty);
            firsts.addAll(currentFirsts);
        }
        return firsts;
    }

    /*
        Rules to compute FIRST set:

        1. If x is a terminal, then FIRST(x) = { ‘x’ }
        2. If x-> Є, is a production rule, then add Є to FIRST(x).
        3. If X->Y1 Y2 Y3….Yn is a production,
                FIRST(X) = FIRST(Y1)
                If FIRST(Y1) contains Є then FIRST(X) = { FIRST(Y1) – Є } U { FIRST(Y2) }
                If FIRST (Yi) contains Є for all i = 1 to n, then add Є to FIRST(X).
     */
    public static List<GrammarElement> first(GrammarElement element, Map<GrammarElement, List<List<GrammarElement>>> processedGrammar, List<GrammarElement> targetGroup) {

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

            List<GrammarElement> y1;
            if (firstFromGroup.isTerminal()) {
                y1 = first(firstFromGroup, processedGrammar, null);
            } else {
                y1 = first(firstFromGroup, processedGrammar, processedGrammar.get(firstFromGroup).get(0));
            }
            GrammarElement emptyInY1 = groupContainsEmpty(y1);
            if (emptyInY1 != null) {
                if (secondFromGroup != null) {
                    List<GrammarElement> y2;
                    if (secondFromGroup.isTerminal()) {
                        y2 = first(secondFromGroup, processedGrammar, null);
                    } else {
                        y2 = first(secondFromGroup, processedGrammar, processedGrammar.get(secondFromGroup).get(0));
                    }
                    if(groupContainsEmpty(y2) != null){
                        y1.remove(emptyInY1);
                    }
                    y1.addAll(y2);
                }
            }

            GrammarElement containsEmptyProduction = anyGroupContainsEmpty(element, processedGrammar);
            if (containsEmptyProduction != null && groupContainsEmpty(y1) == null) y1.add(containsEmptyProduction);
            return y1;
        }
    }

    private static GrammarElement anyGroupContainsEmpty(GrammarElement element, Map<GrammarElement, List<List<GrammarElement>>> processedGrammar) {
        List<List<GrammarElement>> allElements = processedGrammar.get(element);
        for (List<GrammarElement> elements : allElements) {
            for (GrammarElement grammarElement : elements) {
                if (grammarElement.isEmpty()) return grammarElement;
            }
        }
        return null;
    }

    private static GrammarElement groupContainsEmpty(List<GrammarElement> targetGroup) {
        for (GrammarElement grammarElement : targetGroup) {
            if (grammarElement.isEmpty()) {
                return grammarElement;
            }
        }
        return null;
    }
}
