package com.example.siren.domain.file;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Board {
    @Id
    private long id;

    @NotNull
    @Column(name = "BOARDIDX")
    private long boardIdx;

    @NotNull
    @Column(name = "ORIGINAL_FILE_NAME")
    private String originalFileName;

    @NotNull
    @Column(name = "STORED_FILE_NAME")
    private String storedFileName;
    @Column(name = "FILESIZE")
    private long fileSize;
}
