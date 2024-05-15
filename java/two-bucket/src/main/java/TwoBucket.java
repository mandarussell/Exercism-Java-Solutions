class TwoBucket {
    private static final String BUCKET_ONE = "one";
    private static final String BUCKET_TWO = "two";

    private int desiredLiters;
    private int totalMoves;

    private Bucket bucketFrom;
    private Bucket bucketTo;

    TwoBucket(int bucketOneCap, int bucketTwoCap, int desiredLiters, String startBucket) {
        this.bucketFrom = new Bucket(bucketOneCap, BUCKET_ONE);
        this.bucketTo = new Bucket(bucketTwoCap, BUCKET_TWO);
        if (startBucket == BUCKET_TWO) {this.swapBuckets();}
        this.desiredLiters = desiredLiters;
        this.findSolution();
    }

    void findSolution() {
        this.bucketFrom.fill();
        this.totalMoves++;
        this.checkBuckets();
        
        while (!this.checkBuckets()) {
            // Edge Cases: Change the transfer direction if the desiredLiters could be obtained
            if (bucketTo.getCapacity() - bucketFrom.getCapacity() == this.desiredLiters 
            || bucketTo.getCurrent() - bucketFrom.getAvailable() == this.desiredLiters
            || bucketTo.getCapacity() == this.desiredLiters) {
                this.swapBuckets();
            }

            // Fill, empty or transfer water between the two buckets:
            if (bucketFrom.isEmpty()) {
                this.bucketFrom.fill();
            } else if (bucketTo.isFull()) {
                this.bucketTo.empty();
            } else {
                this.transferBucket();
            }

            this.totalMoves++;
            this.checkBuckets();

            // Escape clause in case there is no solution.
            if (this.totalMoves == 100) {break;}
        }
    }

    void transferBucket() {
        bucketTo.transfer(bucketFrom);
    }

    /**
     * Checks if either the bucket being transferred to or from has the desired Liters.
     * If the desired liters is found in the from bucket, swap this with the bucketTo bucket, 
     * as this simplifies the logic for the getFinalBucket and getOtherBucket methods.
     * 
     * @return boolean to state if desired liters has been achieved
     */
    boolean checkBuckets() {
        if (bucketFrom.isDesiredLiters(this.desiredLiters)) {
            this.swapBuckets();
            return true;
        } else if (bucketTo.isDesiredLiters(this.desiredLiters)) {
            return true;
        }
        return false;
    }

    void swapBuckets() {
        Bucket tempBucket = this.bucketFrom;
        this.bucketFrom = this.bucketTo;
        this.bucketTo = tempBucket;
    }

    int getTotalMoves() {
        return this.totalMoves;
    }

    String getFinalBucket() {
        return this.bucketTo.getNumber();
    }

    int getOtherBucket() {
        return this.bucketFrom.getCurrent();
    }
}



