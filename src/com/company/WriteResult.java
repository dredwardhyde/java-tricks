package com.company;

class WriteResult {
    private long offset;
    private long size;

    public WriteResult(long offset, long size) {
        this.setOffset(offset);
        this.setSize(size);
    }


    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
