package util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Formatador {

    public static String moeda(BigDecimal valor) {
        if (valor == null) {
            valor = BigDecimal.ZERO;
        }

        // Utilizando a constante pronta para o Brasil ou Locale.of("pt", "BR")
        NumberFormat formato = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));
        return formato.format(valor);
    }
}