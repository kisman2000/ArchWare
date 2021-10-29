package fun.archware.notifications;

public enum NotificationType {
    OK{
        @Override
        public char getChar() {
            return 'c';
        }
    },
    WARNING{
        @Override
        public char getChar() {
            return 'b';
        }
    },
    ERROR{
        @Override
        public char getChar() {
            return 'a';
        }
    };

    public abstract char getChar();
}
