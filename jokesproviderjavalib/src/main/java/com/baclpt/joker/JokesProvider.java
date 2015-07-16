package com.baclpt.joker;

/**
 * Java joke telling library
 * Provider for jokes
 */
public class JokesProvider {
    /**
     * Return a random joke
     *
     * @return string with a random joke
     */
    public String getJoke() {
        // Just as proof of concept, added extra jokes
        // source: http://www.jokes4us.com/

        int index = (int) (Math.random() * 3);
        switch (index) {
            case 0:
                return "Q: Why was 6 afraid of 7?\n" +
                        "A: Because 7, 8, 9.";
            case 1:
                return "Q: What did the apple say to the apple pie?\n" +
                        "A: You've got some crust.";
            default:
                return "Q: Where do birds go for coffee?\n" +
                        "A: on a NESTcafe.";

        }

    }
}

