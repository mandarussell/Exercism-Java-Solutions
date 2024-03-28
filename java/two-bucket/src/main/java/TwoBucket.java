import java.nio.Buffer;

class TwoBucket {
    private static final String BUCKET_ONE = "one";
    private static final String BUCKET_TWO = "two";
    private String startBucket;
    private int bucketOneCap;
    private int bucketTwoCap;
    private int bucketOneCurrent;    
    private int bucketTwoCurrent;
    private int desiredLiters;
    private int totalMoves;
    private String finalBucket;
    private int otherBucket;

    TwoBucket(int bucketOneCap, int bucketTwoCap, int desiredLiters, String startBucket) {
        this.bucketOneCap = bucketOneCap;
        this.bucketTwoCap = bucketTwoCap;
        this.desiredLiters = desiredLiters;
        this.startBucket = startBucket;

        this.findSolution();
    }

    void findSolution() {
        String transferFrom = BUCKET_ONE;

        this.fillBucket(this.startBucket);

        int bucketOneAvail;
        int bucketTwoAvail;
        
        while (this.bucketOneCurrent != this.desiredLiters && this.bucketTwoCurrent != this.desiredLiters) {
            bucketOneAvail = this.bucketOneCap - this.bucketOneCurrent;
            bucketTwoAvail = this.bucketTwoCap - this.bucketTwoCurrent;

            // Change the transfer direction if the desiredLiters could be obtained
            switch (this.startBucket) {
                case BUCKET_ONE:
                    if (this.bucketTwoCap - this.bucketOneCap == this.desiredLiters 
                        || this.bucketTwoCurrent - bucketOneAvail == this.desiredLiters
                        || this.bucketTwoCap == this.desiredLiters) {
                            transferFrom = BUCKET_TWO;
                    }
                    break;
                default:
                    if (this.bucketOneCurrent - bucketTwoAvail == this.desiredLiters) {
                        transferFrom = BUCKET_ONE;
                    } else {
                        transferFrom = BUCKET_TWO;
                    }
            }

            switch (transferFrom) {
                case BUCKET_ONE:
                    if (this.bucketOneCurrent == 0) {
                        this.fillBucket(BUCKET_ONE);
                    } else if (this.bucketTwoCurrent == bucketTwoCap) {
                        this.emptyBucket(BUCKET_TWO);
                    } else {
                        this.transferBucket(BUCKET_ONE, bucketOneAvail, bucketTwoAvail);
                    }
                    break;
                default:
                    if (this.bucketTwoCurrent == 0) {
                        this.fillBucket(BUCKET_TWO);
                    } else if (this.bucketOneCurrent == bucketOneCap) {
                        this.emptyBucket(BUCKET_ONE);
                    } else {
                        this.transferBucket(BUCKET_TWO, bucketOneAvail, bucketTwoAvail);
                    }
            } 

            // Escape clause in case there is no solution.
            if (this.totalMoves == 100) {break;}
        }
    }

    void fillBucket(String bucketToFill) {
        switch (bucketToFill) {
            case BUCKET_ONE:
                this.bucketOneCurrent = this.bucketOneCap;
                break;
            case BUCKET_TWO:
                this.bucketTwoCurrent = this.bucketTwoCap;
                break;
            default:
                throw new IllegalArgumentException("fillBucket: Unknown bucket");
        }

        this.totalMoves++;
        this.checkBuckets();
    }

    void emptyBucket(String bucketToEmpty) {
        switch (bucketToEmpty) {
            case BUCKET_ONE:
                this.bucketOneCurrent = 0;
                break;
            case BUCKET_TWO:
                this.bucketTwoCurrent = 0;
                break;
            default:
                throw new IllegalArgumentException("emptyBucket: Unknown bucket");
        }

        this.totalMoves++;
    }

    void transferBucket (String fromBucket, int bucketOneAvail, int bucketTwoAvail) {
        switch (fromBucket) {
            case BUCKET_ONE:
                this.bucketTwoCurrent = 
                    (bucketTwoAvail > this.bucketOneCurrent) ?
                    this.bucketTwoCurrent + this.bucketOneCurrent : this.bucketTwoCap;
                this.bucketOneCurrent =
                    (bucketTwoAvail > this.bucketOneCurrent) ?
                    0 : this.bucketOneCurrent - bucketTwoAvail;
                break;
            case BUCKET_TWO:
                this.bucketOneCurrent = 
                    (bucketOneAvail > this.bucketTwoCurrent) ? 
                    this.bucketOneCurrent + this.bucketTwoCurrent : this.bucketOneCap; 
                this.bucketTwoCurrent = 
                    (bucketOneAvail > this.bucketTwoCurrent) ? 
                    0 : this.bucketTwoCurrent - bucketOneAvail;
                break;
            default:   
                throw new IllegalArgumentException("transferBucket: Unknown bucket");
        }

        this.totalMoves++;
        this.checkBuckets();
    }

    void checkBuckets() {
        if (this.bucketOneCurrent == this.desiredLiters) {
            this.finalBucket = BUCKET_ONE;
            this.otherBucket = this.bucketTwoCurrent;
        } else if (this.bucketTwoCurrent == this.desiredLiters) {
            this.finalBucket = BUCKET_TWO;
            this.otherBucket = this.bucketOneCurrent;
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
