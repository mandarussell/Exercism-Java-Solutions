class TwoBucket {
    private static final String BUCKET_ONE = "one";
    private static final String BUCKET_TWO = "two";

    private int desiredLiters;
    private int totalMoves;

    private String finalBucket;

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
        
        while (finalBucket == null) {
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

    void transferBucket () {
        int availableLiters;
        availableLiters = bucketTo.getAvailable();
        bucketTo.transferTo(bucketFrom.getCurrent());
        bucketFrom.transferFrom(availableLiters);
    }

    void checkBuckets() {
        if (bucketFrom.isDesiredLiters(this.desiredLiters)) {
            this.finalBucket = this.bucketFrom.getNumber();
        } else if (bucketTo.isDesiredLiters(this.desiredLiters)) {
            this.finalBucket = this.bucketTo.getNumber();
        }
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
        return this.finalBucket;
    }

    int getOtherBucket() {
        if (this.bucketFrom.getNumber() == this.finalBucket) {
            return this.bucketTo.getCurrent();
        } else {
            return this.bucketFrom.getCurrent();
        }
    }
}



