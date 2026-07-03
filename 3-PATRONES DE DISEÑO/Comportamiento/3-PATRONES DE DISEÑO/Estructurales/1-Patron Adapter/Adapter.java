import java.util.HashMap;
import java.util.Map;

// Interfaz objetivo (Target): lo que el resto del sistema espera usar,
// sin conocer los detalles del proveedor externo de almacenamiento.
interface IAlmacenamientoAdapter {
    String subirArchivo(String nombreArchivo, byte[] contenido);
    boolean eliminarArchivo(String urlArchivo);
}

// Adaptado (Adaptee): representa el SDK externo de Cloudinary, con una
// interfaz propia que NO coincide con la que espera IURIS.
class CloudinaryClienteExterno {
    Map<String, byte[]> almacenSimulado = new HashMap<>();

    public Map<String, Object> upload(byte[] data, Map<String, Object> options) {
        String publicId = "iuris/" + options.get("filename") + "_" + System.currentTimeMillis();
        almacenSimulado.put(publicId, data);
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("secure_url", "https://res.cloudinary.com/iuris-bufete/" + publicId);
        resultado.put("public_id", publicId);
        return resultado;
    }

    public Map<String, Object> destroy(String publicId) {
        boolean existia = almacenSimulado.remove(publicId) != null;
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("result", existia ? "ok" : "not found");
        return resultado;
    }
}

// Adaptador (Adapter): traduce la interfaz de Cloudinary a la interfaz
// que DocumentoService (Módulo de Documentos, ver CONTEXTO/03-Modulos.md)
// realmente necesita usar (RN-18, RN-19).
class CloudinaryAdapter implements IAlmacenamientoAdapter {

    private final CloudinaryClienteExterno cloudinary = new CloudinaryClienteExterno();

    @Override
    public String subirArchivo(String nombreArchivo, byte[] contenido) {
        Map<String, Object> options = new HashMap<>();
        options.put("filename", nombreArchivo);

        Map<String, Object> respuesta = cloudinary.upload(contenido, options);
        return (String) respuesta.get("secure_url");
    }

    @Override
    public boolean eliminarArchivo(String urlArchivo) {
        // Traduce la URL almacenada de vuelta al publicId que espera
        // el SDK de Cloudinary.
        String publicId = urlArchivo.substring(urlArchivo.lastIndexOf("/") + 1);
        Map<String, Object> respuesta = cloudinary.destroy(publicId);
        return "ok".equals(respuesta.get("result"));
    }
}

// Cliente: DocumentoService solo conoce IAlmacenamientoAdapter, nunca
// el SDK concreto de Cloudinary. Si mañana se cambia de proveedor
// (p. ej. AWS S3), solo se crea un nuevo Adapter, sin tocar
// DocumentoService.
class DocumentoService {
    private final IAlmacenamientoAdapter almacenamiento;

    public DocumentoService(IAlmacenamientoAdapter almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public String cargarDocumento(String nombreArchivo, byte[] contenido) {
        String url = almacenamiento.subirArchivo(nombreArchivo, contenido);
        System.out.println("[DocumentoService] Documento '" + nombreArchivo + "' cargado en: " + url);
        return url;
    }
}

public class Adapter {
    public static void main(String[] args) {
        IAlmacenamientoAdapter cloudinaryAdapter = new CloudinaryAdapter();
        DocumentoService documentoService = new DocumentoService(cloudinaryAdapter);

        byte[] contenidoSimulado = "contenido-del-pdf".getBytes();
        String url = documentoService.cargarDocumento("contrato-caso-101.pdf", contenidoSimulado);

        boolean eliminado = cloudinaryAdapter.eliminarArchivo(url);
        System.out.println("¿Archivo eliminado? " + eliminado);
    }
}
