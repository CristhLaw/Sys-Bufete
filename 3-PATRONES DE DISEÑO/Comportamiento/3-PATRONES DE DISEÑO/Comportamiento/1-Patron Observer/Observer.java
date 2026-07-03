import java.util.ArrayList;
import java.util.List;

interface IObservador {
    void actualizar(String evento, Object origen);
}

interface ISujeto {
    void agregarObservador(IObservador observador);
    void removerObservador(IObservador observador);
    void notificarObservadores(String evento);
}

class CasoLegal implements ISujeto {
    private final List<IObservador> observadores = new ArrayList<>();
    private Long id;
    private String estado;

    public CasoLegal(Long id, String estadoInicial) {
        this.id = id;
        this.estado = estadoInicial;
    }

    @Override
    public void agregarObservador(IObservador observador) {
        observadores.add(observador);
    }

    @Override
    public void removerObservador(IObservador observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificarObservadores(String evento) {
        for (IObservador observador : observadores) {
            observador.actualizar(evento, this);
        }
    }

    public void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
        notificarObservadores("CASO_ACTUALIZADO");
    }

    public Long getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }
}

class NotificacionObserver implements IObservador {
    @Override
    public void actualizar(String evento, Object origen) {
        CasoLegal caso = (CasoLegal) origen;
        System.out.println("[NotificacionService] Evento '" + evento + "' del Caso #" + caso.getId()
                + " (estado: " + caso.getEstado() + "). Enviando notificacion al cliente...");
    }
}

class AuditoriaObserver implements IObservador {
    @Override
    public void actualizar(String evento, Object origen) {
        CasoLegal caso = (CasoLegal) origen;
        System.out.println("[AuditoriaService] Registrando evento '" + evento + "' del Caso #" + caso.getId()
                + " en el log de auditoria.");
    }
}

public class Observer {
    public static void main(String[] args) {
        CasoLegal caso = new CasoLegal(101L, "ACTIVO");

        caso.agregarObservador(new NotificacionObserver());
        caso.agregarObservador(new AuditoriaObserver());

        caso.cambiarEstado("EN_AUDIENCIA");
        caso.cambiarEstado("CERRADO");
    }
}
