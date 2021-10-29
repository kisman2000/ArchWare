package fun.archware.base.module;

import java.awt.*;

/**
 * Created by 1 on 01.04.2021.
 */
public enum Category {
    COMBAT{
        @Override
        public int color() {
            return new Color(150, 236, 52).hashCode();
        }

        @Override
        public String getChar() {
            return "A";
        }
    }, MOVE {
        @Override
        public int color() {
            return new Color(35, 70, 121).hashCode();
        }

        @Override
        public String getChar() {
            return "G";
        }
    }, PLAYER{
        @Override
        public int color() {
            return new Color(26, 115, 220).hashCode();
        }

        @Override
        public String getChar() {
            return "F";
        }
    },RENDER{
        @Override
        public int color() {
            return new Color(142, 50, 83).hashCode();
        }

        @Override
        public String getChar() {
            return "H";
        }
    }, MISC {
        @Override
        public int color() {
            return new Color(164, 248, 150).hashCode();
        }

        @Override
        public String getChar() {
            return "J";
        }
    };

    public abstract int color();
    public abstract String getChar();
}
