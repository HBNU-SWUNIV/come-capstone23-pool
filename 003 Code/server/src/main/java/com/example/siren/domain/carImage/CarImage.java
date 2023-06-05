package com.example.siren.domain.carImage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CAR_IMAGE")
public class CarImage {
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
