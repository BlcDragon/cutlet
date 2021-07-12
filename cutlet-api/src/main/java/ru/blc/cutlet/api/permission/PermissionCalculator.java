package ru.blc.cutlet.api.permission;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.blc.cutlet.api.Cutlet;

import java.util.function.BiFunction;

public class PermissionCalculator {

    private static final Logger logger = LoggerFactory.getLogger("Cutlet");

    /**
     * Вычислитель прав. Для нестандартного вычисления прав.
     */
    private static BiFunction<String, String, Boolean> calculator = null;

    /**
     * Проверяет указаное базовове право разрешает ли оно проверяемое право <br>
     * Все исключающие права (права с "-" в начале) обрабатываются как обычные<br>
     * таким образом вызовы:<br>
     * <pre>
     *     isPermissionAllows("permission.example", "permission.example");
     *     isPermissionAllows("-permission.example", "permission.example");
     * </pre>
     * вернут одинаковый результат (в данном случае true)<br>
     * Поддерживает суперправа (*).<br>
     * <ul>
     *      <li>
     *          Суперправо в базовом праве разрешит любое право для проверяемого.<br>
     *          т.е при базовом праве permission.base.* будет разрешено любое право начинающееся с "permission.base."
     *      </li>
     *      <li>
     *          Суперправо в проверямом методе потребует наличие любого права в базовом<br>
     *          т.е. при провреямом праве permission.*.example это право будет разрешено при базовом праве permission.любойтекст.example
     *      </li>
     * </ul>
     *
     * <ul>
     *     <li>
     *         Суперправо и похожее право ведут себя по разному, т.е.
     *         permission.example.* и permission.example - разрешают совершенно разное.<br>
     *         permission.example.* разрешит все права начинающиеся на permission.example. (т.е. permission.example.anything),
     *         но не разрешит право permission.example<br>
     *         permission.example наоборот, разрешит только такое право и ничего больше
     *     </li>
     * </ul>
     * Список результатов для метода при конкретных входных параметрах:
     *
     * <table cellpadding="2" border="0">
     *   <col width="45%"/>
     *   <col width="45%"/>
     *   <col width="10%"/>
     *   <thead>
     *     <tr><th align="center">base</th><th align="center">check</th><th align="center">result</th></tr>
     *   <thead>
     *   <tbody border = "1px">
     *      <tr>
     *          <td>example.test</td> <td>example.test</td> <td>true</td>
     *      </tr>
     *      <tr>
     *          <td>example.*</td> <td>example.test</td> <td>true</td>
     *      </tr>
     *      <tr>
     *          <td>example.*</td> <td>example.test.permission</td> <td>true</td>
     *      </tr>
     *      <tr>
     *          <td>example.*</td> <td>example</td> <td>false</td>
     *      </tr>
     *      <tr>
     *          <td>example.test</td> <td>example.*</td> <td>true</td>
     *      </tr>
     *      <tr>
     *          <td>example.*.permission</td> <td>example.test.permission</td> <td>true</td>
     *      </tr>
     *      <tr>
     *          <td>example.test.permission</td> <td>example.*.permission</td> <td>true</td>
     *      </tr>
     *      <tr>
     *          <td>example.test.permission</td> <td>example.*</td> <td>false</td>
     *      </tr>
     *   </tbody>
     * </table><br>
     * Если хотябы какое-то право - null, результат всегда false
     * <ul>
     *     <li>
     *     Документация выше приведена для стандартного механизма обработки прав.
     *     Этот механизм может быть изменен при помощи метода {@link PermissionCalculator#setCalculator(BiFunction)}
     *     </li>
     * </ul>
     * @param base базовое право
     * @param toCheck право на проверку. Если оно пустое результат всегда true (за исключением null)
     * @return true, если указанное базовое право разрешает проверяемое право
     */
    public static boolean isPermissionAllows(String base, String toCheck){
        if (getCalculator()!=null){
            try{
                return getCalculator().apply(base, toCheck);
            }catch (Exception ex){
               logger.error("Failed permission calculating by custom calculator. Calculating by default calculator. Exception was:", ex);
            }
        }
        return basePermissionsCheck(base, toCheck);
    }

    /**
     * Выполняет вычисление прав по стандартному алгоритму, игнорируя установленнный вычислитель
     * @param base базовое право
     * @param toCheck право на проверку. Если оно пустое результат всегда true (за исключением null)
     * @return true, если указанное базовое право разрешает проверяемое право
     * @see PermissionCalculator#isPermissionAllows(String, String)
     */
    public static boolean basePermissionsCheck(String base, String toCheck){
        if (Cutlet.instance()!=null)Cutlet.instance().getLogger().debug("Calculating permission {} from base {}", toCheck, base);
        if (base==null) return false;
        if (toCheck==null) return false;
        if (toCheck.isEmpty()) return true;
        if (toCheck.equalsIgnoreCase(base)) return true;
        base = base.replaceAll("^-+", "");
        toCheck = toCheck.replaceAll("^-+", "");
        String[] baseS = base.split("\\."), toCheckS = toCheck.split("\\.");
        boolean sectionCompleted = true;
        for (int i = 0; sectionCompleted && i<Math.min(baseS.length, toCheckS.length); i++){
            String baseSection = baseS[i];
            String checkSection = toCheckS[i];
            sectionCompleted = baseSection.equalsIgnoreCase("*")
                    || checkSection.equalsIgnoreCase("*")
                    || baseSection.equalsIgnoreCase(checkSection);
        }
        if (sectionCompleted && baseS.length!= toCheckS.length){
            return baseS.length < toCheckS.length && baseS[baseS.length-1].equalsIgnoreCase("*");
        }
        return sectionCompleted;
    }

    /**
     * Устанавливает кастомный способ вычисления прав
     * @param calculator вычислитель прав. Может быть null для возврата к стандартному
     */
    public static void setCalculator(@Nullable BiFunction<String, String, Boolean> calculator) {
        if (calculator!=null)logger.warn("Permissions calculator changed. Permission system can be unstable or works wrong!");
        PermissionCalculator.calculator = calculator;
    }

    /**
     * @return Текущий вычислитель прав. Может быть null
     */
    @Nullable public static BiFunction<String, String, Boolean> getCalculator() {
        return calculator;
    }
}
