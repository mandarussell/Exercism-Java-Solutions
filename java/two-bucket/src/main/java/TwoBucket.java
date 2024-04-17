// import java.nio.Buffer;

class TwoBucket {
    private static final String BUCKET_ONE = "one";
    private static final String BUCKET_TWO = "two";

    private int desiredLiters;
    private int totalMoves;
    private int otherBucket;

    private String transferFrom;
    private String finalBucket;

    private Bucket bucketOne;
    private Bucket bucketTwo;

    TwoBucket(int bucketOneCap, int bucketTwoCap, int desiredLiters, String startBucket) {
        this.bucketOne = new Bucket(bucketOneCap);
        this.bucketTwo = new Bucket(bucketTwoCap);

        this.desiredLiters = desiredLiters;
        this.transferFrom = startBucket;

        this.findSolution();
    }

    void findSolution() {
        if (this.transferFrom == BUCKET_ONE) {
            this.bucketOne.fill();
        } else {
            this.bucketTwo.fill();
        }
        this.totalMoves++;
        this.checkBuckets();
        
        while (finalBucket == null) {
            // Edge Cases: Change the transfer direction if the desiredLiters could be obtained
            switch (this.transferFrom) {
                case BUCKET_ONE:
                    if (bucketTwo.getCap() - bucketOne.getCap() == this.desiredLiters 
                        || bucketTwo.getCurrent() - bucketOne.getAvailable() == this.desiredLiters
                        || bucketTwo.getCap() == this.desiredLiters) {
                            this.transferFrom = BUCKET_TWO;
                    }
                    break;
                default:
                    if (bucketOne.getCurrent() - bucketTwo.getAvailable() == this.desiredLiters) {
                        this.transferFrom = BUCKET_ONE;
                    } else {
                        this.transferFrom = BUCKET_TWO;
                    }
            }

            // Fill, empty or transfer water between the two buckets:
            switch (this.transferFrom) {
                case BUCKET_ONE:
                    if (bucketOne.isEmpty()) {
                        this.bucketOne.fill();
                    } else if (bucketTwo.isFull()) {
                        this.bucketTwo.empty();
                    } else {
                        this.transferBucket(BUCKET_ONE);
                    }
                    break;
                default:
                    if (bucketTwo.isEmpty()) {
                        this.bucketTwo.fill();
                    } else if (bucketOne.isFull()) {
                        this.bucketOne.empty();
                    } else {
                        this.transferBucket(BUCKET_TWO);
                    }
            } 

            this.totalMoves++;
            this.checkBuckets();

            // Escape clause in case there is no solution.
            if (this.totalMoves == 100) {break;}
        }
    }

    void transferBucket (String fromBucket) {
        int availableLiters;
        switch (fromBucket) {
            case BUCKET_ONE:
                availableLiters = bucketTwo.getAvailable();
                bucketTwo.transferTo(bucketOne.getCurrent());
                bucketOne.transferFrom(availableLiters);
                break;
            case BUCKET_TWO:
                availableLiters = bucketOne.getAvailable();
                bucketOne.transferTo(bucketTwo.getCurrent()); 
                bucketTwo.transferFrom(availableLiters);
                break;
            default:   
                throw new IllegalArgumentException("transferBucket: Unknown bucket");
        }
    }

    void checkBuckets() {
        if (bucketOne.isDesiredLiters(this.desiredLiters)) {
            this.finalBucket = BUCKET_ONE;
            this.otherBucket = bucketTwo.getCurrent();
        } else if (bucketTwo.isDesiredLiters(this.desiredLiters)) {
            this.finalBucket = BUCKET_TWO;
            this.otherBucket = bucketOne.getCurrent();
        }
    }

    int getTotalMoves() {
        return this.totalMoves;
    }

    String getFinalBucket() {
        return this.finalBucket;
    }

    int getOtherBucket() {
        return this.otherBucket;
    }
}



