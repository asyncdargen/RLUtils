package ru.starfarm.api.status.style;

import ru.starfarm.util.Colors;

import java.awt.*;

public interface Style {
    static Style of(Color color) {
        return new Default(color);
    }

    static Style of(Color color, Color background) {
        return new Default(color, background);
    }

    Color getBackground();

    Style setBackground(Color background);

    Color getColor();

    Style setColor(Color color);

    class Default implements Style {
        private Color background;

        private Color color;

        Default(Color color) {
            this(color, Colors.of(153, 151, 151, 183));
        }

        Default(Color color, Color background) {
            this.color = color;
            this.background = background;
        }

        public Color getBackground() {
            return this.background;
        }

        public Default setBackground(Color background) {
            this.background = background;
            return this;
        }

        public Color getColor() {
            return this.color;
        }

        public Default setColor(Color color) {
            this.color = color;
            return this;
        }
    }
}
