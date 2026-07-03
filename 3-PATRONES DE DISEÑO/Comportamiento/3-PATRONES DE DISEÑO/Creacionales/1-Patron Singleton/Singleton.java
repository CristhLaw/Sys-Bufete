public class Singleton {

    private static volatile Singleton instancia;

    // Datos de configuracion global del sistema (equivalente a la
    // entidad ConfiguracionSistema del modelo de datos de IURIS:
    // parametros de sede por defecto, limites de negocio como el
    // maximo de 20 casos activos por abogado (RN-11), plazos minimos
    // de agenda (RN-16), etc.)
    private String nombreBufete;
    private int limiteCasosPorAbogado;

    private Singleton() {
        // Constructor privado: evita instanciacion externa. Se carga
        // una unica vez desde la configuracion persistida al arrancar
        // la aplicacion.
        this.nombreBufete = "Mini Bufete de Abogados";
        this.limiteCasosPorAbogado = 20;
    }

    public static Singleton getInstance() {
        if (instancia == null) {
            synchronized (Singleton.class) {
                if (instancia == null) {
                    instancia = new Singleton();
                }
            }
        }
        return instancia;
    }

    public String getNombreBufete() {
        return nombreBufete;
    }

    public int getLimiteCasosPorAbogado() {
        return limiteCasosPorAbogado;
    }

    public void ejecutar() {
        System.out.println("Configuracion activa: " + nombreBufete
                + " | limite de casos por abogado: " + limiteCasosPorAbogado);
    }

    public static void main(String[] args) {
        Singleton config1 = Singleton.getInstance();
        Singleton config2 = Singleton.getInstance();

        config1.ejecutar();

        // Ambas referencias apuntan a la misma instancia en memoria.
        System.out.println("¿config1 == config2? " + (config1 == config2));
    }
}
