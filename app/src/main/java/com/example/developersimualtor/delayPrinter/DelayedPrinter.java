package com.example.developersimualtor.delayPrinter;

import android.widget.TextView;

import java.util.Random;

public class DelayedPrinter {

    public static void printText(final Word word, final TextView textView) {
        Random random = new Random(System.currentTimeMillis());

        int currentRandOffset = random.nextInt(word.offset);
        boolean addOrSubtract = random.nextBoolean();
        long finalDelay = addOrSubtract ? word.delayBetweenSymbols + currentRandOffset : word.delayBetweenSymbols - currentRandOffset;
        if (finalDelay < 0){ finalDelay = 0;}

        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!word.stop) {
                    if (word.currentCharacterIndex == 0) {
                        textView.setText("");
                    }
                    try {
                        String charAt = String.valueOf(word.word.charAt(word.currentCharacterIndex));
                        ++word.currentCharacterIndex;
                        textView.setText(textView.getText() + charAt);
                        if (word.currentCharacterIndex >= word.word.length()) {
                            word.currentCharacterIndex = 0;
                            return;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    printText(word, textView);
                }

            }
        }, finalDelay);
    }


    public static class Word {

        private long delayBetweenSymbols;
        private String word;
        private int offset;
        private int currentCharacterIndex;
        private boolean stop = false;

        public Word(long delayBetweenSymbols, String word) {
            this.delayBetweenSymbols = delayBetweenSymbols;
            this.word = word;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getCurrentCharacterIndex() {
            return currentCharacterIndex;
        }

        public int getOffset() {
            return offset;
        }

        public long getDelayBetweenSymbols() {
            return delayBetweenSymbols;
        }

        public String getWord() {
            return word;
        }

        public void setCurrentCharacterIndex(int currentCharacterIndex) {
            this.currentCharacterIndex = currentCharacterIndex;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public boolean isStop() {
            return stop;
        }
        public void setStop(boolean stop) {
            this.stop = stop;
        }

    }

}
