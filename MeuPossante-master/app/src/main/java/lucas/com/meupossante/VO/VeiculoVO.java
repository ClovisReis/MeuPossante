package lucas.com.meupossante.VO;

/**
 * Created by Lucas on 17/05/2016.
 */
public class VeiculoVO {
    private Long id;
    private String name;
    private Float quilometragem;
    private Float ultima_revisao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Float quilometragem) {
        this.quilometragem = quilometragem;
    }

    public Float getUltima_revisao() {
        return ultima_revisao;
    }

    public void setUltima_revisao(Float ultima_revisao) { this.ultima_revisao = ultima_revisao; }
}
