package implementations;

import interfaces.Solvable;

public class BalancedParentheses implements Solvable {
    private String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {
        ArrayDeque<Character> openBrackets = new ArrayDeque<>();
        boolean isBalanced = true;
        for (int i = 0; i < this.parentheses.length(); i++) {
            char currentBracket = this.parentheses.charAt(i);

            if (currentBracket == '(' || currentBracket == '{' || currentBracket == '[') {
                openBrackets.push(currentBracket);
            } else {
                if (openBrackets.isEmpty()) {
                    isBalanced = false;
                    break;
                }
                char lastOpenBracket = openBrackets.pop();
                if (currentBracket == ')' && lastOpenBracket != '(') {
                    isBalanced = false;
                    break;
                } else if (currentBracket == '}' && lastOpenBracket != '{') {
                    isBalanced = false;
                    break;
                } else if (currentBracket == ']' && lastOpenBracket != '[') {
                    isBalanced = false;
                    break;
                }
            }

        }
        return isBalanced;
    }
}
