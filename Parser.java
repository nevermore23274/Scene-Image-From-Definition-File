import java.awt.*;
import java.io.*;

class Parser {
    //private JFrame window;
    private Token token;
    private Lexer lexer;

    // Constructor to create new lexical analyzer from input file

    public Parser(File file) throws IOException {
        lexer = new Lexer(file);
    }

    // Parses the production
    // scene -> SCENE IDENTIFIER number_list images END '.'

    public Scene parseScene() throws LexicalError, SyntaxError, IOException {
        verifyNextToken(Token.SCENE);
        verifyNextToken(Token.IDENTIFIER);
        String window = lexer.getLexeme();
        int[] dimensions = getNumberList(2);
        Scene scene = new Scene(window, dimensions[0], dimensions[1]);
        parseImages(scene, lexer.getNextToken());
        verifyNextToken(Token.PERIOD);
        return scene;
    }

    // Parses the following productions

    // images -> image images | image
    // image -> right_triangle | rectangle
    // right_triangle -> RIGHT_TRIANGLE COLOR number_list AT number_list HEIGHT NUMBER WIDTH NUMBER ';'
    // rectangle -> RECTANGLE_ COLOR number_list AT number_list HEIGHT NUMBER WIDTH NUMBER ';'
    // isosceles_triangle -> ISOSCELES_TRIANGLE COLOR number_list AT number_list HEIGHT NUMBER WIDTH NUMBER ';'

    private void parseImages(Scene scene, Token imageToken) throws LexicalError, SyntaxError, IOException {
        int height, width, offset, radius;
        verifyNextToken(Token.COLOR);
        int[] colors = getNumberList(3);
        Color color = new Color(colors[0], colors[1], colors[2]);
        verifyNextToken(Token.AT);//Verify that the next token is AT
        int[] location = getNumberList(2);//Get the location of the image
        Point point = new Point(location[0], location[1]);//Create a point object with the location
        if (imageToken == Token.RIGHT_TRIANGLE) {
            verifyNextToken(Token.HEIGHT);
            verifyNextToken(Token.NUMBER);
            height = lexer.getNumber();
            verifyNextToken(Token.WIDTH);
            verifyNextToken(Token.NUMBER);
            width = lexer.getNumber();
            RightTriangle triangle = new RightTriangle(color, point, height, width);
            scene.addImage(triangle);
        } 
        else if (imageToken == Token.RECTANGLE) {
            verifyNextToken(Token.HEIGHT);
            verifyNextToken(Token.NUMBER);
            height = lexer.getNumber();
            verifyNextToken(Token.WIDTH);
            verifyNextToken(Token.NUMBER);
            width = lexer.getNumber();
            Rectangle rectangle = new Rectangle(color, point, height, width);
            scene.addImage(rectangle);
        }
    //region - ADDED HANDLERS - Additional handlers added for the project
        //region - TEXT - Handles the text
            // Text Color (int, int, int) at (int, int) "String";
            else if(imageToken == Token.TEXT){
                lexer.getNextToken();//Get the next token
                String str = lexer.getLexeme();//Get the string
                Text text = new Text(color, point, str);//Create a text object
                scene.addImage(text);//Add the text to the scene
            }
        //endregion
        
        //region - ISOSCELES TRIANGLE - Handles the isosceles triangle
            else if(imageToken == Token.ISOSCELES_TRIANGLE){
                verifyNextToken(Token.HEIGHT);
                verifyNextToken(Token.NUMBER);
                height = lexer.getNumber();
                verifyNextToken(Token.WIDTH);
                verifyNextToken(Token.NUMBER);
                width = lexer.getNumber();
                IsoscelesTriangle isosceles_triangle = new IsoscelesTriangle(color, point, height, width);
                scene.addImage(isosceles_triangle);
            }
        //endregion
        
        //region - PARALLELOGRAM - Handles the parallelogram
            // Parallelogram Color (int, int, int) at (int, int) (int, int) Offset int;
            else if(imageToken == Token.PARALLELOGRAM){
                int[] location2 = getNumberList(2);//Get the location of the image
                Point point2 = new Point(location2[0], location2[1]);//Create a point object with the location of the second point
                verifyNextToken(Token.OFFSET);//Verify that the next token is OFFSET
                verifyNextToken(Token.NUMBER);//Verify that the next token is a number
                offset = lexer.getNumber();//Get the offset
                Parallelogram parallelogram = new Parallelogram(color, point, point2, offset);//Create a parallelogram object
                scene.addImage(parallelogram);//Add the parallelogram to the scene
            }
        //endregion

        //region - REGULAR POLYGON - Handles the regular polygon
            // RegularPolygon Color (int, int, int) at (int, int) Sides int Radius int;
            else if(imageToken == Token.REGULAR_POLYGON){
                verifyNextToken(Token.SIDES);//Verify that the next token is SIDES
                verifyNextToken(Token.NUMBER);//Verify that the next token is a number
                int sides = lexer.getNumber();//Get the number of sides
                verifyNextToken(Token.RAD);//Verify that the next token is RADIUS
                verifyNextToken(Token.NUMBER);//Verify that the next token is a number
                radius = lexer.getNumber();//Get the radius
                RegularPolygon regular_polygon = new RegularPolygon(color, sides, point, radius);//Create a regular polygon object
                scene.addImage(regular_polygon);//Add the regular polygon to the scene
            }
        //endregion


    //endregion

        else {
             throw new SyntaxError(lexer.getLineNo(), "Unexpected image name " + imageToken);
        }
        verifyNextToken(Token.SEMICOLON);
        token = lexer.getNextToken();
        if (token != Token.END)
            parseImages(scene, token);
    }

    // Parses the following productions

    // number_list -> '(' numbers ')'
    // numbers -> NUMBER | NUMBER ',' numbers

    // Returns an array of the numbers in the number list

    private int[]  getNumberList(int count) throws LexicalError, SyntaxError, IOException {
        int[] list = new int[count];
        verifyNextToken(Token.LEFT_PAREN);
        for (int i = 0; i < count; i++) {
            verifyNextToken(Token.NUMBER);
            list[i] = lexer.getNumber();
            token = lexer.getNextToken();
            if (i < count - 1)
                verifyCurrentToken(Token.COMMA);
            else
                verifyCurrentToken(Token.RIGHT_PAREN);
        }
        return list;
    }

    // Returns a list of numbers

    //private int[] getNumberList() throws LexicalError, SyntaxError, IOException {
    //    ArrayList<Integer> list = new ArrayList<Integer>();
    //    verifyNextToken(Token.LEFT_PAREN);
    //    do {
    //        verifyNextToken(Token.NUMBER);
    //        list.add((int) lexer.getNumber());
    //        token = lexer.getNextToken();
    //    }
    //    while (token == Token.COMMA);
    //    verifyCurrentToken(Token.RIGHT_PAREN);
    //    int[] values = new int[list.size()];
    //    for (int i = 0; i < values.length; i++)
    //        values[i] = list.get(i);
    //    return values;
    //}

    // Verifies that the next token is the expected token

    private void verifyNextToken(Token expectedToken) throws LexicalError, SyntaxError, IOException {
        token = lexer.getNextToken();
        verifyCurrentToken(expectedToken);
    }

    // Verifies that the current token is the expected token

    private void verifyCurrentToken(Token expectedToken) throws SyntaxError {
        if (token != expectedToken)
            throw new SyntaxError(lexer.getLineNo(), "Expecting token " + expectedToken + " not " + token);
    }

    
}