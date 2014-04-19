package com.nttuyen.content.entity;

/**
 * @author nttuyen266@gmail.com
 */
public enum ArticleState {
    ARCHIVED(2), PUBLISHED(1), UNPUBLISHED(0), TRASHED(-2);

    private final int value;
    private ArticleState(int value) {
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }

    public static ArticleState get(int value) {
        switch (value) {
            case 2:
                return ARCHIVED;
            case 1:
                return PUBLISHED;
            case -2:
                return TRASHED;
            case 0:
            default:
                return UNPUBLISHED;
        }
    }
}
