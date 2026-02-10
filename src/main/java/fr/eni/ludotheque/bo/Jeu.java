package fr.eni.ludotheque.bo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Jeux")
public class Jeu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false, length = 50)
    @NonNull
    private String reference;
    @Column(nullable = false, length = 50)
    @NonNull
    private Float ageMini;
    @Column(nullable = false, length = 50)
    @NonNull
    private String description;
    @Column(nullable = false, length = 50)
    @NonNull
    private Float duree;
    @Column(nullable = false, length = 50)
    @NonNull
    private Float tarifJour;
    @Column(nullable = false, length = 50)
    @NonNull
    private String titre;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "jeu_genre",
            joinColumns = @JoinColumn(name = "jeu_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres = new ArrayList<>();

    @OneToMany(mappedBy = "jeu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Exemplaire> exemplaires = new ArrayList<>();

    public void addGenre(Genre genre) {
        genres.add(genre);
    }
}
