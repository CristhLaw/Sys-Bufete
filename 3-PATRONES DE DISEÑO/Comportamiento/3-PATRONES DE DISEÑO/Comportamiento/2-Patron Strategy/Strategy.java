import java.util.HashMap;
import java.util.Map;

interface IExportadorReporte {
    String exportar(String nombreReporte, Map<String, Object> datos);
}

class ExportadorPDF implements IExportadorReporte {
    @Override
    public String exportar(String nombreReporte, Map<String, Object> datos) {
        return "[PDF] Reporte '" + nombreReporte + "' generado con " + datos.size()
                + " campo(s), formato profesional del bufete.";
    }
}

class ExportadorExcel implements IExportadorReporte {
    @Override
    public String exportar(String nombreReporte, Map<String, Object> datos) {
        return "[EXCEL] Reporte '" + nombreReporte + "' generado con " + datos.size()
                + " campo(s), listo para analisis administrativo.";
    }
}

class ReporteService {
    private IExportadorReporte estrategia;

    public ReporteService(IExportadorReporte estrategiaInicial) {
        this.estrategia = estrategiaInicial;
    }

    public void setEstrategia(IExportadorReporte estrategia) {
        this.estrategia = estrategia;
    }

    public String generarReporte(String nombreReporte, Map<String, Object> datos) {
        return estrategia.exportar(nombreReporte, datos);
    }
}

public class Strategy {
    public static void main(String[] args) {
        Map<String, Object> datos = new HashMap<>();
        datos.put("casosActivos", 42);
        datos.put("abogadoConMasCasos", "Abogado Principal - Juan Perez");

        ReporteService reporteService = new ReporteService(new ExportadorPDF());
        System.out.println(reporteService.generarReporte("Casos por Abogado", datos));

        reporteService.setEstrategia(new ExportadorExcel());
        System.out.println(reporteService.generarReporte("Casos por Abogado", datos));
    }
}
