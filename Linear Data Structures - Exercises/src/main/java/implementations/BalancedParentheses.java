package implementations;

import interfaces.Solvable;

import java.util.ArrayDeque;

public class BalancedParentheses implements Solvable {
    private final String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {
        ArrayDeque<Character> openBrackets = new ArrayDeque<>();

        if (this.parentheses == null) {
            return false;
        }

        if (this.parentheses.isEmpty()) {
            return true;
        }

        for (int i = 0; i < this.parentheses.length(); i++) {
            char currentBracket = this.parentheses.charAt(i);

            if (currentBracket == '(' || currentBracket == '{' || currentBracket == '[') {
                openBrackets.push(currentBracket);
            } else {
                if (openBrackets.isEmpty()) {
                    return false;
                }
                char lastOpenBracket = openBrackets.pop();
                if ((currentBracket == ')' && lastOpenBracket != '(') ||
                    (currentBracket == '}' && lastOpenBracket != '{') ||
                    (currentBracket == ']' && lastOpenBracket != '[')) {
                    return false;
                }
            }
        }
        return openBrackets.isEmpty();
    }
}
