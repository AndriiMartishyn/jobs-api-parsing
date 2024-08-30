package com.martishyn.jobsapi.domain.dmo;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "jobs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JobDataDmo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slug_name")
    private String slugName;

    @Column(name = "company_name")
    private String companyName;

    private String title;

    @Column(length = 10485760)
    private String description;

    private String remote;

    private String url;

    private String tags;

    private String jobTypes;

    private String location;

    private long createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobDataDmo that = (JobDataDmo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
