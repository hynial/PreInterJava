import com.hynial.preinter.Sorts;

import com.hynial.preinter.util.ApplicationUtil;
import java.util.stream.IntStream;

public class SortsApplication {
    private static int[] intArr = IntStream.of(3, 2, 1, 4, 5, 8, 6, 7, 0).toArray();

    public static void main(String[] args) {
        Sorts sorts = new Sorts();
        ApplicationUtil.printArray(intArr);
        int[] repIntArr = intArr.clone();
        sorts.quickSort(repIntArr, 0, repIntArr.length - 1);
        ApplicationUtil.printArray(repIntArr);

        int[] simpleArr = intArr.clone();
        sorts.chooseSort(simpleArr, simpleArr.length);
        ApplicationUtil.printArray(simpleArr);
    }
}
