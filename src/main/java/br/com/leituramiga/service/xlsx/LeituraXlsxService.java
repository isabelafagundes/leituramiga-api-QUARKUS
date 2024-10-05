package br.com.leituramiga.service.xlsx;

import br.com.leituramiga.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@ApplicationScoped
public class LeituraXlsxService {

    @Inject
    LogService logService;

    public Map<String, List<String>> obterDadosPlanilha(InputStream inputStream) throws IOException {
        Map<String, List<String>> celulasPorLinha = new HashMap<>();

        try (Workbook planilha = new XSSFWorkbook(inputStream)) {
            logService.iniciar(LeituraXlsxService.class.getName(), "Lendo planilha com " + planilha.getNumberOfSheets() + " abas");
            Sheet abaPlanilha = planilha.getSheetAt(0);

            for (Row row : abaPlanilha) {
                List<String> conteudoLinha = new ArrayList<>();
                for (Cell cell : row) conteudoLinha.add(obterTextoCelula(cell));
                celulasPorLinha.put(String.valueOf(row.getRowNum()), conteudoLinha);
            }
            logService.sucesso(LeituraXlsxService.class.getName(), "Planilha lida com sucesso com " + celulasPorLinha.size() + " linhas");

        } catch (Exception erro) {
            logService.erro(LeituraXlsxService.class.getName(), "Erro ao ler planilha", erro);
            throw erro;
        }

        return celulasPorLinha;
    }

    private String obterTextoCelula(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case BLANK -> "";
            default -> "Tipo desconhecido";
        };
    }

}
