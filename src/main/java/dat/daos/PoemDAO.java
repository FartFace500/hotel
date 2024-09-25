package dat.daos;

import dat.dtos.PoemDTO;
import dat.entities.Poem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PoemDAO {

    private static PoemDAO instance;
    private static EntityManagerFactory emf;

    private PoemDAO() {
        // private constructor
    }

    public static PoemDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            instance = new PoemDAO();
            emf = _emf;
        }
        return instance;
    }

    public List<PoemDTO> getPoems(){
        List<Poem> poemList = new ArrayList<>();
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Poem> query = em.createQuery("SELECT p FROM Poem p", Poem.class);
            return PoemDTO.toDTOList(query.getResultList());
        }
    }

    public PoemDTO create(PoemDTO poemDTO) {
        Poem poem = new Poem(poemDTO);
        try (EntityManager em = emf.createEntityManager()){
          em.getTransaction().begin();
          em.persist(poem);
          em.getTransaction().commit();
        }
        return new PoemDTO(poem);
    }

    public List<PoemDTO> createFromList(PoemDTO[] poemDTOS) {
        List<PoemDTO> poemDTOList = new ArrayList<>();
        for (int index = 0; index < poemDTOS.length ; index++) {
            PoemDTO newPoemDTO = create(poemDTOS[index]);
            poemDTOList.add(newPoemDTO);
        }
        return poemDTOList;
    }
}
