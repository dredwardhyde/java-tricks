package com.company;


import java.util.*;

public class TopDownSyntacticTreeWithoutBacktracking {
    public static void main(String... args){
        Map<String, String> grammar = new LinkedHashMap<>();
        String startSymbol = "S";
        grammar.put("S", "ACB|Cbb|Ba");
        grammar.put("A", "da|BC");
        grammar.put("B", "g|~");
        grammar.put("C", "h|~");

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

        for (GrammarElement element: processedGrammar.keySet()) {
            System.out.println(element.getToken() + " " + first(element));
        }
    }

    public static List<GrammarElement> first(GrammarElement element){

        List<GrammarElement> firstElements = new ArrayList<>();
        if(element.isTerminal()) {
            return Collections.singletonList(element);
        }else {

        }

        return null;

    }
}
