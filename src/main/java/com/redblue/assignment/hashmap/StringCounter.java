package com.redblue.assignment.hashmap;

import java.util.HashMap;
import java.util.Map;

public class StringCounter {
  public static int countCharactersInString(String input) {
    if (input == null || input.isBlank()) {
      return 0;
    }

    Map<Character, Integer> characterCounter = new HashMap<>();

    String trimmedString = input.replaceAll("\\s", "");
    for (char c : trimmedString.toCharArray()) {
      characterCounter.compute(c, (key, value) -> (value == null) ? 1 : value + 1);
    }

    return characterCounter.values().stream().reduce(0, Integer::sum);
  }

  public static void main(String[] args) {
    System.out.println(countCharactersInString("hello world"));
    System.out.println(countCharactersInString("                     hello              world                         "));
    System.out.println(countCharactersInString("                     he   llo              wor   ld                         "));
    System.out.println(countCharactersInString(""));
  }

}
