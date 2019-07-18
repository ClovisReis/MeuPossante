package lucas.com.meupossante.VO;

/**
 * Created by Lucas on 17/05/2016.
 */
public class ItemVO {
    private Long id;
    private String name;
    private Float frequencia;
    private Float ultima_troca;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public Float getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Float frequencia) {
        this.frequencia = frequencia;
    }

    public Float getUltima_troca() {
        return ultima_troca;
    }

    public void setUltima_troca(Float ultima_troca) {
        this.ultima_troca = ultima_troca;
    }
}
