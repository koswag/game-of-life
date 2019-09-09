package gol;

public enum Mode {
    RANDOM {
        @Override
        public String toString() {
            return "Random";
        }
    },
    RECTANGLE {
        @Override
        public String toString() {
            return "Rectangle";
        }
    }
}
