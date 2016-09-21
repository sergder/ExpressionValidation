import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Derenko Sergey
 * This program validates expressions with symbols '(){}[]' and any other symbol for the right sequence of '(){}[]'. Expressions can be nested into each other
 */
 
public class ExpressionValidation
{

    public static void main(String[] args) throws Exception {

        List<String> list = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader("expressions.txt"));

        while (in.ready()) {
            list.add(in.readLine());
        }

        in.close();
        BufferedWriter out = new BufferedWriter(new FileWriter("validation_result.txt"));

        for (String expr: list) {
            out.write(expr + (isValid(expr) ? " - correct expression." : " - INcorrect expression!") + "\n");
        }

        out.close();
    }

    private static int current = 0;

    public static boolean isValid (String str) {

        current = 0;

        if (str.length() == 0)
            return true;

        char firstChar = str.charAt(0);
        String subFromSecondChar = str.substring(1);

        if (firstChar == '(' || firstChar == '[' || firstChar == '{') {
            if (!checkEnd(subFromSecondChar, firstChar)) {
                return false;
            }
            return isValid(str.substring(current + 1));
        } else {
            if (firstChar == ')' || firstChar == ']' || firstChar == '}') {
                return false;
            }
            return isValid(subFromSecondChar);
        }
    }

    private static boolean checkEnd(String str, char start) {

        current++;
        int t = current;

        if (str.length() == 0)
            return false;

        char denied1 = 0, denied2 = 0;

        if (start == '(') {
            denied1 = '}';
            denied2 = ']';
        }

        if (start == '{') {
            denied1 = ')';
            denied2 = ']';
        }

        if (start == '[') {
            denied1 = '}';
            denied2 = ')';
        }

        char firstChar = str.charAt(0);

        if (firstChar == denied1 || firstChar == denied2) {
            return false;
        } else {
            if (firstChar == '(' || firstChar == '[' || firstChar == '{') {
                if (!checkEnd(str.substring(1), firstChar)) {
                    return false;
                }
            } else if (start == '(' && firstChar == ')') {
                return true;
            } else if (start == '[' && firstChar == ']') {
                return true;
            } else if (start == '{' && firstChar == '}') {
                return true;
            }
            return checkEnd(str.substring(current - t + 1), start);
        }
    }

}
