package com.local.common;

public class OffsetObj {
    private long startOffset;
    private long endOffset;

    public OffsetObj() {
    }

    public OffsetObj(long startOffset, long endOffset) {
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    public long getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(long startOffset) {
        this.startOffset = startOffset;
    }

    public long getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(long endOffset) {
        this.endOffset = endOffset;
    }

    @Override
    public String toString() {
        return "OffsetObj{" +
                "startOffset=" + startOffset +
                ", endOffset=" + endOffset +
                '}';
    }
}
