package br.com.leituramiga.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DataHora {
    private Date valor;

    public DataHora(Date valor) {
        this.valor = valor;
    }

    public static DataHora deString(String data) throws DataInvalida {
        try {
            Date dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(data);
            return new DataHora(dateTime);
        } catch (ParseException e) {
            throw new DataInvalida();
        }
    }

    public String formatar(String formato) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(valor);
    }

    public String formatar() {
        return formatar("dd/MM/yyyy HH:mm");
    }

    public String dataFormatada(String formato) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(valor);
    }

    public String dataFormatada() {
        return dataFormatada("dd/MM/yyyy HH:mm");
    }

    public Date getValor() {
        return valor;
    }

    public static DataHora hoje() {
        return new DataHora(new Date());
    }

    public static DataHora ontem() {
        return new DataHora(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)));
    }

    public void subtrair(int dias) {
        valor = new Date(valor.getTime() - TimeUnit.DAYS.toMillis(dias));
    }

    public void adicionar(int dias) {
        valor = new Date(valor.getTime() + TimeUnit.DAYS.toMillis(dias));
    }

    public static class DataInvalida extends Exception {
        public DataInvalida() {
            super("A data é inválida!");
        }
    }
}
