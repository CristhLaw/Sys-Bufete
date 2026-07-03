public class Main {

    public static void main(String[] args) {
        // Demuestra que Singleton.getInstance() siempre retorna
        // la misma instancia de configuracion global del sistema.
        Singleton configuracion = Singleton.getInstance();
        configuracion.ejecutar();
    }
}
