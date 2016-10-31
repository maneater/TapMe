package com.maneater.maneater.tapme.core;

import java.util.Collections;
import java.util.List;


public class Helper {


    public static Range<Integer> findRange(int maxRange, int needRange, List<Range<Integer>> targetRanges) {
        Range<Integer> result = null;
        Range<Integer> targetRange = null;
        if (targetRanges != null) {
            for (int i = 0; i < targetRanges.size(); i++) {
                Range<Integer> range = targetRanges.get(i);
                int contains = range.getUpper() - range.getLower();
                // have enough space
                if (contains >= needRange) {
                    targetRange = range;
                    int maxStart = range.getUpper() - needRange;
                    int minStart = range.getLower();
                    int start = (int) (minStart + Math.random() * (maxStart - minStart));
                    result = new Range<>(start, start + needRange);
                    break;
                    // no use
                }
            }
        }

        if (targetRange != null) {
            int rangeStart1 = targetRange.getLower();
            int rangeEnd1 = result.getLower();

            int rangeStart2 = result.getUpper();
            int rangeEnd2 = targetRange.getUpper();
            targetRanges.remove(targetRange);

            if (rangeStart1 != rangeStart2) {
                targetRanges.add(new Range<Integer>(rangeStart1, rangeEnd1));
                targetRanges.add(new Range<Integer>(rangeStart2, rangeEnd2));
            }
        } else {
            int tmpStart = (int) (Math.random() * (maxRange - needRange));
            result = new Range<>(tmpStart, tmpStart + needRange);
        }

        return result;
    }
}
