public class Bucket {
    private int bucketCap;
    private int bucketCurrent;

    Bucket(int bucketCap) {
        this.bucketCap = bucketCap;
        this.bucketCurrent = 0;
    }

    void setCurrent(int bucketCurrent) {
        this.bucketCurrent = bucketCurrent;
    }

    void fill() {
        this.bucketCurrent = this.bucketCap;
    }

    void empty() {
        this.bucketCurrent = 0;
    }

    int getCap() {
        return this.bucketCap;
    }

    int getCurrent() {
        return this.bucketCurrent;
    }

    int getAvailable() {
        return this.bucketCap - this.bucketCurrent;
    }

    boolean isDesiredLiters(int desiredLiters) {
        return this.bucketCurrent == desiredLiters;
    }

    boolean isEmpty() {
        return this.bucketCurrent == 0;
    }

    boolean isFull() {
        return this.bucketCurrent == this.bucketCap;
    }

    void transferTo(int otherBucket) {
        if (this.getAvailable() > otherBucket) {
            this.bucketCurrent += otherBucket;
        } else {
            this.bucketCurrent = this.bucketCap;
        }
    }

    void transferFrom(int availableLiters) {
        if (availableLiters > this.bucketCurrent) {
            this.bucketCurrent = 0;
        } else {
            this.bucketCurrent -= availableLiters; 
        }
    }
}
