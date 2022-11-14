package lotto;

import static java.lang.Integer.parseInt;
import static lotto.Simulator.calculateRateOfReturn;

import camp.nextstep.edu.missionutils.Console;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Application {
    private final static String inputPriceMessage = "구입금액을 입력해 주세요.";
    private final static String inputWinningNumbersMessage = "당첨 번호를 입력해 주세요.";
    private final static String inputBonusNumberMessage = "보너스 번호를 입력해 주세요.";
    private final static String priceNonNumericErrorMessage = "[ERROR] 구입금액은 숫자여야 합니다.";
    private final static String bonusNonNumericErrorMessage = "[ERROR] 보너스 번호는 숫자여야 합니다.";

    public static void main(String[] args) {
        int price = readNumber(inputPriceMessage, priceNonNumericErrorMessage);
        Simulator lottoSimulator = new Simulator(price);
        printPurchaseHistory(lottoSimulator);
        Lotto winning = new Lotto(readNumbers(inputWinningNumbersMessage));
        int bonus = readNumber(inputBonusNumberMessage, bonusNonNumericErrorMessage);
        List<Integer> wins = lottoSimulator
                        .setWinning(winning, bonus)
                        .getStatistic();
        printWinningHistory(wins);
        printRateOfReturn(calculateRateOfReturn(wins, price));
    }

    private static int readNumber(String outputMessage, String errorMessage) {
        System.out.println(outputMessage);
        try {
            return parseInt(Console.readLine());
        } catch (Exception e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private static List<Integer> readNumbers(String outputMessage) {
        System.out.println(outputMessage);
        try {
            return Arrays.asList(Console.readLine().split(","))
                    .stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 숫자와 쉼표 형식입니다.");
        }
    }

    private static void printPurchaseHistory(Simulator lottoSimulator) {
        System.out.println(lottoSimulator.getQuantity() + "개를 구매했습니다.");
        lottoSimulator.getLottos()
                .stream()
                .forEach(System.out::println);
    }

    private static void printWinningHistory(List<Integer> wins) {
        System.out.println("당첨 통계");
        System.out.println("---");
        System.out.println("3개 일치 (5,000원) - " + wins.get(4) + "개");
        System.out.println("4개 일치 (50,000원) - " + wins.get(3) + "개");
        System.out.println("5개 일치 (1,500,000원) - " + wins.get(2) + "개");
        System.out.println("5개 일치, 보너스 볼 일치 (30,000,000원) - " + wins.get(1) + "개");
        System.out.println("6개 일치 (2,000,000,000원) - " + wins.get(0) + "개");

    }

    private static void printRateOfReturn(BigDecimal rateOfReturn) {
        DecimalFormat df = new DecimalFormat("#,###.0%");
        System.out.println("총 수익률은 " + df.format(rateOfReturn) + "입니다.");
    }
}
