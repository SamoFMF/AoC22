package day08;

public class Tree {
    private final int height;
    private boolean isVisible = false;

    public Tree(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public int checkVisibility(int currentMaxHeight) {
        if (height > currentMaxHeight) {
            isVisible = true;
            return height;
        }

        return currentMaxHeight;
    }
}
