class TwoBucket {
    private int totalMoves;
    private String finalBucket;
    private int otherBucket;
    private final String BUCKET_ONE = "one";
    private static final String BUCKET_TWO = "two";

    TwoBucket(int bucketOneCap, int bucketTwoCap, int desiredLiters, String startBucket) {
        int bucketOneCurrent = 0;
        int bucketTwoCurrent = 0;

        // Check if the solution has been found.
        for (int i = 0; i <= 20; i++) {
            if (bucketOneCurrent == desiredLiters){ 
                totalMoves = i;
                finalBucket = BUCKET_ONE;
                otherBucket = bucketTwoCurrent;
                break;
            } else if (bucketTwoCurrent == desiredLiters) {
                totalMoves = i;
                finalBucket = BUCKET_TWO;
                otherBucket = bucketOneCurrent;
                break;
            }

            /* 
                Edge case(s):
                - Other bucket cap is equal to the desireLiters
            */
            if (i == 1) {
                if (startBucket == BUCKET_ONE && bucketTwoCap == desiredLiters) {
                    bucketTwoCurrent = bucketTwoCap;
                    continue;
                } else if (startBucket == BUCKET_TWO && bucketOneCap == desiredLiters) {
                    bucketOneCurrent = bucketOneCap;
                    continue;
                }
            }

            /* 
                Even iterations:
                - fill the start bucket
                    OR
                - empty the other bucket

                Odd iterations:
                - swap contents from start bucket to other bucket
            */ 
            if (i%2 == 0) {
                switch (startBucket){
                    case (BUCKET_ONE):
                        if (bucketTwoCurrent != bucketTwoCap) {
                            bucketOneCurrent = bucketOneCap;
                        } else {
                            bucketTwoCurrent = 0;
                        }
                        break;
                    default:
                        if (bucketOneCurrent != bucketOneCap) {
                            bucketTwoCurrent = bucketTwoCap;
                        } else {
                            bucketOneCurrent = 0;
                        }
                        break;
                }
            } else {
                int availableLiters;
                switch (startBucket) {
                    case (BUCKET_ONE):
                        availableLiters = bucketTwoCap - bucketTwoCurrent;
                        if (bucketOneCurrent <= availableLiters) {
                            bucketTwoCurrent += bucketOneCurrent;
                            bucketOneCurrent = 0;
                        } else {
                            bucketOneCurrent -= availableLiters;
                            bucketTwoCurrent = bucketTwoCap;
                        }
                        break;
                    default:
                        availableLiters = bucketOneCap - bucketOneCurrent;
                        if (bucketTwoCurrent <= availableLiters) {
                            bucketOneCurrent += bucketTwoCurrent;
                            bucketTwoCurrent = 0;
                        } else {
                            bucketTwoCurrent -= availableLiters;
                            bucketOneCurrent = bucketOneCap;
                        }
                        break;
                }
            }
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
